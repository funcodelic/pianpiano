package com.funcodelic.pianpiano.sheetmusicnotation;

import javax.swing.*;
import java.awt.event.*;

class StaveInspectorPanel extends JPanel {
	
	// The staves to inspect
	private StaveController stave;
	
	private boolean isUpperStaveSelected;
	
	
	// C'tor
	public StaveInspectorPanel( StaveController stave ) {
		this.stave = stave;
		
		// Select the upper stave upon creation
		isUpperStaveSelected = true;
		
		// Create radio buttons to select between the upper and lower staves
        JRadioButton upperStaveButton = new JRadioButton("Upper Stave");
        JRadioButton lowerStaveButton = new JRadioButton("Lower Stave");
        
        // Group the radio buttons
        ButtonGroup staveSelectionGroup = new ButtonGroup();
        staveSelectionGroup.add(upperStaveButton);
        staveSelectionGroup.add(lowerStaveButton);
        
        // Set default selection to upper stave
        upperStaveButton.setSelected(true);
        
        // Listener to set the selection
        upperStaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isUpperStaveSelected = true;
            }
        });
        
        lowerStaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isUpperStaveSelected = false;
            }
        });
        
        // Add the radio buttons to the inspector panel
        this.add(upperStaveButton);
        this.add(lowerStaveButton);
	}

}
