package application;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;
import java.io.Serializable;

public abstract class Obstacle extends Actor implements Serializable {
    protected final double angle = 1.5;
	public Obstacle(double x, double y) {
		super(x, y);
	}
	public abstract void addElementsToGamePane(AnchorPane pane);
	public abstract void rotate();
	public abstract List<? extends Shape> getElements();
	public abstract void removeFromPane(AnchorPane gamePane);
	public abstract void reinitialise();
}
