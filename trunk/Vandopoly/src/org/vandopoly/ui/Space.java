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
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import org.vandopoly.model.Player;

/*
 * Space represents the spaces on the board and takes care of positioning,
 * creating, and performing actions for them.
 * 
 * @author Matt Gioia
 */

public class Space extends JPanel {
	int x_;
	int y_;
	int width_;
	int height_;
	int position_;
	Color c_;
	boolean useTex_;
	String spaceName_;
	boolean isProp_;
	Player owner_;
	JLabel label;
	
	public Space(int pos, String spaceName, boolean isProp, int x, int y, int width, int height, Color c, boolean useTex) {
		x_ = x;
		y_ = y;
		width_ = width;
		height_ = height;
		c_ = c;
		useTex_ = useTex;
		spaceName_ = spaceName;
		isProp_ = isProp;
		position_=pos;
		addSpace();
		addStatus();
	}
	
	// set the display properties of the space
	public void setProperties(int x, int y, int width, int height, Color c, boolean useTex) {
		x_=x;
		y_=y;
		width_=width;
		height_=height;
		c_=c;
		useTex_=useTex;
		addSpace();
	}
	
	void addSpace()
	{
		label = new JLabel();
		label.setOpaque(true);
		
		if (useTex_)
			label.setIcon(new ImageIcon("images/boardTex.png"));
		else {
			label.setOpaque(true);
			label.setBackground(c_);
		}
		
		label.setSize(new Dimension(width_, height_));
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		DisplayAssembler.getInstance().addComponent(label, new Point(x_,y_), 
				JLayeredPane.FRAME_CONTENT_LAYER);
	}

	// add status text to board piece
	void addStatus()
	{
		if(isProp_) {
			String owner = "Unowned";
			if(owner_ == null)
				owner = "";
			label.setToolTipText("Property Name: " + spaceName_ + ", Owned by: " + owner + ", On this property: Nobody");
		}
	}
}
