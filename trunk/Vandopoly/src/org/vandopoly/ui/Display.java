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
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.WindowConstants;

/*
 * Main JFrame from which Vandopoly is built into
 * 
 * @author James Kasten
 */
public class Display extends JFrame {
	
	private JDesktopPane board_;
	Dimension screen_ = Toolkit.getDefaultToolkit().getScreenSize();
	int width_ = screen_.height - 100;
	int height_ = screen_.height - 100;
	int scaleWidth_=13, scaleHeight_=7;
	
	// Constructor effectively creates JFrame
	public Display () {
		this.setSize(screen_.width, screen_.height);
		this.setTitle("Vandopoly");
		
		// Soon to be replaced by the specific JDesktopPane Board
		JDesktopPane board_ = new JDesktopPane();
		board_.setBackground(Color.black);
		board_.setSize(screen_.width, screen_.height);
		board_.setLayout(null);
		
		// JDesktopBoard should take care of the above remarks
		
		// Set the default closse operation for the window, or else the
		// program won't exit when clicking close button
		// (The default is HIDE_ON_CLOSE, which just makes the window
		// invisible, and thus doesn't exit the app)
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		// Setup the DisplayAssembler
		DisplayAssembler.getInstance().setDesktopPane(board_);
		
		int spaceWidth=(int)(height_ / scaleWidth_);
		int start=2;
		
		//prop 1
		Color c = new Color(228, 108, 12);
		addSmall(0, spaceWidth * start, c, true, false);
		start++;
		addSmall(0, spaceWidth * start, c, true, false);
		start++;
		addSmall(0, spaceWidth * start, new Color(0, 0, 0), false, false);
		start++;
		addSmall(0, spaceWidth * start, c, true, false);
		start++;
		addSmall(0, spaceWidth * start, new Color(0, 0, 0), false, false);
		start++;
		
		//prop 2
		c = new Color(148, 55, 53);
		addSmall(0, spaceWidth * start, c, true, false);
		start++;
		addSmall(0, spaceWidth * start, c, true, false);
		start++;
		addSmall(0, spaceWidth * start, new Color(0, 0, 0), false, false);
		start++;
		addSmall(0, spaceWidth * start, c, true, false);
		start++;
		
		//prop 3
		c = new Color(255, 0, 0);
		start=2;
		addSmall(spaceWidth * start, 0, c, true, true);
		start++;
		addSmall(spaceWidth * start, 0, c, false, true);
		start++;
		addSmall(spaceWidth * start, 0, new Color(0, 0, 0), false, true);
		start++;
		addSmall(spaceWidth * start, 0, c, true, true);
		start++;
		addSmall(spaceWidth * start, 0, new Color(0, 0, 0), false, true);
		start++;
		
		//prop 4
		c = new Color(255, 255, 0);
		addSmall(spaceWidth * start, 0, c, true, true);
		start++;
		addSmall(spaceWidth * start, 0, c, true, true);
		start++;
		addSmall(spaceWidth * start, 0, new Color(0, 0, 0), false, true);
		start++;
		addSmall(spaceWidth * start, 0, c, true, true);
		start++;
		
		
		//prop 5
		c = new Color(79, 99, 40);
		start=2;
		addSmallOp(0, spaceWidth * start, c, true, false);
		start++;
		addSmallOp(0, spaceWidth * start, c, true, false);
		start++;
		addSmallOp(0, spaceWidth * start, new Color(0, 0, 0), false, false);
		start++;
		addSmallOp(0, spaceWidth * start, c, true, false);
		start++;
		addSmallOp(0, spaceWidth * start, new Color(0, 0, 0), false, false);
		start++;
		
		//prop 6
		c = new Color(39, 63, 97);
		addSmallOp(0, spaceWidth * start, c, true, false);
		start++;
		addSmallOp(0, spaceWidth * start, c, true, false);
		start++;
		addSmallOp(0, spaceWidth * start, new Color(0, 0, 0), false, false);
		start++;
		addSmallOp(0, spaceWidth * start, c, true, false);
		start++;
		
		//prop 7
		c = new Color(97, 73, 121);
		start=2;
		addSmallOp(spaceWidth * start, 0, c, true, true);
		start++;
		addSmallOp(spaceWidth * start, 0, c, false, true);
		start++;
		addSmallOp(spaceWidth * start, 0, new Color(0, 0, 0), false, true);
		start++;
		addSmallOp(spaceWidth * start, 0, c, true, true);
		start++;
		addSmallOp(spaceWidth * start, 0, new Color(0,0,0), false, true);
		start++;
		
		//prop 8
		c = new Color(85, 141, 215);
		addSmallOp(spaceWidth * start, 0, c, true, true);
		start++;
		addSmallOp(spaceWidth * start, 0, c, true, true);
		start++;
		addSmallOp(spaceWidth * start, 0, new Color(0, 0, 0), false, true);
		start++;
		addSmallOp(spaceWidth * start, 0, c, true, true);
		start++;
		
		//set center of board
		JLabel labelcenter = new JLabel();
		labelcenter.setOpaque(true);
		labelcenter.setBackground(new Color(235, 227, 188));
		labelcenter.setSize(new Dimension(width_ - ((int)(width_ / 7) * 2), 
				height_ - (int)(width_ / 7) * 2));
		DisplayAssembler.getInstance().addComponent(labelcenter, new Point
				((int)(width_ / 7), (int)(width_ / 7)), JLayeredPane.FRAME_CONTENT_LAYER);
		
		this.add(board_);
		this.setVisible(true);
		board_.setVisible(true);
	}
	
