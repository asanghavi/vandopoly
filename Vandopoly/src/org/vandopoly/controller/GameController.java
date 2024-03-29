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

package org.vandopoly.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;
import org.vandopoly.model.ChanceCardSpace;
import org.vandopoly.model.CommCardSpace;
import org.vandopoly.model.CornerSpace;
import org.vandopoly.model.Dice;
import org.vandopoly.model.Player;
import org.vandopoly.model.PlayerInJail;
import org.vandopoly.model.PropertySpace;
import org.vandopoly.model.Space;
import org.vandopoly.model.TaxSpace;
import org.vandopoly.model.UpgradeablePropertySpace;
import org.vandopoly.model.UtilitySpace;
import org.vandopoly.ui.ActionMessage;
import org.vandopoly.ui.DicePanel;
import org.vandopoly.ui.Display;
import org.vandopoly.ui.DisplayAssembler;
import org.vandopoly.ui.GameButtonPanel;
import org.vandopoly.ui.JailPopUp;
import org.vandopoly.ui.MessagePopUp;
import org.vandopoly.ui.Piece;
import org.vandopoly.ui.PlayerPanel;
import org.vandopoly.ui.PropertySelectionPanel;
import org.vandopoly.ui.TradeFrame;

/*
 * GameController is meant to handle all complex button/model interactions. 
 * GameController should have access to all real models and control game state.
 * 
 *  @author James Kasten
 */
public class GameController implements ActionListener {
	Dice dice_;
	ArrayList<Player> players_;
	ArrayList<Piece> pieces_;
	Space board_[];
	int scholarshipFund_;
	
	Display display_;
	DicePanel dicePanel_;
	GameButtonPanel buttonPanel_;
	PlayerPanel playerPanel_;
	PropertySelectionPanel propertySelectionPanel_;
	TradeFrame tradeFrame_;

	String[] namesAndIcons_;
	int numOfPlayers_ = 2;
	
	ChanceCardSpace chance_;
	CommCardSpace commChest_;
	
	final int NUM_OF_SPACES = 40;
	
	// Suggested integer for keeping track of the state of game
	int currentPlayerNum_;
	
	public GameController(Display display) {
		dice_ = new Dice();
		board_ = new Space[NUM_OF_SPACES];
		
		display_ = display;
		
		NotificationManager.getInstance().addObserver(Notification.START_GAME, 
				this, "startGame");
		NotificationManager.getInstance().addObserver(Notification.UPDATE_SCHOLARSHIP_FUND, 
				this, "updateFund");
		NotificationManager.getInstance().addObserver(Notification.AWARD_SCHOLARSHIP_FUND, 
				this, "awardFund");
		NotificationManager.getInstance().addObserver(Notification.DICE_ANIMATION_DONE,
				this, "moveCurrentPlayer");
		NotificationManager.getInstance().addObserver(Notification.GO_TO_JAIL,
				this, "sendPlayerToJail");
		NotificationManager.getInstance().addObserver(Notification.CARD_MOVE, 
				this, "cardMoveTo");
		NotificationManager.getInstance().addObserver(Notification.UNOWNED_PROPERTY, 
				this, "unownedProperty");
		NotificationManager.getInstance().addObserver(Notification.PIECE_MOVE_SPACES, 
				this, "pieceMoveSpaces");
		NotificationManager.getInstance().addObserver(Notification.PIECE_MOVE_TO,
				this, "pieceMoveTo");
		NotificationManager.getInstance().addObserver(Notification.ACTION_MESSAGE,
				this, "displayActionMessage");
		NotificationManager.getInstance().addObserver(Notification.UTILITY_RENT, 
				this, "chargeUtilityRent");
		NotificationManager.getInstance().addObserver(Notification.REMOVE_PLAYER, 
				this, "removePlayer");
		NotificationManager.getInstance().addObserver(Notification.END_TURN, 
				this, "gameOver");
	}
	
	// Called by the START_GAME notification
	public void startGame(Object obj) {
		namesAndIcons_ =(String[]) obj;
		numOfPlayers_ = Integer.parseInt(namesAndIcons_[0]);
		
		createPlayers();
		createSpaces();
			
		shuffleCards();
		
		playerPanel_ = new PlayerPanel(players_);
		dicePanel_ = new DicePanel(dice_);
		buttonPanel_ = new GameButtonPanel(this);
		
		scholarshipFund_ = 500;
	}
	
