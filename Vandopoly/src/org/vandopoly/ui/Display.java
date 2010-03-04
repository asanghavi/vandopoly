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
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.ToolTipManager;
import javax.swing.WindowConstants;

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;
import org.vandopoly.model.Space;

/*
 * Main JFrame from which Vandopoly is built into
 * 
 * @author Matt Gioia
 */

public class Display extends JFrame {
	
	Dimension screen_ = Toolkit.getDefaultToolkit().getScreenSize();
	int width_ = screen_.height - 150;
	int height_ = screen_.height - 150;
	int scaleWidth_ = 12, scaleHeight_ = 7;
	ArrayList<SpacePanel> spaces_ = new ArrayList<SpacePanel>(36);
	int spacesAcross_ = 9;
	int sizeAcross_ = (int)(height_ / scaleWidth_) * spacesAcross_;
	double spaceScale_ = .85;
	int boxSize_ = (int)(width_ / scaleWidth_) * 2 - scaleHeight_;
	int pos_ = boxSize_ + scaleHeight_;
	int spacePos_=0;
	int spaceWidth_ = (int)(height_ / scaleWidth_);
	int TopLeftGo_ = pos_ + sizeAcross_;
	int RightEdge_ = pos_ + sizeAcross_ + boxSize_;
	HashMap<String, SpacePanel> spaceMap_ = null;
	
	static final long serialVersionUID = 1;
	
	// Constructor effectively creates JFrame
	public Display () {
		this.setSize(screen_.width, screen_.height);
		this.setTitle("Vandopoly");
		this.setUndecorated(true);

		ToolTipManager.sharedInstance().setInitialDelay(0); 
		
		JDesktopPane board_ = new JDesktopPane();
		board_.setBackground(Color.black);
		board_.setSize(screen_.width, screen_.height);
		board_.setLayout(null);
		
		// Set the default close operation for the window, or else the
		// program won't exit when clicking close button
		// (The default is HIDE_ON_CLOSE, which just makes the window
		// invisible, and thus doesn't exit the app)
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		// Setup the DisplayAssembler
		DisplayAssembler.getInstance().setDesktopPane(board_);
		DisplayAssembler.getInstance().setBoxSize(boxSize_);
		DisplayAssembler.getInstance().setRightEdge(RightEdge_);
		DisplayAssembler.getInstance().setSpaceWidth(spaceWidth_);
		DisplayAssembler.getInstance().setTopLeftGo(TopLeftGo_);
		
		this.add(board_);
		this.setVisible(true);
		board_.setVisible(true);
		NotificationManager.getInstance().addObserver(Notification.START_GAME, 
				this, "showBoard");

	}
	