	//adds a smaller rectangle to the board.
	//x, y specify the point to be placed on the board. c is the color of the property.
	//isProp specifies if it is a property or not, isVert specifies weather the piece goes vertically or
	//horizontally
	void addSmall(int x, int y, Color c, boolean isProp, boolean isVert) {
		//set the larger part of the piece
		JLabel label1 = new JLabel();
		label1.setOpaque(true);
		label1.setBackground(new Color(235, 227, 188));
		int panelWidth;
		
		if (isProp)
			panelWidth = (int)((width_ / 7) * .85);
		else
			panelWidth = (int)((width_ / 7));
		
		int panelHeight;
		
		if (isVert) {
			panelHeight = panelWidth;
			panelWidth = height_ / scaleWidth_;
		}
		else 
			panelHeight = height_ / scaleWidth_;
		
		label1.setSize(new Dimension(panelWidth, panelHeight));
		label1.setBorder(BorderFactory.createLineBorder(Color.black));
		DisplayAssembler.getInstance().addComponent(label1, new Point(x,y), 
				JLayeredPane.FRAME_CONTENT_LAYER);
		
		//set the smaller colored part of the piece
		if (isProp) {
			JLabel label = new JLabel();
			label.setOpaque(true);
			label.setBackground(c);
			
			panelWidth = (int)((width_ / scaleHeight_) * .15);
			if (isVert) {
				panelHeight = panelWidth;
				panelWidth = height_ / scaleWidth_;
			}
			label.setSize(new Dimension(panelWidth, panelHeight));
			label.setBorder(BorderFactory.createLineBorder(Color.black));
			
			if (isVert)
				DisplayAssembler.getInstance().addComponent
					(label, new Point(x, y + (int)((width_ / scaleHeight_) * .85)), 
							JLayeredPane.FRAME_CONTENT_LAYER);
			else
				DisplayAssembler.getInstance().addComponent
					(label, new Point(x + (int)((width_ / scaleHeight_) * .85), y), 
							JLayeredPane.FRAME_CONTENT_LAYER);
		}
	}
	
	//same as addSmall, but used for bottom and right side of board
	void addSmallOp(int x, int y, Color c, boolean isProp, boolean isVert) {
		
		//set the larger part of the piece
		JLabel label1 = new JLabel();
		label1.setOpaque(true);
		label1.setBackground(new Color(235, 227, 188));
		int panelWidth;
		
		if (isProp)
			panelWidth = (int)((width_ / scaleHeight_) * .85);
		else
			panelWidth = (int)((width_ / scaleHeight_));
		
		int panelHeight;
		if (isVert) {
			panelHeight = panelWidth;
			panelWidth = height_ / scaleWidth_;
		}
		else
			panelHeight = height_ / scaleWidth_;
		
		x = width_ - x - panelWidth;
		y = height_ - y - panelHeight;
		
		label1.setSize(new Dimension(panelWidth, panelHeight));
		label1.setBorder(BorderFactory.createLineBorder(Color.black));
		if (isVert)
			DisplayAssembler.getInstance().addComponent
				(label1, new Point(x, y + (int)((width_ / scaleHeight_) * .15)), 
						JLayeredPane.FRAME_CONTENT_LAYER);
		else
			DisplayAssembler.getInstance().addComponent
				(label1, new Point(x + (int)((width_ / scaleHeight_) * .15), y), 
						JLayeredPane.FRAME_CONTENT_LAYER);
		
		//set the smaller colored part of the piece
		if (isProp) {
			JLabel label = new JLabel();
			label.setOpaque(true);
			label.setBackground(c);
			
			panelWidth = (int)((width_ / scaleHeight_) * .15);
			if (isVert) {
				panelHeight = panelWidth;
				panelWidth = height_ / scaleWidth_;
			}
			label.setSize(new Dimension(panelWidth, panelHeight));
			label.setBorder(BorderFactory.createLineBorder(Color.black));
			DisplayAssembler.getInstance().addComponent(label, 
					new Point(x, y), JLayeredPane.FRAME_CONTENT_LAYER);
		}
	}
	
	public static void main(String[] args) {
		new Display();
	}
}