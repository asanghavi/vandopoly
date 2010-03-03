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

package org.vandopoly.model;

import org.vandopoly.ui.Display;

/*
 * Player class is a model class that represents a game player.
 * Context class for the State pattern
 * 
 * @author Allie Mazzia
 */
public class Player {
	
	private final int SPACES_ON_BOARD = 40;
	
	private PlayerState state_;
	private String name_, icon_;
	private int cash_ = 1500, positionOnBoard_;
	private boolean getOutOfJail_;
	
	public Player() {
		state_ = PlayerFree.Instance();
		name_ = "ANONYMOUS";
		icon_ = "NONE";
		cash_ = 1500;
		positionOnBoard_ = 0;
		getOutOfJail_ = false;
	}
	
	public Player(String name, String icon) {
		state_ = PlayerFree.Instance();
		name_ = name;
		icon_ = icon;
		cash_ = 1500;
		positionOnBoard_ = 0;
		getOutOfJail_ = false;
	}
	
	void changeState(PlayerState newState) {
		state_ = newState;
	}
	
	public PlayerState getState() {
		return state_;
	}
	
	public void movePiece(int numOfSpaces) {
		state_.movePiece(this, numOfSpaces);
	}
	
	public void collectRent(int amount, Player payer) {
		state_.collectRent(this, amount, payer);
	}
	
	public void goToJail() {
		state_.goToJail(this);
	}
	
	public void getOutOfJail() {
		state_.getOutOfJail(this);
	}
	
	public void updatePosition(int numOfSpaces) {
		positionOnBoard_ = (positionOnBoard_ + numOfSpaces) % SPACES_ON_BOARD;
	}
	
	public void setPosition(int space) {
		if(space > 0 && space < SPACES_ON_BOARD)
			positionOnBoard_ = space;
		else
			System.err.println("Invalid space number for setPosition: "+space);
	}
	
	public int getPosition() {
		return positionOnBoard_;
	}
	
	public void updateCash(int value) {
		cash_ += value;
	}
	
	public void setCash(int value) {
		cash_ = value;
	}
	
	public int getCash() {
		return cash_;
	}
	
	public void setGetOutOfJail(boolean hasCard) {
		getOutOfJail_ = hasCard;
	}
	
	public boolean hasGetOutOfJail() {
		return getOutOfJail_;
	}
	
	public void setName(String name) {
		name_ = name;
	}
	
	public String getName() {
		return name_;
	}

	public void setIcon(String icon) {
		icon_ = icon;
	}

	public String getIcon() {
		return icon_;
	}
	
}

