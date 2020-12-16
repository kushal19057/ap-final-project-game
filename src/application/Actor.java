package application;

import java.io.Serializable;

public abstract class Actor implements Serializable{
	private static final long serialVersionUID = 1L;
	protected double currentPositionX, currentPositionY;
	public Actor(double x, double y) {
		currentPositionX = x;
		currentPositionY = y;
	}
	
	public double getPositionY() {
		return currentPositionY;
	}
	
	public void setPositionY(double y) {
		currentPositionY = y;
	}

}
