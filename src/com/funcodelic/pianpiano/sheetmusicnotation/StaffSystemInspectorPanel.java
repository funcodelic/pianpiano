package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.Color;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

class StaffSystemInspectorPanel extends JPanel {
	// The staff system to inspect
	StaffSystemController staffSystem;
	
	
	// C'tor
	public StaffSystemInspectorPanel( StaffSystemController staffSystem ) {
		this.staffSystem = staffSystem;
		
		JButton editStaffLinesButton = new JButton("Edit Staff Lines");
		add(editStaffLinesButton);
		
		// Set the name and background color
        setName( "Staff System Inspector" );
        setBackground(Color.GRAY);
        
        // Add a border with padding around the inspector panel
        Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        setBorder(BorderFactory.createCompoundBorder(etchedBorder, paddingBorder));
	}

}
