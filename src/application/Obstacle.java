package application;

import java.io.Serializable;
import java.util.List;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;

public abstract class Obstacle extends Actor implements Serializable {
	private static final long serialVersionUID = 1L;
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
