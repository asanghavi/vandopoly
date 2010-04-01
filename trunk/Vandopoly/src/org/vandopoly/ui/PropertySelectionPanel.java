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

import org.vandopoly.model.Player;
import org.vandopoly.model.PropertySpace;
import org.vandopoly.model.SpaceMortgaged;


/*
 * PropertySelectionPanel is meant to provide a generic way to present an ArrayList of properties.
 * It should be usable for all presentations of multiple properties and should be adaptable to 
 * mortgaging, renovating, and selling renovations.
 * 
 * @author James Kasten
 */
public class PropertySelectionPanel implements  ListSelectionListener {
	
	private final ArrayList<PropertySpace> propertyList;
	private JButton mortgage, renovate, unrenovate;
	
	JFrame frame;
	
	int panelWidth = 350;
	int panelHeight = 260;
	int buttonHeight = 50;
	int listHeight = 120;
	
    public PropertySelectionPanel(final Player player) { 
    	
    	frame = new JFrame("Vandopoly Properties Owned");
    	frame.setLayout(null);
    	frame.setSize(panelWidth, panelHeight);
    	frame.setLocation((int)((DisplayAssembler.getScreenWidth() - panelWidth) / 2), 
    			(int)((DisplayAssembler.getScreenHeight() - panelHeight) / 2));
    	
    	propertyList = player.getProperties();
    	
    	final DefaultListModel model = new DefaultListModel();
    	
    	for (int i = 0; i < propertyList.size(); i++) {
    		if(propertyList.get(i).getState() != SpaceMortgaged.Instance())
    			model.addElement(propertyList.get(i).getName());
    		else
    			model.addElement(propertyList.get(i).getName() + " (Mortgaged)");
    	}
    	

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
	listSelection.addListSelectionListener(this);

	JScrollPane scrollPane = new JScrollPane(list);
	scrollPane.setBorder(new TitledBorder("Select Properties"));
	scrollPane.setBounds(0, 0, panelWidth, listHeight);
	scrollPane.setVisible(true);
	
	Font buttonFont = new Font("broadway", Font.PLAIN,18);
	
	renovate = new JButton("Renovate");
	renovate.setFont(buttonFont);
	renovate.setBounds(0, listHeight, (panelWidth / 2), buttonHeight);
	renovate.setVisible(true);
	
	mortgage = new JButton("Mortgage");
	mortgage.setFont(buttonFont);
	mortgage.setBounds(0, listHeight + buttonHeight, panelWidth, buttonHeight);
	mortgage.setVisible(true);
	
	mortgage.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
        	int index = listSelection.getAnchorSelectionIndex();
        	
        	// Represents case when no properties are selected
			if(index < 0) {
				System.out.println(player.getName() + " tried to mortage: " + index + " which is invalid");
			}
			// If property selected is currently being mortgaged
			else if(!propertyList.get(index).getState().equals(SpaceMortgaged.Instance())) {
				model.set(index, propertyList.get(index).getName() + " (Mortgaged)");
				player.mortgage(propertyList.get(index));
				mortgage.setText("Unmortgage");
			}
			else if(propertyList.get(index).getState().equals(SpaceMortgaged.Instance())) {
				model.set(index, propertyList.get(index).getName());
				player.unmortgage(propertyList.get(index));
				mortgage.setText("Mortgage");
    		}
        }

    });
	
	unrenovate = new JButton("UnRenovate");
	unrenovate.setFont(buttonFont);
	unrenovate.setBounds((panelWidth / 2), listHeight,(panelWidth / 2),buttonHeight);
	unrenovate.setVisible(true);
	unrenovate.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
        	frame.dispose();
        }

    });
	
	
	frame.add(scrollPane);
	frame.add(renovate);
	frame.add(mortgage);
	frame.add(unrenovate);
	
	frame.setResizable(false);
	frame.setVisible(true);
    }
    
    public void dispose() {
    	frame.dispose();
    }
    
    public void valueChanged(ListSelectionEvent e) { 
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();

        // Update the button words based on state of property
        if(propertyList.get(lsm.getMinSelectionIndex()).getState().equals(SpaceMortgaged.Instance())) {
        	mortgage.setText("Unmortgage");
        }
        else
        	mortgage.setText("Mortgage");
        
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
/*
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
}*/