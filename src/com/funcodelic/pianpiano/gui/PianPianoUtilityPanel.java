package com.funcodelic.pianpiano.gui;

import java.awt.*;
import javax.swing.*;

class PianPianoUtilityPanel extends JPanel {
	// The view to display
	private JComponent currentView;
	
	
	// C'tor
	public PianPianoUtilityPanel() {
		setName( "Utility" );
		setLayout( new BorderLayout() );
		setBackground( Color.GRAY );
	}
	
	public void setView( JComponent view ) {
    	removeAll();
        currentView = view;
        add(currentView, BorderLayout.NORTH);
        revalidate();
        repaint();
    }

}
