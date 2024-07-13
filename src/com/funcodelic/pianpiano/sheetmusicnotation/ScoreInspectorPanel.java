package com.funcodelic.pianpiano.sheetmusicnotation;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

//
//	This class encapsulates the panel used to inspect and configure the score
//
class ScoreInspectorPanel extends JPanel {
	
	// Name title label and text field
	JLabel nameTitleLabel;
	JTextField nameField;
	
	// Composer title label and text field
	JLabel composerTitleLabel;
	JTextField composerField;
	
	
	// C'tor
	public ScoreInspectorPanel(ScoreController score) {
		// Name title field
		nameTitleLabel = new JLabel( "Name: " );
		nameTitleLabel.setForeground(Color.WHITE);
		
		// Composer title field
		composerTitleLabel = new JLabel( "Composer: " );
		composerTitleLabel.setForeground(Color.WHITE);
		
		// Name text field
		nameField = new JTextField();
		
		// Composer text field
		composerField = new JTextField();
		
		// Add the labels and text fields to the panel
		setLayout(new GridLayout(4, 1));
		add(nameTitleLabel);
		add(nameField);
		add(composerTitleLabel);
		add(composerField);
		
		// Add an action listener to the name field
        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String scoreName = nameField.getText();
            	score.setScoreName(scoreName);
            	nameField.transferFocus();
            }
        });
        
        // Capture the name in the name field if the user tabs to the composer field
        nameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // No action needed on focus gained
            }

            @Override
            public void focusLost(FocusEvent e) {
                //updateScoreName(); // Update the score's name when focus is lost
            	String scoreName = nameField.getText();
            	score.setScoreName(scoreName);
            }
        });
		
        // Add an action listener to the composer field
        composerField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	score.setScoreComposer(composerField.getText());
            }
        });
        
        // Set the background color
        setBackground(Color.GRAY);
		
        // Add a border with padding around the inspector panel
        Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        setBorder(BorderFactory.createCompoundBorder(etchedBorder, paddingBorder));
	}

}
