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
import java.io.Serializable;

public class CircleObstacle extends Obstacle implements Serializable {
	private transient List<Arc> listOfArcs;
	private  transient List<Rotate> listOfRotate;

	private static final double ARC_RADIUS = 175d;
	private static final double ARC_LENGTH = 75d;
	private static final double STROKE_WIDTH = 25d;
	
	public CircleObstacle(double y) {
		super(Constants.GAME_WIDTH/2, y);
		initCircleElements();
	}
	
	private void initCircleElements() {
	    listOfArcs = new ArrayList<>();
        listOfRotate = new ArrayList<>();
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

	// XXX add a null pointer exception in all of these
	
	@Override
	public void reinitialise() {
	    initCircleElements();
	}
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

	public void setPositionY(double currentPositonY) {
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

 