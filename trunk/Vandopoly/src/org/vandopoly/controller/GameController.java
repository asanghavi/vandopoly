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

import javax.swing.JButton;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;
import org.vandopoly.model.Dice;
import org.vandopoly.ui.DicePanel;

/*
 * GameController is meant to handle all complex button/model interactions. 
 * GameController should have access to all real models and control game state.
 * 
 *  @author James Kasten
 */

public class GameController {
	Dice dice_;
	DicePanel dicePanel_;
	JButton endTurn_;
	
	int buttonCounter = 0;
	
	public GameController() {
		dice_ = new Dice();
		
		NotificationManager.getInstance().addObserver(Notification.START_GAME, this, "startGame");
	}
	
	public void startGame() {
		dicePanel_ = new DicePanel();
		
		dicePanel_.getRollButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (buttonCounter % 2 == 0) {
					dice_.roll();
					dicePanel_.getRollButton().setText("End Turn");
				}
				else {
					NotificationManager.getInstance().notifyObservers(Notification.END_TURN, null);
					dicePanel_.getRollButton().setText("Roll Dice");
				}
				buttonCounter++;
			}
		});
	}
}
