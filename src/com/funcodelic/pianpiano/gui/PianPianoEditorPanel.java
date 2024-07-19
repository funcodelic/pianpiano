package com.funcodelic.pianpiano.gui;

import javax.swing.*;
import java.awt.*;
import com.funcodelic.pianpiano.sheetmusicnotation.*;

//
//	This class encapsulates the editor panel of the GUI.
//
//	It holds a Page View wrapped in a JScrollPane
//	and helps with zooming and panning to facilitate building the score.
//
class PianPianoEditorPanel extends JPanel {
	// The view to display
	private JComponent currentView;
    

    public PianPianoEditorPanel() {
    	setName( "Editor" );
    	setLayout( new BorderLayout() );
    	setBackground( Color.DARK_GRAY );
    }
    
    public void setView( JComponent view ) {
    	removeAll();
        currentView = view;
        add(currentView, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}