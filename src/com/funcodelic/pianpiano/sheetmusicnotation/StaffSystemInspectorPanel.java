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
	
	// Used to select various items of the staff system
    ButtonGroup selectionGroup;
    
    // To select the overall staff system
    JRadioButton staffSystemButton;
    
    // Used to select the measures of the staff system
    ButtonGroup measureGroup;
    
    // Panel for the staff system measures
    JPanel measuresPanel;
    JPanel measureButtonPanel;
    int measureIndex = 0;
    ActionListener measureSelectionListener;
		
	
	// C'tor
	public StaffSystemInspectorPanel( StaffSystemController staffSystem ) {
		this.staffSystem = staffSystem;
		
		setLayout( new BorderLayout() );
		
		// Create and configure the title label
        JLabel titleLabel = new JLabel( staffSystem.toString() );
        titleLabel.setForeground( Color.WHITE );
        titleLabel.setHorizontalAlignment( SwingConstants.CENTER );
        titleLabel.setBorder( new EmptyBorder( 10, 0, 10, 0) );
        add( titleLabel, BorderLayout.NORTH );
        
        // Create and add the radio button selection panel
		add( createRadioButtonPanel(), BorderLayout.CENTER );
		
		// Create the measures panel but make it invisible
		measuresPanel = createMeasurePanel();
		showMeasuresPanel( false );
		
		// Add the measures panel
		add( measuresPanel, BorderLayout.SOUTH );
		
		// Set the name and background color
        setName( "Staff System Inspector" );
        setBackground( Color.GRAY );
        
        // Add a border with padding around the inspector panel
        Border etchedBorder = BorderFactory.createEtchedBorder( EtchedBorder.LOWERED );
        Border paddingBorder = BorderFactory.createEmptyBorder( 10, 10, 10, 10 );
        setBorder( BorderFactory.createCompoundBorder( etchedBorder, paddingBorder ) );
	}
	
	private void addMeasure() {
		// Create the measure
		MeasureController newMeasure = staffSystem.addMeasure();
		
		// Determine the measure number from its index
		int measureNumber = newMeasure.getIndex() + 1;
		
		// Add the new measure node to the score tree
		addNode( newMeasure, newMeasure.getIndex() );
		
		// Create and configure the measure button representing the new measure
		JRadioButton measureButton = new JRadioButton("Measure " + measureNumber );
		measureButton.setForeground( Color.WHITE );
		measureButton.setActionCommand( "Measure " + measureNumber );
		
		// Add the selection action listener
		measureButton.addActionListener( measureSelectionListener );
		
		// Add it to the group and button panel
		measureGroup.add( measureButton );
		measureButtonPanel.add( measureButton );
		
		// Select the newly added button and as a result the new measure itself
		measureButton.setSelected( true );
		// Select the measure
		selectMeasure( newMeasure.getIndex() );
		
		// Refresh the panel
		refresh();
	}
	
	private void handleMeasureSelection() {
		// Get the selected index from the radio group selection's action command
		String actionCommand = measureGroup.getSelection().getActionCommand();

		// Get the last character of the string, which should be the measure index
		char lastChar = actionCommand.charAt( actionCommand.length() - 1 );

		// Convert the character to an integer
		int index = Character.getNumericValue( lastChar ) - 1;
		
		// Select the measure
		selectMeasure( index );
	}
	
	// Creates a list of measures in the form of selectable radio buttons
    private JPanel createMeasurePanel() {
    	// Create the complete Measures Panel
    	JPanel panel = new JPanel();
    	panel.setName( "Measure Selection Panel" );
        panel.setLayout( new BorderLayout() ); //0 rows for a dynamic list
        panel.setBackground( Color.GRAY );
        
        // Create the add measure button and add it to the center
        JButton addMeasureButton = new JButton( "Add Measure" );
        
        // Create the action listener to be added to each new measure's radio button
        ActionListener addMeasureListener = new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
            	addMeasure();
            }
        };
        addMeasureButton.addActionListener( addMeasureListener );
        
        // Add the button to the panel
        panel.add( addMeasureButton, BorderLayout.NORTH );
        
        // Create the panel to store the measure radio buttons and add it to the south
        measureButtonPanel = new JPanel(); //GridLayout(0, 1, 20, 20)
        measureButtonPanel.setBackground( Color.GRAY );
        measureButtonPanel.setLayout( new GridLayout( 0, 1, 20, 10 ) );
        //measureButtonPanel.setLayout(new BoxLayout(measureButtonPanel, BoxLayout.Y_AXIS));
        measureButtonPanel.setAlignmentY( Component.TOP_ALIGNMENT );
        
        // Add the button to the panel
        panel.add( measureButtonPanel, BorderLayout.CENTER );
        
        // Create a ButtonGroup and add the radio buttons to it
        measureGroup = new ButtonGroup();
        
        // Create the action listener to be added to each new measure's radio button
        measureSelectionListener = new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
            	handleMeasureSelection();
            }
        };
        
        // Add a border with padding around the inspector panel
        Border etchedBorder = BorderFactory.createEtchedBorder( EtchedBorder.LOWERED );
        Border paddingBorder = BorderFactory.createEmptyBorder( 10, 10, 10, 10 );
        panel.setBorder( BorderFactory.createCompoundBorder( etchedBorder, paddingBorder ) );
        
        // Return the configured panel
        return panel;
    }
	
	// Creates and returns the JPanel with the staff system item selection radio buttons
	private JPanel createRadioButtonPanel() {
        // Create a JPanel
        JPanel panel = new JPanel();
        panel.setName( "Staff System Selection Panel" );
        panel.setLayout( new GridLayout( 5, 1, 10, 10 ) );
        
        // Create and configure the title label
        JLabel titleLabel = new JLabel( "Select to adjust:" );
        titleLabel.setForeground( Color.WHITE );
        //titleLabel.setBorder( new EmptyBorder( 10, 0, 10, 0) );
        panel.add( titleLabel );

        // Set the background color to dark gray
        panel.setBackground( Color.GRAY );
        
        // Create radio buttons
        staffSystemButton = new JRadioButton( "Staff System" );
        JRadioButton upperStaffButton = new JRadioButton( "Upper Staff" );
        JRadioButton lowerStaffButton = new JRadioButton( "Lower Staff" );
        JRadioButton measuresButton = new JRadioButton( "Measures" );
        
        // Set the text color to white
        staffSystemButton.setForeground( Color.WHITE );
        upperStaffButton.setForeground( Color.WHITE );
        lowerStaffButton.setForeground( Color.WHITE );
        measuresButton.setForeground( Color.WHITE );

        // Create a ButtonGroup and add the radio buttons to it
        selectionGroup = new ButtonGroup();
        selectionGroup.add( staffSystemButton );
        selectionGroup.add( upperStaffButton );
        selectionGroup.add( lowerStaffButton );
        selectionGroup.add( measuresButton );
        
        // Define the onSelect method to determine which button was selected
        ActionListener onSelectListener = new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                JRadioButton selectedButton = (JRadioButton) e.getSource();
                onSelect( selectedButton );
            }
        };
        
        // Add the listener to each radio button
        staffSystemButton.addActionListener( onSelectListener );
        upperStaffButton.addActionListener( onSelectListener );
        lowerStaffButton.addActionListener( onSelectListener );
        measuresButton.addActionListener( onSelectListener );
        
        // Set the commands of the radio buttons
        staffSystemButton.setActionCommand( "Staff System" );
        upperStaffButton.setActionCommand( "Upper Staff" );
        lowerStaffButton.setActionCommand( "Lower Staff" );
        measuresButton.setActionCommand( "Measures" );

        // Add radio buttons to the panel
        panel.add( staffSystemButton );
        panel.add( upperStaffButton );
        panel.add( lowerStaffButton );
        panel.add( measuresButton );
        
        // Add a border with padding around the inspector panel
        Border etchedBorder = BorderFactory.createEtchedBorder( EtchedBorder.LOWERED );
        Border paddingBorder = BorderFactory.createEmptyBorder( 10, 10, 10, 10 );
        panel.setBorder( BorderFactory.createCompoundBorder( etchedBorder, paddingBorder ) );

        // Return the configured panel
        return panel;
    }
	
	public void clearSelections() {
		selectionGroup.clearSelection();
		measureGroup.clearSelection();
		showMeasuresPanel( false );
	}
	
	public void selectStaffSystem() {
		clearSelections();
		staffSystemButton.setSelected( true );
	}
	
	// Called when a selection is made from the selection radio button panel
    private void onSelect( JRadioButton selectedButton ) {
        // Logic to handle the selection based on the selected button
        PageInterface selectedInterface = null;
        
        if ( selectedButton.getActionCommand() == "Staff System" ) {
        	selectedInterface = staffSystem.selectItem( STAFF_SYSTEM );
        	showMeasuresPanel( false );
        }
        else if ( selectedButton.getActionCommand() == "Upper Staff" ) {
        	selectedInterface = staffSystem.selectItem( UPPER_STAFF );
        	showMeasuresPanel( false );
        }
        else if ( selectedButton.getActionCommand() == "Lower Staff" ) {
        	selectedInterface = staffSystem.selectItem( LOWER_STAFF );
        	showMeasuresPanel( false );
        }
        else if ( selectedButton.getActionCommand() == "Measures" ) {
        	// Select staff system measures, but retrieve the page interface
        	// from the measure selection, so ignore the return value
        	staffSystem.selectItem( MEASURES );
        	showMeasuresPanel( true );
        }
        
        setPageInterface( selectedInterface );
        
 		refresh();
    }
    
    void setPageInterface( PageInterface pgInterface ) {
    	if ( getParent() instanceof NodeAdder ) {
 			((NodeAdder)getParent()).setPageInterface( pgInterface );
 		}
    }
    
    void selectMeasure( int index ) {
    	PageInterface pgIf = staffSystem.selectMeasure( index );
    	setPageInterface( pgIf );
    }
    
    void refresh() {
    	// TODO: Fix this
        // Refresh the view
        Component grandfather = getParent().getParent();
        grandfather.revalidate();
        grandfather.repaint();
    }
    
    private void showMeasuresPanel( boolean showPanel ) {
    	measuresPanel.setVisible( showPanel );
    	
    	// Deselect the selected measure if the panel is being hidden
    	if ( !showPanel ) {
    		measureGroup.clearSelection();
    	}
    }
    
    @Override
    public String toString() {
    	return "Staff System Inspector Panel";
    }
    
    private void addNode( Object nodeObj, int index ) {
    	Component parent = getParent();
        if ( parent instanceof NodeAdder ) {
        	NodeAdder nodeAdder = (NodeAdder) parent;
        	nodeAdder.addNodeAtIndex( nodeObj, index );
        	refresh();
        }
    }
	
}
