package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.*;
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
		
		JLabel titleLabel = new JLabel( measure.toString() );
		titleLabel.setForeground( Color.WHITE );
		titleLabel.setHorizontalAlignment( SwingConstants.CENTER );
		
		setLayout( new BorderLayout() );
		add( titleLabel, BorderLayout.CENTER );
		
		// Set the name and background color
        setName( "Measure Inspector" );
        setBackground( Color.GRAY );
        
        // Add a border with padding around the inspector panel
        Border etchedBorder = BorderFactory.createEtchedBorder( EtchedBorder.LOWERED );
        Border paddingBorder = BorderFactory.createEmptyBorder( 10, 10, 10, 10 );
        setBorder( BorderFactory.createCompoundBorder( etchedBorder, paddingBorder ) );
	}
	
	@Override
	public String toString() {
		return "Measure Inspector Panel";
	}
}
