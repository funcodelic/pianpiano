package com.funcodelic.pianpiano.sheetmusicnotation;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;

//
//	The Page Inspector Panel allows the user to configure the selected page.
//
class PageInspectorPanel extends JPanel {
	
	JButton addStaffSystemButton;
	
	
	// C'tor
	public PageInspectorPanel(PageController page) {
		// Instantiate the button to add new staff systems to the page
		addStaffSystemButton = new JButton("Add New Staff System");
		
		// Add an action listener to the add button
        addStaffSystemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Component parent = getParent();
                if ( parent instanceof NodeAdder ) {
                	NodeAdder nodeAdder = (NodeAdder) parent;
                	nodeAdder.addNodeAtIndex(page.addStaffSystem(), page.getStaffSystems().size() - 1);
                }
                
            }
        });
		
		// Add the button to the panel
        add(addStaffSystemButton);
		
		// Set the name and background color
        setName( "Page Inspector" );
        setBackground(Color.GRAY);
		
        // Add a border with padding around the inspector panel
        Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        setBorder(BorderFactory.createCompoundBorder(etchedBorder, paddingBorder));
	}

}
