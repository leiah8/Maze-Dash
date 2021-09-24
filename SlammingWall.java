/**
 * @author Leiah Nay
 * @version Date: June 7, 2021
 */
import java.awt.*;
public class SlammingWall extends Obstacle{
	
	public int height, maxHeight, direction, size;
	public int yChange = 0;
	public int xChange = 0;
	
	/**
	 * Construct the Slamming Wall object
	 * @param y 			the y/row value in the 2D board array
	 * @param x 			the x/column value in the 2D board array
	 * @param yCoordinate	the y coordinate in the graphics panel
	 * @param xCoordinate	the x coordinate in the graphics panel
	 * @param speed 		the speed the obstacle will move
	 * @param maxHeight		the max height the wall can hit
	 * @param direction 	the direction the wall will move in 
	 */
	public SlammingWall (int y, int x, int yCoordinate, int xCoordinate, int speed, int maxHeight, int direction) {
		super (y, x, yCoordinate, xCoordinate, speed);
		this.direction = direction;
		this.maxHeight = maxHeight;
		this.size = maxHeight*2;
		
		//determine the direction
		//up and down
		if (direction == 1)
			this.yChange = 1;
		//left and right
		else
			this.xChange = 1;
	}
	
	/**
	 * Change the height of the walls till they slam together 
	 *  and then go back down
	 */
	public void moveWalls() {
		if (height > maxHeight || height < 0) {
			speed = -speed;
		}
		height += speed;
	}
	
	/**
	 * Draw the walls on the graphics panel
	 * @param g		the Graphics
	 */
	public void drawWalls(Graphics g) {
		g.setColor(Color.BLACK);
		//if the walls move up and down
		if (direction == 1) {
			g.fillRect(xCoordinate, yCoordinate, size, height);
			g.fillRect(xCoordinate, yCoordinate + size - height, size, height);
		}
		//if the walls move left and right
		else {
			g.fillRect(xCoordinate, yCoordinate, height, size);
			g.fillRect(xCoordinate + size - height, yCoordinate, height, size);
		}

	}

}
