/**
 * @author Leiah Nay
 * @version Date: June 9, 2021
 * Maze Dash - Laser Object
 */
import java.awt.*;
public class Laser extends Obstacle{
			
	public int direction, xChange, yChange, startX, startY;
	
	/**
	 * Construct the Laser Object
	 * @param y 			the y/row value in the 2D board array
	 * @param x 			the x/column value in the 2D board array
	 * @param yCoordinate	the y coordinate in the graphics panel
	 * @param xCoordinate	the x coordinate in the graphics panel
	 * @param speed 		the speed the laser will move
	 * @param direction 	the direction the laser will move
	 */
	public Laser(int y, int x, int yCoordinate, int xCoordinate, int speed, int direction) {
		super(y, x, yCoordinate, xCoordinate, speed);
		
		//set all values
		this.xChange = 0;
		this.yChange = 0;
		this.direction = direction;
		this.startX = xCoordinate;
		this.startY = yCoordinate;
		
		//determine the direction
		//moving right
		if (direction == 1)
			this.xChange = 1;
		//moving left
		else if (direction == 2) 
			this.xChange = -1;
		//moving up
		else if (direction == 3)
			this.yChange = 1;
		//moving down
		else
			this.yChange = -1;
	}
	
	/**
	 * Adjust the x and y coordinates of the laser to 
	 *  move it on the screen
	 */
	public void moveLaser() {
		xCoordinate += xChange *speed;
		yCoordinate += yChange *speed;
	}
	
	/**
	 * Change the direction of the laser.
	 * 	This will be used when the laser hits a wall
	 */
	public void changeDirection() {
		xChange = -xChange;
		yChange = -yChange;
	}
	
	/**
	 * Draw the laser on the graphics panel
	 * @param g	the Graphics 
	 */
	public void drawLaser(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(xCoordinate, yCoordinate, 10, 10);
		g.setColor(Color.PINK);
		g.fillOval(xCoordinate, yCoordinate, 5, 5);
	}

}
