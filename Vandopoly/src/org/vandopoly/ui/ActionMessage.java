package org.vandopoly.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class ActionMessage extends JLabel {
	
	private static ActionMessage INSTANCE;
	
	private int screenWidth = DisplayAssembler.getScreenWidth();
	
	private int leftSide = DisplayAssembler.getBoxSize() + 7;
	private int rightSide = leftSide + DisplayAssembler.getSpaceWidth() * 9;
	
	private Font actionFont;
	
	static final long serialVersionUID = 101;

	public static ActionMessage getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ActionMessage();
		
		return INSTANCE;
	}
	
	private ActionMessage() {
		this.setForeground(Color.yellow);
		this.setBounds(0, 0, screenWidth, 100);
		this.setVisible(true);
		DisplayAssembler.getInstance().addComponent(this, 0, 0, JLayeredPane.POPUP_LAYER);
	}
	
	public void newMessage(final String message) {
		new Thread() {
			public void run() {
				synchronized(ActionMessage.this) {
					
					int fontSize = 10;
					actionFont = new Font("broadway", Font.BOLD, fontSize);
					
					// Removes old font and Text before displaying and going through loop
					ActionMessage.this.setFont(actionFont);
					ActionMessage.this.setText(message);
					setLocation(message);
					ActionMessage.this.setVisible(true);
				
					try {
					
						for (int i = 0; i < 25; i++) {
							fontSize++;
							actionFont = new Font("broadway", Font.BOLD, fontSize);
						
							setLocation(message);
							ActionMessage.this.setFont(actionFont);
						
							Thread.sleep(10);
						}
						
						Thread.sleep(1600);
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				
					ActionMessage.this.setVisible(false);
				}
			}
		}.start();
	}
	
	private void setLocation(String message) {
		
		FontMetrics metrics = DisplayAssembler.getInstance().getFontMetrics(actionFont);
		DisplayAssembler.getInstance().animateComponentLocation(this, 
				(leftSide + ((rightSide - leftSide) / 2) - (metrics.stringWidth(message)) / 2), 
				leftSide - metrics.getHeight() + 10);
		
		
	}
}
