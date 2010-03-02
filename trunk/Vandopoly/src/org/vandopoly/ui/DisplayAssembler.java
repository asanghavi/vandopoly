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

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
/*
 * The DisplayAssembler class is a Singleton intended to allow developers access
 * to the underlying DesktopPane.  Any JComponent can be added to the JDesktopPane
 * by using DisplayAssembler.getInstance().addComponent(JComponent,Point,layer)
 * The layer is meant to be one of 5 JLayeredPane layers, JLayeredPane.DEFAULT_LAYER,
 * JLayeredPane.PALETTE_LAYER,JLayeredPane.MODAL_LAYER, JLayeredPane.POPUP_LAYER, 
 * and finally JLayeredPane.DRAG_LAYER.
 * 
 * @author James Kasten
 */
public class DisplayAssembler {

	private static DisplayAssembler INSTANCE;
	private JDesktopPane desktopPane_ = null;
	
	private int rightEdge_, boxSize_, spaceWidth_, topLeftGo_;
	
	private static Dimension screen_;
	
	private DisplayAssembler() {
		 screen_ = Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	// Singleton method
	public static DisplayAssembler getInstance() {
		if(INSTANCE == null) 
			INSTANCE = new DisplayAssembler();
		
		return INSTANCE;
	}
	public void setRightEdge(int rightEdge) {
		rightEdge_ = rightEdge;
	}
	
	public void setBoxSize(int boxSize) {
		boxSize_ = boxSize;
	}
	
	public void setSpaceWidth(int spaceWidth) {
		spaceWidth_ = spaceWidth;
	}
	
	public void setTopLeftGo(int topLeftGo) {
		topLeftGo_ = topLeftGo;
	}
	
	public int getRightEdge() {
		return rightEdge_;
	}
	
	public int getBoxSize() {
		return boxSize_;
	}
	
	public int getSpaceWidth() {
		return spaceWidth_;
	}
	
	public int getTopLeftGo() {
		return topLeftGo_;
	}
	
	// Must be used by Frame to allow access to the DesktopPane
	public void setDesktopPane(JDesktopPane desktopPane) {
		desktopPane_ = desktopPane;
	}
	
	// Function allows components to be added to the DesktopPane
	public void addComponent(JComponent component, Point location, Object layer) {
		component.setLocation(location);
		desktopPane_.add(component, layer);
	}
	
	public void removeComponent(JComponent component) {
		desktopPane_.remove(component);
	}
	
	public static int getScreenHeight() {
		return screen_.height;
	}
	
	public static int getScreenWidth() {
		return screen_.width;
	}
}
