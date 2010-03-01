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
import org.vandopoly.model.Dice;
import org.vandopoly.model.Player;
import org.vandopoly.model.Space;
import org.vandopoly.ui.DicePanel;
import org.vandopoly.ui.GameButtonPanel;
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
	
	DicePanel dicePanel_;
	GameButtonPanel buttonPanel_;
	PlayerPanel playerPanel_;
	
	String[] namesAndIcons_;
	int numOfPlayers_ = 2;
	
	final int NUM_OF_SPACES = 40;
	
	// Suggested integer for keeping track of the state of game
	int currentPlayer_;
	
	public GameController() {
		dice_ = new Dice();
		board_ = new Space[NUM_OF_SPACES];
		
		NotificationManager.getInstance().addObserver(Notification.START_GAME, 
				this, "startGame");
	}
	
	// Called by the START_GAME notification
	public void startGame(Object obj) {
		namesAndIcons_ =(String[]) obj;
		numOfPlayers_ = Integer.parseInt(namesAndIcons_[0]);
		
		playerPanel_ = new PlayerPanel(namesAndIcons_);
		dicePanel_ = new DicePanel(dice_);
		buttonPanel_ = new GameButtonPanel(this);
		
		createSpaces();
		createPlayers();
	}
	
	private void createPlayers() {
		players_ = new ArrayList<Player>();
		for (int i = 0; i < numOfPlayers_; i++) {
			players_.add(new Player(namesAndIcons_[i + 1], namesAndIcons_[numOfPlayers_ + i + 1]));
		}
	}
	
	private void createSpaces() {
		// Either import a file or create each space individually
	}
	
	// Represents the logic for the GameButtonPanel class
	public void actionPerformed(ActionEvent action) {
		if(action.getActionCommand().equals("Purchase")) {
			int position = players_.get(currentPlayer_).getPosition();
			
			// TODO implement logic for purchasing property
			// this will probably need a purchase abstract function in Space
		}
		else if(action.getActionCommand().equals("Mortgage")) {
			int position = players_.get(currentPlayer_).getPosition();

			// TODO implement logic for mortgaging property
		}
		else if(action.getActionCommand().equals("End Turn")) {
			currentPlayer_ = (currentPlayer_ + 1) % numOfPlayers_;
			NotificationManager.getInstance().notifyObservers(Notification.END_TURN, null);
		}
		else if(action.getActionCommand().equals("Quit Game")) {
			System.exit(0);
		}
	}
}
