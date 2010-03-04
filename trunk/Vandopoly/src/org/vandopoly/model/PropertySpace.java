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
 * Model class that is a descendant of Space and represents a property space on the board
 * 
 * @author Allie Mazzia
 */
public class PropertySpace extends Space {
	private PropertySpaceState state_;
	private int purchasePrice_, mortgageValue_;
	
	private Player owner_;
	
	public PropertySpace() {
		name_ = "NONE";
		purchasePrice_ = 0;
		mortgageValue_ = 0;
		owner_ = null;
	}
	
	public PropertySpace(String name, int purchasePrice, int mortgageValue) {
		name_ = name;
		purchasePrice_ = purchasePrice;
		mortgageValue_ = mortgageValue;
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
	
	void changeState(PropertySpaceState newState) {
		state_ = newState;
	}
	
	public void landOn(Player p) {
		state_.landOn(p, this);
	}

	// Getters and setters
	public void setOwner(Player p) {
		owner_ = p;
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

	public void bePurchased() {
		state_.changeState(this, PropertyOwned.Instance());
	}
	
	public void beMortgaged() {
		state_.changeState(this, PropertyMortgaged.Instance());
	}
	
	public void unmortgage() {
		state_.changeState(this, PropertyOwned.Instance());
	}
}
