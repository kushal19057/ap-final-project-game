package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Shape;

public class ColorSwitch extends Actor implements Serializable {
	private static final long serialVersionUID = 1L;
	private transient List<Arc> listOfArcs;
	private static final int ARC_LENGTH = 80;
	private static final int ARC_RADIUS = 30;
	private static final int STROKE_WIDTH = 3;
	
	public ColorSwitch(double y) {
		this(Constants.GAME_WIDTH/2, y);
	}
	
	public ColorSwitch(double x, double y) {
		super(x, y);
		initialise();
	}
	
	private void initialise() {
	    listOfArcs = new ArrayList<>();
        initArc();
	}
	
	public void reinitialise() {
	    initialise();
	}

	private void initArc() {
		for(int i=0; i<4; i++) {
			Arc arc = new Arc(currentPositionX, currentPositionY, ARC_RADIUS, ARC_RADIUS, i * 90, ARC_LENGTH);
			listOfArcs.add(arc);
			arc.setType(ArcType.ROUND);
			arc.setFill(Constants.map.get(i));
			arc.setStrokeWidth(STROKE_WIDTH);
			arc.setStroke(Color.ORANGE);
		}
	}
	
	public void addElementsToGamePane(AnchorPane gamePane) {
		for(Arc arc : listOfArcs) {
			gamePane.getChildren().add(arc);
		}
	}
	@Override
	public void setPositionY(double y) {
		super.setPositionY(y);
		for(Arc arc : listOfArcs) {
			arc.setCenterY(currentPositionY);
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
