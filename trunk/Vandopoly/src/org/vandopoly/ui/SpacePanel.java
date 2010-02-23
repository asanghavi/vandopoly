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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.vandopoly.messaging.NotificationManager;

/*
 * Space represents the spaces on the board and takes care of positioning,
 * creating, and performing actions for them.
 * 
 * @author Matt Gioia
 */

public class SpacePanel extends JPanel {
	int x_;
	int y_;
	int width_;
	int height_;
	int position_;
	Color c_;
	boolean useTex_;
	String spaceName_;
	boolean isProp_;
	String owner_;
	ArrayList<String> onSpace_;
	JLabel label;
	
	public SpacePanel(int pos, String spaceName, boolean isProp, int x, int y, int width, int height, Color c, boolean useTex) {
		x_ = x;
		y_ = y;
		width_ = width;
		height_ = height;
		c_ = c;
		useTex_ = useTex;
		spaceName_ = spaceName;
		isProp_ = isProp;
		position_= pos;
		onSpace_ = new ArrayList<String>();
		owner_ = "";
		addSpace();
		updateStatus();
		NotificationManager.getInstance().addObserver("EndTurn", this, "updateStatus");
	}
	
	// set the display properties of the space
	public void setProperties(String sn) {
		spaceName_ = sn;
		updateStatus();
	}
	
	void addSpace() {
		label = new JLabel();
		label.setOpaque(true);
		
		if (useTex_) {
			label.setIcon(new ImageIcon("images/boardTex.png"));
		}
		else {
			label.setOpaque(true);
			label.setBackground(c_);
		}
		
		label.setSize(new Dimension(width_, height_));
		label.setBorder(BorderFactory.createLineBorder(Color.black));

		DisplayAssembler.getInstance().addComponent(label, new Point(x_,y_), 
				JLayeredPane.FRAME_CONTENT_LAYER);
	}

	// updates the owner
	void setOwner(String owner) {
		owner_ = owner;
	}
	
	void addOnSpace(String name) {
		onSpace_.add(name);
	}
	
	// add status text to board piece
	public void updateStatus() {
		String status = "";
		if(isProp_) {
			String owner = "Unowned";
			if(owner_ != "")
				owner = owner_;
			status += "Property Name: " + spaceName_ + ", Owned by: " + owner + ", ";
		} 
		else if(spaceName_ != "") {
			status += spaceName_ + ", ";
		}
		
		status += "On this space: ";
		
		if(onSpace_.size() == 0)
			status += "Nobody";
		else {
			String spacelist = "";
			for (String s : onSpace_) {
				spacelist += ", " + s;
			}
			status += spacelist.substring(3);
		}
		
		label.setToolTipText(status);
	}
}
