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

import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;

public class DisplayAssembler {

	private static DisplayAssembler INSTANCE;
	private JDesktopPane desktopPane_ = null;
	
	private DisplayAssembler() {
		
	}
	
	// Singleton method
	public static DisplayAssembler getInstance() {
		if(INSTANCE == null) 
			INSTANCE = new DisplayAssembler();
		
		return INSTANCE;
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
		// Needs to be implemented
	}

}
