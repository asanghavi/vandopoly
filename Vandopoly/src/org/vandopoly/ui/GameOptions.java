/*****************************************************************************
 *   Copyright 2010 Vandopoly Team                                           *
 *   Licensed under the Apache License, Version 2.0 (the "License");         *
 *   you may not use this file except in compliance with the License.        *
 *   You may obtain a copy of the License at                                 *
 *                                                                           *
 *   http://www.apache.org/licenses/LICENSE-2.0                              *
 *                                                                           *
 *   Unless required by applicable law or agreed to in writing, software     *
 *   distributed under the License is distributed on an "AS IS" BASIS,       *
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.*
 *   See the License for the specific language governing permissions and     *
 *   limitations under the License.                                          *
 ****************************************************************************/

package org.vandopoly.ui;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * GameOptions JPanel that allows user to customize current game
 * 
 * @author Allie Mazzia
 */
public class GameOptions extends JPanel{
	private JRadioButton one_, two_, three_, four_;
	private JTextField nameOne_, nameTwo_, nameThree_, nameFour_;
	private JLabel playersHeader_, playerNames_, playerOne_, playerTwo_, 
		playerThree_, playerFour_;
	private JButton continue_;
				
		public GameOptions() {
			
			int frameWidth = 730, frameHeight = 750;

			// Set size of window
			this.setSize(frameWidth, frameHeight);
			
			// Allows for automatic positioning
			this.setLayout(null);
			
			// Center the frame on the user's screen
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

			Point location = new Point((int)(screen.getWidth() - frameWidth) / 2, (int)(screen.getHeight() - frameHeight) / 2);
			
			// Set up the fonts used on this panel
			Font subTitleFont = new Font("broadway", Font.BOLD, 36);
			Font headerFont = new Font("broadway", Font.PLAIN, 20);
			Font radioButtonFont = new Font("broadway", Font.PLAIN, 16);
			Font buttonFont = new Font("broadway", Font.BOLD, 32);
			
			// Set up the title bar along with positioning and size
			JLabel titleBar = new JLabel();
			ImageIcon title = new ImageIcon("images/Vandopoly2.jpg");
			titleBar.setIcon(title);
			titleBar.setBounds(0, 0, 730, 200);
			
			// Set up the Options header along with positioning and size
			JLabel subTitleBar = new JLabel("Game Options");
			subTitleBar.setFont(subTitleFont);
			subTitleBar.setBounds(240, 160, 500, 90);
			
			// Set up the sub-headers & labels along with positioning and size
			playersHeader_ = new JLabel("Number of Players:");
			playersHeader_.setFont(headerFont);
			playersHeader_.setBounds(50, 200, 300, 100);
			
			playerNames_ = new JLabel("Player Names:");
			playerNames_.setFont(headerFont);
			playerNames_.setBounds(500, 200, 300, 100);
			
			playerOne_ = new JLabel("Player 1: ");
			playerOne_.setFont(radioButtonFont);
			playerOne_.setBounds(475, 275, 100, 25);
			
			playerTwo_ = new JLabel("Player 2: ");
			playerTwo_.setFont(radioButtonFont);
			playerTwo_.setBounds(475, 300, 100, 25);
			
			playerThree_ = new JLabel("Player 3: ");
			playerThree_.setFont(radioButtonFont);
			playerThree_.setBounds(475, 325, 100, 25);
			
			playerFour_ = new JLabel("Player 4: ");
			playerFour_.setFont(radioButtonFont);
			playerFour_.setBounds(475, 350, 100, 25);
			
			// Set up and create radio buttons and text fields
			one_ = new JRadioButton("1");
			one_.setBounds(100, 275, 50, 25);
			one_.setFont(radioButtonFont);
			one_.setSelected(true);
			one_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	                showOne();
	            }
	        });

			two_ = new JRadioButton("2");
			two_.setBounds(100, 300, 50, 25);
			two_.setFont(radioButtonFont);
			two_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	            	showTwo();
	            }
	        });
			
			three_ = new JRadioButton("3");
			three_.setBounds(100, 325, 50, 25);
			three_.setFont(radioButtonFont);
			three_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	                showThree();
	            }
	        });
			
			four_ = new JRadioButton("4");
			four_.setBounds(100, 350, 50, 25);
			four_.setFont(radioButtonFont);
			four_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	                showFour();
	            }
	        });
			
			ButtonGroup players_ = new ButtonGroup();
		    players_.add(one_);
		    players_.add(two_);
		    players_.add(three_);
		    players_.add(four_);
		    
		    nameOne_ = new JTextField();
		    nameOne_.setFont(radioButtonFont);
		    nameOne_.setBounds(575, 275, 100, 25);
		    
		    nameTwo_ = new JTextField();
		    nameTwo_.setFont(radioButtonFont);
		    nameTwo_.setBounds(575, 300, 100, 25);
		    
		    nameThree_ = new JTextField();
		    nameThree_.setFont(radioButtonFont);
		    nameThree_.setBounds(575, 325, 100, 25);
		    
		    nameFour_ = new JTextField();
		    nameFour_.setFont(radioButtonFont);
		    nameFour_.setBounds(575, 350, 100, 25);
		    
			continue_ = new JButton("Continue");
			continue_.setBounds(115, 550, 500, 100);
			continue_.setFont(buttonFont);
			continue_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	            	removePanels();
	            }
	        });		    

			// Add some Components to the panel
			add(titleBar);
			add(subTitleBar);
			add(playersHeader_);
			add(playerNames_);
			add(playerOne_);
			add(playerTwo_);
			add(playerThree_);
			add(playerFour_);
			add(one_);
			add(two_);
			add(three_);
			add(four_);
			add(nameOne_);
			add(nameTwo_);
			add(nameThree_);
			add(nameFour_);
			add(continue_);		
			
			// Add the Panel to the display
			DisplayAssembler display = DisplayAssembler.getInstance();
			display.addComponent(this, location, JLayeredPane.POPUP_LAYER);
	
			// Set the visibility as true, thereby displaying it
			setVisible(true);
			
		    // Declare add'l text fields as invisible initally
			playerTwo_.setVisible(false);
			nameTwo_.setVisible(false);
			playerThree_.setVisible(false);
			nameThree_.setVisible(false);
			playerFour_.setVisible(false);
			nameFour_.setVisible(false);	
		}
		
		// Can be used to remove all buttons/labels within the GameOptions class
		public void removePanels() {
			playerOne_.setVisible(false);
			nameOne_.setVisible(false);
			playerTwo_.setVisible(false);
			nameTwo_.setVisible(false);
			playerThree_.setVisible(false);
			nameThree_.setVisible(false);
			playerFour_.setVisible(false);
			nameFour_.setVisible(false);
			continue_.setVisible(false);
			one_.setVisible(false);
			two_.setVisible(false);
			three_.setVisible(false);
			four_.setVisible(false);
			playerNames_.setVisible(false);
			playersHeader_.setVisible(false);
		}

		// Methods to display the appropriate number of text boxes
		public void showOne() {
			playerTwo_.setVisible(false);
			nameTwo_.setVisible(false);
			playerThree_.setVisible(false);
			nameThree_.setVisible(false);
			playerFour_.setVisible(false);
			nameFour_.setVisible(false);
		}
		
		public void showTwo() {
			playerTwo_.setVisible(true);
			nameTwo_.setVisible(true);
			playerThree_.setVisible(false);
			nameThree_.setVisible(false);
			playerFour_.setVisible(false);
			nameFour_.setVisible(false);
		}
		
		public void showThree() {
			playerTwo_.setVisible(true);
			nameTwo_.setVisible(true);
			playerThree_.setVisible(true);
			nameThree_.setVisible(true);
			playerFour_.setVisible(false);
			nameFour_.setVisible(false);
		}
		
		public void showFour() {
			playerTwo_.setVisible(true);
			nameTwo_.setVisible(true);
			playerThree_.setVisible(true);
			nameThree_.setVisible(true);
			playerFour_.setVisible(true);
			nameFour_.setVisible(true);
		}

}
