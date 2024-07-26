package com.funcodelic.pianpiano.sheetmusicnotation;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

//
//	This class encapsulates the panel used to inspect and configure the score
//
class ScoreInspectorPanel extends JPanel {
	
	// The score this inspector panel is inspecting
	ScoreController score;
	
	// Name title label and text field
	JLabel nameTitleLabel;
	JTextField nameField;
	
	// Composer title label and text field
	JLabel composerTitleLabel;
	JTextField composerField;
	
	// Add Page title label and button
	JLabel addPageTitleLabel;
	JButton addPageButton;
	
	
	// C'tor
	public ScoreInspectorPanel(ScoreController score) {
		// Store the score
		this.score = score;
		
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
		
		// Add page button
		int numPages = score.getNumberOfPages();
		addPageTitleLabel = new JLabel( numPages + " pages" );
		addPageTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		addPageTitleLabel.setForeground(Color.WHITE);
        addPageButton = new JButton("Add New Page");
		
		// Create a grid layout and give each field some room vertically
        GridLayout gridLayout = new GridLayout(6, 1);
        gridLayout.setVgap(10); // Set the vertical gap to 10
        setLayout(gridLayout);
        
        // Add the labels and text fields to the panel
		add(nameTitleLabel);
		add(nameField);
		add(composerTitleLabel);
		add(composerField);
		add(addPageTitleLabel);
		add(addPageButton);
		
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
                // Update the score's name when focus is lost (the user tabs)
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
        
        // Capture the name in the name field if the user tabs to the composer field
        composerField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // No action needed on focus gained
            }

            @Override
            public void focusLost(FocusEvent e) {
            	// Update the score's composer when focus is lost (the user tabs)
            	String compName = composerField.getText();
            	score.setScoreComposer(compName);
            }
        });
        
        // Add action listener to addPageButton
        addPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openFileChooser();
            }
        });
        
        // Set the name and background color
        setName( "Score Inspector" );
        setBackground(Color.GRAY);
		
        // Add a border with padding around the inspector panel
        Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        setBorder(BorderFactory.createCompoundBorder(etchedBorder, paddingBorder));
	}
	
	public void refresh() {
		int numPages = score.getNumberOfPages();
		
		String unitsString = numPages == 1 ? " page" : " pages";
		addPageTitleLabel.setText( numPages + unitsString );
	}
	
	private void openFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            score.addPage(filePath);
        }
    }

}
