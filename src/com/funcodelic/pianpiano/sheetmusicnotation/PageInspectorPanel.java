package com.funcodelic.pianpiano.sheetmusicnotation;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.Color;
import java.awt.event.*;

//
//	The Page Inspector Panel allows the user to configure the selected page.
//
class PageInspectorPanel extends JPanel {
	
	JButton addStaffSystemButton = new JButton("Add New Staff System");
	
	
	// C'tor
	public PageInspectorPanel(PageController page) {
		// Add an action listener to the addStaffSystemButton
        addStaffSystemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	System.out.println("Adding a new staff system to the page...");
                //page.addStaffSystem();
            }
        });
		
		// Add the button to the panel
        add(addStaffSystemButton);
		
		// Set the background color
        setBackground(Color.GRAY);
		
        // Add a border with padding around the inspector panel
        Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        setBorder(BorderFactory.createCompoundBorder(etchedBorder, paddingBorder));
	}

}
