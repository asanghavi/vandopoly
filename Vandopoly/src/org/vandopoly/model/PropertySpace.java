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
	private int purchasePrice_;
	
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

}