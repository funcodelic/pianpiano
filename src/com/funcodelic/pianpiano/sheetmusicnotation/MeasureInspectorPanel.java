package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

//
//	The inspector panel for the selected measure
//
class MeasureInspectorPanel extends JPanel {

	// The measure to inspect
	MeasureController measure;
	
	
	// C'tor
	MeasureInspectorPanel( MeasureController measure ) {
		this.measure = measure;
		
		// Set the name and background color
        setName( "Measure Inspector" );
        setBackground( Color.GRAY );
        
        // Give it a border layout
        setLayout( new BorderLayout() );
		
		// Add a border with padding
        Border etchedBorder = BorderFactory.createEtchedBorder( EtchedBorder.LOWERED );
        Border paddingBorder = BorderFactory.createEmptyBorder( 10, 10, 10, 10 );
        setBorder( BorderFactory.createCompoundBorder( etchedBorder, paddingBorder ) );
		
        // Title label displaying which measure is being inspected
		JLabel titleLabel = new JLabel( measure.toString() );
		titleLabel.setForeground( Color.WHITE );
		titleLabel.setHorizontalAlignment( SwingConstants.CENTER );
		titleLabel.setBorder( paddingBorder );
		
		add( titleLabel, BorderLayout.NORTH );
        
        // Make a JPanel to hold the two add note buttons, one for each staff
        JPanel buttonPanel = new JPanel();
        buttonPanel.setName( "Add Note Button Panel" );
        buttonPanel.setBackground( Color.GRAY );
        buttonPanel.setLayout( new GridLayout( 2, 1, 4, 4 ) );
        
        JButton addUpperNoteButton = new JButton( "Add Upper Note" );
        JButton addLowerNoteButton = new JButton( "Add Lower Note" );
        
        addUpperNoteButton.setActionCommand( "Add Upper Note" );
        addLowerNoteButton.setActionCommand( "Add Lower Note" );
        
        ActionListener addNoteListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// Get the action command of the button
			    String actionCommand = e.getActionCommand();
			    
			    // Set the note's staff based on which button was pressed
			    boolean isUpperStaff = actionCommand == "Add Upper Note" ? true : false;
			    measure.createNote( isUpperStaff );

			    // Set the page interface to place the note
			    PageInterface pgInterface = measure.getPlaceNoteInterface();
				setPageInterface( pgInterface );
				
				// Refresh the GUI
				refresh();
			}
		};
		addUpperNoteButton.addActionListener( addNoteListener );
		addLowerNoteButton.addActionListener( addNoteListener );
		
		buttonPanel.add( addUpperNoteButton );
		buttonPanel.add( addLowerNoteButton );
		
		add( buttonPanel, BorderLayout.CENTER );
	}
	
	@Override
	public String toString() {
		return "Measure Inspector Panel";
	}
	
	void setPageInterface( PageInterface pgInterface ) {
    	if ( getParent() instanceof NodeAdder ) {
 			((NodeAdder)getParent()).setPageInterface( pgInterface );
 		}
    }
	
	void refresh() {
    	// TODO: Fix this
        // Refresh the view
        Component grandfather = getParent().getParent();
        grandfather.revalidate();
        grandfather.repaint();
    }
}
