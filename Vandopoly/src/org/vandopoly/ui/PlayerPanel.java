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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
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
	private JPanel panel_[];
	private JLabel properties_, cashLabel_;
	private JLabel cashAmount_[];
	private JScrollPane scrollPane_[];
	private JList list_[];
	
	private double panelScaleX_ = .80, coordScaleX_ = .1;
	private double panelScaleY_ = .64, coordScaleY_ = .18;
	ArrayList<Player> players_;
	
	Font propertyFont_ = new Font("broadway", Font.PLAIN, 18);

	public PlayerPanel(ArrayList<Player> players) {
		
		players_ = players;
		
		Font nameFont = new Font("broadway", Font.PLAIN, 20);
		
		int paneX = 0, paneY = 0;
		
		this.setSize((int) (panelScaleX_ * width_), (int) (panelScaleY_ * height_));
		this.setLayout(null);

		infoPanel_ = new JTabbedPane();
		infoPanel_.setBounds(paneX, paneY, (int)(panelScaleX_ * width_), (int) (panelScaleY_ * height_));
		infoPanel_.setFont(nameFont);
		
		// Must create the cashAmount JLabels outside a separate method
		cashAmount_ = new JLabel[players.size()];
		list_ = new JList[players.size()];
		scrollPane_ = new JScrollPane[players.size()];
		panel_ = new JPanel[players.size()];
		for (int i = 0; i < players.size(); i++) {
			cashAmount_[i] = new JLabel("");
			list_[i] = new JList(players.get(i).getProperties().toArray());
			scrollPane_[i] = new JScrollPane();
			list_[i].setBackground(new Color(240, 240, 240));
			list_[i].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list_[i].setSelectionBackground(new Color(250, 250, 250));
			scrollPane_[i].setBackground(new Color(240, 240, 240));
			scrollPane_[i].setBounds(5, 110, (int) (panelScaleX_ * width_) - 10, 
					(int) (panelScaleY_ * height_) - 100);
			list_[i].setFont(propertyFont_);
			scrollPane_[i].setFont(propertyFont_);
		}

		// Create all panels
		panel_[0] = createPanel(players_.get(0), cashAmount_[0], scrollPane_[0], list_[0]);
		panel_[1] = createPanel(players_.get(1), cashAmount_[1], scrollPane_[1], list_[1]);
		
		if (players_.size() > 2) {
			panel_[2] = createPanel(players_.get(2), cashAmount_[2], scrollPane_[2], list_[2]);
			
			if (players_.size() == 4)
				panel_[3] = createPanel(players_.get(3), cashAmount_[3], scrollPane_[3], list_[3]);
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
		NotificationManager.getInstance().addObserver(Notification.END_TURN, 
				this, "switchPanel");
	}
	
	private JPanel createPanel(Player player, JLabel cashAmount, JScrollPane scroll, JList list) {
		JPanel panel = new JPanel();
		Font labelFont = new Font("broadway", Font.PLAIN, 16);
		Font cashFont = new Font("broadway", Font.PLAIN, 40);
		
		String cash = "" + player.getCash();
		
		// Set up the JTabbedPane and its JPanels
		panel.setLayout(null);
		
		cashLabel_ = new JLabel("Commodore Cash: ");
		cashLabel_.setFont(labelFont);
		cashLabel_.setBounds(5, 0, 200, 50);
		
		cashAmount.setText(cash);
		cashAmount.setFont(cashFont);
		cashAmount.setBounds(200, 0, 250, 50);
		
		properties_ = new JLabel("Properties Owned: ");
		properties_.setFont(labelFont);
		properties_.setBounds(5, 60, 250, 50);
		
		scroll.setVisible(true);
		list.setVisible(true);
		
		panel.add(cashLabel_);
		panel.add(cashAmount);
		panel.add(properties_);
		panel.add(scroll);
		panel.add(list);
		
		infoPanel_.addTab(player.getName(), panel);
		return panel;
	}

	// Called by the updateCash notification
	public void updateCash(Object object) {
		try {
			Player player = (Player) object;
			String cash = "" + player.getCash();
			
			cashAmount_[player.getIndex()].setText(cash);
				
		} 
		catch (ClassCastException e) {
			System.err.println("Unexpected object passed to updateCash");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Called by the updateProperties notification
	public void updateProperties(Object object) {
		try {
				Player player = (Player) object;
				int i = player.getIndex();
				
				list_[i].setListData(player.getPropertyArray());				
				scrollPane_[i].setViewportView(list_[i]);
				scrollPane_[i].setBackground(new Color(240, 240, 240));
				scrollPane_[i].setBounds(5, 110, (int) (panelScaleX_ * width_) - 10, 
					(int) (panelScaleY_ * height_) - 100);
				scrollPane_[i].setVisible(true);
				panel_[i].add(scrollPane_[i]);
				list_[i].setFont(propertyFont_);
				scrollPane_[i].setFont(propertyFont_);
		
		} 
		catch (ClassCastException e) {
			System.err.println("Unexpected object passed to updateCash");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void switchPanel(Object i) {
		Integer number = (Integer) i;
		infoPanel_.setSelectedIndex((int)number);
	}

}

