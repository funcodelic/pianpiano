package com.funcodelic.pianpiano.sheetmusicnotation;

import static com.funcodelic.pianpiano.sheetmusicnotation.ViewState.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//
//	The Measure View class of the MVC paradigm
//
class MeasureView {
	
	// The rectangle
	HorizontallyResizableRectangle rectangle;
	
	private ViewState state;
	
	// Staves used for note placement and pitch assignment
	StaveView upperStaveView;
	StaveView lowerStaveView;
	
	// The notes
	private List<NoteView> noteViews;
	
	// The currently selected note
	NoteView selectedNote;
	
	
	// C'tor
	MeasureView( HorizontallyResizableRectangle rectangle, 
				 StaveView upperStaveView, 
				 StaveView lowerStaveView ) {
		// Store the bounds rectangle to display
		this.rectangle = rectangle;
		
		// Store the stave views
		this.upperStaveView = upperStaveView;
		this.lowerStaveView = lowerStaveView;
		
		// Start out hidden
		state = HIDDEN;
		
		// Instantiate the note views list to display
		noteViews = new ArrayList<>();
	}
	
	void setState(ViewState state) {
		this.state = state;
    }
	
	ViewState getState() {
		return state;
	}
	
	boolean isEditing() {
		return state == EDITING;
	}
	
	void setSelectedNote( NoteView note ) {
		selectedNote = note;
	}
	
	NoteView getSelectedNote() {
		return selectedNote;
	}
	
	@Override
	public String toString() {
		return "Measure View";
	}
	
	// Add the note to the list
	void addNoteToList( NoteView note ) {
		if ( !noteViews.contains( note ) ) {
			noteViews.add( note );
		}
	}
	
    void draw( Graphics2D g2d ) {
		if ( state != HIDDEN ) {
			// Draw the lines gray for now
	        Color originalColor = g2d.getColor();
	        
	        if ( state == GRAYED ) {
	        	g2d.setColor( Color.LIGHT_GRAY );
	        }
	        else if ( state == VIEWING ) {
	        	g2d.setColor( Color.BLUE );
	        }
	        else if ( state == EDITING ) {
	        	g2d.setColor( Color.MAGENTA );
	        }
	        
	    	// Save the original stroke
	        Stroke originalStroke = g2d.getStroke();
	        
	        // Set the stroke size according to the scale
	        float strokeWidth = (float) ( 2.0 / rectangle.getScale() );
	        g2d.setStroke( new BasicStroke( strokeWidth ) );
	    	
	        // Draw depending on the editing state
	        if ( state == EDITING ) {
	        	// Draw the resizable rectangle
	            g2d.draw( rectangle.getRectangle() );

	            // Draw the resize handles
	            g2d.setColor( Color.RED );
	            
	            for ( Point handle : rectangle.getHandles() ) {
	                int scaledHandleSize = (int) rectangle.getScaledHandleSize();
	                
	                g2d.fillRect( handle.x - scaledHandleSize / 2, 
	                			  handle.y - scaledHandleSize / 2, 
	                			  scaledHandleSize, 
	                			  scaledHandleSize );
	            }
	        }
	        else {
	        	g2d.draw( rectangle.getRectangle() ); // Draw simple blue rectangle
	        }
	        
	        // Restore the original color
	        g2d.setColor( originalColor );
	        
	        // Restore the original stroke
	        g2d.setStroke( originalStroke );
		}
		
		// Draw the notes
        for ( NoteView note : noteViews ) {	        	
        	note.draw( g2d );
        }
    }
	
}
