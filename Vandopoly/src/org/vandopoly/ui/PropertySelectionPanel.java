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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.vandopoly.model.PropertySpace;


/*
 * PropertySelectionPanel is meant to provide a generic way to present an ArrayList of properties.
 * It should be useable for all presentations of multiple properties and should be adaptable to 
 * mortgageing, renovating, and selling renovations.
 * 
 * @author James Kasten
 */
public class PropertySelectionPanel
{
    public PropertySelectionPanel(ArrayList<PropertySpace> propertyList) { 
    	
    	final DefaultListModel model = new DefaultListModel();
    	
    	for (int i = 0; i < propertyList.size(); i++) {
    		model.addElement(propertyList.get(i).getName());
    	}
    	model.addElement("TEST1");
    	model.addElement("TEST2");
    	model.addElement("TEST3");
    	

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

	JList list = new JList(model);
	//list.addKeyListener(keyTypedListener);
	final ListSelectionModel listSelection = list.getSelectionModel();
	listSelection.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	listSelection.addListSelectionListener(new SharedListSelectionHandler());
	
	JFrame frame = new JFrame("Vandopoly Properties Owned");
	frame.setLayout(null);
	frame.setSize(300,180);
	frame.setLocation((int)((DisplayAssembler.getScreenWidth() - 300) / 2), (int)((DisplayAssembler.getScreenHeight() - 180) / 2));

	JScrollPane scrollPane = new JScrollPane(list);
	scrollPane.setBorder(new TitledBorder("Select Properties"));
	scrollPane.setBounds(0,0,300,100);
	scrollPane.setVisible(true);
	
	Font buttonFont = new Font("broadway",Font.PLAIN,18);
	JButton mortgage = new JButton("Mortgage");
	mortgage.setFont(buttonFont);
	mortgage.setBounds(0,100,150,50);
	mortgage.setVisible(true);
	mortgage.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
        	model.remove(listSelection.getAnchorSelectionIndex());
        }

    });
	
	JButton finish = new JButton("Finished");
	finish.setFont(buttonFont);
	finish.setBounds(150,100,150,50);
	finish.setVisible(true);
	
	frame.add(scrollPane);
	frame.add(mortgage);
	frame.add(finish);

	frame.setVisible(true);
    }

}

class SharedListSelectionHandler implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) { 
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();

        int firstIndex = e.getFirstIndex();
        int lastIndex = e.getLastIndex();
        boolean isAdjusting = e.getValueIsAdjusting(); 
        System.out.println("Event for indexes "
                      + firstIndex + " - " + lastIndex
                      + "; isAdjusting is " + isAdjusting
                      + "; selected indexes:");

        if (lsm.isSelectionEmpty()) {
        	System.out.println(" <none>");
        } else {
            // Find out which indexes are selected.
            int minIndex = lsm.getMinSelectionIndex();
            int maxIndex = lsm.getMaxSelectionIndex();
            for (int i = minIndex; i <= maxIndex; i++) {
                if (lsm.isSelectedIndex(i)) {
                	System.out.println(" " + i);
                }
            }
        }
    }
}