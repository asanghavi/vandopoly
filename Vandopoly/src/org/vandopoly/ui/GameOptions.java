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

import org.vandopoly.messaging.NotificationManager;

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
	private JButton continue_, back_, playGame_;
	private DisplayAssembler display;
	private int numberOfPlayers_ = 2, optionsPageNum_ = 1;
	
	
	// Private data members for page 2 of options
	private JRadioButton piece1_1_, piece2_1_, piece3_1_, piece4_1_, 
		piece1_2_, piece2_2_, piece3_2_, piece4_2_,piece1_3_, piece2_3_, 
		piece3_3_, piece4_3_, piece1_4_, piece2_4_, piece3_4_, piece4_4_;
	private JLabel selectPieces_, playerOne_2_, playerTwo_2_, playerThree_2_,
		playerFour_2_;
				
		public GameOptions() {
			
			int frameWidth = 529, frameHeight = 500, titleBarHeight = 159,
				labelHeight = 25;
			

			// Set size of window
			this.setSize(frameWidth, frameHeight);
			
			// Allows for automatic positioning
			this.setLayout(null);
			
			// Center the frame on the user's screen
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

			Point location = new Point((int)(screen.getWidth() - frameWidth) / 2, 
					(int)(screen.getHeight() - frameHeight) / 2);
			
			// Set up the fonts used on this panel
			Font subTitleFont = new Font("fonts/BROADW.TTF", Font.BOLD, 36);
			Font headerFont = new Font("fonts/BROADW.TTF", Font.PLAIN, 20);
			Font radioButtonFont = new Font("fonts/BROADW.TTF", Font.PLAIN, 16);
			Font buttonFont = new Font("fonts/BROADW.TTF", Font.BOLD, 32);
			
			// Set up the title bar along with positioning and size
			JLabel titleBar = new JLabel();
			ImageIcon title = new ImageIcon("images/vandopoly-logo.gif");
			titleBar.setIcon(title);
			titleBar.setBounds(0, 0, frameWidth, titleBarHeight);
			
			// Set up the Options header along with positioning and size
			JLabel subTitleBar = new JLabel("Game Options");
			subTitleBar.setFont(subTitleFont);
			subTitleBar.setBounds((frameWidth / 2) - 90, titleBarHeight, 500, 90);
			
			// Set up the sub-headers & labels along with positioning and size
			playersHeader_ = new JLabel("Number of Players:");
			playersHeader_.setFont(headerFont);
			playersHeader_.setBounds(50, 200, 300, 100);
			
			playerNames_ = new JLabel("Player Names:");
			playerNames_.setFont(headerFont);
			playerNames_.setBounds(frameWidth - 200, 200, 300, 100);
			
			playerOne_ = new JLabel("Player 1: ");
			playerOne_.setFont(radioButtonFont);
			playerOne_.setBounds(frameWidth - 250, 275, 100, labelHeight);
			
			playerTwo_ = new JLabel("Player 2: ");
			playerTwo_.setFont(radioButtonFont);
			playerTwo_.setBounds(frameWidth - 250, 305, 100, labelHeight);
			
			playerThree_ = new JLabel("Player 3: ");
			playerThree_.setFont(radioButtonFont);
			playerThree_.setBounds(frameWidth - 250, 335, 100, labelHeight);
			
			playerFour_ = new JLabel("Player 4: ");
			playerFour_.setFont(radioButtonFont);
			playerFour_.setBounds(frameWidth - 250, 365, 100, labelHeight);
			
			// Labels for page 2
			selectPieces_ = new JLabel("Select your game piece: ");
			selectPieces_.setFont(headerFont);
			selectPieces_.setBounds(50, 200, 300, 100);
			
			playerOne_2_ = new JLabel("Player 1: ");
			playerOne_2_.setFont(radioButtonFont);
			playerOne_2_.setBounds(50, 270, 100, labelHeight);
			
			playerTwo_2_ = new JLabel("Player 2: ");
			playerTwo_2_.setFont(radioButtonFont);
			playerTwo_2_.setBounds(200, 270, 100, labelHeight);
			
			playerThree_2_ = new JLabel("Player 3: ");
			playerThree_2_.setFont(radioButtonFont);
			playerThree_2_.setBounds(350, 270, 100, labelHeight);
			
			playerFour_2_ = new JLabel("Player 4: ");
			playerFour_2_.setFont(radioButtonFont);
			playerFour_2_.setBounds(500, 270, 100, labelHeight);
			
			// Set up and create radio buttons and text fields
			two_ = new JRadioButton("2");
			two_.setBounds(100, 280, 50, labelHeight);
			two_.setFont(radioButtonFont);
			two_.setSelected(true);
			two_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	            	GameOptions.this.showTwo();
	            	numberOfPlayers_ = 2;
	            }
	        });
			
			three_ = new JRadioButton("3");
			three_.setBounds(100, 310, 50, labelHeight);
			three_.setFont(radioButtonFont);
			three_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	                GameOptions.this.showThree();
	                numberOfPlayers_ = 3;
	            }
	        });
			
			four_ = new JRadioButton("4");
			four_.setBounds(100, 340, 50, labelHeight);
			four_.setFont(radioButtonFont);
			four_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	                GameOptions.this.showFour();
	                numberOfPlayers_ = 4;
	            }
	        });
			
			ButtonGroup players_ = new ButtonGroup();
		    players_.add(one_);
		    players_.add(two_);
		    players_.add(three_);
		    players_.add(four_);
		    
			piece1_1_ = new JRadioButton("ICON1");
			piece1_1_.setBounds(100, 300, 150, labelHeight);
			piece1_1_.setFont(radioButtonFont);
			piece1_1_.setSelected(true);
			piece1_1_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	            	// Empty for now
	            }
	        });
			
			piece2_1_ = new JRadioButton("ICON2");
			piece2_1_.setBounds(100, 335, 150, labelHeight);
			piece2_1_.setFont(radioButtonFont);
			piece2_1_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	              // Empty for now
	            }
	        });
			
			piece3_1_ = new JRadioButton("ICON3");
			piece3_1_.setBounds(100, 370, 150, labelHeight);
			piece3_1_.setFont(radioButtonFont);
			piece3_1_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	               // Empty for now
	            }
	        });
			
			piece4_1_ = new JRadioButton("ICON4");
			piece4_1_.setBounds(100, 405, 150, labelHeight);
			piece4_1_.setFont(radioButtonFont);
			piece4_1_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	                // Empty for now
	            }
	        });
			
			ButtonGroup icons1_ = new ButtonGroup();
			icons1_.add(piece1_1_);
			icons1_.add(piece2_1_);
			icons1_.add(piece3_1_);
			icons1_.add(piece4_1_);
		    
			piece1_2_ = new JRadioButton("ICON1");
			piece1_2_.setBounds(250, 300, 150, labelHeight);
			piece1_2_.setFont(radioButtonFont);
			piece1_2_.setSelected(true);
			piece1_2_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	               // Empty for now
	            }
	        });
			
			piece2_2_ = new JRadioButton("ICON2");
			piece2_2_.setBounds(250, 335, 150, labelHeight);
			piece2_2_.setFont(radioButtonFont);
			piece2_2_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	            	// Empty for now
	            }
	        });
			
			piece3_2_ = new JRadioButton("ICON3");
			piece3_2_.setBounds(250, 370, 150, labelHeight);
			piece3_2_.setFont(radioButtonFont);
			piece3_2_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	            	// Empty for now
	            }
	        });
			
			piece4_2_ = new JRadioButton("ICON4");
			piece4_2_.setBounds(250, 405, 150, labelHeight);
			piece4_2_.setFont(radioButtonFont);
			piece4_2_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	            	// Empty for now
	            }
	        });
			
			ButtonGroup icons2_ = new ButtonGroup();
			icons2_.add(piece1_2_);
			icons2_.add(piece2_2_);
			icons2_.add(piece3_2_);
			icons2_.add(piece4_2_);
			
			piece1_3_ = new JRadioButton("ICON1");
			piece1_3_.setBounds(400, 300, 150, labelHeight);
			piece1_3_.setFont(radioButtonFont);
			piece1_3_.setSelected(true);
			piece1_3_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	            	// Empty for now
	            }
	        });
			
			piece2_3_ = new JRadioButton("ICON2");
			piece2_3_.setBounds(400, 335, 150, labelHeight);
			piece2_3_.setFont(radioButtonFont);
			piece2_3_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	            	// Empty for now
	            }
	        });
			
			piece3_3_ = new JRadioButton("ICON3");
			piece3_3_.setBounds(400, 370, 150, labelHeight);
			piece3_3_.setFont(radioButtonFont);
			piece3_3_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	            	// Empty for now
	            }
	        });
			
			piece4_3_ = new JRadioButton("ICON4");
			piece4_3_.setBounds(400, 405, 150, labelHeight);
			piece4_3_.setFont(radioButtonFont);
			piece4_3_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	            	// Empty for now
	            }
	        });
			
			ButtonGroup icons3_ = new ButtonGroup();
			icons3_.add(piece1_3_);
			icons3_.add(piece2_3_);
			icons3_.add(piece3_3_);
			icons3_.add(piece4_3_);
			
			piece1_4_ = new JRadioButton("ICON1");
			piece1_4_.setBounds(550, 300, 150, labelHeight);
			piece1_4_.setFont(radioButtonFont);
			piece1_4_.setSelected(true);
			piece1_4_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	            	// Empty for now
	            }
	        });
			
			piece2_4_ = new JRadioButton("ICON2");
			piece2_4_.setBounds(550, 335, 150, labelHeight);
			piece2_4_.setFont(radioButtonFont);
			piece2_4_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	            	// Empty for now
	            }
	        });
			
			piece3_4_ = new JRadioButton("ICON3");
			piece3_4_.setBounds(550, 370, 150, labelHeight);
			piece3_4_.setFont(radioButtonFont);
			piece3_4_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	            	// Empty for now
	            }
	        });
			
			piece4_4_ = new JRadioButton("ICON4");
			piece4_4_.setBounds(550, 405, 150, labelHeight);
			piece4_4_.setFont(radioButtonFont);
			piece4_4_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	            	// Empty for now
	            }
	        });
			
			ButtonGroup icons4_ = new ButtonGroup();
			icons4_.add(piece1_4_);
			icons4_.add(piece2_4_);
			icons4_.add(piece3_4_);
			icons4_.add(piece4_4_);
			
		    nameOne_ = new JTextField();
		    nameOne_.setFont(radioButtonFont);
		    nameOne_.setBounds(565, 275, 110, labelHeight);
		    
		    nameTwo_ = new JTextField();
		    nameTwo_.setFont(radioButtonFont);
		    nameTwo_.setBounds(565, 305, 110, labelHeight);
		    
		    nameThree_ = new JTextField();
		    nameThree_.setFont(radioButtonFont);
		    nameThree_.setBounds(565, 335, 110, labelHeight);
		    
		    nameFour_ = new JTextField();
		    nameFour_.setFont(radioButtonFont);
		    nameFour_.setBounds(565, 365, 110, labelHeight);
		    
			continue_ = new JButton("Continue");
			continue_.setBounds(115, 500, 500, 75);
			continue_.setFont(buttonFont);
			continue_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	            	GameOptions.this.hideFirstPagePanels();
	            	GameOptions.this.showSecondPagePanels();
	            	optionsPageNum_ = 2;
	            }
	        });		    
			
			back_ = new JButton("Back");
			back_.setBounds(115, 580, 500, 75);
			back_.setFont(buttonFont);
			back_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	            	if (optionsPageNum_ == 1)
	            		GameOptions.this.backToMain();
	            	else
	            		GameOptions.this.backToFirstPage();
	            }
	        });		
			
			playGame_ = new JButton("PLAY GAME");
			playGame_.setBounds(115, 500, 500, 75);
			playGame_.setFont(buttonFont);
			playGame_.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
	            	NotificationManager.getInstance().notifyObservers("StartGame", null);
	            	//GameOptions.this.hideSecondPagePanels();
	            	//GameOptions.this.setVisible(false);
	            	
	            }
	        });	

			// Add some Components to the panel
			this.add(titleBar);
			this.add(subTitleBar);
			this.add(playersHeader_);
			this.add(playerNames_);
			this.add(playerOne_);
			this.add(playerTwo_);
			this.add(playerThree_);
			this.add(playerFour_);
			this.add(two_);
			this.add(three_);
			this.add(four_);
			this.add(nameOne_);
			this.add(nameTwo_);
			this.add(nameThree_);
			this.add(nameFour_);
			this.add(continue_);	
			this.add(back_);
			this.add(playGame_);
			this.add(piece1_1_);
			this.add(piece2_1_);
			this.add(piece3_1_);
			this.add(piece4_1_);
			
			this.add(piece1_2_);
			this.add(piece2_2_);
			this.add(piece3_2_);
			this.add(piece4_2_);
			
			this.add(piece1_3_);
			this.add(piece2_3_);
			this.add(piece3_3_);
			this.add(piece4_3_);
			
			this.add(piece1_4_);
			this.add(piece2_4_);
			this.add(piece3_4_);
			this.add(piece4_4_);
			
			this.add(selectPieces_);
			this.add(playerOne_2_);
			this.add(playerTwo_2_);
			this.add(playerThree_2_);
			this.add(playerFour_2_);
			
			// Add the Panel to the display
			display = DisplayAssembler.getInstance();
			display.addComponent(this, location, JLayeredPane.POPUP_LAYER);
	
			// Set the visibility as true, thereby displaying it
			this.setVisible(true);
			
			this.hideSecondPagePanels();
		}
		
		public void hideFirstPagePanels() {
			playerOne_.setVisible(false);
			nameOne_.setVisible(false);
			playerTwo_.setVisible(false);
			nameTwo_.setVisible(false);
			playerThree_.setVisible(false);
			nameThree_.setVisible(false);
			playerFour_.setVisible(false);
			nameFour_.setVisible(false);
			
			two_.setVisible(false);
			three_.setVisible(false);
			four_.setVisible(false);
			
			continue_.setVisible(false);
			playerNames_.setVisible(false);
			playersHeader_.setVisible(false);
		}
		
		public void showFirstPagePanels() {
			playerOne_.setVisible(true);
			nameOne_.setVisible(true);
			playerTwo_.setVisible(true);
			nameTwo_.setVisible(true);
			
			if (numberOfPlayers_ > 2) {
				playerThree_.setVisible(true);
				nameThree_.setVisible(true);
			
				if (numberOfPlayers_ == 4) {
					playerFour_.setVisible(true);
					nameFour_.setVisible(true);
				}
			}
			
			two_.setVisible(true);
			three_.setVisible(true);
			four_.setVisible(true);
			
			continue_.setVisible(true);
			playerNames_.setVisible(true);
			playersHeader_.setVisible(true);
		}
		
		public void showSecondPagePanels() {
			
			selectPieces_.setVisible(true);
			playGame_.setVisible(true);
			
			playerOne_2_.setVisible(true);
			piece1_1_.setVisible(true);
			piece2_1_.setVisible(true);
			piece3_1_.setVisible(true);
			piece4_1_.setVisible(true);
			
			playerTwo_2_.setVisible(true);
			piece1_2_.setVisible(true);
			piece2_2_.setVisible(true);
			piece3_2_.setVisible(true);
			piece4_2_.setVisible(true);
			
			if (numberOfPlayers_ > 2) {
				playerThree_2_.setVisible(true);
				piece1_3_.setVisible(true);
				piece2_3_.setVisible(true);
				piece3_3_.setVisible(true);
				piece4_3_.setVisible(true);
			
				if (numberOfPlayers_ == 4) {
					playerFour_2_.setVisible(true);
					piece1_4_.setVisible(true);
					piece2_4_.setVisible(true);
					piece3_4_.setVisible(true);
					piece4_4_.setVisible(true);
				}
			}
		}
		
		public void hideSecondPagePanels() {
			playerThree_.setVisible(false);
			nameThree_.setVisible(false);
			playerFour_.setVisible(false);
			nameFour_.setVisible(false);	
			
			piece1_1_.setVisible(false);
			piece2_1_.setVisible(false);
			piece3_1_.setVisible(false);
			piece4_1_.setVisible(false);
		
			piece1_2_.setVisible(false);
			piece2_2_.setVisible(false);
			piece3_2_.setVisible(false);
			piece4_2_.setVisible(false);
			
			piece1_3_.setVisible(false);
			piece2_3_.setVisible(false);
			piece3_3_.setVisible(false);
			piece4_3_.setVisible(false);
			
			piece1_4_.setVisible(false);
			piece2_4_.setVisible(false);
			piece3_4_.setVisible(false);
			piece4_4_.setVisible(false);
			
			selectPieces_.setVisible(false);
			playerOne_2_.setVisible(false);
			playerTwo_2_.setVisible(false);
			playerThree_2_.setVisible(false);
			playerFour_2_.setVisible(false);
			
			playGame_.setVisible(false);
		}

		// Methods to display the appropriate number of text boxes	on the
		// first options page 
		public void showTwo() {
			playerThree_.setVisible(false);
			nameThree_.setVisible(false);
			playerFour_.setVisible(false);
			nameFour_.setVisible(false);
			
			nameThree_.setText(null);
			nameFour_.setText(null);
		}
		
		public void showThree() {
			playerThree_.setVisible(true);
			nameThree_.setVisible(true);
			playerFour_.setVisible(false);
			nameFour_.setVisible(false);
			
			nameFour_.setText(null);
		}
		
		public void showFour() {
			playerThree_.setVisible(true);
			nameThree_.setVisible(true);
			playerFour_.setVisible(true);
			nameFour_.setVisible(true);
		}
		
		public void backToMain() {
			
			two_.setSelected(true);	
			nameOne_.setText(null);
			nameTwo_.setText(null);
			nameThree_.setText(null);
			nameFour_.setText(null);
			
			this.setVisible(false);
		}
		
		public void backToFirstPage() {
			hideSecondPagePanels();
			showFirstPagePanels();
			
			optionsPageNum_ = 1;
		}

}
