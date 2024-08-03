package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import static com.funcodelic.pianpiano.sheetmusicnotation.StaffSystemController.InterfaceSelection.*;


//
//	The inspector panel for the Staff System
//
class StaffSystemInspectorPanel extends JPanel {
	
	// The staff system to inspect
	private StaffSystemController staffSystem;
	
    ButtonGroup selectionGroup;
		
	
	// C'tor
	public StaffSystemInspectorPanel( StaffSystemController system ) {
		this.staffSystem = system;
		
		setLayout( new BorderLayout() );
		
		// Create and configure the title label
        JLabel titleLabel = new JLabel( "Select to edit:" );
        titleLabel.setForeground( Color.WHITE );
        titleLabel.setBorder( new EmptyBorder( 10, 0, 10, 0) );
        
        add( titleLabel, BorderLayout.NORTH );
		add( createRadioButtonPanel(), BorderLayout.CENTER );
		
		// Set the name and background color
        setName( "Staff System Inspector" );
        setBackground( Color.GRAY );
        
        // Add a border with padding around the inspector panel
        Border etchedBorder = BorderFactory.createEtchedBorder( EtchedBorder.LOWERED );
        Border paddingBorder = BorderFactory.createEmptyBorder( 10, 10, 10, 10 );
        setBorder( BorderFactory.createCompoundBorder( etchedBorder, paddingBorder ) );
	}
	
	// Creates and returns the JPanel with the staff system item selection radio buttons
	private JPanel createRadioButtonPanel() {
        // Create a JPanel
        JPanel panel = new JPanel();
        panel.setName("Staff System Selection Panel");
        panel.setLayout( new GridLayout( 3, 1, 20, 20 ) ); // Set layout to GridLayout with one column and three rows

        // Set the background color to dark gray
        panel.setBackground( Color.GRAY );
        
        // Create radio buttons
        JRadioButton staffSystemButton = new JRadioButton("Staff System");
        JRadioButton upperStaffButton = new JRadioButton("Upper Staff");
        JRadioButton lowerStaffButton = new JRadioButton("Lower Staff");
        
        // Set the text color to white
        staffSystemButton.setForeground( Color.WHITE );
        upperStaffButton.setForeground( Color.WHITE );
        lowerStaffButton.setForeground( Color.WHITE );

        // Create a ButtonGroup and add the radio buttons to it
        selectionGroup = new ButtonGroup();
        selectionGroup.add(staffSystemButton);
        selectionGroup.add(upperStaffButton);
        selectionGroup.add(lowerStaffButton);
        
        // Define the onSelect method to determine which button was selected
        ActionListener onSelectListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JRadioButton selectedButton = (JRadioButton) e.getSource();
                onSelect(selectedButton);
            }
        };
        
        // Add the ActionListener to each radio button
        staffSystemButton.addActionListener(onSelectListener);
        upperStaffButton.addActionListener(onSelectListener);
        lowerStaffButton.addActionListener(onSelectListener);
        
        // Set the commands of the radio buttons
        staffSystemButton.setActionCommand( "Staff System" );
        upperStaffButton.setActionCommand( "Upper Staff" );
        lowerStaffButton.setActionCommand( "Lower Staff" );

        // Add radio buttons to the panel
        panel.add(staffSystemButton);
        panel.add(upperStaffButton);
        panel.add(lowerStaffButton);
        
        // Add a border with padding around the inspector panel
        Border etchedBorder = BorderFactory.createEtchedBorder( EtchedBorder.LOWERED );
        Border paddingBorder = BorderFactory.createEmptyBorder( 10, 10, 10, 10 );
        panel.setBorder( BorderFactory.createCompoundBorder( etchedBorder, paddingBorder ) );

        // Return the configured panel
        return panel;
    }
	
	public void clearSelections() {
		selectionGroup.clearSelection();
	}
	
	// Called when a selection is made from the selection radio button panel
    private void onSelect(JRadioButton selectedButton) {
        // Logic to handle the selection based on the selected button
        PageInterface selectedInterface = null;
        
        if ( selectedButton.getActionCommand() == "Staff System" ) {
        	selectedInterface = staffSystem.selectItem( STAFF_SYSTEM );
        }
        else if ( selectedButton.getActionCommand() == "Upper Staff" ) {
        	selectedInterface = staffSystem.selectItem( UPPER_STAFF );
        }
        else if ( selectedButton.getActionCommand() == "Lower Staff" ) {
        	selectedInterface = staffSystem.selectItem( LOWER_STAFF );
        }

        // Update the page interface to manipulate the selected object in the page
 		if ( getParent() instanceof NodeAdder ) {
 			((NodeAdder)getParent()).setPageInterface(selectedInterface);
 		}
        
 		// TODO: Fix this
        // Refresh the view
        Component grandfather = getParent().getParent();
        grandfather.revalidate();
        grandfather.repaint();
    }
    
    @Override
    public String toString() {
    	return "Staff System Inspector Panel";
    }
	
}
