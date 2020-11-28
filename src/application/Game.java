package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

/**
 * @author kushal
 *
 */
public class Game {
    private Stage menuStage;
    
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    
    private CircleObstacle obstacle;
    private Ball ball;
    
    private List<Obstacle> listOfObstacles;
    
    private Random random;
    
    private int posY;
    private int velY;
    private int gravity;
    
    Timeline ballTimeline;
    Timeline obstacleTimeline;

    private boolean firstSpace;
    
    public Game() {
    	
    	firstSpace = false;
    	gravity = 0;
    	random = new Random();
    	listOfObstacles = new ArrayList<>();
        initStage();
        
        initObstacle();
        initBall();
        createKeyListener();
        createBackground();
        
        setupObstacleTimeline();
        setupBallTimeline();
    }
    
    private void initBall() {
    	ball = new Ball(gamePane);
    	posY = ball.getPosY();
    	velY = ball.getVelocityY();
    }
    
    private void setupObstacleTimeline() {
    	KeyFrame kf = new KeyFrame(Duration.seconds(0.01), new ObstacleTimeHandler());
    	obstacleTimeline = new Timeline(kf);
    	obstacleTimeline.setCycleCount(Animation.INDEFINITE);
    	obstacleTimeline.play();
    }
    
    private void setupBallTimeline() {
    	KeyFrame kf = new KeyFrame(Duration.seconds(0.025), new BallTimeHandler());
    	ballTimeline = new Timeline(kf);
    	ballTimeline.setCycleCount(Animation.INDEFINITE);
    	ballTimeline.play();
    }
    // inner class
    private class ObstacleTimeHandler implements EventHandler<ActionEvent> {
    	public void handle(ActionEvent event) {
    		obstacle.rotate();
    	}
    }
    // inner class
    private class BallTimeHandler implements EventHandler<ActionEvent> {
    	public void handle(ActionEvent event) {
//    		calculate y velocity using physics simulation equation
//    		calculate balls new position using y velocity
//    		check if potential y position is above height/2
//    			if ball above midpoint:
//    				calculate how far above midpoint
//    				set position to midpoint
//    				for each platform, lower it by how much above the midpoint
//    			else
//    				set the doodles position to the potential position
//    		check if any platform is below bottom of window
//    			if a platform is below bottom, remove it graphically and logically.
    		int dy = velY;
    		velY += gravity;
    		posY += dy;
    		ball.setVelocity(velY);
    		ball.motion(posY);
    		if(posY > Constants.GAME_HEIGHT) {
    			System.out.println("GAME OVER");
    			ballTimeline.pause();
    			obstacleTimeline.pause();
    		}
    		checkShapeIntersection(ball.getCircle());
    	}
    }
    
    private void initObstacle() {
    	obstacle = new CircleObstacle();
    	obstacle.addElementsToGamePane(gamePane);
    	listOfObstacles.add(obstacle);
    }
    

    private void initStage() {
        this.gamePane = new AnchorPane();
        this.gameScene = new Scene(gamePane, Constants.GAME_WIDTH, Constants.GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    public void createNewGame(Stage menuStage) {
        this.menuStage = menuStage;
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
                    // make the obstacle move down a little
                    obstacle.moveDown();
                    if(obstacle.getPosY() > Constants.GAME_HEIGHT + 100) {
                    	obstacle.setPosY(0);
                    }
                    
                    if(firstSpace == false) {
                    	firstSpace = true;
                    	gravity = Constants.GRAVITY;
                    }
                    velY = -20;
                }
            }
        });
    }
    

    private void createBackground() {
        Image backgroundImage = new Image("resources/deep_blue.png", 256, 256, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(background));
    }
    
    private void checkShapeIntersection(Shape block) {
    	boolean collisionDetected = false;
    	for(Shape arc : obstacle.getElements()) {
    		Shape intersect = Shape.intersect(block, arc);
    		if(intersect.getBoundsInLocal().getWidth() != -1) {
    			if(block.getFill().equals(arc.getStroke())) {
    				collisionDetected = true;
    				System.out.println(block.getFill());
    				System.out.println(arc.getFill());
    				break;
    			}
    		}
    	}
    	if(collisionDetected) {
			ballTimeline.pause();
			obstacleTimeline.pause();
			System.out.println("Collision");
		}
    }
}

/* platform generation 
 * create the first obstacle and store it in a current topmost variable
 * add this obstacle to arraylist
 * while current topmost s y position is not above the top of the pane
 * 		create a new obstacle and add it to the arraylist
 *		postion the new obstacle relative to the current topmost
 *		update the current topmost variable to refer to this new obstacle
 *
 *---
 *when the program starts, create enough obstacles to conver the entire screen using while loop
 *when the ball jumps to a higher obstacle, create new obstacles.
 *when the ball is above the center, move all obstacles down, and remove any offscreen obstacles.
 * */


/* updated-velocity = current velocity + acceleration
 * updated-position = current-position + updated-velocity 
 * 
 * 
 * we know how our ball is colliding with one obstacle, how to check if it is colliding with any obstacle ?
 * using a loop ! check all obstacles for a collision
 * */


