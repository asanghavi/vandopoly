package org.vandopoly.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


public class Display extends JFrame {
	
	private JDesktopPane board_;
	
	public Display () {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screen.width, screen.height);
		this.getContentPane().setBackground(Color.black);
		
		// Soon to be replaced by the specific JDesktopPane Board
		JDesktopPane board_ = new JDesktopPane();
		board_.setBackground(Color.black);
		board_.setSize(screen.width, screen.height);
		board_.setLayout(null);
		// JDesktopBoard should take care of the above remarks
		
		// Set the default close operation for the window, or else the
		// program won't exit when clicking close button
		// (The default is HIDE_ON_CLOSE, which just makes the window
		// invisible, and thus don't exit the app)
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		DisplayAssembler.getInstance().setDesktopPane(board_);
		this.add(board_);
		this.setVisible(true);
	}
	public void addComponent(JComponent component, Point location, Object layer) {
		component.setLocation(location);
		this.add(component, layer);
	}
	public static void main(String[] args) {
		new Display();
	}
}