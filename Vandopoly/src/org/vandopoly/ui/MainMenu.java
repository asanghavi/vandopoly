// Import the swing and AWT classes needed
package org.vandopoly.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * Main Menu for Vandopoly
 */
public class MainMenu extends JPanel {
	
	private JButton newGame_, loadGame_, quitGame_;
	
	public MainMenu() {
		
		int frameWidth = 730, frameHeight = 750;

		// Set size of window
		this.setSize(frameWidth, frameHeight);
		
		// Center the frame on the user's screen
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		Point location = new Point((int)(screen.getWidth() - frameWidth) / 2, (int)(screen.getHeight() - frameHeight) / 2);
		
		JLabel titleBar = new JLabel();
		ImageIcon title = new ImageIcon("images/Vandopoly2.jpg");
		titleBar.setIcon(title);
		titleBar.setBounds(0, 0, 730, 200);

		Font buttonFont = new Font("broadway", Font.BOLD, 32);

		newGame_ = new JButton("New Game");
		newGame_.setBounds(115, 250, 500, 100);
		newGame_.setFont(buttonFont);
		newGame_.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                removePanels();
                //new GameOptions();
            }

        });


		loadGame_ = new JButton("Load Game");
		loadGame_.setBounds(115, 400, 500, 100);
		loadGame_.setFont(buttonFont);

		quitGame_ = new JButton("Quit Game");
		quitGame_.setBounds(115, 550, 500, 100);
		quitGame_.setFont(buttonFont);
		quitGame_.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

		// Add some Components
		DisplayAssembler display = DisplayAssembler.getInstance();
		add(newGame_);
		add(loadGame_);
		add(quitGame_);
		display.addComponent(this, location, JLayeredPane.FRAME_CONTENT_LAYER);

		// Set the visibility as true, thereby displaying it
		setVisible(true);
	}
	
	public void removePanels() {
		newGame_.setVisible(false);
        loadGame_.setVisible(false);
        quitGame_.setVisible(false);
	}
	
	
	public static void main(String[] args) {
		new Display();
		new MainMenu();
	}
}