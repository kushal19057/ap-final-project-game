package application;

public abstract class Actor {
	protected int currentPositionX, currentPositionY;
	public Actor(int x, int y) {
		currentPositionX = x;
		currentPositionY = y;
	}
	
	public int getPositionY() {
		return currentPositionY;
	}
	
	public void setPositionY(int y) {
		currentPositionY = y;
	}

}
