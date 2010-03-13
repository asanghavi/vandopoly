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

import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/*
 * Piece is designed to be the visual representation of a player's piece
 * on the board.  The Piece object listens for notifications to move the piece
 * appropriately
 * 
 * @author James Kasten
 */
public class Piece {
	
	private int currentSpace_;
	int pixelX_, pixelY_;
	private int player_;
	private final int TOTAL_SPACES = 40;
	
	private int pieceSeparation = 50;
	private String name_;
	
	private PieceState state_;
	
	private ImageIcon image_;
	private JLabel icon_;
	
	final static long serialVersionUID = 20;
	
	public Piece(String name, int playerNum) {
		
		name_ = "images/Piece/"+name+".png";
		currentSpace_ = 0;
		player_ = playerNum;
		state_ = PieceLeft.Instance();
		
		icon_ = new JLabel();
		icon_.setIcon(new ImageIcon(name_));
		icon_.setBounds(0,0,55,60);
		icon_.setVisible(true);
		
		if(playerNum == 1) {
			pixelX_ = DisplayAssembler.getTopLeftGo() + 10;
			pixelY_ = 980;
			DisplayAssembler.getInstance().addComponent(icon_, new Point(pixelX_, 
					pixelY_), JLayeredPane.MODAL_LAYER);
		}
		if(playerNum == 2) {
			pixelX_ = DisplayAssembler.getTopLeftGo() + 10;
			pixelY_ = 980 + pieceSeparation;
			DisplayAssembler.getInstance().addComponent(icon_, new Point(pixelX_, 
					pixelY_), JLayeredPane.MODAL_LAYER);
		}
		// TODO: change PlayerRollDie to something more appropriate
		//NotificationManager.getInstance().addObserver("PlayerRollDie", this, "move");
	}
	
	// TODO: Standard move function that moves the GUI the correct number of spaces.
	// based on a roll of the dice
	public void move(final int numSpaces) {
		int oldSpace = currentSpace_;
		currentSpace_ = (currentSpace_ + numSpaces) % TOTAL_SPACES;
		state_.move(this, oldSpace, currentSpace_);
	}
	
	public JLabel getIcon() {
		return icon_;
	}
	
	void changeState(PieceState newState) {
		state_ = newState;
	}
	
	public PieceState getState() {
		return state_;
	}
	
	// TODO: Intended to be used to move to specific spaces, like Jail,
	// or cards that direct the piece to a particular spot
	public void moveToSpace(int spaceNum) {
		
	}
}
