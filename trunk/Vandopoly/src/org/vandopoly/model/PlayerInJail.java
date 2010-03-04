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
 * PlayerFree class implements the behavior associated with the player 
 * being in jail.
 * ConcreteState class for the State pattern.
 * 
 * @author Allie Mazzia
 */
public class PlayerInJail extends PlayerState {

	private static PlayerInJail INSTANCE = null;
	
	protected PlayerInJail() {
		// Exists to disable instantiation
	}
	
	public static PlayerState Instance() {
		if (INSTANCE == null) {
			INSTANCE = new PlayerInJail();
		}
		
		return INSTANCE;
	}

	@Override
	public void movePiece(Player player, int spaces) {
		// Empty - player does not move when in jail

	}
	
	@Override
	public void collectRent(Player payee, int amount, Player player) {
		// Empty - rent is not collected when players are in jail
	}
	
	@Override
	public void goToJail(Player player) {
		// Empty - player is already in jail
	}
	
	@Override
	public void getOutOfJail(Player player) {
		player.changeState(PlayerFree.Instance());
	}

}