package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

//
//	ScoreView displays the score's metadata
//
class ScoreView extends JPanel {
	
	JLabel nameTitleLabel;
	JLabel nameLabel;
	JLabel composerTitleLabel;
	JLabel composerLabel;
	
	
	// C'tor
	public ScoreView() {
		// Instantiate the labels
		nameTitleLabel = new JLabel("Name:");
		nameLabel= new JLabel();
		composerTitleLabel = new JLabel("Composer:");
		composerLabel = new JLabel();
		
		// Center-align the name and composer name labels
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		composerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Set the font to the label
		Font titleFont = new Font("Arial", Font.PLAIN, 20);
		Font nameFont = new Font("Arial", Font.PLAIN, 25);
		nameTitleLabel.setFont(titleFont);
		nameLabel.setFont(nameFont);
		composerTitleLabel.setFont(titleFont);
		composerLabel.setFont(nameFont);
		
		// Set the title text color
		nameTitleLabel.setForeground(Color.DARK_GRAY);
		composerTitleLabel.setForeground(Color.DARK_GRAY);

		// Add the labels
		setLayout(new GridLayout(4, 1));
		add(nameTitleLabel);
		add(nameLabel);
		add(composerTitleLabel);
		add(composerLabel);
		
		// Set the background color
        setBackground(Color.LIGHT_GRAY);
		
		// Add a border with padding around the inspector panel
        Border etchedBorder = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border paddingBorder = BorderFactory.createEmptyBorder(200, 200, 200, 200);
        setBorder(BorderFactory.createCompoundBorder(etchedBorder, paddingBorder));
	}
	
	public void setName(String name) {
		nameLabel.setText(name);
	}

	public void setComposer(String composer) {
		composerLabel.setText(composer);
	}
}
