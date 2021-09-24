/**
 * @author Leiah Nay
 * @version Date: June 9, 2021
 * MazeDash - Instructions Page
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Instructions {
	
	//initialize components
	public JFrame frame;
	private ShowGraphics graphicsPanel;
	private JButton btnNext, btnBack;
	
	//initialize image
	BufferedImage img1;

	
	//initialize value to determine which page the user is on
	private int imgNum = 0;
	
	/**
	 * Create the application.
	 */
	public Instructions() {
		initialize();
	}
	
	/**
	 * Draw the first page
	 * @param g 	Graphics 
	 * @param g2d	Graphics in 2D
	 */
	public void drawOne(Graphics g, Graphics2D g2d) {	
		//draw circle around player
		g.setColor(Color.BLUE);
		g2d.setStroke(new BasicStroke(5));
		g.drawOval(47, 85, 50, 50);
		
		//explain player movement
		g.setColor(Color.BLACK);
		g.drawString("You are the blue square. You can move the square using the arrow keys." , 5, 320);
		g.drawString("In multiplayer, the second user moves their square using the AWSD keys.", 5, 335);
	}
	
	/**
	 * Draw the second page
	 * @param g 	Graphics 
	 * @param g2d	Graphics in 2D
	 */
	public void drawTwo(Graphics g, Graphics2D g2d) {
		//create circles around obstacles
		g.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(5));
		g.drawOval(90, 115, 50, 50);
		g.drawOval(213, 102, 25, 25);
		g.drawOval(172, 102, 25, 25);
		g.drawOval(202, 84, 25, 25);
		
		//explain obstacles
		g.setColor(Color.BLACK);
		g.drawString("Your goal is to reach the end of the maze while avoiding the obstacles." , 7, 320);
		g.drawString("There are lazers and slamming walls. If you get hit, you will lose points.", 7, 335);
	}
	
	/**
	 * Draw the third page
	 * @param g 	Graphics 
	 * @param g2d	Graphics in 2D
	 */
	public void drawThree(Graphics g, Graphics2D g2d) {
		//create arrow pointing at the score
		g2d.setStroke(new BasicStroke(5));
		g.setColor(Color.BLUE);
		g.drawLine(25, 25, 25, 100);
		int [] xPoints = {25, 15, 35};
		int [] yPoints = {20, 33, 33};
		g.fillPolygon(xPoints, yPoints, 3);
		
		//explain score
		g.setColor(Color.BLACK);
		g.drawString("Your score can be seen in the top right corner." , 83, 320);
		
	}
	
	/**
	 * Draw the fourth page
	 * @param g 	Graphics 
	 * @param g2d	Graphics in 2D
	 */
	public void drawFour(Graphics g, Graphics2D g2d) {
		//create arrow pointing at the pause button
		g2d.setStroke(new BasicStroke(5));
		g.setColor(Color.BLUE);
		g.drawLine(385, 40, 385, 140);
		int [] xPoints = {385, 375, 395};
		int [] yPoints = {35, 47, 47};
		g.fillPolygon(xPoints, yPoints, 3);
		
		//explain pause
		g.setColor(Color.BLACK);
		g.drawString("You can pause the game by clicking the pause button or" , 50, 320);
		g.drawString("by pressing the space bar.", 130, 335);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		//create the frame
		frame = new JFrame("Maze Dash - Instructions");
		frame.setBounds(100, 100, 600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.BLACK);
		
		//create the graphics panel
		graphicsPanel = new ShowGraphics();
		graphicsPanel.setBounds(100, 25, 400, 350);
		frame.getContentPane().add(graphicsPanel);
		
		//create the back button
		btnBack = new JButton("Back");
		btnBack.setBounds(200, 390, 90, 25);
		btnBack.setFont(new Font("Britannic Bold", Font.BOLD, 15));
		btnBack.setBackground(Color.BLUE);
		btnBack.setForeground(Color.WHITE);
		frame.getContentPane().add(btnBack);
		
		//decrement the imgNum when back is clicked
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (imgNum > 0)
					imgNum--;
				frame.repaint();
			}
		});
		
		//create the next button
		btnNext = new JButton("Next");
		btnNext.setBounds(310, 390, 90, 25);
		btnNext.setFont(new Font("Britannic Bold", Font.BOLD, 15));
		btnNext.setBackground(Color.BLUE);
		btnNext.setForeground(Color.WHITE);
		frame.getContentPane().add(btnNext);
		
		//increment imgNum when the next button is clicked
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (imgNum < 3)
					imgNum++;
				
				frame.repaint();
			}
		});
		
		//create the main menu button
		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.setBounds(10, 419, 112, 33);
		btnMainMenu.setFont(new Font("Britannic Bold", Font.BOLD, 15));
		btnMainMenu.setBackground(Color.BLUE);
		btnMainMenu.setForeground(Color.WHITE);
		frame.getContentPane().add(btnMainMenu);
		
		//return to main menu when clicked
		btnMainMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();

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
		});
	}
	
	public class ShowGraphics extends JPanel {
		/**
		 * Graphics Panel 
		 */
	    public void paintComponent(Graphics g) { 
	      super.paintComponent(g);
	      Graphics2D g2d = (Graphics2D) g;
	      
	      //retrieve the image
	      try {
	    	  img1 = ImageIO.read(new File("image1.jpg"));
	      } catch(Exception e) {}
	      
	      //draw the image
	      g.drawImage(img1, 0, 5, null);
		
	      //determine which page is being shown
	      if (imgNum == 0)
		      	drawOne(g, g2d);
	      else if (imgNum == 1)
	    	  drawTwo(g, g2d);
	      else if (imgNum == 2) 
	    	  drawThree(g, g2d);
	      else if (imgNum == 3)
	    	  drawFour(g, g2d);
	      
	      //draw border
	      g.setColor(Color.BLACK);
	      g.drawRect(0, 0, 400, 349);
	      
	      //draw page number
	      g.setFont(new Font(null, Font.BOLD, 9));
	      g.drawString(String.format("%d/4", imgNum+1), 383, 345);
	      
	    }
	}
}
