package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.Color;
import java.awt.Graphics2D;

//
//	The Note View class of the MVP paradigm
//
class NoteView {
	// The note body
	SmartCircle noteBody;
	
	// Colors for drawing the note in various states
	private final Color DARK_GREEN = new Color( 0, 210, 0 );
	private final Color LIGHT_GREEN = new Color( 0, 255, 0 );
	
	
	// C'tor
	NoteView( SmartCircle noteBody ) {
        this.noteBody = noteBody;
    }
	
	public void draw( Graphics2D g2d ) {
		// Store the original context color
		Color originalColor = g2d.getColor();
		
		// Set the color depending on the selection state
		Color c = noteBody.isSelected() ? LIGHT_GREEN : DARK_GREEN;
		g2d.setColor( c );
		
		// Draw the note's circle
		g2d.fill( noteBody.getCircle() );
		
		// Restore the original context color
		g2d.setColor( originalColor );
	}
	
	@Override
	public String toString() {
		return "Note View";
	}

}
