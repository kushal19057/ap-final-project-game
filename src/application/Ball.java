package application;

import java.io.Serializable;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class Ball extends Actor implements Serializable {
	private int color;
	private double velocityY;
	private transient Circle circle;
	
	public Ball(AnchorPane pane) {
		super(Constants.GAME_WIDTH/2, Constants.GAME_HEIGHT - Constants.START_DELTA_BALL);
		initialise(pane);
	}
	
	private void initialise(AnchorPane pane) {
	    color = 0;
	    velocityY = 0;
	    reinitialise(pane);
	}
	
	public void reinitialise(AnchorPane pane) {
	    circle = new Circle();
	    circle.setCenterX(currentPositionX);
	    circle.setCenterY(currentPositionY);
	    circle.setRadius(Constants.BALL_RADIUS);
	    circle.setFill(Constants.map.get(color));
	    pane.getChildren().add(circle);
	}

	public double getVelocityY() {
		return velocityY;
	}
	
	public void setVelocity(double v) {
		this.velocityY = v;
	}
	
	public Circle getCircle() {
		return circle;
	}
	@Override
	public void setPositionY(double y) {
		super.setPositionY(y);
		circle.setCenterY(y);
	}
	
	public void changeColor() {
		color = (color + 1)%4;
		circle.setFill(Constants.map.get(color));
	}
}