/**
 * @author Leiah Nay
 * @version Date: May 30, 2021
 * Maze Dash - Player Object
 */
public class Player {
	
	public int xPos, yPos, size;
	public double score;
	
	/**
	 * Constructs the Player Object
	 * @param x 	the x coordinate of the player on the graphics panel
	 * @param y 	the y coordinate of the player on the graphics panel
	 * @param size  the size of the player
	 */
	public Player(int x, int y, int size) {
		this.xPos = x;
		this.yPos = y;
		this.size = size;
		this.score = 1000;
	}
	
	/**
	 * Check if a new high score has been achieved
	 */
	public void updateHighScore() {
		if (score > StoreData.highScore) 
			StoreData.highScore = score;
	}
	
	/**
	 * Resets the values of the player if a new game has started
	 * @param startY 	the starting y position of the player
	 */
	public void resetValues(int startY) {
		xPos = 100;
		yPos = 25 + startY*(size + 2);
		score = 1000;
	}
	
}
