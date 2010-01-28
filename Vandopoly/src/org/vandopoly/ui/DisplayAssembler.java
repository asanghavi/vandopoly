package org.vandopoly.ui;

import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;

public class DisplayAssembler {

	private static DisplayAssembler INSTANCE;
	private JDesktopPane desktopPane_ = null;
	
	private DisplayAssembler() {
		
	}
	
	public static DisplayAssembler getInstance() {
		if(INSTANCE == null) 
			INSTANCE = new DisplayAssembler();
		
		return INSTANCE;
	}
	
	public void setDesktopPane(JDesktopPane desktopPane) {
		desktopPane_ = desktopPane;
	}
	
	public void addComponent(JComponent component, Point location, Object layer) {
		component.setLocation(location);
		desktopPane_.add(component, layer);
	}

}
