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
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;
import org.vandopoly.model.Player;


/*
 * PlayerPanel will display player names in a JTabbedPane. It will also display
 * player-specific information, like amount of cash and properties owned.
 * 
 * @author Allie Mazzia
 */
public class PlayerPanel extends JPanel {

	Dimension screen_ = Toolkit.getDefaultToolkit().getScreenSize();
	double width_ = screen_.getWidth() - DisplayAssembler.getRightEdge();
	int height_ = screen_.height;
	private JTabbedPane infoPanel_;
	private JPanel panel1_, panel2_, panel3_, panel4_;
	private JLabel properties_, cashLabel_, cashAmount1_, cashAmount2_, 
		cashAmount3_, cashAmount4_;
	//private String names_[];
	private double panelScaleX_ = .80, coordScaleX_ = .1;
	private double panelScaleY_ = .64, coordScaleY_ = .18;
	ArrayList<Player> players_;

	public PlayerPanel(ArrayList<Player> players) {
		
		players_ = players;
		Font nameFont = new Font("broadway", Font.PLAIN, 20);
		
		int paneX = 0, paneY = 0;
		
		this.setSize((int) (panelScaleX_ * width_), (int) (panelScaleY_ * height_));
		this.setLayout(null);

		infoPanel_ = new JTabbedPane();
		infoPanel_.setBounds(paneX, paneY, (int)(panelScaleX_ * width_), (int) (panelScaleY_ * height_));
		infoPanel_.setFont(nameFont);

		// Create all panels
		panel1_ = createPanel(players_.get(0), cashAmount1_);
		panel2_ = createPanel(players_.get(1), cashAmount2_);
		if (players_.size() > 2) {
			panel3_ = createPanel(players_.get(2), cashAmount3_);
			
			if (players_.size() == 4)
				panel4_ = createPanel(players_.get(3), cashAmount4_);
		}
		
		Point location = new Point((int) (coordScaleX_ * width_) + DisplayAssembler.getRightEdge(), 
				(int) (coordScaleY_ * height_));
		DisplayAssembler.getInstance().addComponent(this, location,
				JLayeredPane.PALETTE_LAYER);

        //The following line enables to use scrolling tabs.
        infoPanel_.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        this.add(infoPanel_);
		this.setVisible(true);
		
		NotificationManager.getInstance().addObserver(Notification.UPDATE_PROPERTIES, 
				this, "updateProperties");
		NotificationManager.getInstance().addObserver(Notification.UPDATE_CASH, 
				this, "updateCash");
	}
	
	private JPanel createPanel(Player player, JLabel cashAmount) {
		JPanel panel = new JPanel();
		Font labelFont = new Font("broadway", Font.PLAIN, 16);
		Font cashFont = new Font("broadway", Font.PLAIN, 40);
		
		String cash = "" + player.getCash();
		
		// Set up the JTabbedPane and its JPanels
		int hGap = 10, vGap = 0;
		GridLayout gridLayout = new GridLayout(10, 1, hGap, vGap);
		
		cashLabel_ = new JLabel("Commodore Cash: ");
		cashLabel_.setFont(labelFont);
		cashLabel_.setVerticalAlignment(SwingConstants.BOTTOM);
		cashLabel_.setHorizontalAlignment(SwingConstants.CENTER);
		
		cashAmount = new JLabel(cash);
		cashAmount.setFont(cashFont);
		cashAmount.setVerticalAlignment(SwingConstants.TOP);
		cashAmount.setHorizontalAlignment(SwingConstants.CENTER);
		
		properties_ = new JLabel("Properties Owned: ");
		properties_.setFont(labelFont);
		properties_.setVerticalAlignment(SwingConstants.CENTER);
		properties_.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel.add(cashLabel_);
		panel.add(cashAmount);
		panel.add(properties_);
		
		panel.setLayout(gridLayout);
		
		infoPanel_.addTab(player.getName(), panel);
		return panel;
	}
	
	public void updateCash(Object object) {
		try {
			Player player = (Player) object;
			String cash = "" + player.getCash();
			
			if (player == players_.get(0)) 
				cashAmount1_.setText(cash);
			else if (player == players_.get(1)) 
				cashAmount2_.setText(cash);
			else if (player == players_.get(2))
				cashAmount3_.setText(cash);				
			else if (player == players_.get(3))
				cashAmount4_.setText(cash);
				
		} 
		catch (ClassCastException e) {
			System.err.println("Unexpected object passed to updateCash");
		}
		
	}
	
	public void updateProperties(Object object) {
		try {
			Player player = (Player) object;
			
				
		} 
		catch (ClassCastException e) {
			System.err.println("Unexpected object passed to updateCash");
		}
	}

}

