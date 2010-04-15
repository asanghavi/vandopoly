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
 * NetworkedMessageFilter is class used to subscribe to all notifications
 * and push them onto a message queue to be sent over the socket connection
 * to the client.
 * 
 * @author Allie Mazzia
 */
public class NetworkedMessageFilter {
	ArrayList<NetworkedMessage> messageQueue_ = null;
	
	public NetworkedMessageFilter () {
		messageQueue_ = new ArrayList<NetworkedMessage>(20);
		
		// Subscribe to all notifications and have each call 
		// addToQueue()
	}
	
	public void addToQueue(Object obj) {
		// Encapsulate each object in a NetworkedMessage and
		// add it to the queue
	}
}
