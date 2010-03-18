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
 * PropertyLevel3 class implements the behavior associated with the 
 * upgradeable property space being upgraded to level 3.
 * ConcreteState class for the State pattern.
 * 
 * @author Allie Mazzia
 */

public class PropertyLevel3 extends PropertyState {
	
	private static PropertyLevel3 INSTANCE = null;
	
	protected PropertyLevel3() {
		// Exists to disable instantiation
	}
	
	public static PropertyState Instance() {
		if (INSTANCE == null) {
			INSTANCE = new PropertyLevel3();
		}
		
		return INSTANCE;
	}

	public void landOn(Player player, PropertySpace property) {
		property.getOwner().collectRent(property.getRentValues()[3], player);
	}
}
