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

import javax.swing.JPanel;

import org.vandopoly.messaging.NotificationManager;

/*
 * Piece is designed to be the visual representation of a player's piece
 * on the board.  The Piece object listens for notifications to move the piece
 * appropriately
 * 
 * @author James Kasten
 */
public class Piece extends JPanel {
	
	int currentSpace_;
	int player_;
	
	final static long serialVersionUID = 20;
	
	public Piece(String name, int playerNum) {
		currentSpace_ = 0;
		
		player_ = playerNum;
		// TODO: change PlayerRollDie to something more appropriate
		NotificationManager.getInstance().addObserver("PlayerRollDie", this, "move");
	}
	
	// TODO: Standard move function that moves the GUI the correct number of spaces.
	// based on a roll of the dice
	public void move(int numSpaces) {
		
	}
	
	// TODO: Intended to be used to move to specific spaces, like Jail,
	// or cards that direct the piece to a particular spot
	public void moveToSpace(int spaceNum) {
		
	}
}
