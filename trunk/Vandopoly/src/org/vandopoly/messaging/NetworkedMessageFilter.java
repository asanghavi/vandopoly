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

package org.vandopoly.messaging;

import java.util.ArrayList;

/*
 * NetworkedMessageFilter is a class used to subscribe to all notifications
 * and push them onto a message queue to be sent over the socket connection
 * to the client.
 * 
 * @author Allie Mazzia and Matt Gioia
 */

public class NetworkedMessageFilter {
	ArrayList<NetworkedMessage> messageQueue_ = null;
	
	public NetworkedMessageFilter () {
		messageQueue_ = new ArrayList<NetworkedMessage>(20);
		
		// Subscribe to all notifications and have each call 
		// addToQueue()
		
		String arr[] = new String[10];
		arr[0] = Notification.START_GAME;
		arr[1] = Notification.UPDATE_SCHOLARSHIP_FUND;
		arr[2] = Notification.AWARD_SCHOLARSHIP_FUND;
		arr[3] = Notification.DICE_ANIMATION_DONE;
		arr[4] = Notification.GO_TO_JAIL;
		arr[5] = Notification.CARD_MOVE;
		arr[6] = Notification.UNOWNED_PROPERTY;
		arr[7] = Notification.PIECE_MOVE_SPACES;
		arr[8] = Notification.PIECE_MOVE_TO;
		arr[9] = Notification.ACTION_MESSAGE;
		arr[10] = Notification.UTILITY_RENT;
		
		for(int x = 0; x < arr.length; x++) {
			NetworkedNotificationManager.getInstance().addObserver(arr[x], 
					this, "addToQueue", true);
		}
	}
	
	public void addToQueue(Object obj, String eventName) {
		// Encapsulate each object in a NetworkedMessage and
		// add it to the queue
		
		messageQueue_.add(new NetworkedMessage(eventName, obj));
	}
}
