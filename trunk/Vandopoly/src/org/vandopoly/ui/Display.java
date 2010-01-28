/**
 *  Vandopoly
 *  http://code.google.com/p/vandopoly/
 * 
 * @author James Kasten
 * @date Jan 28, 2009
 * 
 *  Copyright 2010 Vandopoly Team
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software 
 *  distributed under the License is distributed on an "AS IS" BASIS, 
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 *  implied. See the License for the specific language governing 
 *  permissions and limitations under the License. 
 */

package org.vandopoly.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Display extends JFrame {
	
	private JDesktopPane board_;
	
	// Constructor effectively creates JFrame
	public Display () {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screen.width, screen.height);
		this.getContentPane().setBackground(Color.black);
		
		// Soon to be replaced by the specific JDesktopPane Board
		JDesktopPane board_ = new JDesktopPane();
		board_.setBackground(Color.black);
		board_.setSize(screen.width, screen.height);
		board_.setLayout(null);
		// JDesktopBoard should take care of the above remarks
		
		// Set the default close operation for the window, or else the
		// program won't exit when clicking close button
		// (The default is HIDE_ON_CLOSE, which just makes the window
		// invisible, and thus doesn't exit the app)
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		// Setup the DisplayAssembler
		DisplayAssembler.getInstance().setDesktopPane(board_);
		
		this.add(board_);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Display();
	}
}