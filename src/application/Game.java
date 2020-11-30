package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.sun.javafx.collections.MappingChange.Map;

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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Game {    
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    
    private Ball ball;
    
    private List<Obstacle> listOfObstacles;
    private List<ColorSwitch> listOfColorSwitchers;
    private List<Star> listOfStars;
    
    private Label pointsLabel;
    
    private Random random;
    
    private int currentScore;
    
    private int currentPositionY;
    private int currentVelocityY;
    private int gravity;
    
    private Timeline ballTimeline;
    private int numObstacles;
    
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
    	currentScore = 0;
    	gravity = 0;
    	firstSpace = false;
    	random = new Random();
    	listOfObstacles = new ArrayList<>();
    	listOfColorSwitchers = new ArrayList<>();
    	listOfStars = new ArrayList<>();
    	pointsLabel = new Label("POINTS: 00");
    	numObstacles = 1;
		Constants.map.put(0,Color.AQUA);
		Constants.map.put(1,Color.HOTPINK);
		Constants.map.put(2,Color.YELLOW);
		Constants.map.put(3,Color.INDIGO);
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
    	pointsLabel.setFont(Font.font("arial", FontWeight.BOLD, 40));
    	pointsLabel.setTextFill(Color.ALICEBLUE);
    	gamePane.getChildren().add(pointsLabel);
    	
    	topmost = generateObstacleRandomly(Constants.GAME_HEIGHT/2);
    	int current = topmost.getPositionY();
    	listOfObstacles.add(topmost);
    	listOfColorSwitchers.add(new ColorSwitch(current));
    	listOfStars.add(new Star(current - Constants.DISTANCE_BETWEEN_OBSTACLES/2));
    	while(topmost.getPositionY() >= 0) {
    		current = current - Constants.DISTANCE_BETWEEN_OBSTACLES;
    		topmost = generateObstacleRandomly(current);
    		listOfObstacles.add(topmost);
    		listOfColorSwitchers.add(new ColorSwitch(current));
    		listOfStars.add(new Star(current - Constants.DISTANCE_BETWEEN_OBSTACLES/2));
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
    
    private Obstacle generateObstacleRandomly(int y) {
    	int n = random.nextInt(numObstacles);
    	// generate random obstacles and return reference using a switch case block
    	Obstacle obstacle = new CircleObstacle(y);
    	return obstacle;
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
    		int updatedVelocityY = currentVelocityY + gravity;
    		int updatedPositionY = currentPositionY + updatedVelocityY;
    		int dy = 0;
    		if(updatedPositionY < Constants.GAME_HEIGHT/2) {
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
    			System.out.println("GAME OVER");
    			ballTimeline.pause();
    		}
    		//XXX check for collision
    		checkShapeIntersection(ball.getCircle());
    		// generate new obstacles and collectables
    		generateNewObstacleAndCollectables();
    		// remove old obstacles and collectables( there wont be any collectables since we consume them)
    		removeOffScreenObstacles();
    	}
    }
    
    private void lowerObstaclesAndCollectables(int delta) {
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
    		for(Shape arc : obstacle.getElements()) {
    			Shape intersect = Shape.intersect(block, arc);
    			if(intersect.getBoundsInLocal().getWidth() != -1) {
    				if(block.getFill().equals(arc.getStroke())) {
    					// nothing
    				} else {
    					// this is the correct logic
    					// the ball passes through the same color only
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
    	
    	// assert collisionDetected = false
    	
    	// check for Collectables
    	collisionDetected = false;
    	Iterator<ColorSwitch> iter = listOfColorSwitchers.iterator();
    	while(iter.hasNext()) {
    		ColorSwitch c = iter.next();
    		// XXX try the brown approach of check intersection with bounds
    		// otherwise use case by case method
    		
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
    			String text = "POINTS: ";
    			if(currentScore < 10) {
    				text = text + "0";
    			}
    			text = text + currentScore;
    			pointsLabel.setText(text);
    			// remove this star graphically and logically
    			iterstar.remove();
    			s.removeFromPane(gamePane); // XXXkchecl
    		}
    	}
    }
    
    private int calculateDistance(int y1, int y2) {
    	return Math.abs(y1 - y2);
    	
    }
    
    private void obstacleCollision() {
    	// helper method
    	System.out.println("Collision");
		currentPositionY -= 80;
		currentVelocityY = 0;
		gravity = 0;
		firstSpace = false;
		ballTimeline.stop();
		gameStage.close();
		System.out.println(currentScore);
		// add revive option
		//ballTimeline.pause();
    }
    
    private void generateNewObstacleAndCollectables() {
    	int topY = topmost.getPositionY();
    	if(topY > Constants.HEIGHT_AFTER_WHICH_OBSTACLE_GENERATE) {
    		// generate obstacle
    		topmost = generateObstacleRandomly(topY - Constants.DISTANCE_BETWEEN_OBSTACLES);
    		listOfObstacles.add(topmost);
    		topmost.addElementsToGamePane(gamePane);
    		topY = topmost.getPositionY();
    		// corresponding to this obstacle, generate a color switch and star
    		ColorSwitch c1 = new ColorSwitch(topY);
    		listOfColorSwitchers.add(c1);
    		c1.addElementsToGamePane(gamePane);
    		// corresponding to this , generate a star
    		Star s1 = new Star(topY - Constants.DISTANCE_BETWEEN_OBSTACLES/2);
    		listOfStars.add(s1);
    		s1.addElementsToGamePane(gamePane);
    	}
    }
//    
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
    }

    public void createNewGame(Stage menuStage) {
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
                
                if( keyEvent.getCode() == KeyCode.R) {
                	keyEvent.consume();
                	System.out.println("R pressed");
                	ballTimeline.play();
                }
            }
        });
    }
    

    private void createBackground() {
        Image backgroundImage = new Image("resources/deep_blue.png", 256, 256, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(background));
    }
}

