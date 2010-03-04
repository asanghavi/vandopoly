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
 * UpgradablePropertyLevel0 class implements the behavior associated with the 
 * upgradable property space being owned, but not upgraded. All spaces are in
 * this state immediate after being bought.
 * ConcreteState class for the State pattern.
 * 
 * @author Allie Mazzia
 */

public class UpgradeablePropertyLevel0 extends UpgradeablePropertyState {
	
	private static UpgradeablePropertyLevel0 INSTANCE = null;
	
	protected UpgradeablePropertyLevel0() {
		// Exists to disable instantiation
	}
	
	public static UpgradeablePropertyState Instance() {
		if (INSTANCE == null) {
			INSTANCE = new UpgradeablePropertyLevel0();
		}
		
		return INSTANCE;
	}
	
	public void landOn(Player player, UpgradeablePropertySpace property) {
		property.getOwner().collectRent(property.getRentValues()[0], player);
	}
}
