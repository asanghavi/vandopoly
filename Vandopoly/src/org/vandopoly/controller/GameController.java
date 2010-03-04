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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;
import org.vandopoly.model.CardSpace;
import org.vandopoly.model.CornerSpace;
import org.vandopoly.model.Dice;
import org.vandopoly.model.Player;
import org.vandopoly.model.PropertySpace;
import org.vandopoly.model.Space;
import org.vandopoly.model.TaxSpace;
import org.vandopoly.model.UpgradeablePropertySpace;
import org.vandopoly.ui.DicePanel;
import org.vandopoly.ui.Display;
import org.vandopoly.ui.GameButtonPanel;
import org.vandopoly.ui.Piece;
import org.vandopoly.ui.PlayerPanel;

/*
 * GameController is meant to handle all complex button/model interactions. 
 * GameController should have access to all real models and control game state.
 * 
 *  @author James Kasten
 */

public class GameController implements ActionListener {
	Dice dice_;
	ArrayList<Player> players_;
	Space board_[];
	int scholarshipFund_;
	
	Display display_;
	DicePanel dicePanel_;
	GameButtonPanel buttonPanel_;
	PlayerPanel playerPanel_;
	ArrayList<Piece> piece_;
	
	String[] namesAndIcons_;
	int numOfPlayers_ = 2;
	
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
	}
	
	// Called by the START_GAME notification
	public void startGame(Object obj) {
		namesAndIcons_ =(String[]) obj;
		numOfPlayers_ = Integer.parseInt(namesAndIcons_[0]);
		
		createSpaces();
		createPlayers();
		
		playerPanel_ = new PlayerPanel(players_);
		dicePanel_ = new DicePanel(dice_);
		buttonPanel_ = new GameButtonPanel(this);
		
		scholarshipFund_ = 0;
	}
	
	// Called by the UpdateScholarship notification
	public void updateFund(Object obj) {
		Integer value = (Integer)obj;
		
		if((scholarshipFund_ += value.intValue()) > 0)
			scholarshipFund_ += value.intValue();
		else
			System.err.println("Attempted to remove more money from scholarship than there currently is");
	}
	
	// Called by the Award Scholarship Fund notification
	public void awardFund(Object obj) {
		Player player = (Player)obj;
		
		player.updateCash(scholarshipFund_);
		scholarshipFund_ = 0;
	}
	
	private void createPlayers() {
		players_ = new ArrayList<Player>();
		piece_ = new ArrayList<Piece>();
		
		for (int i = 0; i < numOfPlayers_; i++) {
			players_.add(new Player(namesAndIcons_[i + 1], namesAndIcons_[numOfPlayers_ + i + 1]));
		}
		
		// Create all of the appropriate pieces
		for (int i = 0; i < numOfPlayers_; i++) {
			//piece_.add(new Piece(namesAndIcons_[numOfPlayers_ + i + 1], (i + 1)));
		}
		
	}
	
	private void createSpaces() {
		
		int start = 0;
		// set 1
		board_[start++] = new CornerSpace("GO");
		board_[start++] = new UpgradeablePropertySpace("Chaffin Place", 200,100, 16, 80, 220, 600, 800, 1000);
		board_[start++] = new UpgradeablePropertySpace("Morgan House", 180, 90, 14, 70, 200, 550, 750, 950);
		board_[start++] = new CardSpace("Community Chest", false);
		board_[start++] = new UpgradeablePropertySpace("Lewis House", 180, 90, 14, 70, 200, 550, 750, 950);
		board_[start++] = new PropertySpace("Vandy Van Long Route", 200, 100);
		board_[start++] = new UpgradeablePropertySpace("Buttrick Hall", 160, 80, 12, 60, 180, 500, 700, 900);
		board_[start++] = new UpgradeablePropertySpace("Wilson Hall", 140, 70, 10, 50, 150, 450, 625, 750);
		board_[start++] = new PropertySpace("CoGeneration Plant", 150, 75);
		board_[start++] = new UpgradeablePropertySpace("Furman Hall", 140, 70, 10, 50, 150, 450, 625, 750);
		
		// set 2
		board_[start++] = new CornerSpace("Go On Academic Probation");
		board_[start++] = new UpgradeablePropertySpace("Kirkland Hall", 220, 110, 18, 90, 250, 700, 875, 1050);
		board_[start++] = new CardSpace("Chance", true);
		board_[start++] = new UpgradeablePropertySpace("Wyatt Center", 220, 110, 18, 90, 250, 700, 875, 1050);
		board_[start++] = new UpgradeablePropertySpace("Featheringill Hall", 240, 120, 20, 100, 300, 750, 925, 1100);
		board_[start++] = new PropertySpace("Vandy Van Normal Route", 200, 100);
		board_[start++] = new UpgradeablePropertySpace("Sarratt Student Center", 260, 130, 22, 110, 330, 800, 975, 1150);
		board_[start++] = new UpgradeablePropertySpace("Student Life Center", 260, 130, 22, 110, 330, 800, 975, 1150);
		board_[start++] = new PropertySpace("BioDiesel Initiative", 150, 75);
		board_[start++] = new UpgradeablePropertySpace("Ingram Center", 280, 140, 24, 120, 360, 850, 1025, 1200);
		
		// set 3
		board_[start++] = new CornerSpace("Academic Probation");
		board_[start++] = new UpgradeablePropertySpace("Murray House", 300, 150, 26, 130, 390, 900, 1100, 1275);
		board_[start++] = new UpgradeablePropertySpace("Stambaugh House", 300, 150, 26, 130, 390, 900, 1100, 1275);
		board_[start++] = new CardSpace("Community Chest", false);
		board_[start++] = new UpgradeablePropertySpace("Hank Ingram House", 320, 160, 28, 150, 450, 1000, 1200, 1400);
		board_[start++] = new PropertySpace("Vandy Van Express Route", 200, 100);
		board_[start++] = new CardSpace("Chance", true);
		board_[start++] = new UpgradeablePropertySpace("McGugin Center", 350, 175, 35, 175, 500, 1100, 1300, 1500);
		board_[start++] = new TaxSpace("Parking Ticket");
		board_[start++] = new UpgradeablePropertySpace("Commons Center", 400, 200, 50, 200, 600, 1400, 1700, 2000);
		
		// set 4
		board_[start++] = new CornerSpace("Scholarship Fund");
		board_[start++] = new UpgradeablePropertySpace("McGill Hall", 120, 30, 8, 40, 100, 300, 450, 600);
		board_[start++] = new UpgradeablePropertySpace("Cole Hall", 100, 30, 6, 30, 90, 270, 400, 550);
		board_[start++] = new CardSpace("Chance", true);
		board_[start++] = new UpgradeablePropertySpace("Tolman Hall", 100, 30, 6,	30,	90,	270, 400, 550);
		board_[start++] = new PropertySpace("Vandy Van Reverse Route", 200, 100);
		board_[start++] = new TaxSpace("Pay Tuition");
		board_[start++] = new UpgradeablePropertySpace("Mims Hall", 60, 30, 4,	20,	60,	180, 320, 450);
		board_[start++] = new CardSpace("Community Chest", false);
		board_[start++] = new UpgradeablePropertySpace("Dyer Hall", 60, 30, 2, 10, 30, 90, 160, 250);
		
		display_.showBoard(board_);
		
	}
	
	// Represents the logic for the GameButtonPanel class
	public void actionPerformed(ActionEvent action) {
		if(action.getActionCommand().equals("Purchase")) {
			int position = players_.get(currentPlayerNum_).getPosition();
			players_.get(currentPlayerNum_).purchase((PropertySpace)board_[0]);
		}
		else if(action.getActionCommand().equals("Mortgage")) {
			//	new PropertySelectionPanel();
			// TODO implement logic for mortgaging property
		}
		else if(action.getActionCommand().equals("End Turn")) {
			currentPlayerNum_ = (currentPlayerNum_ + 1) % numOfPlayers_;
			NotificationManager.getInstance().notifyObservers(Notification.END_TURN, null);
		}
		else if(action.getActionCommand().equals("Quit Game")) {
			System.exit(0);
		}
	}
}
