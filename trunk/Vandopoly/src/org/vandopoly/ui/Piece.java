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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.Semaphore;

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
	
	private final int TOTAL_SPACES = 40;
	
	private int pieceSeparation = DisplayAssembler.getSpaceWidth() / 2;
	private String name_;
	
	private PieceState state_;
	
	private JLabel icon_;
	int iconWidth = (DisplayAssembler.getSpaceWidth() * 2 / 3);
	int iconHeight = (60/55) * iconWidth;
	
	// Used to prevent multiple threads competing to move the piece.
	private Semaphore motionControl;
	
	final static long serialVersionUID = 20;
	
	public Piece(String name, int playerNum) {
		
		name_ = "images/Piece/"+name+".png";
		currentSpace_ = 0;

		state_ = PieceLeft.Instance();
		
		icon_ = new JLabel();
		//icon_.setIcon(new ImageIcon(name_));
		
		try {
			icon_.setIcon(new ImageIcon(Display.scaleImage(new FileInputStream(name_),
					iconWidth, iconHeight)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		icon_.setBounds(0,0,iconWidth,iconHeight);
		icon_.setVisible(true);
		
		if(playerNum == 1) {
			pixelX_ = DisplayAssembler.getTopLeftGo() + 2;
			pixelY_ = DisplayAssembler.getTopLeftGo()+ (int)(DisplayAssembler.getSpaceWidth() * .35);
			DisplayAssembler.getInstance().addComponent(icon_, new Point(pixelX_, 
					pixelY_), JLayeredPane.MODAL_LAYER);
		}
		else if(playerNum == 2) {
			pixelX_ = DisplayAssembler.getTopLeftGo() + 2;
			pixelY_ = DisplayAssembler.getTopLeftGo() + (int)(DisplayAssembler.getSpaceWidth() * .35) + pieceSeparation;
			DisplayAssembler.getInstance().addComponent(icon_, new Point(pixelX_, 
					pixelY_), JLayeredPane.MODAL_LAYER);
		}
		else if(playerNum == 3) {
			pixelX_ = DisplayAssembler.getTopLeftGo() + 2 + pieceSeparation;
			pixelY_ = DisplayAssembler.getTopLeftGo()+ (int)(DisplayAssembler.getSpaceWidth() * .35);
			DisplayAssembler.getInstance().addComponent(icon_, new Point(pixelX_, 
					pixelY_), JLayeredPane.MODAL_LAYER);
		}
		else if(playerNum == 4) {
			pixelX_ = DisplayAssembler.getTopLeftGo() + 2 + pieceSeparation;
			pixelY_ = DisplayAssembler.getTopLeftGo() + (int)(DisplayAssembler.getSpaceWidth() * .35) + pieceSeparation;
			DisplayAssembler.getInstance().addComponent(icon_, new Point(pixelX_, 
					pixelY_), JLayeredPane.MODAL_LAYER);
		}
		
		motionControl = new Semaphore(1);
	}
	
	// TODO: Standard move function that moves the GUI the correct number of spaces.
	// based on a roll of the dice
	public void move(final int numSpaces) {
		new Thread("PieceMovement") {
			public void run() {
				try {
					// Must acquire the lock before moving...
					// This prevents multiple movements at the same time that result
					// in glitches
					motionControl.acquire();
					
					// Update current piece spaces
					final int oldSpace = currentSpace_;
					currentSpace_ = (currentSpace_ + numSpaces) % TOTAL_SPACES;
					
					// Call on proper state to move the piece
					state_.move(Piece.this, oldSpace, currentSpace_);
					
					// Must release lock
					motionControl.release();
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		
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
		int numSpaces = spaceNum + TOTAL_SPACES - currentSpace_;
		move(numSpaces);
	}
}
