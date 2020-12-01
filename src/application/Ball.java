package application;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class Ball extends Actor{
	private int color;
	private double velocityY;
	private  Circle circle;
	private  AnchorPane gamePane;
	
	public Ball(AnchorPane pane) {
		super(Constants.GAME_WIDTH/2, Constants.GAME_HEIGHT - Constants.START_DELTA_BALL);
		color = 0;
		circle = new Circle();
		velocityY = 0;
		circle.setCenterX(currentPositionX);
		circle.setCenterY(currentPositionY);
		circle.setRadius(Constants.BALL_RADIUS);
		circle.setFill(Constants.map.get(color));
		// add this to the pane
		gamePane = pane;
		gamePane.getChildren().add(circle);
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