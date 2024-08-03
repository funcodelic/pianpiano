package com.funcodelic.pianpiano.gui;

import java.awt.*;
import javax.swing.*;
import com.funcodelic.pianpiano.sheetmusicnotation.NodeAdder;
import com.funcodelic.pianpiano.sheetmusicnotation.PageInterface;

//
//	Utility panel used to inspect and configure sheet music entities
//
class PianPianoUtilityPanel extends JPanel implements NodeAdder {
	// The view to display
	private JComponent currentView;
	
	// TODO: FIX THIS: A hack to get new nodes into the score tree
	// Used to pass new nodes to the Score Builder to add to the score tree
	private NodeAdder nodeAdder;
	
	
	// C'tor
	public PianPianoUtilityPanel(NodeAdder nodeAdder) {
		this.nodeAdder = nodeAdder;
				
		setName( "Utility Panel" );
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

	@Override
	public void addNodeAtIndex(Object nodeObject, int index) {
		if (nodeAdder != null) {
			nodeAdder.addNodeAtIndex(nodeObject, index);
		}
	}
	
	public void setPageInterface(PageInterface pageInterface) {
		if (nodeAdder != null) {
			nodeAdder.setPageInterface(pageInterface);
		}
	}
	
	@Override
	public String toString() {
		return "Utility Panel";
	}
}
