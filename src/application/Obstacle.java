package application;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;
import java.io.Serializable;

public abstract class Obstacle extends Actor{
    protected double angle;
	public Obstacle(double x, double y) {
		super(x, y);
		angle = 1.5;
	}
	public abstract void addElementsToGamePane(AnchorPane pane);
	public abstract void rotate();
	public abstract List<? extends Shape> getElements();
	public abstract void removeFromPane(AnchorPane gamePane);
	public void increaseDifficulty(int currentScore) {
	    angle = angle + 0.5 * currentScore;
	}
}
