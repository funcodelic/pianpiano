package com.funcodelic.pianpiano.gui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;

//
//	This class represents the Navigator panel of the Editor GUI.
//
//	It houses the scrore's tree so the user can select its entities
//	and navigate through the score's sheet music entity hierarchy.
//
class PianPianoNavigatorPanel extends JPanel {
	// The view to display
	private JComponent currentView;
	
	
	// C'tor
	public PianPianoNavigatorPanel() {
		setName( "Navigator Panel" );
		setLayout( new BorderLayout() );
		setBackground( Color.BLACK );
	}
	
	public void setView( JComponent view ) {
		removeAll();
        currentView = new JScrollPane(view);
		add(currentView, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

}
