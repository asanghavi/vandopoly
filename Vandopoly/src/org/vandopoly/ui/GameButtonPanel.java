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

package org.vandopoly.ui;

import javax.swing.JButton;

import org.vandopoly.controller.GameController;

/* 
 * Holds all game control buttons located next to the board in Vandopoly
 * 
 * @author James Kasten
 */
public class GameButtonPanel {
	
	JButton purchase_, mortgage_, endTurn_, quitGame_;
	
	// Will handle all controls in the actual gameController class
	// GameController will have to implement ActionListener
	public GameButtonPanel(GameController controller_) {
		purchase_.setActionCommand("Purchase");
		//purchase_.addActionListener(controller_);
	}

}
