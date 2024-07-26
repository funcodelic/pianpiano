package com.funcodelic.pianpiano.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

//
//	Represents the toolbar to display prompts and score tree paths
//
class PianPianoToolbarPanel extends JPanel {
	// The view to display
	private JComponent currentView;
	
	
	// C'tor
	public PianPianoToolbarPanel() {
		setName( "Toolbar" );
		setLayout( new BorderLayout() );
		setBackground( Color.LIGHT_GRAY );
		
		// Add a border with padding around the inspector panel
        Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        setBorder(BorderFactory.createCompoundBorder(etchedBorder, paddingBorder));
	}
	
	public void setView( JComponent view ) {
    	removeAll();
        currentView = view;
        add(currentView, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

}
