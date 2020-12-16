package application;

public class ObstacleFactory {
	/*
	 * Factory design pattern used here.
	 * 
	 * @param	code	code for obstacle to be generated. Constants for code declared in Constants class
	 * @param	y		y coordinate of obstacle where it is to be generated
	 * @return 			instance of Obstacle, never null.
	 */
	public static Obstacle createObstacle(int code, double y) {
		if(code == Constants.CODE_CIRCLE_OBSTACLE) {
			return new CircleObstacle(y);
		} else if(code == Constants.CODE_CONCENTRIC_CIRCLE_OBSTACLE) {
			return new ConcentricCircleObstacle(y);
		} else if(code == Constants.CODE_TRIPLE_CONCENTRIC_OBSTACLE) {
			return new TripleConcentricCircleObstacle(y);
		} else if(code == Constants.CODE_SQUARE_OBSTACLE) {
			return new SquareObstacle(y);	
		} else {
			return new CircleObstacle(y);
		}
	}
}
