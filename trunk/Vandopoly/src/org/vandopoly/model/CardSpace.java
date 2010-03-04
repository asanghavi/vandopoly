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
 * Model class that is a descendant of Space and represents a card space on the board
 * All cards are initialized as "Community Chest" (not Chance)
 * 
 * @author Allie Mazzia
 */
public class CardSpace extends Space {
	
	private boolean isChance_;

	public CardSpace() {
		name_ = "NONE";
		isChance_ = false;
	}
	
	public CardSpace(String name, boolean isChance) {
		name_ = name;
		isChance_ = isChance;
	}
	
	public String toString() {
		String string = "Property Name: " + name_;
		string += " On this space: Nobody";
		
		return string;
	}
	
	public void landOn(Player p) {
		// Empty
	}
}
