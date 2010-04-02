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

import java.util.ArrayList;
import java.util.ListIterator;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;
import org.vandopoly.ui.ActionMessage;
import org.vandopoly.ui.Piece;

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
	private int cash_ = 1500, positionOnBoard_, index_;
	private boolean getOutOfJail_;
	private ArrayList<PropertySpace> properties_;
	private Piece piece_;
	//Used to move player out of jail after three rolls
	private int numOfRolls_ = 0;

	protected Player() {
		state_ = PlayerFree.Instance();
		name_ = "ANONYMOUS";
		icon_ = "NONE";
		cash_ = 1500;
		index_ = 0;
		positionOnBoard_ = 0;
		getOutOfJail_ = false;
		setProperties(new ArrayList<PropertySpace>());
		numOfRolls_ = 0;
	}

	public Player(int index, String name, String icon, int playerNum) {
		state_ = PlayerFree.Instance();
		name_ = name;
		icon_ = icon;
		cash_ = 1500;
		index_ = index;
		positionOnBoard_ = 0;
		getOutOfJail_ = false;
		piece_ = new Piece(icon, playerNum);
		setProperties(new ArrayList<PropertySpace>());
		numOfRolls_ = 0;
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
	
	public void movePiece(Dice dice) {
		state_.movePiece(this, dice);
	}

	public void collectRent(int amount, Player payer) {
		state_.collectRent(this, amount, payer);
	}

	public void goToJail() {
		setPosition(10);
		state_.goToJail(this);
	}

	public void getOutOfJail() {
		state_.getOutOfJail(this);
	}

	public void updatePosition(int numOfSpaces) {
		if((positionOnBoard_ + numOfSpaces) >= SPACES_ON_BOARD)
			updateCash(200);
		
		piece_.move(numOfSpaces);
		positionOnBoard_ = (positionOnBoard_ + numOfSpaces) % SPACES_ON_BOARD;
	}

	public void setPosition(int space) {
		if (space >= 0 && space < SPACES_ON_BOARD) {
			positionOnBoard_ = space;
			piece_.moveToSpace(space);
		}
		else
			System.err.println("Invalid space number for setPosition: " + space);
	}

	public int getPosition() {
		return positionOnBoard_;
	}

	public void updateCash(int value) {
		cash_ += value;
		NotificationManager.getInstance().notifyObservers(Notification.UPDATE_CASH, this);
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

	public void setProperties(ArrayList<PropertySpace> properties) {
		properties_ = properties;
	}

	public ArrayList<PropertySpace> getProperties() {
		return properties_;
	}
	
	public String[] getPropertyArray() {
		String [] array = new String[properties_.size()];
		for (int i = 0; i < properties_.size(); i++) {
			array[i] = properties_.get(i).getNameAndStatus();
		}
		return array;
	}
	
	public int getIndex() {
		return index_;
	}
	
	public void setIndex(int i) {
		index_ = i;
	}
	
	public void setNumOfRolls(int i) {
		numOfRolls_ = i;
	}
	
	public int getNumOfRolls() {
		return numOfRolls_;
	}

	// Adds elements to the property list in the correct order -
	// sorted first by type (color), and then by space number
	public void updateProperties(PropertySpace property) {
		System.out.println("Inserting: " + property.getName() + "type:" + 
				property.getTypeInt() + " Space: " + property.getSpaceNumber());
		if (properties_.size() == 0)
			properties_.add(property);
		else {
			//Start iterator at the end of the list - for proper insertion we must traverse
			// the list from right to left
			ListIterator<PropertySpace> itr = properties_.listIterator(properties_.size());
			PropertySpace tempSpace = null;
			while (itr.hasPrevious()) {
				tempSpace = itr.previous(); 
				//Found properties of the same type, so insert based on space number
				if (property.getTypeInt() == tempSpace.getTypeInt()) {
					if (property.getSpaceNumber() < tempSpace.getSpaceNumber())
						properties_.add(itr.nextIndex(), property);
					else
						properties_.add(1 + itr.nextIndex(), property);
					
					break;
				}
				// Found property with a type less than 'property''s type - add after
				else if (property.getTypeInt() > tempSpace.getTypeInt()) {
					properties_.add(1 + (itr.nextIndex()), property);
					break;
				}
			}
			// Property has the least type on the list, insert at the beginning
			if (property.getTypeInt() < tempSpace.getTypeInt())
				properties_.add(itr.nextIndex(), property);
		}
		NotificationManager.getInstance().notifyObservers
			(Notification.UPDATE_PROPERTIES, this);
	}
	
	public void purchase(PropertySpace property) {
		if(property.getPurchasePrice() < cash_) {
			updateCash(-1 * property.getPurchasePrice());
			updateProperties(property);
			property.bePurchased(this);
			ActionMessage.getInstance().newMessage(name_+" purchased "+property.getName());
			NotificationManager.getInstance().notifyObservers(Notification.DISABLE_PURCHASE, null);
		}
	}

	public void mortgage(PropertySpace property) {
		updateCash(property.getMortgageValue());
		property.beMortgaged();
		NotificationManager.getInstance().notifyObservers
		(Notification.UPDATE_PROPERTIES, this);
	}

	public void unmortgage(PropertySpace property) {
		if (getCash() - property.getMortgageValue() > 0) {
			updateCash(-property.getMortgageValue());
			property.unmortgage();
			NotificationManager.getInstance().notifyObservers
			(Notification.UPDATE_PROPERTIES, this);
		}
		else
			System.out.println("Can't Unmortgage "+getName()+", not enough cash");
	}
}
