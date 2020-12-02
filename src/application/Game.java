package application;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Game{
    private  AnchorPane gamePane;
    private  Scene gameScene;
    private  Stage gameStage;
    
    private  PauseMenu pauseMenuManager;
    private  CollideMenu collideMenuManager;
    
    private Ball ball;
    
    private List<Obstacle> listOfObstacles;
    private List<ColorSwitch> listOfColorSwitchers;
    private List<Star> listOfStars;
    
    private  Label pointsLabel;
    
    private Random random;
    
    private int currentScore;
    
    private double currentPositionY;
    private double currentVelocityY;
    private double gravity;
    
    private  Timeline ballTimeline;
    
    private boolean firstSpace;
    private Obstacle topmost;

    public Game() {
    	initStage();
    	initConstants();
    	initGameElements();
        createBackground();
        createKeyListener();
        setupBallTimeline();
        
        ballTimeline.play();
    }
    
    private void initConstants() {
    	pauseMenuManager = new PauseMenu(this);
    	collideMenuManager = new CollideMenu(this);
    	currentScore = 0;
    	gravity = 0d;
    	firstSpace = false;
    	random = new Random();
    	listOfObstacles = new ArrayList<>();
    	listOfColorSwitchers = new ArrayList<>();
    	listOfStars = new ArrayList<>();
    	pointsLabel = new Label("# 00");
    }
    
    private void initStage() {
        this.gamePane = new AnchorPane();
        this.gameScene = new Scene(gamePane, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }
    
    private void initGameElements() {
    	ball = new Ball(gamePane);
    	
    	currentPositionY = ball.getPositionY();
    	currentVelocityY = ball.getVelocityY();
    	
    	pointsLabel.setLayoutX(10);
    	pointsLabel.setLayoutY(10);
    	pointsLabel.setFont(Font.font("arial", FontWeight.BOLD, 50));
    	pointsLabel.setTextFill(Color.AZURE);
    	gamePane.getChildren().add(pointsLabel);
    	
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
    	
    	// add game elements to the pane
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
    
    private Obstacle generateObstacleRandomly(double y) {
    	if(currentScore < 2) {
    	    return new CircleObstacle(y);
    	} else if(currentScore < 4) {
    	    return new SquareObstacle(y);
    	} else if(currentScore < 6) {
    	    return new DoubleCircleObstacle(y);
    	} else if(currentScore < 8) {
    	    return new ConcentricCircleObstacle(y);
    	} else {
    	    int n = random.nextInt(Constants.NUMBER_OF_OBSTACLES);
    	    if(n == 0) {
    	        return new CircleObstacle(y);
    	    } else if (n == 1) {
    	        return new SquareObstacle(y);
    	    } else if (n == 2) {
    	        return new DoubleCircleObstacle(y);
    	    } else if (n == 3) {
    	        return new ConcentricCircleObstacle(y);
    	    } else {
    	        // should never reach this state
    	        System.out.println("logical error.");
    	        return new CircleObstacle(y);
    	    }
    	}
    }

    private void setupBallTimeline() {
    	KeyFrame kf = new KeyFrame(Duration.millis(Constants.UPDATE_PERIOD), new BallTimeHandler());
    	ballTimeline = new Timeline(kf);
    	ballTimeline.setCycleCount(Animation.INDEFINITE);
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
    		increaseDifficulty();
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
    			    if(current instanceof Arc && block.getFill().equals(current.getStroke())) {
    			        // this is ulta logic . reverse it later XXX
    			        collisionDetected = true;
    			        break;
    			    } else if(current instanceof Rectangle && block.getFill().equals(current.getFill())) {
    			        // this is ulta logic, reverse XXX
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
    
    private double calculateDistance(double y1, double y2) {
    	return Math.abs(y1 - y2);
    	
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
    	String text = "# ";
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
    
    private void increaseDifficulty() {
       for(Obstacle o : listOfObstacles) {
           o.increaseDifficulty((int)currentScore);
       }
    }

    public void createNewGame() {
        //this.menuStage = menuStage;
        gameStage.initModality(Modality.APPLICATION_MODAL);
        gameStage.setTitle("Color Switch");
        gameStage.show();
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
                	ballTimeline.pause();
                	gamePane.setFocusTraversable(false);
                	pauseMenuManager.showPauseMenu();
                	
                }
            }
        });
    }
    
    public void resumeGame() {
    	firstSpace = false;
    	currentVelocityY = 0;
    	gravity = 0;
    	ballTimeline.play();
    }
    

    private void createBackground() {
        Image backgroundImage = new Image("resources/deep_blue.png", 256, 256, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(background));
    }
    
    public void closeStage() {
    	gameStage.close();
    }
}