	// show the board
	public void showBoard() {
		
		int start = 0;

		// prop 1
		Color c = new Color(228, 108, 12);
		String name = "Branscomb";
		addSmall(name, 0, pos_ + (spaceWidth_ * start++), c, true, false);
		addSmall(name, 0, pos_ + (spaceWidth_ * start++), c, true, false);
		addSmall("", 0, pos_ + (spaceWidth_ * start++), new Color(0, 0, 0), false, false);
		addSmall(name, 0, pos_ + (spaceWidth_ * start++), c, true, false);
		addSmall("", 0, pos_ + (spaceWidth_ * start++), new Color(0, 0, 0), false, false);
		
		// prop 2
		c = new Color(148, 55, 53);
		name = "Kissam";
		addSmall(name, 0, pos_ + (spaceWidth_ * start++), c, true, false);
		addSmall(name, 0, pos_ + (spaceWidth_ * start++), c, true, false);
		addSmall("", 0, pos_ + (spaceWidth_ * start++), new Color(0, 0, 0), false, false);
		addSmall(name, 0, pos_ + (spaceWidth_ * start++), c, true, false);
		
		// prop 3
		c = new Color(255, 0, 0);
		start = 0;
		name = "Morgan";
		addSmall(name, pos_ + (spaceWidth_ * start++), 0, c, true, true);
		addSmall("", pos_ + (spaceWidth_ * start++), 0, new Color(0, 0, 0), false, true);
		addSmall(name, pos_ + (spaceWidth_ * start++), 0, c, true, true);
		addSmall(name, pos_ + (spaceWidth_ * start++), 0, c, true, true);
		addSmall("", pos_ + (spaceWidth_ * start++), 0, new Color(0, 0, 0), false, true);
		
		// prop 4
		c = new Color(255, 255, 0);
		name = "Wilson";
		addSmall(name, pos_ + (spaceWidth_ * start++), 0, c, true, true);
		addSmall(name, pos_ + (spaceWidth_ * start++), 0, c, true, true);
		addSmall("", pos_ + (spaceWidth_ * start++), 0, new Color(0, 0, 0), false, true);
		addSmall(name, pos_ + (spaceWidth_ * start++), 0, c, true, true);
		
		// prop 5
		c = new Color(79, 99, 40);
		start = 0;
		name = "Rand";
		addSmallOp(name, 0, pos_ + (spaceWidth_ * start++), c, true, false);
		addSmallOp(name, 0, pos_ + (spaceWidth_ * start++), c, true, false);
		addSmallOp("", 0, pos_ + (spaceWidth_ * start++), new Color(0, 0, 0), false, false);
		addSmallOp(name, 0, pos_ + (spaceWidth_ * start++), c, true, false);
		addSmallOp("", 0, pos_ + (spaceWidth_ * start++), new Color(0, 0, 0), false, false);
		
		// prop 6
		c = new Color(39, 63, 97);
		name = "Sarratt";
		addSmallOp("", 0, pos_ + (spaceWidth_ * start++), new Color(0, 0, 0), false, false);
		addSmallOp(name, 0, pos_ + (spaceWidth_ * start++), c, true, false);
		addSmallOp("", 0, pos_ + (spaceWidth_ * start++), new Color(0, 0, 0), false, false);
		addSmallOp(name, 0, pos_ + (spaceWidth_ * start++), c, true, false);
		
		// prop 7
		c = new Color(97, 73, 121);
		start = 0;
		name = "Stevenson Center";
		addSmallOp(name, pos_ + (spaceWidth_ * start++), 0, c, true, true);
		addSmallOp("", pos_ + (spaceWidth_ * start++), 0, new Color(0, 0, 0), false, true);
		addSmallOp(name, pos_ + (spaceWidth_ * start++), 0, c, true, true);
		addSmallOp("", pos_ + (spaceWidth_ * start++), 0, new Color(0,0,0), false, true);
		
		// prop 8
		c = new Color(85, 141, 215);
		name = "Recreation Center";
		addSmallOp("", pos_ + (spaceWidth_ * start++), 0, new Color(0, 0, 0), false, true);
		addSmallOp(name, pos_ + (spaceWidth_ * start++), 0, c, true, true);
		addSmallOp("", pos_ + (spaceWidth_ * start++), 0, new Color(0, 0, 0), false, true);
		addSmallOp(name, pos_ + (spaceWidth_ * start++), 0, c, true, true);
		addSmallOp(name, pos_ + (spaceWidth_ * start++), 0, c, true, true);
		
		// make the corners
		
		// top left corner
		addLabel("Community Chest", false, scaleHeight_, scaleHeight_, boxSize_, boxSize_, c, true);
		
		// top right corner
		addLabel("Chance", false, pos_ + sizeAcross_, scaleHeight_, boxSize_, boxSize_, c, true);
		
		// bottom left corner
		addLabel("Jail", false, scaleHeight_, pos_ + sizeAcross_, boxSize_, boxSize_, c, true);
		
		// bottom right corner
		addLabel("Start", false, pos_ + sizeAcross_, pos_ + sizeAcross_, boxSize_, boxSize_, c, true);
		
		// set center of board
		addLabel("", false, pos_, pos_, sizeAcross_, sizeAcross_, c, true);
	}
	
