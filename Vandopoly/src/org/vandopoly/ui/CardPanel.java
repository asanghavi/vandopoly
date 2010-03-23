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

import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;

import org.vandopoly.model.Card;



/*
 * CardPanel will display a pop-up window with the card message when the
 * user lands on a "Chance" or "Community Chest" space
 * 
 * @author Allie Mazzia
 */
public class CardPanel extends JPanel {

	public CardPanel(Card card) {  	
	
		JPanel panel = new JPanel();
		
		int hGap = 10, vGap = 10;
		GridLayout gridLayout = new GridLayout(2, 1, hGap, vGap);
		
		panel.setLayout(null);
		Font labelFont = new Font("broadway", Font.PLAIN, 20);
		
		JLabel label = new JLabel(card.getMessage());
		System.out.println("Message: " + card.getMessage());

		label.setFont(labelFont);
		panel.add(label);
		
		Font buttonFont = new Font("broadway", Font.PLAIN, 18);
		JButton ok = new JButton("OK");
		ok.setFont(buttonFont);
		ok.setVisible(true);
		panel.setLayout(gridLayout);
		Color white = new Color(255, 255, 255);
		panel.setBackground(white);
		panel.add(ok);
		panel.setVisible(true);
		
		PopupFactory factory = PopupFactory.getSharedInstance();
		final Popup popup = factory.getPopup(this, panel, (int)((DisplayAssembler.getScreenWidth() - 400) / 2), (int)((DisplayAssembler.getScreenHeight() - 300) / 2));
		
		ok.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent event) {
	        	popup.hide();
	        }
	
	    });
	
		//frame.setVisible(true);
	
		popup.show();	
	
    }
}
