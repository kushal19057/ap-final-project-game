package application;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball {
	private Color color = Color.AQUA;
	
	private int posX;
	private int posY;
	
	private int velocityY;
	
	private Circle circle;
	AnchorPane gamePane;
	// always start with blue color
	public Ball(AnchorPane pane) {
		circle = new Circle();
		posX = Constants.GAME_WIDTH/2;
		posY = Constants.GAME_HEIGHT - 100;
		velocityY = 0;
		circle.setCenterX(posX);
		circle.setCenterY(posY);
		circle.setRadius(Constants.BALL_RADIUS);
		circle.setFill(color);
		// add this to the pane
		gamePane = pane;
		gamePane.getChildren().add(circle);
	}
	
	public void jump() {
		
	}
	
	public void motion(int posY) {
		this.posY = posY;
		circle.setCenterY(posY);
	}
	
	public int getPosY() {
		return posY;
	}
	
	public int getVelocityY() {
		return velocityY;
	}
	
	public void setVelocity(int v) {
		this.velocityY = v;
	}
	
	public Circle getCircle() {
		return circle;
	}
	
	
}

/*
 * create a mapping bw actual colors and color consrtants
 */