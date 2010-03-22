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
	ListIterator itr;
	private static CommCardSpace INSTANCE = null;
	public static final int NUMBER = 1;

	protected CommCardSpace() {
		stack_ = new Vector<Card>(NUMBER);
		
		stack_.add(new CardTypeOutOfJail());
	}
	
	public static CommCardSpace Instance() {
		if (INSTANCE == null) {
			INSTANCE = new CommCardSpace();
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
		
		if (c instanceof CardTypeMove) 
			p.setPosition(((CardTypeMove)c).getSpace());
		else if (c instanceof CardTypeOutOfJail)
			p.setGetOutOfJail(true);
		else if (c instanceof CardTypePayFund) {
			p.updateCash(-((CardTypePayFund)c).getAmount());
			NotificationManager.getInstance().notifyObservers(Notification.UPDATE_SCHOLARSHIP_FUND, 
					new Integer(((CardTypePayFund)c).getAmount()));
		}
		else if (c instanceof CardTypePayPlayers) {
			//Pay people
		}
		else if (c instanceof CardTypeWinMoney) 
			p.updateCash(((CardTypeWinMoney)c).getAmount());
		else
			System.out.print("Unknown Card type passed to landOn()");
		
	}
	
	public Card drawCard() {
		
		if (itr.hasNext())
			return (Card) itr.next();
		else {
			while(itr.hasPrevious()) {
				itr.previous();
			}
			return (Card) itr.next();
		}
	}
	
	public void shuffleCards() {
		Collections.shuffle(stack_);
	}
}
