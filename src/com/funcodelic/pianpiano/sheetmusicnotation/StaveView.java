package com.funcodelic.pianpiano.sheetmusicnotation;

import static com.funcodelic.pianpiano.sheetmusicnotation.ViewState.*;

import java.awt.*;
import java.awt.geom.*;

//
//	The Stave View class of the MVC paradigm
//
class StaveView {
	
	// The rectangle to draw
	private final VerticallyResizableRectangle vertResizableRect;
	
	// View state
	private ViewState state;
	
	// Upper or lower staff
	private boolean isUpperStave = false;
	
	// Staff lines constant
	public static final int NUMBER_OF_STAFF_LINES = 5;
	
	// Y values for the keys for fast look-up
	private final int NUMBER_OF_KEYBOARD_KEYS = 88;
	private double[] keyYVals = new double[NUMBER_OF_KEYBOARD_KEYS];
	private boolean showKeyLines = false;
	
	// Colors to show the key lines
	private Color[] keyColors = new Color[4];
	{
		keyColors[0] = Color.MAGENTA;
		keyColors[1] = Color.CYAN;
		keyColors[2] = Color.BLUE;
		keyColors[3] = Color.ORANGE;
	}

	
	// C'tor
	StaveView( VerticallyResizableRectangle rectangle, boolean isUpperStave ) {
		// Initialize the rectangle
		this.vertResizableRect = rectangle;
		
		// Capture which stave this is
		this.isUpperStave = isUpperStave;
		
		state = HIDDEN;
        
        // Initialize the key y vals array
        for ( int i = 0; i < keyYVals.length; i++ ) {
        	keyYVals[i] = 0.0;
        }
	}
	
	boolean isEditing() {
		return state == EDITING;
	}
	
	boolean isUpperStave() {
		return this.isUpperStave;
	}
	
	void setKeyYVals( double[] yVals ) {
		if ( yVals.length == NUMBER_OF_KEYBOARD_KEYS ) {
			keyYVals = yVals;
		}
	}
	
	double[] getKeyYVals() {
		return keyYVals;
	}
	
	void showKeyLines( boolean showLines ) {
		showKeyLines = showLines;
	}
	
	void setState(ViewState state) {
		this.state = state;
    }
	
	ViewState getState() {
		return state;
	}
	
	// Draw the stave
	void draw( Graphics2D g2d ) {
		if ( state != HIDDEN ) {
			// Draw the lines gray for now
	        Color originalColor = g2d.getColor();
	        
	        if ( state == VIEWING ) {
	        	g2d.setColor( Color.GRAY );
	        }
	        else if ( state == EDITING ) {
	        	g2d.setColor( Color.BLUE );
	        }
	        else if ( state == GRAYED ) {
	        	g2d.setColor( Color.LIGHT_GRAY );
	        }
	        
	        //
	        // Draw the rectangle
	        //
	        g2d.draw( vertResizableRect.getRectangle() );
	        
	        //
	        // Draw the staff lines
	        //
	        if ( state == EDITING ) {
	        	// Store the current color and stroke  before drawing the lines
	        	Color currentColor = g2d.getColor();
		        g2d.setColor( Color.BLUE );
		        
		        Stroke currentStroke = g2d.getStroke();
		        g2d.setStroke( new BasicStroke( vertResizableRect.getScaledStroke() ) );
		        
		        // Draw the staff lines
		        Line2D.Double[] lines = vertResizableRect.getLines();
		        
		        for ( Line2D.Double line : lines ) {
		        	g2d.draw( line );
		        }
		        
		        // Set the context color and stroke back
		        g2d.setColor( currentColor );
		        g2d.setStroke( currentStroke );
	        }
	        
	    	// Save the original stroke
	        Stroke originalStroke = g2d.getStroke();
	        
	        // Set the stroke size according to the scale
	        float strokeWidth = (float) ( 2.0 / vertResizableRect.getScale() );
	        g2d.setStroke( new BasicStroke( strokeWidth ) );
	    	
	        // Draw handles if the stave is being edited
	        if ( state == EDITING ) {
	            g2d.setColor( Color.RED );
	            
	            for ( Point handle : vertResizableRect.getHandles() ) {
	                int scaledHandleSize = (int) vertResizableRect.getScaledHandleSize();
	                
	                g2d.fillRect( 	handle.x - scaledHandleSize / 2, 
	                				handle.y - scaledHandleSize / 2, 
	                				scaledHandleSize, 
	                				scaledHandleSize);
	            }
	        }
	        
	        //
	        // Draw the key lines
	        //
//	        if ( showKeyLines ) {
//	        	// x start and stop values
//		        int xStart = (int) vertResizableRect.getRectangle().getX();
//		        int xStop  = xStart + 20;
//		        
//		        // Bump the lines to the right for the lower staff
//		        if ( !isUpperStave() ) {
//		        	xStart += 40;
//			        xStop += 40;
//		        }
//		        
//		        g2d.setStroke( new BasicStroke( 2 ) );
//		        
//		        // The color to draw the line
//		        Color keyLineColor = keyColors[0];
//		        
//		        // Draw the key lines
//	        	for ( int i = 0; i < keyYVals.length; i++ ) {
//		        	//keyLineColor = keyColors[ i % 22 ];
//	        		keyLineColor = Color.blue;
//		        	
//		        	// Draw the half lines light gray
//		        	if ( i % 2 == 0 ) { 
//		        		keyLineColor = Color.LIGHT_GRAY;
//		        	}
//		        	
//		        	// Make the G4 line of the upper staff and 
//		        	// F2 of the lower staff stand out
//		        	if ( isUpperStave() && i == 43 || !isUpperStave() && i == 21 ) {
//		        		keyLineColor = Color.GREEN;
//		        	}
//		        	
//		        	// Set the color
//		        	g2d.setColor( keyLineColor );
//			        
//		        	// Get the y val
//		        	int yVal = (int)keyYVals[i];
//		        	
//		        	g2d.drawLine( xStart, yVal, xStop, yVal );
//			    }
//	        }
	        
	        // Restore the original stroke and color
	        g2d.setStroke( originalStroke );
	        g2d.setColor( originalColor );
		}
    }

	@Override
    public String toString() {
    	return "Stave View";
    }

}
