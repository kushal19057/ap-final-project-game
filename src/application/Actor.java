package application;


public abstract class Actor{
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
