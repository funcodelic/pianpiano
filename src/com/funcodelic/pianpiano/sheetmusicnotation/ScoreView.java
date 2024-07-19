
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
	JLabel numPagesTitleLabel;
	JLabel numPagesLabel;
	
	
	// C'tor
	public ScoreView() {
		// Instantiate the labels
		nameTitleLabel = new JLabel("Name:");
		nameLabel= new JLabel();
		composerTitleLabel = new JLabel("Composer:");
		composerLabel = new JLabel();
		numPagesTitleLabel = new JLabel("Pages:");
		numPagesLabel = new JLabel();
		
		// Center-align the name, composer, and page number labels
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		composerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		numPagesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		// Set the font to the label
		Font titleFont = new Font("Arial", Font.PLAIN, 20);
		Font nameFont = new Font("Arial", Font.PLAIN, 25);
		nameTitleLabel.setFont(titleFont);
		nameLabel.setFont(nameFont);
		composerTitleLabel.setFont(titleFont);
		composerLabel.setFont(nameFont);
		numPagesTitleLabel.setFont(titleFont);
		numPagesLabel.setFont(nameFont);
		
		// Set the title text color
		nameTitleLabel.setForeground(Color.DARK_GRAY);
		composerTitleLabel.setForeground(Color.DARK_GRAY);
		numPagesTitleLabel.setForeground(Color.DARK_GRAY);

		// Add the labels
		setLayout(new GridLayout(6, 1));
		add(nameTitleLabel);
		add(nameLabel);
		add(composerTitleLabel);
		add(composerLabel);
		add(numPagesTitleLabel);
		add(numPagesLabel);
		
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
	
	public void setNumPages(String pages) {
		numPagesLabel.setText(pages);
	}

	public JComponent getView() {
		return this;
	}
}
