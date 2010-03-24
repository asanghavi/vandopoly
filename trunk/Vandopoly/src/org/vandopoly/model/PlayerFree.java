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

import org.vandopoly.model.PlayerState;

/*
 * PlayerFree class implements the behavior associated with the player 
 * being free, or not in jail.
 * ConcreteState class for the State pattern.
 * 
 * @author Allie Mazzia
 */
public class PlayerFree extends PlayerState{

	private static PlayerFree INSTANCE = null;
	
	protected PlayerFree() {
		// Exists to disable instantiation
	}
	
	public static PlayerState Instance() {
		if (INSTANCE == null) {
			INSTANCE = new PlayerFree();
		}
		
		return INSTANCE;
	}

	@Override
	public void movePiece(Player player, int spaces) {
		player.updatePosition(spaces);
	}
	
	public void movePiece(Player player, Dice dice) {
		player.updatePosition(dice.getTotalRoll());
	}
	
	@Override
	public void collectRent(Player payee, int amount, Player payer) {
		// Call to payee.updateCash(), payer.updateCash() 
		// Temporary - for checking to make sure states are working properly
		payee.updateCash(amount);
		payer.updateCash(-amount);
	}

	@Override
	public void goToJail(Player player) {
		player.changeState(PlayerInJail.Instance());
	}
	
	@Override
	public void getOutOfJail(Player player) {
		// Empty - player is not in jail
	}
}
