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
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;

import javax.swing.JLabel;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import org.vandopoly.model.Card;



/*
 * CardPanel will display a pop-up window with the card message when the
 * user lands on a "Chance" or "Community Chest" space
 * 
 * @author Allie Mazzia
 */
public class CardPanel extends JPanel {

	public CardPanel(Card card, String spaceName) {  	
	
		JPanel panel = new JPanel();
		
		int hGap = 10, vGap = 10;
		GridLayout gridLayout = new GridLayout(2, 1, hGap, vGap);
		
		panel.setLayout(null);
		Font labelFont = new Font("broadway", Font.PLAIN, 20);
		Font buttonFont = new Font("broadway", Font.PLAIN, 18);
		Font titleFont = new Font("broadway", Font.PLAIN, 14);
		
		JLabel label = new JLabel(card.getMessage());
		System.out.println("Message: " + card.getMessage());

		label.setFont(labelFont);
		panel.add(label);
		
		JButton ok = new JButton("OK");
		ok.setFont(buttonFont);
		ok.setVisible(true);
		panel.setLayout(gridLayout);
		panel.setBackground(new Color(236, 234, 166));
		
		Border blackline = BorderFactory.createLineBorder(Color.black, 2);
		TitledBorder title = BorderFactory.createTitledBorder(
			       blackline, spaceName);
		title.setTitleFont(titleFont);
		title.setTitleJustification(TitledBorder.LEFT);
		panel.setBorder(title);
		panel.add(ok);
		panel.setVisible(true);
	
		int xCoord = (int)(DisplayAssembler.getScreenWidth() - 
				panel.getPreferredSize().getWidth()) / 2;
		int yCoord = (int)(DisplayAssembler.getScreenHeight() - 
				panel.getPreferredSize().getHeight()) / 2;
		
		PopupFactory factory = PopupFactory.getSharedInstance();
		final Popup popup = factory.getPopup(this, panel, xCoord, yCoord);
		
		ok.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
	        	popup.hide();
	        }
	
	    });
	
		//frame.setVisible(true);
	
		popup.show();	
	
    }
}
