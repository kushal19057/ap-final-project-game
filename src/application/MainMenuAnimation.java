package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class MainMenuAnimation {
	private List<Arc> listOfOuterArcs = new ArrayList<>();
    private List<Rotate> listOfOuterRotate = new ArrayList<>();
    private List<Arc> listOfMiddleArcs = new ArrayList<>();
    private List<Rotate> listOfMiddleRotate = new ArrayList<>();
    private List<Arc> listOfInnerArcs = new ArrayList<>();
    private List<Rotate> listOfInnerRotate = new ArrayList<>();
    
    private Circle circleParallelDown = new Circle(300, 350, 50);
    private Circle circleParallelUp = new Circle(300, 350, 50);
    
    private FadeTransition fadeTransitionDown = new FadeTransition(Duration.millis(2000), circleParallelDown);
    private FadeTransition fadeTransitionUp = new FadeTransition(Duration.millis(2000), circleParallelUp);
    
    private TranslateTransition translateTransitionDown = new TranslateTransition(Duration.millis(2000), circleParallelDown);
    private TranslateTransition translateTransitionUp = new TranslateTransition(Duration.millis(2000), circleParallelUp);
    
    private ScaleTransition scaleTransitionDown = new ScaleTransition(Duration.millis(2000), circleParallelDown);
    private ScaleTransition scaleTransitionUp = new ScaleTransition(Duration.millis(2000), circleParallelUp);
    
    private ParallelTransition parallelTransitionDown = new ParallelTransition();
    private ParallelTransition parallelTransitionUp = new ParallelTransition();
    
    private int NUMBER_OF_SNOWBALLS = 1000;
    private Circle snowballs[] = new Circle[NUMBER_OF_SNOWBALLS];
    
    public MainMenuAnimation(AnchorPane mainMenuPane) {
    	circleParallelDown.setFill(Color.AZURE);
        circleParallelUp.setFill(Color.BEIGE);

        fadeTransitionDown.setFromValue(1.0f);
        fadeTransitionDown.setToValue(0.3f);
        fadeTransitionDown.setCycleCount(2);
        fadeTransitionDown.setAutoReverse(true);
        
        fadeTransitionUp.setFromValue(1.0f);
        fadeTransitionUp.setToValue(0.3f);
        fadeTransitionUp.setCycleCount(2);
        fadeTransitionUp.setAutoReverse(true);

        translateTransitionDown.setFromY(0);
        translateTransitionDown.setToY(200);
        translateTransitionDown.setCycleCount(2);
        translateTransitionDown.setAutoReverse(true);
        
        translateTransitionUp.setFromY(0);
        translateTransitionUp.setToY(-200);
        translateTransitionUp.setCycleCount(2);
        translateTransitionUp.setAutoReverse(true);

        scaleTransitionDown.setToX(2f);
        scaleTransitionDown.setToY(2f);
        scaleTransitionDown.setCycleCount(2);
        scaleTransitionDown.setAutoReverse(true);
        
        scaleTransitionUp.setToX(2f);
        scaleTransitionUp.setToY(2f);
        scaleTransitionUp.setCycleCount(2);
        scaleTransitionUp.setAutoReverse(true);
    
        parallelTransitionDown.getChildren().addAll(
            fadeTransitionDown,
            translateTransitionDown,
            scaleTransitionDown
         );
        
        parallelTransitionUp.getChildren().addAll(
                fadeTransitionUp,
                translateTransitionUp,
                scaleTransitionUp
             );
        parallelTransitionDown.setCycleCount(Timeline.INDEFINITE);
        parallelTransitionUp.setCycleCount(Timeline.INDEFINITE);
        
        parallelTransitionDown.play();
        parallelTransitionUp.play();
        
        for(int i=0;i<4;i++) {
            Arc arc = new Arc(300, 350, 150, 150, i * 90, 60);
            listOfOuterArcs.add(arc);
            arc.setFill(Color.TRANSPARENT);
            arc.setStroke(Constants.map.get(i));
            arc.setStrokeWidth(20);
            arc.setType(ArcType.OPEN);
            mainMenuPane.getChildren().add(arc);
        }
        for(int i=0;i<4;i++) {
            Arc arc = new Arc(300, 350, 120, 120, i * 90, 60);
            listOfMiddleArcs.add(arc);
            arc.setFill(Color.TRANSPARENT);
            arc.setStroke(Constants.map.get(i));
            arc.setStrokeWidth(20);
            arc.setType(ArcType.OPEN);
            mainMenuPane.getChildren().add(arc);
        }
        for(int i=0;i<4;i++) {
            Arc arc = new Arc(300, 350, 90, 90, i * 90, 60);
            listOfInnerArcs.add(arc);
            arc.setFill(Color.TRANSPARENT);
            arc.setStroke(Constants.map.get(i));
            arc.setStrokeWidth(20);
            arc.setType(ArcType.OPEN);
            mainMenuPane.getChildren().add(arc);
        }
        
        for(int i=0;i<4;i++) {
            Rotate rotate = new Rotate();
            listOfOuterRotate.add(rotate);
            rotate.setPivotX(300);
            rotate.setPivotY(350);
            rotate.setAngle(3.5);
        }
        for(int i=0;i<4;i++) {
            Rotate rotate = new Rotate();
            listOfMiddleRotate.add(rotate);
            rotate.setPivotX(300);
            rotate.setPivotY(350);
            rotate.setAngle(3);
        }
        for(int i=0;i<4;i++) {
            Rotate rotate = new Rotate();
            listOfInnerRotate.add(rotate);
            rotate.setPivotX(300);
            rotate.setPivotY(350);
            rotate.setAngle(2.5);
        }
        
        initSnowBalls(mainMenuPane);
    }
    
    public void initSnowBalls(AnchorPane mainMenuPane) {
    	Random random = new Random();
    	for(int i=0;i<NUMBER_OF_SNOWBALLS;i++) {
    		snowballs[i] = new Circle(1,1,1);
    		snowballs[i].setRadius((random.nextDouble() * 3 ) % 20);
    		Color color = Color.rgb(255, 255, 255, random.nextDouble());
    		snowballs[i].setFill(color);
    		mainMenuPane.getChildren().add(snowballs[i]);
    		raining(snowballs[i]);
    	}
    }
    
    private void raining(Circle snowball) {
    	Random random = new Random();
    	snowball.setCenterX(random.nextInt((int)Constants.MENU_WIDTH));
    	snowball.setCenterY(random.nextInt((int)Constants.MENU_HEIGHT));
    }
    
    public void addElementsToGamePane(AnchorPane mainMenuPane) {
        mainMenuPane.getChildren().add(circleParallelDown);
        mainMenuPane.getChildren().add(circleParallelUp);
    }
    
    public void transformElements() {
    	Random random = new Random();
    	for(int i=0;i<4;i++) {
            listOfInnerArcs.get(i).getTransforms().add(listOfInnerRotate.get(i));
            listOfMiddleArcs.get(i).getTransforms().add(listOfMiddleRotate.get(i));
            listOfOuterArcs.get(i).getTransforms().add(listOfOuterRotate.get(i));
        }
    	for(int i=0;i<NUMBER_OF_SNOWBALLS;i++) {
    		Circle current = snowballs[i];
    		double y = current.getCenterY();
    		double x = current.getCenterX();
    		y += 0.5;
    		boolean left = random.nextBoolean();
    		if(left) {
    			x -= 0.2;
    		} else {
    			x += 0.2;
    		}
    		if(y > Constants.MENU_HEIGHT) {
    			y = 0;
    		}
    		current.setCenterY(y);
    		current.setCenterX(x);
    	}
    }
}
