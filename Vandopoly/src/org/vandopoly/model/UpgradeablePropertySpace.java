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

/*
 * Model class that is a descendant of Space and represents an 
 * upgradeable-property on the board
 * 
 * @author Allie Mazzia
 */
public class UpgradeablePropertySpace extends PropertySpace {
	private UpgradeablePropertyState state_;
	private int purchasePrice_;
	private int mortgageValue_;
	private int[] rentValues_;
	private Player owner_;
	
	public UpgradeablePropertySpace() {
		name_ = "NONE";
		state_ = UpgradeablePropertyUnowned.Instance();
		purchasePrice_ = 0;
		mortgageValue_ = 0;
		rentValues_ = new int[6];
		owner_ = null;
	}
	
	public UpgradeablePropertySpace(String name, int purchasePrice, int mortgageValue, int rent0, int rent1, 
			int rent2, int rent3, int rent4, int rent5) {
		name_ = name;
		state_ = UpgradeablePropertyUnowned.Instance();
		purchasePrice_ = purchasePrice;
		mortgageValue_ = mortgageValue;
		rentValues_ = new int[6];
		rentValues_[0] = rent0;
		rentValues_[1] = rent1;
		rentValues_[2] = rent2; 
		rentValues_[3] = rent3;
		rentValues_[4] = rent4;
		rentValues_[5] = rent5;
		owner_ = null;
	}
	
	public String toString() {
		String string = "Property Name: " + name_;
		if (owner_ == null)
			string += ", Owned by: Nobody";
		else
			string += ", Owned by: " + owner_;
		
		return string;
	}
	
	void changeState(UpgradeablePropertyState newState) {
		state_ = newState;
	}
	
	public UpgradeablePropertyState getState() {
		return state_;
	}
	
	public void landOn(Player p) {
		state_.landOn(p, this);
	}

	// Getters and setters
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

	public void setRentValues(int[] rentValues) {
		rentValues_ = rentValues;
	}

	public int[] getRentValues() {
		return rentValues_;
	}
	
	public void setOwner(Player p) {
		owner_ = p;
	}
	
	public Player getOwner() {
		return owner_;
	}
	
	public void bePurchased() {
		state_.changeState(this, UpgradeablePropertyLevel0.Instance());
	}
	
	public void beMortgaged() {
		state_.changeState(this, UpgradeablePropertyMortgaged.Instance());
	}
	
	public void unmortgage() {
		state_.changeState(this, UpgradeablePropertyLevel0.Instance());
	}
}
