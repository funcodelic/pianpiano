package com.funcodelic.pianpiano.sheetmusicnotation;

import static com.funcodelic.pianpiano.sheetmusicnotation.ViewState.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;

//
//	The Measure View class of the MVC paradigm
//
class MeasureView extends HorizontallyResizableRectangle {
	
	private ViewState state;
	
	
	// C'tor
	public MeasureView( Rectangle2D.Double rectangle ) {
		super( rectangle );
		
		state = HIDDEN;
	}
	
	public void setState(ViewState state) {
		this.state = state;
    }
	
	public ViewState getState() {
		return state;
	}
	
	@Override
	public void setScale( double scale ) {
		super.setScale( scale );
	}
	
	@Override
	public String toString() {
		return "Measure View";
	}
	
	@Override
    public void draw( Graphics2D g2d ) {
		if ( state != HIDDEN ) {
			// Draw the lines gray for now
	        Color originalColor = g2d.getColor();
	        
	        if ( state == GRAYED ) {
	        	g2d.setColor( Color.LIGHT_GRAY );
	        }
	        else if ( state == VIEWING ) {
	        	g2d.setColor( Color.MAGENTA );
	        }
	        else if ( state == EDITING ) {
	        	g2d.setColor( Color.MAGENTA );
	        }
	        
	    	// Save the original stroke
	        Stroke originalStroke = g2d.getStroke();
	        
	        // Set the stroke size according to the scale
	        float strokeWidth = (float) ( 2.0 / scale );
	        g2d.setStroke(new BasicStroke( strokeWidth ));
	    	
	        // Draw depending on the editing state
	        if ( state == EDITING ) {
	        	super.draw( g2d ); // Draw horizontally resizable rectangle with handles
	        }
	        else {
	        	g2d.draw( getRectangle() ); // Draw simple blue rectangle
	        }
	        
	        // Restore the original color
	        g2d.setColor( originalColor );
	        
	        // Restore the original stroke
	        g2d.setStroke( originalStroke );
		}
    }
	
}
