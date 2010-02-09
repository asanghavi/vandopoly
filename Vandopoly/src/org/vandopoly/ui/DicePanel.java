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

import org.vandopoly.messaging.Notification;
import org.vandopoly.messaging.NotificationManager;
import org.vandopoly.model.Dice;

/*
 * DicePanel will allow the user to roll the dice and displays them appropriately
 * 
 * @author James Kasten
 */
public class DicePanel extends JPanel {

	private JButton rollDice_;
	private JLabel die1_, die2_;
	// Holds all 6 pictures of the die faces
	private ImageIcon diePic_[];

	// TODO Remove and put in controller**************************
	public Dice dice = new Dice();
	// *********************************************************

	public DicePanel() {
		int panelWidth = 200, panelHeight = 130;
		int rightMargin = 400, topMargin = 100;

		int buttonHeight = 50, dieSize = 82;

		this.setSize(panelWidth, panelHeight);
		this.setLayout(null);

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		Point location = new Point((int) (screen.getWidth() - rightMargin),
				topMargin);

		Font buttonFont = new Font("broadway", Font.BOLD, 26);

		rollDice_ = new JButton("Roll Dice");
		rollDice_.setBounds(0, 0, panelWidth, buttonHeight);
		rollDice_.setFont(buttonFont);

		// TODO: This should be put in a controller
		// It has temporarily been placed here along with a fake
		// Dice object
		// **************************************************************
		// **************************************************************
		rollDice_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dice.roll();
			}

		});

		diePic_ = new ImageIcon[6];

		// Upload all of the images into the array
		for (int i = 0; i < diePic_.length; i++) {
			diePic_[i] = new ImageIcon("images/Dice/die" + (i + 1) + ".jpg");
		}

		die1_ = new JLabel();
		die1_.setIcon(diePic_[0]);
		die1_.setBounds(0, buttonHeight, (panelWidth / 2), panelHeight
				- buttonHeight);

		die2_ = new JLabel();
		die2_.setIcon(diePic_[0]);
		die2_.setBounds((panelWidth - dieSize), buttonHeight, (panelWidth / 2),
				panelHeight - buttonHeight);

		this.add(rollDice_);
		this.add(die1_);
		this.add(die2_);

		DisplayAssembler.getInstance().addComponent(this, location,
				JLayeredPane.PALETTE_LAYER);

		this.setVisible(true);

		NotificationManager.getInstance().addObserver(Notification.ROLL_DICE,
				this, "updateDice");
	}

	public void updateDice(Object obj) {

		if (obj instanceof Dice) {
			Dice dice = (Dice) obj;

			die1_.setIcon(diePic_[dice.getDie1() - 1]);
			die2_.setIcon(diePic_[dice.getDie2() - 1]);
		}
	}
}
