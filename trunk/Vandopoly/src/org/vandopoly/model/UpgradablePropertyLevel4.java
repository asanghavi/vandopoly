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
 * UpgradablePropertyLevel4 class implements the behavior associated with the 
 * upgradable property space being upgraded to level 4.
 * ConcreteState class for the State pattern.
 * 
 * @author Allie Mazzia
 */

public class UpgradablePropertyLevel4 extends UpgradablePropertyState {
	
	private static UpgradablePropertyLevel4 INSTANCE = null;
	
	protected UpgradablePropertyLevel4() {
		// Exists to disable instantiation
	}
	
	public static UpgradablePropertyState Instance() {
		if (INSTANCE == null) {
			INSTANCE = new UpgradablePropertyLevel4();
		}
		
		return INSTANCE;
	}

}
