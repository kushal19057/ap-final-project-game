package application;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;

public abstract class Obstacle extends Actor{
	public Obstacle(int x, int y) {
		super(x, y);
	}
	public abstract void addElementsToGamePane(AnchorPane pane);
	public abstract void rotate();
	public abstract List<? extends Shape> getElements();
	public abstract void removeFromPane(AnchorPane gamePane);
}
