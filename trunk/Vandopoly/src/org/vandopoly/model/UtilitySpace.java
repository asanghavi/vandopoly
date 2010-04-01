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

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;

/*
 * Model class that is a descendant of PropertySpace and represents a utility space on the board
 * 
 * @author Allie Mazzia
 * 
 * TO-DO: If player owns 2 and mortgages 1, change state in other to UO1 from UO2 
 */
public class UtilitySpace extends PropertySpace {
	private SpaceState state_;
	
	public UtilitySpace() {
		name_ = "NONE";
		purchasePrice_ = 0;
		mortgageValue_ = 0;
		state_ = SpaceUnowned.Instance();
		owner_ = null;
	}
	
	public UtilitySpace(String name, int type, int spaceNumber, int purchasePrice, int mortgageValue) {
		name_ = name;
		type_ = type;
		spaceNumber_ = spaceNumber;
		purchasePrice_ = purchasePrice;
		mortgageValue_ = mortgageValue;
		state_ = SpaceUnowned.Instance();
		owner_ = null;
	}
	
	public String toString() {
		String string = "Property Name: " + name_;
		if (owner_ == null)
			string += ", Owned by: Nobody";
		else
			string += ", Owned by: " + owner_.getName();
		
		return string;
	}
	
	void changeState(SpaceState newState) {
		state_ = newState;
	}
	
	public void landOn(Player p) {
		state_.landOn(p, this);
	}

	// Getters and setters
	public void setOwner(Player p) {
		owner_ = p;
		NotificationManager.getInstance().notifyObservers(Notification.CHANGED_OWNER, this);
	}
	
	public Player getOwner() {
		return owner_;
	}
	
	public void setPurchasePrice(int purchasePrice) {
		purchasePrice_ = purchasePrice;
	}

	public int getPurchasePrice() {
		return purchasePrice_;
	}

	public void setMortgageValue(int mortgageValue) {
		mortgageValue_ = mortgageValue;
	}

	public int getMortgageValue() {
		return mortgageValue_;
	}
	
	public void bePurchased(Player owner) {
		setOwner(owner);
		state_.changeState(this, UtilityOwns1.Instance());
	}
	
	public void beMortgaged() {
		state_.changeState(this, SpaceMortgaged.Instance());
	}
	
	public void unmortgage() {
		state_.changeState(this, UtilityOwns1.Instance());
	}
	
	public SpaceState getState() {
		return state_;
	}
}
