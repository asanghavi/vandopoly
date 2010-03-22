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

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.vandopoly.model.Card;



/*
 * CardPanel will display a pop-up window with the card message when the
 * user lands on a "Chance" or "Community Chest" space
 * 
 * @author Allie Mazzia
 */
public class CardPanel extends JPanel {

	public CardPanel(Card card) {  	

	/*KeyListener keyTypedListener = new KeyAdapter() {
	    public void keyTyped(KeyEvent e) {
		if ((e.getKeyChar() == '\b') && (model.getSize() > 0)) {
		    model.removeElementAt(model.getSize() - 1);
		}
		else if (e.getKeyChar() != '\b') {
		    model.addElement("Added " + e.getKeyChar());
		}
	    }
	};*/
	
	JFrame frame = new JFrame(card.getMessage());
	frame.setLayout(null);
	frame.setSize(300,180);
	frame.setLocation((int)((DisplayAssembler.getScreenWidth() - 300) / 2), (int)((DisplayAssembler.getScreenHeight() - 180) / 2));
	
	Font buttonFont = new Font("broadway",Font.PLAIN,18);
	JButton ok = new JButton("OK");
	ok.setFont(buttonFont);
	ok.setBounds(0,100,150,50);
	ok.setVisible(true);
	ok.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
        	//Action goes here
        }

    });
	
	frame.add(ok);

	frame.setVisible(true);
    }
}
