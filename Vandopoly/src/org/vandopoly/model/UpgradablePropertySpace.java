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
 * Model class that is a descendant of Space and represents an upgradable-property on the board
 * 
 * @author Allie Mazzia
 */
public class UpgradablePropertySpace extends Space {
	private UpgradablePropertyState state_;
	private int purchasePrice_;
	private int mortgageValue_;
	private int[] rentValues_;
	private int renovationLevel_ = 0;
	private boolean isOwned_ = false, isMortgaged_ = false;
	
	void changeState(UpgradablePropertyState newState) {
		state_ = newState;
	}
	
	public UpgradablePropertyState getState() {
		return state_;
	}
	
	public void landOn(Player p) {
		// Empty
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
		this.rentValues_ = rentValues;
	}

	public int[] getRentValues() {
		return rentValues_;
	}

	public void setRenovationLevel(int renovationLevel) {
		this.renovationLevel_ = renovationLevel;
	}

	public int getRenovationLevel() {
		return renovationLevel_;
	}

	public void setOwned(boolean isOwned) {
		isOwned_ = isOwned;
	}

	public boolean isOwned() {
		return isOwned_;
	}

	public void setMortgaged(boolean isMortgaged) {
		isMortgaged_ = isMortgaged;
	}

	public boolean isMortgaged() {
		return isMortgaged_;
	}


	

}
