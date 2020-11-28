package application;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.transform.Rotate;

public class CircleObstacle extends Obstacle {
	private Arc arc1, arc2, arc3, arc4;
	private Rotate rotate1, rotate2, rotate3, rotate4;
	private List<Arc> listOfArcs;
	private List<Rotate> listOfRotate;
	private static final int INIT_CENTER_X = Constants.GAME_WIDTH/2;
	private static final int INIT_CENTER_Y = Constants.GAME_WIDTH/2;
	private static final int INIT_ANGLE = 1;
	private static final int ARC_RADIUS = 150;
	private static final int ARC_LENGTH = 75;
	private static final int STROKE_WIDTH = 25;
	int posX, posY, angle;
	
	public CircleObstacle() {
		super();
		listOfArcs = new ArrayList<>();
		listOfRotate = new ArrayList<>();
		posX = INIT_CENTER_X;
		posY = INIT_CENTER_Y;
		angle = INIT_ANGLE;
		
		initArcs();
		initRotate();		
	}
	
	private void initArcs() {
		arc1 = new Arc(posX, posY, ARC_RADIUS, ARC_RADIUS, 0, ARC_LENGTH);
		arc1.setFill(Color.TRANSPARENT);
		arc1.setStroke(Color.AQUA); 
		arc1.setStrokeWidth(STROKE_WIDTH);
		arc1.setType(ArcType.OPEN);
		listOfArcs.add(arc1);
		
		arc2 = new Arc(posX, posY, ARC_RADIUS, ARC_RADIUS, 90, ARC_LENGTH);
		arc2.setFill(Color.TRANSPARENT);
		arc2.setStroke(Color.HOTPINK); 
		arc2.setStrokeWidth(STROKE_WIDTH);
		arc2.setType(ArcType.OPEN);
		listOfArcs.add(arc2);
		
		arc3 = new Arc(posX, posY, ARC_RADIUS, ARC_RADIUS, 180, ARC_LENGTH);
		arc3.setFill(Color.TRANSPARENT);
		arc3.setStroke(Color.YELLOW); 
		arc3.setStrokeWidth(STROKE_WIDTH);
		arc3.setType(ArcType.OPEN);
		listOfArcs.add(arc3);
		
		arc4 = new Arc(posX, posY, ARC_RADIUS, ARC_RADIUS, 270, ARC_LENGTH);
		arc4.setFill(Color.TRANSPARENT);
		arc4.setStroke(Color.INDIGO); 
		arc4.setStrokeWidth(STROKE_WIDTH);
		arc4.setType(ArcType.OPEN);
		listOfArcs.add(arc4);
	}
	
	// next time, consider iterating over an array list to do this
	private void initRotate() {
		rotate1 = new Rotate();
		rotate2 = new Rotate();
		rotate3 = new Rotate();
		rotate4 = new Rotate();
		
		listOfRotate.add(rotate1);
		listOfRotate.add(rotate2);
		listOfRotate.add(rotate3);
		listOfRotate.add(rotate4);
		
		rotate1.setPivotX(posX);
		rotate1.setPivotY(posY);
		rotate1.setAngle(angle);
		
		rotate2.setPivotX(posX);
		rotate2.setPivotY(posY);
		rotate2.setAngle(angle);
		
		rotate3.setPivotX(posX);
		rotate3.setPivotY(posY);
		rotate3.setAngle(angle);
		
		rotate4.setPivotX(posX);
		rotate4.setPivotY(posY);
		rotate4.setAngle(angle);
		
		/*
		 * arc1.getTransforms().add(rotate1); arc2.getTransforms().add(rotate2);
		 * arc3.getTransforms().add(rotate3); arc4.getTransforms().add(rotate4);
		 */
	}
	
	public void rotate() {
		arc1.getTransforms().add(rotate1); arc2.getTransforms().add(rotate2);
		arc3.getTransforms().add(rotate3); arc4.getTransforms().add(rotate4);
	}
	
	public void addElementsToGamePane(AnchorPane gamePane) {
		gamePane.getChildren().addAll(arc1, arc2, arc3, arc4);
	}
	
	public void moveDown() {
		posY += 10;
		arc1.setCenterY(posY);
		arc2.setCenterY(posY);
		arc3.setCenterY(posY);
		arc4.setCenterY(posY);
		rotate1.setPivotY(posY);
		rotate2.setPivotY(posY);
		rotate3.setPivotY(posY);
		rotate4.setPivotY(posY);
	}
	
	public int getPosY() {
		return posY;
	}
	
	public void setPosY(int pos) {
		posY = pos;
		arc1.setCenterY(posY);
		arc2.setCenterY(posY);
		arc3.setCenterY(posY);
		arc4.setCenterY(posY);
		rotate1.setPivotY(posY);
		rotate2.setPivotY(posY);
		rotate3.setPivotY(posY);
		rotate4.setPivotY(posY);
	}
	
	public List<Arc> getElements() {
		return this.listOfArcs;
	}
	
	
}
  
//  Pane box = new Pane(); box.getChildren().addAll(arc1, arc2, arc3, arc4);

 