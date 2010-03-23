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

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;
import java.util.Vector;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;
import org.vandopoly.ui.CardPanel;

/*
 * Model class that is a descendant of Space and represents a Community Chest
 * card space on the board
 * 
 * @author Allie Mazzia
 */
public class CommCardSpace extends Space {
	
	Vector<Card> stack_;
	ListIterator<Card> itr;
	ArrayList<Player> players_;
	private static CommCardSpace INSTANCE = null;
	public static final int NUMBER = 2;

	protected CommCardSpace(ArrayList<Player> players) {
		stack_ = new Vector<Card>(NUMBER);
		
		stack_.add(new CardTypeOutOfJail());
		stack_.add(new CardTypeMove("Out late partying. Go directly to the Vandy"
				+ " Van Normal Route", 25));
		stack_.add(new CardTypePayFund("Buy a Rites of Spring ticket. Pay $30", 30));
		
		players_ = players;
		itr = stack_.listIterator();
	}
	
	public static CommCardSpace Instance(ArrayList<Player> players) {
		if (INSTANCE == null) {
			INSTANCE = new CommCardSpace(players);
		}
		
		return INSTANCE;
	}
	
	public String toString() {
		return "Community Chest";
	}
	
	public void landOn(Player p) {
		Card c = drawCard();
		NotificationManager.getInstance().notifyObservers(Notification.SHOW_CARD, c);
		new CardPanel(c);
		
		if (c instanceof CardTypeMove) {
			p.setPosition(((CardTypeMove)c).getSpace());
			//Move piece
		}
		else if (c instanceof CardTypeOutOfJail)
			p.setGetOutOfJail(true);
		else if (c instanceof CardTypePayFund) {
			p.updateCash(-((CardTypePayFund)c).getAmount());
			NotificationManager.getInstance().notifyObservers(Notification.UPDATE_SCHOLARSHIP_FUND, 
					new Integer(((CardTypePayFund)c).getAmount()));
		}
		else if (c instanceof CardTypePayPlayers) {
			//Pay people
			ListIterator<Player> iter = players_.listIterator();
			while (iter.hasNext()) {
				if (iter.next() != p) {
					p.updateCash(-((CardTypePayFund)c).getAmount());
					iter.previous().updateCash(((CardTypePayFund)c).getAmount());
				}
			}
		
		}
		else if (c instanceof CardTypeWinMoney) 
			p.updateCash(((CardTypeWinMoney)c).getAmount());
		else
			System.out.print("Unknown Card type passed to landOn()");
		
	}
	
	public Card drawCard() {
		
		if (itr.hasNext()) {
			return itr.next();
		}
		else {
			while(itr.hasPrevious()) {
				itr.previous();
			}
			return itr.next();
		}
	}
	
	public void shuffleCards() {
		Collections.shuffle(stack_);
	}
	
/*	Debugging Code:
	public static void main(String[] args)  {
		CommCardSpace space = new CommCardSpace();
		space.shuffleCards();
		System.out.println(space.drawCard().getMessage());
		System.out.println(space.drawCard().getMessage());
		System.out.println(space.drawCard().getMessage());
		System.out.println(space.drawCard().getMessage());
		System.out.println(space.drawCard().getMessage());
		System.out.println(space.drawCard().getMessage());
		System.out.println(space.drawCard().getMessage());
		System.out.println(space.drawCard().getMessage());
	}
*/
}
