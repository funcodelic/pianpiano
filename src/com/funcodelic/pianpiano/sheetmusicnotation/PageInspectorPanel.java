package com.funcodelic.pianpiano.sheetmusicnotation;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;

//
//	The Page Inspector Panel allows the user to configure the selected page.
//
class PageInspectorPanel extends JPanel {
	
	// The page
	PageController page;
	
	// Button used to add staff systems to the page
	JButton addStaffSystemButton;
	JLabel statusLabel;
	
	
	// C'tor
	public PageInspectorPanel( PageController page ) {
		// Store the page to inspect
		this.page = page;
		
		// Add a border with padding around the inspector panel
        Border etchedBorder = BorderFactory.createEtchedBorder( EtchedBorder.LOWERED );
        Border paddingBorder = BorderFactory.createEmptyBorder( 10, 10, 10, 10 );
        setBorder( BorderFactory.createCompoundBorder( etchedBorder, paddingBorder ) );
        
        // Set the layout to border layout
        setLayout( new BorderLayout() );
        
		// Title label
		JLabel titleLabel = new JLabel( "Page " + page.getPageNumber() );
		titleLabel.setHorizontalAlignment( SwingConstants.CENTER );
		titleLabel.setForeground( Color.WHITE );
		titleLabel.setBorder( paddingBorder );
		
		// Add the title label to the panel
		add( titleLabel, BorderLayout.NORTH );
		
		// Instantiate the button to add new staff systems to the page
		addStaffSystemButton = new JButton( "Add New Staff System" );
		
		// Add an action listener to the add button
        addStaffSystemButton.addActionListener( new ActionListener() {
        	
            @Override
            public void actionPerformed( ActionEvent e ) {
            	// TODO: Fix this: get the parent, which is the utility panel,
            	//		 to add the newly created staff system node
            	Component parent = getParent();
            	
                if ( parent instanceof NodeAdder ) {
                	// Add the newly created staff system to the tree
                	NodeAdder nodeAdder = (NodeAdder) parent;
                	nodeAdder.addNodeAtIndex( page.createStaffSystem(), page.getStaffSystems().size() - 1 );
                	
                	// Update the status label
                	statusLabel.setText( page.getNumStaffSystems() + getNumStaffsString() );
                }
            }
        });
		
		// Add the button to the panel
        add( addStaffSystemButton, BorderLayout.CENTER );
        
        // Create a status label to let the user know how many staff systems have been added
        statusLabel = new JLabel( page.getNumStaffSystems() + getNumStaffsString() );
        statusLabel.setHorizontalAlignment( SwingConstants.CENTER );
        statusLabel.setForeground( Color.WHITE );
        statusLabel.setBorder( paddingBorder );
        
        // Add the status label to the panel
        add( statusLabel, BorderLayout.SOUTH );
		
		// Set the name and background color
        setName( "Page Inspector" );
        setBackground( Color.GRAY );
	}
	
	private String getNumStaffsString() {
		return page.getNumStaffSystems() != 1 ? " Staff Systems" : " Staff System";
	}

}
