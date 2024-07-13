package com.funcodelic.pianpiano.gui;

import javax.swing.*;
import java.awt.BorderLayout;
import com.funcodelic.pianpiano.sheetmusicnotation.ScoreTree;

//
//	This class represents the Navigator panel of the Editor GUI.
//
//	It houses the scrore's tree so the user can select its entities
//	and navigate through the score's sheet music entity hierarchy.
//
class PianPianoNavigatorPanel extends JPanel {
	
	
	// C'tor
	public PianPianoNavigatorPanel() {
	}
	
	// Set the score's tree for display in the navigator panel
	public void setScoreTree(ScoreTree scoreTree) {
		// Wrap the tree in a scroll pane, add it to the panel, and refresh
		JScrollPane scrollPane = new JScrollPane(scoreTree);
		add(scrollPane, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

}
