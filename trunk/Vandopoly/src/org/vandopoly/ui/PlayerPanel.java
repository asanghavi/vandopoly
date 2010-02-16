package org.vandopoly.ui;

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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;


/*
 * PlayerPanel will display player names in a JTabbedPane. It will also display
 * player-specific information, like amount of cash and properties owned.
 * 
 * @author Allie Mazzia
 */
public class PlayerPanel extends JPanel {

	private JTabbedPane infoPanel_;
	private JPanel panel1_, panel2_, panel3_, panel4_;
	private JLabel properties_, cash_;
	private String names_[];

	public PlayerPanel(String[] names) {
		names_ = names;
		Font nameFont = new Font("broadway", Font.PLAIN, 20);
		Font labelFont = new Font("broadway", Font.PLAIN, 16);
		
		NotificationManager.getInstance().addObserver(Notification.START_GAME,
				this, "getNames");
		
		int paneHeight = 500, paneWidth = 350, paneX = 0, paneY = 0;
		int rightMargin = 400, topMargin = 200;
		int numOfPlayers = Integer.parseInt(names_[0]);
		
		this.setSize(paneWidth, paneHeight);
		this.setLayout(null);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		// Set up the JTabbedPane and its JPanels
		int hGap = 10, vGap = 10;
		GridLayout gridLayout = new GridLayout(4, 1, hGap, vGap);
	
		infoPanel_ = new JTabbedPane();
		infoPanel_.setBounds(paneX, paneY, paneWidth, paneHeight);
		infoPanel_.setFont(nameFont);

		panel1_ = new JPanel();
		panel1_.setLayout(gridLayout);
		cash_ = new JLabel("Commodore Cash: ");
		cash_.setFont(labelFont);
		cash_.setVerticalAlignment(SwingConstants.TOP);
		cash_.setHorizontalAlignment(SwingConstants.CENTER);
		
		properties_ = new JLabel("Properties Owned: ");
		properties_.setFont(labelFont);
		properties_.setVerticalAlignment(SwingConstants.TOP);
		properties_.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel1_.add(cash_);
		panel1_.add(properties_);
		
		// Add the appropriate number of tabs to the pane, with the
		// player names		
		infoPanel_.addTab(names_[1], panel1_);
		
		infoPanel_.addTab(names_[2], panel2_);
		if (numOfPlayers > 2) {
			infoPanel_.addTab(names_[3], panel3_);
			if (numOfPlayers == 4)
				infoPanel_.addTab(names_[4], panel4_);
		}
		
		Point location = new Point((int) (screen.getWidth() - rightMargin),
				topMargin);
		
		DisplayAssembler.getInstance().addComponent(this, location,
				JLayeredPane.PALETTE_LAYER);

        //The following line enables to use scrolling tabs.
        infoPanel_.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        this.add(infoPanel_);
		this.setVisible(true);
	}
	
	public void getNames(Object obj) {
		// Fill in for notification manager
	}
}

