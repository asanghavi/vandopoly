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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;

/*
 * NetworkedGame JPanel that allows user to customize current game
 * 
 * @author Matt Gioia
 */

public class NetworkedGame extends JPanel {

	private JRadioButton one_;
	private JTextField nameOne_, gameIp_;
	private JLabel playerOne_;
	private JButton continue_, back_, playGame_, createGame_, joinGame_;
	private DisplayAssembler display;
	private int numberOfPlayers_ = 1, optionsPageNum_ = 1;
	private String namesAndIcons_[];

	// Private data members for page 2 of options
	// Holds the radio buttons for pieces of each player
	private JRadioButton player_[][];

	private JLabel selectPieces_, player1Piece_, waiting_, loadPanel_, choose_,
			selectGame_;
	private ImageIcon commodoreIcon_, squirrelIcon_, zepposIcon_,
			corneliusIcon_;

	private JLabel repeatError_, longNameError_, noNameError_, pieceError_;

	private ButtonGroup icons_[];

	private int numOfPieces_, maxPlayers_;

	static final long serialVersionUID = 3;

	private MainMenu mainMenu_;

	public NetworkedGame(MainMenu mainMenu) {

		mainMenu_ = mainMenu;

		String commodore = "Commodore";
		String squirrel = "Squirrel";
		String zeppos = "Zeppos";
		String cornelius = "Cornelius";

		int frameWidth = 730, frameHeight = 750;

		// Set size of window
		this.setSize(frameWidth, frameHeight);

		// Allows for automatic positioning
		this.setLayout(null);

		// Center the frame on the user's screen
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		Point location = new Point((int) (screen.getWidth() - frameWidth) / 2,
				(int) (screen.getHeight() - frameHeight) / 2);

		// Set up the fonts used on this panel
		Font subTitleFont = new Font("broadway", Font.PLAIN, 36);
		Font headerFont = new Font("broadway", Font.PLAIN, 20);
		Font radioButtonFont = new Font("broadway", Font.PLAIN, 16);
		Font buttonFont = new Font("broadway", Font.PLAIN, 32);
		Font errorFont = new Font("broadway", Font.BOLD, 20);

		// Set up the title bar along with positioning and size
		JLabel titleBar = new JLabel();
		ImageIcon title = new ImageIcon("images/vandopoly-logo2.gif");
		titleBar.setIcon(title);
		titleBar.setBounds(0, 0, frameWidth, 159);

		// Set up the Options header along with positioning and size
		JLabel subTitleBar = new JLabel("Game Options");
		subTitleBar.setFont(subTitleFont);
		subTitleBar.setBounds(240, 160, 500, 90);

		// Set up the sub-headers & labels along with positioning and size

		playerOne_ = new JLabel("Enter Your Name: ");
		playerOne_.setFont(headerFont);
		playerOne_.setBounds(480, 200, 250, 100);

		selectPieces_ = new JLabel("Select your game piece: ");
		selectPieces_.setFont(headerFont);
		selectPieces_.setBounds(50, 200, 300, 100);

		waiting_ = new JLabel("Waiting for players to connect...");
		waiting_.setFont(headerFont);
		waiting_.setBounds(50, 200, 400, 100);

		selectGame_ = new JLabel("Select a game to join:");
		selectGame_.setFont(headerFont);
		selectGame_.setBounds(50, 200, 400, 100);

		choose_ = new JLabel("Choose an option:");
		choose_.setFont(headerFont);
		choose_.setBounds(50, 200, 300, 100);

		loadPanel_ = new JLabel();
		ImageIcon loading = new ImageIcon("images/loading.gif");
		loadPanel_.setIcon(loading);
		loadPanel_.setBounds(420, 230, 35, 35);

		ButtonGroup players_ = new ButtonGroup();
		players_.add(one_);

		// Now set up all of the piece icons
		commodoreIcon_ = new ImageIcon("images/Piece/Commodore.png");
		squirrelIcon_ = new ImageIcon("images/Piece/Squirrel.png");
		zepposIcon_ = new ImageIcon("images/Piece/Zeppos.png");
		corneliusIcon_ = new ImageIcon("images/Piece/Cornelius.png");

		// Create the labels that will be placed underneath the player choices
		player1Piece_ = new JLabel();
		player1Piece_.setBounds(105, 410, 100, 100);
		this.add(player1Piece_);

		maxPlayers_ = 4;
		numOfPieces_ = 4;

		player_ = new JRadioButton[maxPlayers_][numOfPieces_];
		icons_ = new ButtonGroup[numOfPieces_];

		// Now set up the radio buttons
		player_[0][0] = new JRadioButton(commodore);
		player_[0][0].setBounds(100, 300, 150, 25);
		player_[0][0].setFont(radioButtonFont);
		player_[0][0].addActionListener(new ActionListener() {
			// Deselect all other player selections for the same piece
			public void actionPerformed(ActionEvent event) {
				player1Piece_.setIcon(commodoreIcon_);
			}
		});

		player_[0][1] = new JRadioButton(cornelius);
		player_[0][1].setBounds(100, 335, 150, 25);
		player_[0][1].setFont(radioButtonFont);
		player_[0][1].addActionListener(new ActionListener() {
			// Deselect all other player selections for the same piece
			public void actionPerformed(ActionEvent event) {
				player1Piece_.setIcon(corneliusIcon_);
			}
		});

		player_[0][2] = new JRadioButton(squirrel);
		player_[0][2].setBounds(100, 370, 150, 25);
		player_[0][2].setFont(radioButtonFont);
		player_[0][2].addActionListener(new ActionListener() {
			// Deselect all other player selections for the same piece
			public void actionPerformed(ActionEvent event) {
				player1Piece_.setIcon(squirrelIcon_);
			}
		});

		player_[0][3] = new JRadioButton(zeppos);
		player_[0][3].setBounds(100, 405, 150, 25);
		player_[0][3].setFont(radioButtonFont);
		player_[0][3].addActionListener(new ActionListener() {
			// Deselect all other player selections for the same piece
			public void actionPerformed(ActionEvent event) {
				player1Piece_.setIcon(zepposIcon_);
			}
		});

		icons_[0] = new ButtonGroup();
		icons_[0].add(player_[0][0]);
		icons_[0].add(player_[0][1]);
		icons_[0].add(player_[0][2]);
		icons_[0].add(player_[0][3]);

		// Set action commands for all radio buttons
		// Helps identify proper icon when figuring out which button was pressed

		player_[0][0].setActionCommand(commodore);
		player_[0][1].setActionCommand(cornelius);
		player_[0][2].setActionCommand(squirrel);
		player_[0][3].setActionCommand(zeppos);

		createGame_ = new JButton("Create A Game");
		createGame_.setBounds(220, 300, 300, 60);
		createGame_.setFont(buttonFont);
		createGame_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				NetworkedGame.this.hideSecondPagePanels();
				NetworkedGame.this.showPlayerPagePanels();
			}
		});

		joinGame_ = new JButton("Join A Game");
		joinGame_.setBounds(220, 370, 300, 60);
		joinGame_.setFont(buttonFont);
		joinGame_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				NetworkedGame.this.hideSecondPagePanels();
				NetworkedGame.this.showFourthPagePanels();
			}

		});

		nameOne_ = new JTextField();
		nameOne_.setFont(radioButtonFont);
		nameOne_.setBounds(480, 275, 200, 25);

		gameIp_ = new JTextField();
		gameIp_.setFont(radioButtonFont);
		gameIp_.setBounds(480, 275, 200, 25);

		continue_ = new JButton("Continue");
		continue_.setBounds(115, 500, 500, 75);
		continue_.setFont(buttonFont);
		continue_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				namesAndIcons_ = new String[(2 * numberOfPlayers_) + 1];

				namesAndIcons_[0] = "" + numberOfPlayers_;

				namesAndIcons_[1] = nameOne_.getText();

				/*
				 * namesAndIcons_[2] = nameTwo_.getText();
				 * playerTwo_2_.setText(namesAndIcons_[2]+":");
				 * 
				 * if (numberOfPlayers_ > 2) { namesAndIcons_[3] =
				 * nameThree_.getText();
				 * playerThree_2_.setText(namesAndIcons_[3]+":");
				 * 
				 * if (numberOfPlayers_ == 4) { namesAndIcons_[4] =
				 * nameFour_.getText();
				 * playerFour_2_.setText(namesAndIcons_[4]+":"); } }
				 */

				if (noRepeatNames() && shortNames() && allHaveNames()) {
					NetworkedGame.this.hidePlayerPagePanels();
					NetworkedGame.this.showThirdPagePanels();
				}
			}

			// Check to see if any of the names given are duplicates
			public boolean noRepeatNames() {
				// Had to adjust for loops to compensate for blank first name
				for (int i = 0; i <= numberOfPlayers_; i++) {
					for (int j = i + 1; j <= numberOfPlayers_; j++) {
						if (namesAndIcons_[i].equals(namesAndIcons_[j])) {
							repeatError_.setVisible(true);
							return false;
						}
					}
				}

				repeatError_.setVisible(false);
				return true;
			}

			public boolean shortNames() {
				for (int i = 1; i <= numberOfPlayers_; i++) {
					if (namesAndIcons_[i].length() > 20) {
						longNameError_.setVisible(true);
						return false;
					}
				}
				longNameError_.setVisible(false);
				return true;
			}

			public boolean allHaveNames() {
				for (int i = 1; i <= numberOfPlayers_; i++) {
					if (namesAndIcons_[i].length() == 0) {
						noNameError_.setVisible(true);
						return false;
					}
				}
				noNameError_.setVisible(false);
				return true;
			}
		});

		back_ = new JButton("Back");
		back_.setBounds(115, 580, 500, 75);
		back_.setFont(buttonFont);
		back_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (optionsPageNum_ == 2)
					NetworkedGame.this.backToMain();
				else if (optionsPageNum_ == 3)
					NetworkedGame.this.backToPlayerPage();
				else
					NetworkedGame.this.backToSecondPage();
			}
		});

		playGame_ = new JButton("PLAY GAME");
		playGame_.setBounds(115, 500, 500, 75);
		playGame_.setFont(buttonFont);
		playGame_.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if ((icons_[0].getSelection() == null || icons_[1]
							.getSelection() == null)
							|| (numberOfPlayers_ > 2 && icons_[2]
									.getSelection() == null)
							|| (numberOfPlayers_ > 3 && icons_[3]
									.getSelection() == null)) {
						pieceError_.setVisible(true);
					}

					else {
						pieceError_.setVisible(false);
						// Get all appropriate names for the buttons and puts
						// them in the namesAndIcons_ array
						for (int i = 0; i < numberOfPlayers_; i++) {
							namesAndIcons_[numberOfPlayers_ + i + 1] = icons_[i]
									.getSelection().getActionCommand();
						}
						NotificationManager.getInstance().notifyObservers(
								Notification.START_GAME, namesAndIcons_);
						NetworkedGame.this.hideSecondPagePanels();
						NetworkedGame.this.setVisible(false);
					}
				}
			}
		});

		playGame_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				if ((icons_[0].getSelection() == null || icons_[1]
						.getSelection() == null)
						|| (numberOfPlayers_ > 2 && icons_[2].getSelection() == null)
						|| (numberOfPlayers_ > 3 && icons_[3].getSelection() == null)) {
					pieceError_.setVisible(true);
				} else {
					pieceError_.setVisible(false);
					// Get all appropriate names for the buttons and puts them
					// in the namesAndIcons_ array
					for (int i = 0; i < numberOfPlayers_; i++) {
						namesAndIcons_[numberOfPlayers_ + i + 1] = icons_[i]
								.getSelection().getActionCommand();
					}
					NotificationManager.getInstance().notifyObservers(
							Notification.START_GAME, namesAndIcons_);
					NetworkedGame.this.hideSecondPagePanels();
					NetworkedGame.this.setVisible(false);
				}
			}

		});

		repeatError_ = new JLabel("Each Player must have a unique name");
		repeatError_.setFont(errorFont);
		repeatError_.setForeground(Color.red);
		repeatError_.setBounds(138, 675, 600, 40);
		repeatError_.setVisible(false);

		longNameError_ = new JLabel(
				"Please limit your name to a maximum of 20 characters");
		longNameError_.setFont(errorFont);
		longNameError_.setForeground(Color.red);
		longNameError_.setBounds(25, 675, 700, 40);
		longNameError_.setVisible(false);

		noNameError_ = new JLabel("Please enter a name");
		noNameError_.setFont(errorFont);
		noNameError_.setForeground(Color.red);
		noNameError_.setBounds(187, 675, 700, 40);
		noNameError_.setVisible(false);

		pieceError_ = new JLabel("Please choose a game piece");
		pieceError_.setFont(errorFont);
		pieceError_.setForeground(Color.red);
		pieceError_.setBounds(143, 675, 600, 40);
		pieceError_.setVisible(false);

		// Add some Components to the panel
		this.add(titleBar);
		this.add(subTitleBar);
		this.add(playerOne_);
		this.add(nameOne_);
		this.add(continue_);
		this.add(back_);
		this.add(playGame_);
		this.add(waiting_);
		this.add(loadPanel_);
		this.add(choose_);
		this.add(createGame_);
		this.add(joinGame_);
		this.add(selectGame_);
		this.add(gameIp_);

		for (int j = 0; j < numOfPieces_; j++)
			this.add(player_[0][j]);

		this.add(selectPieces_);

		this.add(longNameError_);
		this.add(noNameError_);
		this.add(pieceError_);

		// Add the Panel to the display
		display = DisplayAssembler.getInstance();
		display.addComponent(this, location, JLayeredPane.POPUP_LAYER);

		// Set the visibility as true, thereby displaying it
		this.setVisible(true);

		this.hidePlayerPagePanels();

		NotificationManager.getInstance().addObserver(Notification.START_GAME,
				this, "backToMain");
	}

	public void hidePlayerPagePanels() {
		playerOne_.setVisible(false);
		nameOne_.setVisible(false);
		selectPieces_.setVisible(false);

		player_[0][0].setVisible(false);
		player_[0][1].setVisible(false);
		player_[0][2].setVisible(false);
		player_[0][3].setVisible(false);
		player1Piece_.setVisible(false);

		continue_.setVisible(false);
		hideThirdPagePanels();
		hideFourthPagePanels();
	}

	public void showPlayerPagePanels() {
		playerOne_.setVisible(true);
		nameOne_.setVisible(true);
		selectPieces_.setVisible(true);

		player_[0][0].setVisible(true);
		player_[0][1].setVisible(true);
		player_[0][2].setVisible(true);
		player_[0][3].setVisible(true);
		player1Piece_.setVisible(true);

		continue_.setVisible(true);
		optionsPageNum_ = 5;
	}

	public void showSecondPagePanels() {
		choose_.setVisible(true);
		createGame_.setVisible(true);
		joinGame_.setVisible(true);
		optionsPageNum_ = 2;
	}

	public void hideSecondPagePanels() {
		choose_.setVisible(false);
		createGame_.setVisible(false);
		joinGame_.setVisible(false);
	}

	public void hideThirdPagePanels() {
		loadPanel_.setVisible(false);
		waiting_.setVisible(false);
		playGame_.setVisible(false);
	}

	public void showThirdPagePanels() {
		playGame_.setVisible(true);
		loadPanel_.setVisible(true);
		waiting_.setVisible(true);
		optionsPageNum_ = 3;
		
		new Thread("startGame") {
			public void run() {
				String data = "Game started";
				try {
				     ServerSocket srvr = new ServerSocket(3913);
				     Socket skt = srvr.accept();
				     System.out.print("Server has connected!\n");
				     PrintWriter out = new PrintWriter(skt.getOutputStream(), true);
				     System.out.print("Sending string: '" + data + "'\n");
				     out.print(data);
				     out.close();
				     skt.close();
				     srvr.close();
				}
				  catch(Exception e) {
				     System.out.print("Error opening socket.\n");
				}
			}
		}.start();
	}

	public void hideFourthPagePanels() {
		selectGame_.setVisible(false);
		gameIp_.setVisible(false);
	}

	public void showFourthPagePanels() {
		selectGame_.setVisible(true);
		gameIp_.setVisible(true);
		optionsPageNum_ = 4;

		try {
			Socket skt = new Socket("localhost", 3913);
			BufferedReader in = new BufferedReader(new InputStreamReader(skt
					.getInputStream()));
			System.out.print("Received string: '");

			while (!in.ready()) {
			}
			System.out.println(in.readLine()); // Read one line and output it

			System.out.print("'\n");
			in.close();
		} catch (Exception e) {
			System.out.print("Can't connect\n");
		}
	}

	public void backToMain() {
		nameOne_.setText(null);
		numberOfPlayers_ = 1;

		longNameError_.setVisible(false);
		repeatError_.setVisible(false);
		noNameError_.setVisible(false);
		mainMenu_.showMenu();
		this.setVisible(false);
	}

	public void backToPlayerPage() {
		hideThirdPagePanels();
		showPlayerPagePanels();
	}

	public void backToSecondPage() {
		hideThirdPagePanels();
		hideFourthPagePanels();
		hidePlayerPagePanels();
		showSecondPagePanels();
	}
}