	public void moveCurrentPlayer(Object obj) {
		
		try {
			Dice dice = (Dice)obj;
			Player currentPlayer = players_.get(currentPlayerNum_);
			
			// Update current position of player model
			currentPlayer.movePiece(dice);
			// Kept for testing purposes
			//currentPlayer.movePiece(1);
			
			/*//Print out some statements that help testing
			System.out.println("Current Player: "+currentPlayer.getName());
			System.out.println("Dice Roll: "+dice.getTotalRoll());
			System.out.println("Giving a position of: "+currentPlayer.getPosition()+
					" which is "+board_[currentPlayer.getPosition()].getName());
			 */
			
			// Have currentPlayer landOn the appropriate board position
			board_[currentPlayer.getPosition()].landOn(currentPlayer);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Same as moveCurrentPlayer except that it expects an Integer to be passed to it
	public void moveCurrentPlayerInteger(Object obj) {
		
		try {
			Integer numSpaces = (Integer)obj;
			Player currentPlayer = players_.get(currentPlayerNum_);
			
			// Update current position of player model
			currentPlayer.movePiece(numSpaces);
			
			// Have currentPlayer landOn the appropriate board position
			board_[currentPlayer.getPosition()].landOn(currentPlayer);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Called by PIECE_MOVE_TO notification
	// Avoids player needing access to piece to allow for unit testing
	public void pieceMoveTo(Object obj) {
		Integer spaceNum = (Integer) obj;
		pieces_.get(currentPlayerNum_).moveToSpace(spaceNum);
	}
	
	// Called by PIECE_MOVE_SPACES notification
	// Avoids player needing access to piece to allow for unit testing
	public void pieceMoveSpaces(Object obj) {
		Integer spaceNum = (Integer) obj;
		pieces_.get(currentPlayerNum_).move(spaceNum);
	}

	public void sendPlayerToJail() {
		players_.get(currentPlayerNum_).goToJail();
	}
	
	// Called by REMOVE_PLAYER to remove the current player
	public void removePlayer() {
		pieces_.get(currentPlayerNum_).removePiece();
		pieces_.remove(currentPlayerNum_);
		players_.remove(currentPlayerNum_);
		playerPanel_.removePanel(currentPlayerNum_);
		numOfPlayers_--;
		
		if(players_.size() == 1) {
			ActionMessage.getInstance().newMessage(players_.get(0).getName() + " Won!");
			buttonPanel_.setAllDisabled();
			dicePanel_.setDisabled();
		} else {
			currentPlayerNum_ = (currentPlayerNum_ + 2) % numOfPlayers_;
			
			disposeFrames();
			
			NotificationManager.getInstance().notifyObservers(Notification.END_TURN, new Integer(currentPlayerNum_));
		}
	}
	
	public void gameOver() {
		if(players_.size() == 1) {
			ActionMessage.getInstance().newMessage(players_.get(0).getName() + " Won!");
			buttonPanel_.setAllDisabled();
			dicePanel_.setDisabled();
		}
	}
	
	// Called by the UpdateScholarship notification
	public void updateFund(Object obj) {
		Integer value = (Integer)obj;
		
		if((scholarshipFund_ + value.intValue()) > 0)
			scholarshipFund_ += value.intValue();
		else
			System.err.println("Attempted to remove more money from scholarship than there currently is");
	}
	
	// Called by the Award Scholarship Fund notification
	public void awardFund(Object obj) {
		Player player = (Player)obj;
		
		new MessagePopUp(player.getName()+" has been awarded $"+scholarshipFund_
				+" from the Scholarship Fund");
		player.updateCash(scholarshipFund_);
		scholarshipFund_ = 500;
	}
	
	private void createPlayers() {
		players_ = new ArrayList<Player>();
		pieces_ = new ArrayList<Piece>();
		
		for (int i = 0; i < numOfPlayers_; i++) {
			players_.add(new Player(i, namesAndIcons_[i + 1]));
			pieces_.add(new Piece(namesAndIcons_[numOfPlayers_ + i + 1], i + 1));
		}
		
		//Enables easy testing
		if(namesAndIcons_[1].equals("test"))
			cheatMode();
	}
	
	// Called by Notification UTILITY_RENT
	public void chargeUtilityRent(Object obj) {
		UtilitySpace property = (UtilitySpace) obj;
		int fee = property.getMultiplier() * dice_.getTotalRoll();
		
		property.getOwner().collectRent(fee, players_.get(currentPlayerNum_));
	}
	
	private void createSpaces() {
		chance_ = ChanceCardSpace.Instance(players_);
		commChest_ = CommCardSpace.Instance(players_);
		
		// set 1
		board_[0] = new CornerSpace("GO");
		board_[1] = new UpgradeablePropertySpace("Dyer Hall", 0, 1, 60, 30, 2, 10, 30, 90, 160, 250);
		board_[2] = commChest_;
		board_[3] = new UpgradeablePropertySpace("Mims Hall", 0, 3, 60, 30, 4, 20,	60,	180, 320, 450);
		board_[4] = new TaxSpace("Pay Tuition");
		board_[5] = new PropertySpace("Vandy Van Reverse Route", 8, 5, 200, 100, 25, 50, 100, 200);
		board_[6] = new UpgradeablePropertySpace("Tolman Hall", 1, 6, 100, 30, 6,	30,	90,	270, 400, 550);
		board_[7] = chance_;
		board_[8] = new UpgradeablePropertySpace("Cole Hall", 1, 8, 100, 30, 6, 30, 90, 270, 400, 550);
		board_[9] = new UpgradeablePropertySpace("McGill Hall", 1, 9, 120, 30, 8, 40, 100, 300, 450, 600);
		board_[10] = new CornerSpace("Academic Probation");
		board_[11] = new UpgradeablePropertySpace("Furman Hall", 2, 11, 140, 70, 10, 50, 150, 450, 625, 750);
		board_[12] = new UtilitySpace("CoGeneration Plant", 9, 12, 150, 75);
		board_[13] = new UpgradeablePropertySpace("Wilson Hall", 2, 13, 140, 70, 10, 50, 150, 450, 625, 750);
		board_[14] = new UpgradeablePropertySpace("Buttrick Hall", 2, 14, 160, 80, 12, 60, 180, 500, 700, 900);
		board_[15] = new PropertySpace("Vandy Van Long Route", 8, 15, 200, 100, 25, 50, 100, 200);
		board_[16] = new UpgradeablePropertySpace("Lewis House", 3, 16, 180, 90, 14, 70, 200, 550, 750, 950);
		board_[17] = commChest_;
		board_[18] = new UpgradeablePropertySpace("Morgan House", 3, 18, 180, 90, 14, 70, 200, 550, 750, 950);
		board_[19] = new UpgradeablePropertySpace("Chaffin Place", 3, 19, 200,100, 16, 80, 220, 600, 800, 1000);
		board_[20] = new CornerSpace("Scholarship Fund");
		board_[21] = new UpgradeablePropertySpace("Kirkland Hall", 4, 21, 220, 110, 18, 90, 250, 700, 875, 1050);
		board_[22] = chance_;
		board_[23] = new UpgradeablePropertySpace("Wyatt Center", 4, 23, 220, 110, 18, 90, 250, 700, 875, 1050);
		board_[24] = new UpgradeablePropertySpace("Featheringill Hall", 4, 24, 240, 120, 20, 100, 300, 750, 925, 1100);
		board_[25] = new PropertySpace("Vandy Van Normal Route", 8, 25, 200, 100, 25, 50, 100, 200);
		board_[26] = new UpgradeablePropertySpace("Sarratt Student Center", 5, 26, 260, 130, 22, 110, 330, 800, 975, 1150);
		board_[27] = new UpgradeablePropertySpace("Student Life Center", 5, 27, 260, 130, 22, 110, 330, 800, 975, 1150);
		board_[28] = new UtilitySpace("BioDiesel Initiative", 9, 28, 150, 75);
		board_[29] = new UpgradeablePropertySpace("Ingram Center", 5, 29, 280, 140, 24, 120, 360, 850, 1025, 1200);
		board_[30] = new CornerSpace("Go On Academic Probation");
		board_[31] = new UpgradeablePropertySpace("Murray House", 6, 31, 300, 150, 26, 130, 390, 900, 1100, 1275);
		board_[32] = new UpgradeablePropertySpace("Stambaugh House", 6, 32, 300, 150, 26, 130, 390, 900, 1100, 1275);
		board_[33] = commChest_;
		board_[34] = new UpgradeablePropertySpace("Hank Ingram House", 6, 34, 320, 160, 28, 150, 450, 1000, 1200, 1400);
		board_[35] = new PropertySpace("Vandy Van Express Route", 8, 35, 200, 100, 25, 50, 100, 200);
		board_[36] = chance_;
		board_[37] = new UpgradeablePropertySpace("McGugin Center", 7, 37, 350, 175, 35, 175, 500, 1100, 1300, 1500);
		board_[38] = new TaxSpace("Parking Ticket");
		board_[39] = new UpgradeablePropertySpace("Commons Center", 7, 39, 400, 200, 50, 200, 600, 1400, 1700, 2000);
		
		display_.showBoard(board_);
		
	}
	
	public void shuffleCards() {
		chance_.shuffleCards();
		commChest_.shuffleCards();
	}
	
	// Represents the logic for the GameButtonPanel class
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand().equals("Purchase")) {
			// This stops players from opening a propertySelectionPanel, then buying a property
			// and adjusting the levels inappropriately
			disposeFrames();
			
			int position = players_.get(currentPlayerNum_).getPosition();
			players_.get(currentPlayerNum_).purchase((PropertySpace)board_[position]);
		}
		else if (action.getActionCommand().equals("Manage Properties")) {
			// If the propertySelectionPanel has already been created, dispose and get a new one
			// This makes sure the panel is fully updated, and allows only a single propertySelection
			// panel at a time
			disposeFrames();
			
			propertySelectionPanel_ = new PropertySelectionPanel(players_.get(currentPlayerNum_));
		}
		else if (action.getActionCommand().equals("Trade")) {
			disposeFrames();
			
			tradeFrame_ = new TradeFrame(players_, currentPlayerNum_, false);
		}
		else if (action.getActionCommand().equals("End Turn")) {
			// Change the current player
			currentPlayerNum_ = (currentPlayerNum_ + 1) % numOfPlayers_;
			
			// Get rid of current propertySelectionPanel
			disposeFrames();
			
			NotificationManager.getInstance().notifyObservers(Notification.END_TURN, new Integer(currentPlayerNum_));
			if ((players_.get(currentPlayerNum_).getState() == PlayerInJail.Instance())
					&& (players_.get(currentPlayerNum_).getNumOfRolls() < 2))
				new JailPopUp(players_.get(currentPlayerNum_));
		}
		else if (action.getActionCommand().equals("Quit Game")) {
			confirmationPopUp();
		}
	}
	
	// Kill off all floating frames
	public void disposeFrames() {
		if (propertySelectionPanel_ != null) {
			propertySelectionPanel_.dispose();
			propertySelectionPanel_ = null;
		}
		if (tradeFrame_ != null) {
			tradeFrame_.dispose();
			tradeFrame_ = null;
		}
	}
	
	// Called by Notification CARD_MOVE_TO
	public void cardMoveTo(Object obj) {
		System.out.println("cardmoveTo");
		Integer num = (Integer)obj;
		board_[(int)num].landOn(players_.get(currentPlayerNum_));
		
	}
	
	// Used to create the pop-up window to confirm quitting the game
	public void confirmationPopUp() {
		JPanel basePanel = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		
		//Create layouts and buttons
		int hGap = 10, vGap = 10;
		GridLayout baseGridLayout = new GridLayout(2, 1, hGap, vGap);
		GridLayout gridLayout1 = new GridLayout(1, 1, 0, 0);
		GridLayout gridLayout2 = new GridLayout(1, 2, 0, 0);
		
		basePanel.setLayout(null);
		panel1.setLayout(null);
		panel2.setLayout(null);
		Font labelFont = new Font("broadway", Font.PLAIN, 20);
		Font buttonFont = new Font("broadway", Font.PLAIN, 18);
		Font titleFont = new Font("broadway", Font.PLAIN, 14);
		
		JLabel label = new JLabel("Are you sure you want to quit?");
		label.setFont(labelFont);
		panel1.setLayout(gridLayout1);
		panel1.add(label);
		panel1.setBackground(new Color(236, 234, 166));
		
		JButton quit = new JButton("Quit");
		quit.setFont(buttonFont);
		quit.setVisible(true);
		
		JButton cancel = new JButton("Cancel");
		cancel.setFont(buttonFont);
		cancel.setVisible(true);
		
		panel2.add(quit);
		panel2.add(cancel);
		panel2.setLayout(gridLayout2);
		
		basePanel.setLayout(baseGridLayout);
		basePanel.setBackground(new Color(236, 234, 166));
		
		Border blackline = BorderFactory.createLineBorder(Color.black, 2);
		TitledBorder title = BorderFactory.createTitledBorder(
			       blackline, "Quit Game");
		title.setTitleFont(titleFont);
		title.setTitleJustification(TitledBorder.LEFT);
		basePanel.setBorder(title);
		basePanel.add(panel1);
		basePanel.add(panel2);
		basePanel.setVisible(true);
	
		int xCoord = (int)(DisplayAssembler.getScreenWidth() - 
				basePanel.getPreferredSize().getWidth()) / 2;
		int yCoord = (int)(DisplayAssembler.getScreenHeight() - 
				basePanel.getPreferredSize().getHeight()) / 2;
		
		PopupFactory factory = PopupFactory.getSharedInstance();
		final Popup popup = factory.getPopup(null, basePanel, xCoord, yCoord);
		
		quit.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
	        	popup.hide();
	        	NotificationManager.getInstance().notifyObservers(Notification.REMOVE_CARD, null);
	        	System.exit(0);
	        }
		});
		cancel.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
	        	popup.hide();
	        	NotificationManager.getInstance().notifyObservers(Notification.REMOVE_CARD, null);
	        }
	    });
	
		popup.show();	
		NotificationManager.getInstance().notifyObservers(Notification.SHOW_CARD, null);
	}
	
	public void displayActionMessage(Object obj) {
		try {
			String message = (String) obj;
			ActionMessage.getInstance().newMessage(message);
			
		}
		catch (ClassCastException e) {
			System.err.println("Unknown object passed to method displayActionMessage");
		}
	}
	
	//Called by the UNOWNED_PROPERTY notification to display a pop-up window
	public void unownedProperty(Object obj) {
		try {
			PropertySpace property = (PropertySpace) obj;
			new MessagePopUp(property.getName() + " is unowned. To purchase it for $"
					+ property.getPurchasePrice() + ", use the purchase button.");
		} 		
		catch (ClassCastException e) {
			System.err.println("Unknown object passed to method unownedProperty");
		}
	}
	
	// Called if Player 1's name is equal to "test"
	private void cheatMode() {
		int totalX = 150;
		int buttonY = 20;
		
		final JTextField cheatSpaces = new JTextField();
		cheatSpaces.setBounds(0, 0, (totalX * 2 / 3), buttonY);
		cheatSpaces.setVisible(true);
		
		// Go button effectively rolls the dice the number given in the text field
		JButton go = new JButton("Go");
		go.setBounds(0, 0, (totalX / 3), buttonY);
		go.setVisible(true);
		
		go.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int num = Integer.parseInt(cheatSpaces.getText());
				moveCurrentPlayerInteger(num);
			}
		});
		
		// Creates an End Turn button to change players
		JButton endTurn = new JButton("End Turn");
		endTurn.setBounds(0, 0, totalX, buttonY);
		endTurn.setVisible(true);
		
		// Simulates End Turn button in GameButtonPanel
		endTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				currentPlayerNum_ = (currentPlayerNum_ + 1) % numOfPlayers_;
				NotificationManager.getInstance().notifyObservers(Notification.END_TURN, new Integer(currentPlayerNum_));
				if (players_.get(currentPlayerNum_).getState() == PlayerInJail.Instance())
					new JailPopUp(players_.get(currentPlayerNum_));
			}
		});
		
		// Add the cheat control bar to display assembler
		DisplayAssembler.getInstance().addComponent(go, DisplayAssembler.getScreenWidth() - (totalX / 3), 
				DisplayAssembler.getScreenHeight() - (2 * buttonY),
				JLayeredPane.POPUP_LAYER);
		DisplayAssembler.getInstance().addComponent(cheatSpaces, DisplayAssembler.getScreenWidth() - totalX, 
				DisplayAssembler.getScreenHeight() - (2 * buttonY),
				JLayeredPane.POPUP_LAYER);
		DisplayAssembler.getInstance().addComponent(endTurn, DisplayAssembler.getScreenWidth() - totalX, 
				DisplayAssembler.getScreenHeight() - buttonY,
				JLayeredPane.POPUP_LAYER);
	}
}