	public void updateAllSpaces(Space[] space) {
		int x = 0;
		Iterator<SpacePanel> itr = spaces_.iterator();
		SpacePanel sp = null;
		while(itr.hasNext()) {
			sp = itr.next();
			sp.setSpaceObj(space[x]);
			sp.updateStatus();
			spaceMap_.put(sp.getSpaceName(), sp);
			x++;
		}
	}
	
	public void updateSpace(Space space) {
		SpacePanel s = spaceMap_.get(space.getName());
		s.setSpaceObj(space);
		s.updateStatus();
	}
	
	// adds a smaller rectangle to the board.
	// x, y specify the point to be placed on the board. c is the color of the property.
	// isProp specifies if it is a property or not, isVert specifies weather the piece goes vertically or
	// horizontally
	void addSmall(String name, int x, int y, Color c, boolean isProp, boolean isVert) {
		// set the larger part of the piece
		int panelWidth;
		
		if (isProp)
			panelWidth = (int)(spaceScale_ * boxSize_);
		else
			panelWidth = boxSize_;
		
		int panelHeight;
		
		if (isVert) {
			panelHeight = panelWidth;
			panelWidth = height_ / scaleWidth_;
			y += scaleHeight_;
		}
		else {
			panelHeight = height_ / scaleWidth_;
			x += scaleHeight_;
		}
		
		addLabel(name, isProp, x, y, panelWidth, panelHeight,c,true);
		
		// set the smaller colored part of the piece
		if (isProp) {
			panelWidth = boxSize_ - Math.max(panelWidth, panelHeight);
			if (isVert) {
				panelHeight = panelWidth;
				panelWidth = (int)height_ / scaleWidth_;
			}
	
			if (isVert)
				addLabel(name, isProp, x, y + (int)(spaceScale_ * boxSize_), panelWidth,
						panelHeight, c, false);
			else
				addLabel(name, isProp, x + (int)(spaceScale_ * boxSize_), y, panelWidth, 
						panelHeight, c, false);	
		}
	}
	
	// same as addSmall, but used for bottom and right side of board
	void addSmallOp(String name, int x, int y, Color c, boolean isProp, boolean isVert) {
		// set the larger part of the piece	
		int panelWidth;

		if (isProp)
			panelWidth = (int)(spaceScale_ * boxSize_);
		else
			panelWidth = boxSize_;
		
		int panelHeight;
		if (isVert) {
			panelHeight = panelWidth;
			panelWidth = height_ / scaleWidth_;
		}
		else 
			panelHeight = height_ / scaleWidth_;

		if(isVert)
			y += pos_ + sizeAcross_;
		else
			x += pos_ + sizeAcross_;

		// set the smaller colored part of the piece
		int panelWidthS = panelWidth, panelHeightS = panelHeight;
		if (isProp) {
			panelWidthS = boxSize_ - Math.max(panelWidth, panelHeight);
			if (isVert) {
				panelHeightS = panelWidthS;
				panelWidthS = height_ / scaleWidth_;
			}
			addLabel(name, isProp, x, y, panelWidthS, panelHeightS, c, false);
		} 
		else {
			panelWidthS = 0;
			panelHeightS = 0;
		}
		
		// set the larger part
		if (isVert)
			addLabel(name, isProp, x, y + panelHeightS, panelWidth, panelHeight, c, true);
		else
			addLabel(name, isProp, x + panelWidthS, y, panelWidth, panelHeight, c, true);	
	}

	// make a space and add it to the board
	void addLabel(String name, boolean isProp, int x, int y, int width, int height, 
			Color c, boolean useTex) {
		SpacePanel s = new SpacePanel(spacePos_++, name, isProp, x, y, width, height, c, useTex);
		spaces_.add(s);
	}
	
	public static void main(String[] args) {
		new Display();
	}
}