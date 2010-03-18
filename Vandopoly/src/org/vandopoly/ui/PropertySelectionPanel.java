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
import org.vandopoly.model.PropertyMortgaged;
import org.vandopoly.model.PropertySpace;


/*
 * PropertySelectionPanel is meant to provide a generic way to present an ArrayList of properties.
 * It should be usable for all presentations of multiple properties and should be adaptable to 
 * mortgaging, renovating, and selling renovations.
 * 
 * @author James Kasten
 */
public class PropertySelectionPanel implements  ListSelectionListener {
	
	private final ArrayList<PropertySpace> propertyList;
	private JButton mortgage;
	
    public PropertySelectionPanel(final Player player) { 
    	
    	propertyList = player.getProperties();
    	
    	final DefaultListModel model = new DefaultListModel();
    	
    	for (int i = 0; i < propertyList.size(); i++) {
    		if(propertyList.get(i).getState() != PropertyMortgaged.Instance())
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
	
	JFrame frame = new JFrame("Vandopoly Properties Owned");
	frame.setLayout(null);
	frame.setSize(300,180);
	frame.setLocation((int)((DisplayAssembler.getScreenWidth() - 300) / 2), (int)((DisplayAssembler.getScreenHeight() - 180) / 2));

	JScrollPane scrollPane = new JScrollPane(list);
	scrollPane.setBorder(new TitledBorder("Select Properties"));
	scrollPane.setBounds(0,0,300,100);
	scrollPane.setVisible(true);
	
	Font buttonFont = new Font("broadway",Font.PLAIN,18);
	mortgage = new JButton("Mortgage");
	mortgage.setFont(buttonFont);
	mortgage.setBounds(0,100,150,50);
	mortgage.setVisible(true);
	
	mortgage.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
        	int index = listSelection.getAnchorSelectionIndex();
        	
        	model.set(index, propertyList.get(index).getName() + " (Mortgaged)");
        	player.mortgage(propertyList.get(index));
        	mortgage.setEnabled(false);
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
    
    public void valueChanged(ListSelectionEvent e) { 
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();

        if(propertyList.get(lsm.getMinSelectionIndex()).getState() == PropertyMortgaged.Instance())
        	mortgage.setEnabled(false);
        else
        	mortgage.setEnabled(true);
        
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