package application;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game implements Serializable {
    
    private transient AnchorPane gamePane;
    private transient Scene gameScene;
    private transient Stage gameStage;

    private transient PauseMenu pauseMenuManager;
    private transient CollideMenu collideMenuManager;
    
    private Ball ball;
    
    private List<Obstacle> listOfObstacles;
    private List<ColorSwitch> listOfColorSwitchers;
    private List<Star> listOfStars;
    
    private transient Label pointsLabel;
    
    private transient Random random;
    
    private int currentScore;
    
    private double currentPositionY;
    private double currentVelocityY;
    private double gravity;
    
    private transient Timeline ballTimeline;
    
    private boolean firstSpace;
    private Obstacle topmost;

    public Game() {
    	initStage();
        initInGameMenus();
        initRandom();
        initPointsLabel();
    	initConstants();
        initBackground();
        setupBallTimeline();
        createKeyListener();
        initGameElements();
    }

    private void initStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    private void initInGameMenus() {
        pauseMenuManager = new PauseMenu(this);
        collideMenuManager = new CollideMenu(this);
    }

    private void initRandom() {
        random = new Random();
    }
    
    private void initPointsLabel() {
        pointsLabel = new Label("* 00");
        pointsLabel.setLayoutX(10);
        pointsLabel.setLayoutY(10);
        pointsLabel.setFont(Font.font("arial", FontWeight.BOLD, 50));
        pointsLabel.setTextFill(Color.AZURE);
        gamePane.getChildren().add(pointsLabel);
    }

    private void initConstants() {
    	currentScore = 0;
    	gravity = 0d;
    	firstSpace = false;
    	listOfObstacles = new ArrayList<>();
    	listOfColorSwitchers = new ArrayList<>();
    	listOfStars = new ArrayList<>();
    }
    
    private void initBackground() {
        Image backgroundImage = new Image(Constants.MAIN_MENU_BACKGROUND_PATH, 256, 256, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(background));
    }

    
    private void initGameElements() {
    	ball = new Ball(gamePane);
    	currentPositionY = ball.getPositionY();
    	currentVelocityY = ball.getVelocityY();
    	generateInitialObstaclesAndCollectables();
        ballTimeline.play();
    }

    private void generateInitialObstaclesAndCollectables() {
        // generate initial obstacles, stars and color switchers
        topmost = generateObstacleRandomly(Constants.GAME_HEIGHT/2);
        double current = topmost.getPositionY();
        listOfObstacles.add(topmost);
        listOfColorSwitchers.add(new ColorSwitch(current  - Constants.DISTANCE_BETWEEN_OBSTACLES/2));
        listOfStars.add(new Star(current));
        while(topmost.getPositionY() > 0) {
            current = current - Constants.DISTANCE_BETWEEN_OBSTACLES;
            topmost = generateObstacleRandomly(current);
            listOfObstacles.add(topmost);
            listOfColorSwitchers.add(new ColorSwitch(current  - Constants.DISTANCE_BETWEEN_OBSTACLES/2));
            listOfStars.add(new Star(current));
        }
        addElementsToGamePane();
    }

    private void addElementsToGamePane() {
        // add game elements to the pane
        // XXX add a NULL POINTER EXCEPTION HERE
        for(Obstacle o : listOfObstacles) {
            o.addElementsToGamePane(gamePane);
        }
        for(ColorSwitch c : listOfColorSwitchers) {
            c.addElementsToGamePane(gamePane);
        }
        for(Star s : listOfStars) {
            s.addElementsToGamePane(gamePane);
        }
    }

    private void setupBallTimeline() {
        KeyFrame kf = new KeyFrame(Duration.millis(Constants.UPDATE_PERIOD), new BallTimeHandler());
        ballTimeline = new Timeline(kf);
        ballTimeline.setCycleCount(Animation.INDEFINITE);
    }

    private void createKeyListener() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if( keyEvent.getCode() == KeyCode.SPACE) {
                    System.out.println("Space bar pressed");
                    if(firstSpace == false) {
                        firstSpace = true;
                        gravity = Constants.GRAVITY;
                    }
                    currentVelocityY = -20;
                }
                
                if(keyEvent.getCode() == KeyCode.P) {
                    System.out.println("P key pressed");
                    // XXX null pointer exception. ballTimeline and pauseMenuManager may not be init
                    ballTimeline.pause();
                    pauseMenuManager.showPauseMenu();
                }
            }
        });
    }

    // ---

    private double calculateDistance(double y1, double y2) {
        return Math.abs(y1 - y2);
        
    }

    public void createNewGame() {
        // this method can be called by PAUSE MENU || COLLISION MENU || DESERIALISE
        // XXX null pointer exception for gameStage
        gameStage.initModality(Modality.APPLICATION_MODAL);
        gameStage.setTitle("Color Switch");
        gameStage.show();
        ballTimeline.play();
    }
    
    public void startGame() {
        // xxx null pointer excepion
        gameStage.show();
        ballTimeline.play();
    }

    // --- 

    
    private Obstacle generateObstacleRandomly(double y) {
    	if(currentScore < 2) {
    	    return new CircleObstacle(y);
    	} else if(currentScore < 4) {
    	    return new SquareObstacle(y);
    	} else if(currentScore < 6) {
    	    return new TripleConcentricCircleObstacle(y);
    	} else if(currentScore < 8) {
    	    return new ConcentricCircleObstacle(y);
    	} else {
    	    int n = random.nextInt(Constants.NUMBER_OF_OBSTACLES);
    	    if(n == 0) {
    	        return new CircleObstacle(y);
    	    } else if (n == 1) {
    	        return new SquareObstacle(y);
    	    } else if (n == 2) {
    	        return new TripleConcentricCircleObstacle(y);
    	    } else if (n == 3) {
    	        return new ConcentricCircleObstacle(y);
    	    } else {
    	        // should never reach this state
    	        System.out.println("logical error.");
    	        return new CircleObstacle(y);
    	    }
    	}
    }



    private class BallTimeHandler implements EventHandler<ActionEvent> {
    	public void handle(ActionEvent event) {
    		for(Obstacle obstacle : listOfObstacles) {
    			obstacle.rotate();
    		}
    		// update position of ball
    		double updatedVelocityY = currentVelocityY + gravity;
    		double updatedPositionY = currentPositionY + updatedVelocityY;
    		double dy = 0;
    		if(updatedPositionY < Constants.GAME_HEIGHT/2) {
    		    // if the ball tries to jump above game height, stop it from doing so
    			dy = Constants.GAME_HEIGHT/2 - updatedPositionY;
    			currentPositionY = Constants.GAME_HEIGHT/2;
    		} else {
    			currentPositionY = updatedPositionY;
    		} 
    		currentVelocityY = updatedVelocityY;
    		ball.setVelocity(currentVelocityY);
    		ball.setPositionY(currentPositionY);
    		// update position of non-ball game elements
    		lowerObstaclesAndCollectables(dy);
    		// check if ball below screen
    		if(currentPositionY > Constants.GAME_HEIGHT) {
    			gameOver();
    		    //obstacleCollision();
    		}
    		checkShapeIntersection(ball.getCircle());
    		// generate new obstacles and collectables
    		generateNewObstacleAndCollectables();
    		// remove old obstacles and collectables( there wont be any collectables since we consume them)
    		removeOffScreenObstacles();
    	}
    }
    
    private void obstacleCollision() {
        // XXX this is redundant
        gameOver();
    }
    
    
    private void gameOver() {
        currentPositionY -= 75;
        currentPositionY -= 80;
        currentVelocityY = 0;
        gravity = 0;
        firstSpace = false;
        ballTimeline.stop();
        checkForRevival();
        
    }
    
    private void lowerObstaclesAndCollectables(double delta) {
    	for(Obstacle o : listOfObstacles) {
    		o.setPositionY(o.getPositionY() + delta);
    	}
    	for(ColorSwitch c : listOfColorSwitchers) {
    		c.setPositionY(c.getPositionY() + delta);
    	}
    	for(Star s : listOfStars) {
    		s.setPositionY(s.getPositionY() + delta);
    	}
    }
    
    private void checkShapeIntersection(Shape block) {
    	boolean collisionDetected = false;
    	// check for obstacles
    	for (Obstacle obstacle : listOfObstacles) {
    		for(Shape current : obstacle.getElements()) {
    			Shape intersect = Shape.intersect(block, current);
    			if(intersect.getBoundsInLocal().getWidth() != -1) {
    			    if(current instanceof Arc && !(block.getFill().equals(current.getStroke()))) {
    			        
    			        collisionDetected = true;
    			        break;
    			    } else if(current instanceof Rectangle && !(block.getFill().equals(current.getFill()))) {
    			        
    			        collisionDetected = true;
    			        break;
    			    }
    			}
    		}
    		if(collisionDetected) {
    			obstacleCollision();
    			break;
    		}
    	}
    	
    	collisionDetected = false;
    	Iterator<ColorSwitch> iter = listOfColorSwitchers.iterator();
    	while(iter.hasNext()) {
    		ColorSwitch c = iter.next();
    		// check for ColorSwitch
    		for(Shape arc : c.getElements()) {
    			Shape intersect = Shape.intersect(block, arc);
    			if(intersect.getBoundsInLocal().getWidth() != -1) {
    				collisionDetected = true;
    				break;
    			}
    		}
    		if(collisionDetected) {
    			System.out.println("change color");
    			this.ball.changeColor();
    			// XXX uncomment this for removing element
    			c.removeFromPane(gamePane);
    			iter.remove();
    			break;
    		}
    	}
		// check for star
    	Iterator<Star> iterstar = listOfStars.iterator();
    	while(iterstar.hasNext()) {
    		Star s = iterstar.next();
    		if(Constants.BALL_RADIUS + Constants.STAR_RADIUS > calculateDistance(ball.getPositionY(), s.getPositionY())) {
    			currentScore++;
    			updateScoreLabel();
    			// remove this star graphically and logically
    			iterstar.remove();
    			s.removeFromPane(gamePane); 
    		}
    	}
    }
    

    

    private void checkForRevival() {
    	if(currentScore > Constants.THRESHOLD_SCORE_REVIVAL) {
    	    showRevivePopup();

    		resumeGame();
    	} else {
    	    showRetryPopup();
    	}
    }
    
    public void updateScoreOnRevival() {
      currentScore -= Constants.THRESHOLD_SCORE_REVIVAL;
      updateScoreLabel();
    }
    
    private void showRevivePopup() {
        // show options :
        // resume for 2 gems
        // retry from scratch
        // back to main menu
        // exit game
        collideMenuManager.reviveOption();
    }
    
    private void showRetryPopup() {
        // show options
        // retry 
        // main menu
        // exit game
        collideMenuManager.restartOption();
    }
    
    private void updateScoreLabel() {
    	String text = "* ";
		if(currentScore < 10) {
			text = text + "0";
		}
		text = text + currentScore;
		pointsLabel.setText(text);
    }
    
    private void generateNewObstacleAndCollectables() {
    	double topY = topmost.getPositionY();
    	if(topY > Constants.HEIGHT_AFTER_WHICH_OBSTACLE_GENERATE) {
    		// generate obstacle
    		topmost = generateObstacleRandomly(topY - Constants.DISTANCE_BETWEEN_OBSTACLES);
    		listOfObstacles.add(topmost);
    		topmost.addElementsToGamePane(gamePane);
    		topY = topmost.getPositionY();
    		// corresponding to this obstacle, generate a color switch and star
    		ColorSwitch c1 = new ColorSwitch(topY - Constants.DISTANCE_BETWEEN_OBSTACLES/2);
    		listOfColorSwitchers.add(c1);
    		c1.addElementsToGamePane(gamePane);
    		// corresponding to this , generate a star
    		Star s1 = new Star(topY);
    		listOfStars.add(s1);
    		s1.addElementsToGamePane(gamePane);
    	}
    }
 
    private void removeOffScreenObstacles() {
    	// remove the obstacle/collectable graphically and logically
    	// obstacle
    	Iterator<Obstacle> iterobstacle = listOfObstacles.iterator();
    	while(iterobstacle.hasNext()) {
    		Obstacle current = iterobstacle.next();
    		if(current.getPositionY() > Constants.GAME_HEIGHT) {
    			iterobstacle.remove();
    			current.removeFromPane(gamePane);
    		}
    	}
    	// remove offscreen stars
    	Iterator<Star> iterStar = listOfStars.iterator();
    	while(iterStar.hasNext()) {
    	    Star current = iterStar.next();
    	    if(current.getPositionY() > Constants.GAME_HEIGHT) {
    	        iterStar.remove();
    	        current.removeFromPane(gamePane);
    	    }
    	}
    	// remove offscreen color switch
    	Iterator<ColorSwitch> iterSwitch = listOfColorSwitchers.iterator();
    	while(iterSwitch.hasNext()) {
    	    ColorSwitch current = iterSwitch.next();
    	    if(current.getPositionY() > Constants.GAME_HEIGHT) {
    	        iterSwitch.remove();
    	        current.removeFromPane(gamePane);
    	    }
    	}
    }

    
    public void resumeGame() {
    	firstSpace = false;
    	currentVelocityY = 0;
    	gravity = 0;
    	ballTimeline.play();
    }

    
    public void closeStage() {
    	gameStage.close();
    }
    
    public void serialize() {
        try {
            Timestamp time = new Timestamp(System.currentTimeMillis());
            String fileName = Constants.FILE_START_STRING + time.getTime() + ".ser";
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
            os.writeObject(ball);
            os.writeObject(listOfColorSwitchers);
            os.writeObject(listOfStars);
            os.writeObject(listOfObstacles);
            os.writeObject(currentScore);
            os.writeObject(currentPositionY);
            os.writeObject(currentVelocityY);
            os.writeObject(topmost);
        } catch(IOException e) {
            System.out.println("serialize()");
            e.printStackTrace();
        }
        ball = null;
        listOfColorSwitchers = null;
        listOfStars = null;
        listOfObstacles = null;
        topmost = null;
        ballTimeline = null;
    }
    
    public void deserialise(String fileName) {
        try {
            // read values from file
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
            ball = (Ball) is.readObject();
            listOfColorSwitchers = (ArrayList<ColorSwitch>) is.readObject();
            listOfStars = (ArrayList<Star>) is.readObject();
            listOfObstacles = (ArrayList<Obstacle>) is.readObject();
            currentScore = (Integer) is.readObject();
            currentPositionY = (Double) is.readObject();
            currentVelocityY = (Double) is.readObject();
            topmost = (Obstacle) is.readObject();
            // re initialise all the transient variables.
            // since they have been init to null
            initStage();
            initInGameMenus();
            initRandom();
            initPointsLabel();
            initBackground();
            createKeyListener();
            ball.reinitialise(gamePane);
            reinitialiseObstaclesAndCollectables(); // XXX
            updateScoreLabel(); 
            gameStage.show();
            setupBallTimeline(); // XXX 
            resumeGame();
            ballTimeline.play();
            
        } catch(Exception e) {
            System.out.println("deserialise()");
            e.printStackTrace();
        }
    }
    
    private void reinitialiseObstaclesAndCollectables() {
        for(Obstacle o : listOfObstacles) {
            o.reinitialise();
        }
        for(ColorSwitch c : listOfColorSwitchers) {
            c.reinitialise();
        }
        for(Star s : listOfStars) {
            s.reinitialise();
        }
        addElementsToGamePane();
    }
}

