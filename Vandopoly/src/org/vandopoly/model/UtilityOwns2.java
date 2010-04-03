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
 * UtilityOwns2 class implements the behavior associated with the 
 * utility property space being owned by the same player who owns the other
 * utility property space.
 * ConcreteState class for the State pattern.
 * 
 * @author Allie Mazzia
 */
public class UtilityOwns2 extends UtilityState{

	private static UtilityOwns2 INSTANCE = null;
	
	protected UtilityOwns2() {
		// Exists to disable instantiation
	}
	
	public static UtilityState Instance() {
		if (INSTANCE == null) {
			INSTANCE = new UtilityOwns2();
		}
		
		return INSTANCE;
	}
	
	public void landOn(Player player, UtilitySpace property) {
		//property.getOwner().collectRent(property.getRentValues()[4], player);
		// REnt is 10 times amount shown on dice
	}
	
	protected void ownershipIncrease(PropertySpace p) {
		System.err.println("UtiltiyOwns2 ownershipIncrease called - Not supposed to happen");
	}
	
	protected void ownershipDecrease(PropertySpace p) {
		p.changeState(UtilityOwns1.Instance());
	}
	
	protected String getNameAndStatus() {
		return " (2 Owned)";
	}

}
