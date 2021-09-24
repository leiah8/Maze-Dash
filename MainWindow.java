/**
 * @author Leiah Nay
 * @version Date: June 9, 2021
 * Maze Dash Game - Start Screen
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class MainWindow {

	public JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		//create the frame
		frame = new JFrame("Maze Dash");
		frame.setBounds(100, 100, 600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.BLACK);
		
		//create the title
		JLabel lblTitle = new JLabel("MAZE DASH");
		lblTitle.setFont(new Font("Britannic Bold", Font.BOLD, 60));
		lblTitle.setForeground(Color.BLUE);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(10, 39, 566, 84);
		frame.getContentPane().add(lblTitle);
		
		//create the single player button
		JButton btnSinglePlayer = new JButton("Single Player");
		btnSinglePlayer.setFont(new Font("Britannic Bold", Font.BOLD, 15));
		btnSinglePlayer.setBackground(Color.BLUE);
		btnSinglePlayer.setForeground(Color.WHITE);
		btnSinglePlayer.setBounds(125, 241, 150, 120);
		frame.getContentPane().add(btnSinglePlayer);
		
		//create the multiplayer button
		JButton btnMultiPlayer = new JButton("Multi Player");
		btnMultiPlayer.setFont(new Font("Britannic Bold", Font.BOLD, 15));
		btnMultiPlayer.setBackground(Color.BLUE);
		btnMultiPlayer.setForeground(Color.WHITE);
		btnMultiPlayer.setBounds(320, 241, 150, 120);
		frame.getContentPane().add(btnMultiPlayer);
		
		//create the instructions button
		JButton btnInstructions = new JButton("Instructions");
		btnInstructions.setFont(new Font("Britannic Bold", Font.BOLD, 15));
		btnInstructions.setBackground(Color.BLUE);
		btnInstructions.setForeground(Color.WHITE);
		btnInstructions.setBounds(222, 151, 150, 60);
		frame.getContentPane().add(btnInstructions);
		
		//go to instructions page when clicked
		btnInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//close frame
				frame.dispose();
				
				//open instructions
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Instructions window = new Instructions();
							window.frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		//create single player game when clicked
		btnSinglePlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//close frame
				frame.dispose();
				
				//open game
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							SingleGame window = new SingleGame(false);
							window.frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		//create two player game when clicked
		btnMultiPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//close frame
				frame.dispose(); 
				
				//open game
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							SingleGame window = new SingleGame(true);
							window.frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
	}
}
