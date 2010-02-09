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


/*  The Notification class is intended to store all known events as public
 *  static strings.  These event strings are intended to be used with the
 *  NotificationManager singleton, so that other classes can simply call
 *  NotificationManager.getInstance().addObserver(Notification.START_GAME,this,update).
 *  
 *  @author James Kasten
 */
public class Notification {
	
	// Game Events
	public static final String START_GAME = "StartGame";
	public static final String ROLL_DICE = "RollDice";
}
