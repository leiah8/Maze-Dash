/**
 * @author Leiah Nay
 * @version Date: May 30, 2021
 * MazeDash - Obstacle Object
 */

public class Obstacle {
	
	public int xPos, yPos, xCoordinate, yCoordinate, speed;
	
	/**
	 * Constructs the Obstacle Object
	 * @param y 			the y/row value in the 2D board array
	 * @param x 			the x/column value in the 2D board array
	 * @param yCoordinate	the y coordinate in the graphics panel
	 * @param xCoordinate	the x coordinate in the graphics panel
	 * @param speed 		the speed the obstacle will move
	 */
	public Obstacle(int y, int x, int yCoordinate, int xCoordinate, int speed) {
		this.xPos = x;
		this.yPos = y;
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.speed = speed;
	}

}
