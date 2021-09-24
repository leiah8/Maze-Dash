/**
 * @author Leiah Nay
 * @version Date: June 9, 2021
 * MazeDash - Game Class
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JFrame;

public class SingleGame {

	//create the frame and panels 
	public JFrame frame;
	private ShowGraphics mazePanel;
	
	//create a boolean to determine if there are two players
	private boolean multiPlayer;
	
	//initialize speed for the player
	private int speed = 2;
	
	//create the board and its block size
	private final int BOARD_SIZE = 20;
	private int size = 400 / BOARD_SIZE;	
	private String [][] board = new String [BOARD_SIZE][BOARD_SIZE];
	private int [] finalPos;
	
	//create ArrayList to store the path and obstacles
	private ArrayList <int []> path = new ArrayList <int []> ();
	private ArrayList <Laser> allLasers = new ArrayList <Laser> ();
	private ArrayList <SlammingWall> allWalls = new ArrayList <SlammingWall> ();
	
	//create the player(s)
	private int startY = (int) (Math.random()*((8 - 1) + 1) + 1);
	private Player user;
	private Player user2;
	
	private Color playerColour = Color.BLUE;
	private Color playerColour2 = Color.CYAN;
	
	private boolean playerRed = false;
	private boolean playerRed2 = false;
	
	//create booleans for game status
	private boolean gameOver = false;
	private boolean pause = false;
	
	//create ArrayList to know which keys are being bressed
	ArrayList <Integer> pressedKeys = new ArrayList <Integer> ();
	
	/**
	 * Generates the 2D string array version of the maze board
	 * @param startY	The starting position of the player/ first open space
	 * @return  		Coordinates of the exit point of the maze
	 */
	public int [] generateBoard(int startY) {
		
		//create the board
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int n = 0; n < BOARD_SIZE; n++) {
				board[i][n] = " ";
			}
		}
		
		//set board string values for the first two spaces
		board[startY][0] = "O";
		board[startY][1] = "O";
		
		//add these values to the path
		int [] pos1 = {startY, 0};
		path.add(pos1);
		pos1[1] = 1;
		path.add(pos1);
		
		//initialize starting positions
		int xPos = 1;
		int yPos = startY;
		
		//initialize loop values
		boolean notFilled = true;
		int randomNum;
		
		//loop until the board's path is created
		while (notFilled) {
			randomNum = (int)(Math.random()*(3));
			
			//determine direction of next blank block
			if (randomNum == 0)
				xPos += 1;
			else if (randomNum == 1)
				yPos += 1;
			else if(randomNum == 2)
				yPos -= 1;
			
			//determine if the path is complete
			if (xPos <= 0 || xPos >= BOARD_SIZE - 1 || yPos <= 0 || yPos >= BOARD_SIZE - 1)
				notFilled = false;
			
			//set the board's value 
			board[yPos][xPos] = "O";
		}
		
		//fill in the rest of the board randomly
		for (int i = 1; i < BOARD_SIZE - 1; i++) {
			for (int n = 1; n < BOARD_SIZE - 1; n++) {
				if (board[i][n].equals("O")) {
					int [] temp = {i, n};
					path.add(temp);
				}
				else if (board[i][n].equals(" ")) {
					randomNum = (int) (Math.random()*(5));
					if (randomNum % 3 == 0) 
						board[i][n] = "X";
				}
			}
		}
		
		//get the final position (to check later if the game is complete)
		int [] finalPosition = {yPos, xPos};
		return finalPosition;
	}
	
	/**
	 * Prints the board onto the screen
	 * @param g		the graphics paint component 
	 */
	public void printBoard(Graphics g) {
		g.setColor(Color.BLACK);
		
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int n = 0; n < BOARD_SIZE; n++) {
				if (board[i][n].equals(" ")) {
					g.setColor(Color.BLACK);
					g.fillRect(100 + n*size, 25 + i*size, size, size);
					g.setColor(Color.DARK_GRAY);
					g.drawRect(100 + n*size, 25 + i*size, size, size);
					g.drawString(board[i][n], 100 + n*size, 25 + i*size + 20);
				}
			}
		}
		
	}
	
	/**
	 * Find the coordinates of the block the player is in
	 * 	that corresponds to the 2D string array board
	 * @param x		the x coordinate of the player
	 * @param y		the y coordinates of the player
	 * @return		the coordinates of the block the player is in
	 * 				or {-1, -1} if the player is not in the maze
	 */
	public int [] findBlock(int x, int y) {
		//initialize position and not found values
		int [] position = {-1, -1};
		
		//find the block
		for (int i = 0; i < board.length; i++) {
			for (int n = 0; n < board.length; n++) {
				if(y >= 25+i*size && y <= 25+i*size + size &&
						x >= 100 +n*size && x <= 100+n*size + size) {
					position[0] = i;
					position[1] = n;
					return position;
				}
			}
		}
		return position;
		
	}
	
	/**
	 * Determine if the player will hit a wall of the maze with the 
	 *  given changes to their position
	 * @param xChange	the change in x position
	 * @param yChange	the change in y position
	 * @return			true if the player will not hit the wall and can move, 
	 * 					false if the player will hit the wall
	 */
	public boolean checkHitWalls(int xCoordinate, int yCoordinate) {
		
		//check if the players are at finalPos
		checkGameOver();
		
		//initialize values for the user's location
		int [] nextPos;
		int [] nextPos2;
		
		//find coordinates of the top left corner of the player
		nextPos = findBlock(xCoordinate, yCoordinate);
		
		//find the coordinates of the bottom right corner of the player
		nextPos2 = findBlock(xCoordinate + size -2, yCoordinate + size -2);
		
		//check if any of these coordinates are not on the maze
		if (nextPos[0] == -1 || nextPos[1] == -1 || nextPos2[0] == -1 || nextPos2[1] == -1)
			return false;
		
		//check if the player will hit any walls
		if (board[nextPos[0]][nextPos[1]].compareTo(" ") != 0 && 
				board[nextPos2[0]][nextPos2[1]].compareTo(" ") != 0 && 
				board[nextPos[0]][nextPos2[1]].compareTo(" ") != 0 && 
				board[nextPos2[0]][nextPos[1]].compareTo(" ") != 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if the user(s) are at the final position
	 */
	public void checkGameOver() {
		
		//find the coordinates of the first user
		int [] pos1 = findBlock(user.xPos, user.yPos);
		int [] pos2 = findBlock(user.xPos + user.size-1, user.yPos + user.size-1);
		
		//initialize position arrays for the second user
		int [] pos3;
		int [] pos4;
		
		//check if the first user has reached finalPos
		if (finalPos[0] == pos1[0] && finalPos[1] == pos1[1] &&
				finalPos[0] == pos2[0] && finalPos[1] == pos2[1]) {
			gameOver = true;
			
			//if this is a multiPlayer game, check if the second user has reached finalPos
			if (multiPlayer) {
				
				//find user2 position
				pos3 = findBlock(user2.xPos, user2.yPos);
				pos4 = findBlock(user2.xPos + user2.size-1, user2.yPos + user2.size-1);
				
				//check if that have reached finalPos
				if (finalPos[0] == pos3[0] && finalPos[1] == pos3[1] &&
						finalPos[0] == pos4[0] && finalPos[1] == pos4[1]) {
					gameOver = true;
					user.updateHighScore();
				}
				else 
					gameOver = false;
			}
			//if no multiplayer, the game is over
			else {
				gameOver = true;
				user.updateHighScore();
			}
		} 
	}
	
	/**
	 * Create the obstacles for the maze
	 */
	public void makeObstacles() {
		
		//clear previous obstacles
		allLasers.clear();
		allWalls.clear();
		
		//determine if path is long enough
		if (path.size() < 5) {
			finalPos = generateBoard(startY);
			makeObstacles();
		}
		else {			
			//create the lasers
			while(allLasers.size() < 3) {
				//find random point on the path and a random direction
				int randomNum = (int) (Math.random()*((path.size() - 3) + 2));
				int [] pos = path.get(randomNum);
				int direction = (int) (Math.random()*4 + 1);
				
				//create laser and its possible positions
				Laser temp = new Laser(pos[0], pos[1], 25+ pos[0]*size + size/2,  100+pos[1]*size + size/2, 1, direction);
				int possible0 = pos[0] + temp.yChange*3;
				int possible1 = pos[1] + temp.xChange*3;
				
				//determine if the laser can move. if it can, then add it to the arrayList
				if (possible0 < 20 && possible0 >= 0 && possible1 < 20 && possible1 >=0 &&
						(!board[possible0][possible1].equals(" "))){
					allLasers.add(temp);
				}
			}
			
			//make the slamming walls
			int count = 0;
			while(count < 10) {
				
				//find random position on the path
				int randomNum = (int) (Math.random()*((path.size() - 3) + 2));
				int [] pos = path.get(randomNum);
				int row = pos[0];
				int col = pos[1];
				
				//ensure this is on the path
				if (board[row][col].equals(" ") == false ) {
					//check if the wall can move vertically and add it
					if (row +1 <BOARD_SIZE  && row -1 >=0 && board[row+1][col].equals(" ") && 
							board[row-1][col].equals(" ")) {
						allWalls.add(new SlammingWall(row, col, 25+ row*size, 100+col*size, 1, size/2, 1));
						board[row][col] = "B";
					}
					//check if the wall can move horizontally and add it
					else if(col +1 <BOARD_SIZE  && col -1 >=0 && 
							board[row][col+1] == " " && board[row][col-1] == " ") {
						allWalls.add(new SlammingWall(row, col, 25+ row*size, 100+col*size, 1, size/2, 2));
						board[row][col] = "B";
					}
				}
				count++;
			}
		}
	};

	/**
	 * Create the application.
	 */
	public SingleGame(boolean multiPlayer) {
		initialize();
		
		//determine if this is a multiPlayer game
		this.multiPlayer = multiPlayer;
		
		//create the user(s)
		this.user = new Player(100, 25 + startY * size + 1, size - 2);
		if (multiPlayer)
			this.user2 = new Player(100, 25 + startY * size + 1, size - 2);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		//create the frame
		frame = new JFrame("MAZE DASH");		
		
		//create the maze and obstacles
		finalPos = generateBoard(startY);
		makeObstacles();
		
		
		//check if a key has been pressed
		KeyListener checkKeys = new KeyListener() {
			/**
			 * Check if a key was pressed
			 */
            public void keyPressed(KeyEvent event) {
            	
            	//get the key that was pressed
            	int keyCode = event.getKeyCode();
            	
            	//add to arrayList if needed
            	if (!pressedKeys.contains(keyCode)) {
            		pressedKeys.add(keyCode);
            	}
            	
            	//space bar/pause pressed
                if(keyCode == 32) {
                	pause = !pause;
                }
            	
                //check if the game is paused/over or not
                if(!gameOver && !pause) {
                	
	                //user move left
                	if (pressedKeys.contains(37)) {
	                	if (checkHitWalls(user.xPos - speed, user.yPos))
	                		user.xPos -= speed;
	                }
	                //user move up
                	if (pressedKeys.contains(38)) {
	                	if (checkHitWalls(user.xPos, user.yPos - speed))
	                		user.yPos -= speed;
	                }
	                //user move right
                	if (pressedKeys.contains(39)) {
	                	if (checkHitWalls(user.xPos + speed, user.yPos))
	                		user.xPos += speed;
	                }
	                //user move down 
                	if (pressedKeys.contains(40)) {
	                	if (checkHitWalls(user.xPos, user.yPos + speed))
	                		user.yPos += speed;
	                }
	                
                	//determine if there is a second player
	                if (multiPlayer) {
		                //A: user2 moves left
	                	if (pressedKeys.contains(65)) {
		                	if (checkHitWalls(user2.xPos - speed, user2.yPos))
		                		user2.xPos -= speed;
		                }
		                //W: user2 moves up
	                	if (pressedKeys.contains(87)) {
		                	if (checkHitWalls(user2.xPos, user2.yPos - speed))
		                		user2.yPos -= speed;
		                }
		                //D: user2 moves right
	                	if (pressedKeys.contains(68)) {
		                	if (checkHitWalls(user2.xPos + speed, user2.yPos))
		                		user2.xPos += speed;
		                }
		                //S: user2 moves down 
	                	if (pressedKeys.contains(83)) {
		                	if (checkHitWalls(user2.xPos, user2.yPos + speed))
		                		user2.yPos += speed;
		                }
	                }
	            }
                
                frame.repaint();
            }
            /**
             * Check if key has been released
             */
            public void keyReleased(KeyEvent event) {
            	//remove that key from the arraylist
            	int keyCode = event.getKeyCode();
            	pressedKeys.remove(pressedKeys.indexOf(keyCode));
            }
            public void keyTyped(KeyEvent event) {}
        };
        
        //check if the mouse has been clicked
        MouseListener checkMouse = new MouseListener() {
        	public void mousePressed(MouseEvent e) {}
    	    public void mouseReleased(MouseEvent e) {}
    	    public void mouseEntered(MouseEvent e) {}
    	    public void mouseExited(MouseEvent e) {}
    	    
    	    /**
    	     * Check if the mouse has been clicked
    	     */
    	    public void mouseClicked(MouseEvent e) {
    	    	
    	    	//get coordinates
    	    	int mouseX = e.getX();
    			int mouseY = e.getY();
    			
    			//check if pause/play button was pressed
    			if(mouseX >= 550 && mouseX <= 580 && 
        					mouseY >= 40 && mouseY <= 65 && !gameOver) {
    				pause = !pause;
    				frame.repaint();
    			}
    			
    			//check if pause/game over screen buttons were pressed
    			if (pause || gameOver) {
        			//new game was pressed
        			if (mouseX >= 190 && mouseX <= 295 && 
        					mouseY >= 200 && mouseY <= 280) {
        				
        				//reset game values
        				pause = false;
        				gameOver = false;
        				
        				//create new starting position and reset the player
        				startY = (int) (Math.random()*((8 - 1) + 1) + 1);
        				user.resetValues(startY);
        				
        				//adjust player's starting position
        				user.yPos += 1;
        				
        				//repeat for user2 if needd
        				if (multiPlayer) {
        					user2.resetValues(startY);
        					user2.yPos += 1;
        				}
        				
        				//create the maze
        				finalPos = generateBoard(startY);
        				makeObstacles();
        				
        				frame.repaint();
        			}
        			
        			//main menu was pressed
        			else if (mouseX >= 315 && mouseX <= 420 && 
        					mouseY >= 200 && mouseY <= 280) {
        				
        				//close this frame
        				frame.dispose();
        				
        				//open the main menu frame
        				EventQueue.invokeLater(new Runnable() {
        					public void run() {
        						try {
        							MainWindow window = new MainWindow();
        							window.frame.setVisible(true);
        						} catch (Exception e) {
        							e.printStackTrace();
        						}
        					}
        				});
        			}
             	}
    	    }
        };
        
        //create frame
        frame.setBounds(100, 100, 600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
		//create graphics panel
		mazePanel = new ShowGraphics();
		
		//add listeners and panel to the frame
		frame.addKeyListener(checkKeys);
		frame.addMouseListener(checkMouse);
		frame.add(mazePanel);
	}
	
	/**
	 * Draw on the Graphics Panel
	 */
	public class ShowGraphics extends JPanel {
	    public void paintComponent(Graphics g) { 
	      super.paintComponent(g);
	      
	      //draw the board
	      printBoard(g);
    	  
    	  //draw the score
	      g.setColor(Color.BLACK);
	      g.drawString(String.format("Score: %.2f", user.score), 10, 10);
	      
	      //draw player
    	  g.setColor(playerColour);
    	  g.fillRect(user.xPos, user.yPos, user.size -1, user.size -1);
    	  
    	  //draw the other player if needed
    	  if (multiPlayer) {
    		  g.setColor(playerColour2);
    		  g.fillRect(user2.xPos, user2.yPos, user2.size -1, user2.size -1);
    	  }
	      
	      //pause screen
	      if (pause && !gameOver) {
	    	  //play button
	    	  g.setColor(Color.BLACK);
	    	  int [] xPoints = {550, 570, 550};
	    	  int [] yPoints = {10, 22, 35};
	    	  g.fillPolygon(xPoints, yPoints, 3);
	    	  
	    	  //border box
	    	  g.setColor(Color.BLACK);
	    	  g.fillRect(150, 75, 300, 300);
	    	  g.setColor(Color.LIGHT_GRAY);
	    	  g.drawRect(150, 75, 300, 300);
	    	  
	    	  //buttons (new game and main menu)
	    	  g.setColor(Color.WHITE);
	    	  g.fill3DRect(190, 200, 100, 50, true);
	    	  g.fill3DRect(315, 200, 100, 50, true);
	    	  g.setFont(new Font("Britannic Bold", Font.PLAIN, 18));
	    	  g.setColor(Color.BLACK);
	    	  g.drawString("New Game", 195, 230);
	    	  g.drawString("Main Menu", 320, 230);
	    	  
	    	  //title
	    	  g.setFont(new Font("Britannic Bold", Font.BOLD, 50));
	    	  g.setColor(Color.BLUE);
	    	  g.drawString("MAZE DASH", 165, 175);
	    	  
	    	  //display score
	    	  g.setColor(Color.WHITE);
	    	  g.setFont(new Font("Britannic Bold", Font.PLAIN, 14));
	    	  g.drawString(String.format("HIGH SCORE: %.2f", StoreData.highScore), 230, 280);
	    	  
	    	  //drawing at bottom 
	    	  g.setColor(Color.BLUE);
	    	  g.fillRect(315, 300, 50, 50);
	    	  g.drawLine(200, 325, 295, 325);
	    	  g.drawLine(250, 310, 300, 310);
	    	  g.drawLine(225, 340, 300, 340);
	      }
	      
	      //playing game
	      else if (!gameOver){
	    	  //pause button
	    	  g.setColor(Color.BLACK);
		      g.fillRect(550, 10, 10, 25);
		      g.fillRect(565, 10, 10, 25);
		      
		      //update score
		      user.score -= 0.005;
		      
		      //the user's colour should not be red to begin
		      playerRed = false;
		      playerRed2 = false;
		      
		      //determine the block the user is in
		      int [] pos1 = findBlock(user.xPos, user.yPos);
	    	  int [] pos2 = findBlock(user.xPos + user.size, user.yPos + user.size);
	    	  
	    	  //initialize values for user2 position
	    	  int [] pos3 = {-5, -5};
	    	  int [] pos4 = {-5, -5};
	    	  
	    	  //determine the block user2 is in if needed
	    	  if (multiPlayer) {
	    		  pos3 = findBlock(user2.xPos, user2.yPos);
	    		  pos4 = findBlock(user2.xPos + user2.size, user2.yPos + user2.size);
	    	  }
	    	  
	    	  //move the slamming walls
		      for (int i = 0; i < allWalls.size(); i++) {
		    	  allWalls.get(i).moveWalls();
		    	  allWalls.get(i).drawWalls(g);
		    	  
		    	  //check if the player has hit any of the walls
		    	  if ((pos1[0] == allWalls.get(i).yPos && pos1[1] == allWalls.get(i).xPos) ||
		    			  (pos2[0] == allWalls.get(i).yPos && pos2[1] == allWalls.get(i).xPos) ||
		    			  (pos1[0] == allWalls.get(i).yPos && pos2[1] == allWalls.get(i).xPos) ||
		    			  (pos2[0] == allWalls.get(i).yPos && pos1[1] == allWalls.get(i).xPos)) {
		    		  user.score -= 2;
		    		  playerRed = true;
		    	  }
		    	  
		    	  //if another player, check if they also hit any walls
		    	  if (multiPlayer) {
		    		  if ((pos3[0] == allWalls.get(i).yPos && pos3[1] == allWalls.get(i).xPos) ||
			    			  (pos4[0] == allWalls.get(i).yPos && pos4[1] == allWalls.get(i).xPos) ||
			    			  (pos3[0] == allWalls.get(i).yPos && pos4[1] == allWalls.get(i).xPos) ||
			    			  (pos4[0] == allWalls.get(i).yPos && pos3[1] == allWalls.get(i).xPos)) {
			    		  user2.score -= 2;
			    		  playerRed2 = true;
			    	  }
		    	  }
		      }
		      
		      //move the lasers
		      for (int i = 0; i < allLasers.size(); i++) {
		    	  allLasers.get(i).moveLaser();
		    	  
		    	  //determine the laser's position
		    	  int [] pos = findBlock(allLasers.get(i).xCoordinate, allLasers.get(i).yCoordinate);
		    	  
		    	  //if the laser is in an open space, draw it on the board, if not change its directions
		    	  if (pos[0] >= 0 && pos[1] >= 0 && (board[pos[0]][pos[1]]).equals(" ") == false)
		    	  	allLasers.get(i).drawLaser(g);
		    	  else
		    		  allLasers.get(i).changeDirection();
		    	  
		    	  //check if the player and laser are overlapping.
		    	  // If they are, reduce the score and turn the player red
		    	  if (allLasers.get(i).xCoordinate < user.xPos + size-1 && 
		    			  allLasers.get(i).xCoordinate + 10 > user.xPos &&
		    			  allLasers.get(i).yCoordinate < user.yPos + size-1 && 
		    			  allLasers.get(i).yCoordinate + 10 > user.yPos) {
		    		  user.score -= 2;
		    		  playerRed = true;
		    	  }
		    	  
		    	  //do the same for the second user if needed
		    	  if (multiPlayer) {
		    		  if (allLasers.get(i).xCoordinate < user2.xPos + size-1 && 
			    			  allLasers.get(i).xCoordinate + 10 > user2.xPos &&
			    			  allLasers.get(i).yCoordinate < user2.yPos + size-1 && 
			    			  allLasers.get(i).yCoordinate + 10 > user2.yPos) {
		    			  user.score -= 2;
			    		  playerRed2 = true;
			    	  }
		    	  }
		    	  
		    	  //turn players red if needed
		    	  if (playerRed) 
		    		  playerColour = Color.RED;
		    	  else 
		    		  playerColour = Color.BLUE;
		    	  
		    	  if (multiPlayer && playerRed2)
		    		  playerColour2 = Color.RED;
		    	  else if (multiPlayer)
		    		  playerColour2 = Color.CYAN;
		      }
	      }
    	  
	      //game over (you won) 
	      else {
	    	  
    		  //border box
	    	  g.setColor(Color.BLACK);
	    	  g.fillRect(150, 75, 300, 300);
	    	  g.setColor(Color.LIGHT_GRAY);
	    	  g.drawRect(150, 75, 300, 300);
	    	  
	    	  //buttons
	    	  g.setColor(Color.WHITE);
	    	  g.fill3DRect(190, 200, 100, 50, true);
	    	  g.fill3DRect(315, 200, 100, 50, true);
	    	  g.setFont(new Font("Britannic Bold", Font.PLAIN, 18));
	    	  g.setColor(Color.BLACK);
	    	  g.drawString("New Game", 195, 230);
	    	  g.drawString("Main Menu", 320, 230);
	    	  
	    	  //title
	    	  g.setFont(new Font("Britannic Bold", Font.BOLD, 50));
	    	  g.setColor(Color.RED);
	    	  g.drawString("YOU WON!", 180, 175);
	    	  
	    	  //drawing at bottom 
	    	  g.setColor(Color.BLUE);
	    	  g.fillRect(315, 300, 50, 50);
	    	  g.drawLine(200, 325, 295, 325);
	    	  g.drawLine(250, 310, 300, 310);
	    	  g.drawLine(225, 340, 300, 340);
	    	  
	    	  //display score
	    	  g.setColor(Color.WHITE);
	    	  g.setFont(new Font("Britannic Bold", Font.PLAIN, 14));
	    	  g.drawString(String.format("SCORE: %.2f", user.score), 250, 270);
	    	  g.drawString(String.format("HIGH SCORE: %.2f", StoreData.highScore), 230, 290);
	      }
	      
	      //add a delay
	      try{Thread.sleep(5);
	      } catch (Exception exc){}
    	  
		  frame.repaint();
	    }
	  }
}