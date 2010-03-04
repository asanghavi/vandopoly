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
 * UpgradablePropertyMortgaged class implements the behavior associated with the 
 * upgradable property space being owned and mortgaged.
 * ConcreteState class for the State pattern.
 * 
 * @author Allie Mazzia
 */

public class UpgradeablePropertyMortgaged extends UpgradeablePropertyState {
	
	private static UpgradeablePropertyMortgaged INSTANCE = null;
	
	protected UpgradeablePropertyMortgaged() {
		// Exists to disable instantiation
	}
	
	public static UpgradeablePropertyState Instance() {
		if (INSTANCE == null) {
			INSTANCE = new UpgradeablePropertyMortgaged();
		}
		
		return INSTANCE;
	}

	public void landOn(Player player, UpgradeablePropertySpace property) {
		//Do nothing as the space is mortgaged
	}
}