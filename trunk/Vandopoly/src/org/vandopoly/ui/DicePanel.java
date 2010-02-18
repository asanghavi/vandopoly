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
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

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

	private Dice dice_;

	int buttonCounter = 0;

	public DicePanel(Dice dice) {

		int rightMargin = 200, bottomMargin = 200;

		int buttonHeight = 50, dieSize = 83;
		int panelWidth = 200, panelHeight = buttonHeight + dieSize;

		this.setSize(panelWidth, panelHeight);
		this.setLayout(null);

		dice_ = dice;

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		Point location = new Point((int) (screen.getWidth() - rightMargin),
				((int)screen.getHeight() - bottomMargin));

		Font buttonFont = new Font("broadway", Font.BOLD, 26);
		Font messageFont = new Font("broadway", Font.PLAIN, 18);

		rollDice_ = new JButton("Roll Dice");
		rollDice_.setBounds(0, 0, panelWidth, buttonHeight);
		rollDice_.setFont(buttonFont);

		// Add action listener to roll dice button
		rollDice_.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				dice_.roll();
			}
		});

		diePic_ = new ImageIcon[6];

		// Upload all of the images into the array
		for (int i = 0; i < diePic_.length; i++) {
			diePic_[i] = new ImageIcon("images/Dice/die" + (i + 1) + ".jpg");
		}

		die1_ = new JLabel();
		die1_.setIcon(diePic_[0]);
		die1_.setBounds(0, buttonHeight, (panelWidth / 2), dieSize);

		die2_ = new JLabel();
		die2_.setIcon(diePic_[0]);
		die2_.setBounds((panelWidth - dieSize), buttonHeight, (panelWidth / 2),
				dieSize);

		this.add(rollDice_);
		this.add(die1_);
		this.add(die2_);

		DisplayAssembler.getInstance().addComponent(this, location,
				JLayeredPane.PALETTE_LAYER);

		this.setVisible(true);

		NotificationManager.getInstance().addObserver(Notification.ROLL_DICE,
				this, "updateDice");
		NotificationManager.getInstance().addObserver(Notification.END_TURN,
				this, "endTurn");
	}

	public JButton getRollButton() {
		return rollDice_;
	}

	public void updateDice(Object obj) {

		try {
			final Dice dice = (Dice) obj;

			new Thread() {
				public void run() {
					Random r = new Random();
					for (int i = 0; i < 30; i++) {
						die1_.setIcon(diePic_[r.nextInt(6)]);
						die2_.setIcon(diePic_[r.nextInt(6)]);
						try {
							Thread.sleep(25);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					die1_.setIcon(diePic_[dice.getDie1() - 1]);
					die2_.setIcon(diePic_[dice.getDie2() - 1]);

					if (dice.getNumInRowDoubles() == 0)
						rollDice_.setEnabled(false);
					else if (dice.getNumInRowDoubles() < 3) {
						rollDice_.setForeground(Color.red);
						rollDice_.setText("Roll Again");
					} else {
						rollDice_.setText("Go To Jail");
						rollDice_.setEnabled(false);
					}
				}
			}.start();
		} catch (ClassCastException e) {
			System.err.println("Dice expected object to method updateDice");
		}
	}

	public void endTurn() {
		rollDice_.setForeground(Color.black);
		rollDice_.setText("Roll Dice");
		rollDice_.setEnabled(true);
	}
}
