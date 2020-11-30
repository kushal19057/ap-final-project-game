package application;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;

public class CircleObstacle extends Obstacle {
	private List<Arc> listOfArcs;
	private List<Rotate> listOfRotate;
	private static final int INIT_CENTER_X = Constants.GAME_WIDTH/2;
	private static final int INIT_ANGLE = 1;
	private static final int ARC_RADIUS = 150;
	private static final int ARC_LENGTH = 75;
	private static final int STROKE_WIDTH = 25;
	private int angle;
	
	public CircleObstacle(int _y) {
		super(INIT_CENTER_X, _y);
		listOfArcs = new ArrayList<>();
		listOfRotate = new ArrayList<>();
		angle = INIT_ANGLE;
		initArcs();
		initRotate();
	}
	
	private void initArcs() {
		for(int i=0;i<4;i++) {
			Arc arc = new Arc(currentPositionX, currentPositionY, ARC_RADIUS, ARC_RADIUS, i *90, ARC_LENGTH);
			listOfArcs.add(arc);
			arc.setFill(Color.TRANSPARENT);
			arc.setStroke(Constants.map.get(i));
			arc.setStrokeWidth(STROKE_WIDTH);
			arc.setType(ArcType.OPEN);	
		}
	}

	private void initRotate() {
		for(int i=0;i<4;i++) {
			Rotate rotate = new Rotate();
			listOfRotate.add(rotate);
			rotate.setPivotX(currentPositionX);
			rotate.setPivotY(currentPositionY);
			rotate.setAngle(angle);
		}
	}
	
	// add an option to increase speed of rotation
	
	@Override
	public void rotate() {
		for(int i=0;i<4;i++) {
			listOfArcs.get(i).getTransforms().add(listOfRotate.get(i));
		}
	}
	
	@Override
	public void addElementsToGamePane(AnchorPane gamePane) {
		for(Arc arc : listOfArcs) {
			gamePane.getChildren().add(arc);
		}
	}
	
	public void setPositionY(int currentPositonY) {
		super.setPositionY(currentPositonY);
		for(Arc arc : listOfArcs) {
			arc.setCenterY(currentPositonY);
		}
		for(Rotate r : listOfRotate) {
			r.setPivotY(currentPositonY);
		}
	}
	
	public List<? extends Shape> getElements() {
		return this.listOfArcs;
	}
	
	public void removeFromPane(AnchorPane pane) {
		for(Arc arc : listOfArcs) {
			pane.getChildren().remove(arc);
		}
	}
	
	
}

 