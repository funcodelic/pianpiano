package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

//
//	The Stave model class of the MVC paradigm
//
class StaveModel {

	// The bounds of the stave
	private final VerticallyResizableRectangle vertResizableRect;
	
	// Upper or lower staff
	private boolean isUpperStaff = false;
	
	// Staff lines constant and vertical spacing between staff lines
	private static final int NUMBER_OF_STAFF_LINES = 5;
	private double vGap;
	
	// The y positions of the 5 equally-spaced staff lines
	private final double[] staffLineYPositions = new double[NUMBER_OF_STAFF_LINES];
	
	// Y values for the keys for fast look-up
	private double[] keyYVals = new double[88];
	
	
	// C'tor
	StaveModel( Rectangle2D.Double bounds, boolean isUpperStaff, double scale ) {
		// Instantiate the vertically resizable rectangle and set its scale
		this.vertResizableRect = new VerticallyResizableRectangle( bounds, scale );
		
		// Set which staff this is (lower = false)
		this.isUpperStaff = isUpperStaff;
		
		// Calculate the staff line positions
		calculateStaffLineYPositions();
	}
	
	VerticallyResizableRectangle getVertResizableRect() {
        return vertResizableRect;
    }
	
	boolean isUpperStave() { // false = lower stave
		return this.isUpperStaff;
	}
	
	void startResizing( Point p ) {
		vertResizableRect.startResizing( p );
    }
    
    void resize( Point p ) {
    	// Resize the rectangle
    	vertResizableRect.resize( p );
    	
    	// Calculate the staff line y positions
    	calculateStaffLineYPositions();
    }
    
    void stopResizing() {
    	// Calculate the staff y positions
    	calculateStaffLineYPositions();
    }
    
    private void calculateStaffLineYPositions() {
    	// Get the rectangle
    	Rectangle2D.Double rect = vertResizableRect.getRectangle();
    	vGap = rect.height / (double)( NUMBER_OF_STAFF_LINES - 1 );
    	
    	for ( int i = 0; i < NUMBER_OF_STAFF_LINES; i++ ) {
    		staffLineYPositions[i] = rect.y + ( (double)i * vGap );
    	}
    	
    	// Calculate the key y values
    	calculateKeyYValues();
    }
    
    // Get the vertical gap (spacing) between staff lines
    double getVerticalSpacing() {
    	return vGap;
    }
    
    double[] getStaffLineYPositions() {
    	return staffLineYPositions;
    }
    
    double getTopLineY() {
		return staffLineYPositions[ 0 ];
	}
	
	double getG4LineY() {
		return staffLineYPositions[ 3 ];
	}
	
	double getF2LineY() {
		return staffLineYPositions[ 1 ];
	}
    
	double[] getKeyYVals() {
		return keyYVals;
	}
	
	void calculateKeyYValues() {
		if ( isUpperStave() ) { // Upper stave
			// The G4 line is of interest for the upper stave
			double g4LineY = getG4LineY(); // the y position of G4
		    
			// Every key value is on a "half gap" line, 
			// half the vertical space between staff lines
		    double halfGap = getVerticalSpacing() * 0.5;
		    
		    // G4 is the 44th key on the piano
		    final double KEYS_BELOW_G4 = 43;
		    
		    // Calculate the y position of the lowest key (A0, key 1)
		    double greatestYVal = g4LineY + ( halfGap * KEYS_BELOW_G4 );
		    
		    // Assign y positions for each key, 
		    // starting from the lowest key (greatest y value)
		    for ( int i = 0; i < keyYVals.length; i++ ) {
		        keyYVals[i] = greatestYVal - ( halfGap * i );
		    }
		}
		else { // Lower stave
			// The F2 line is of interest for the lower stave
			double f2LineY = getF2LineY();
			
			// Calculate the half-gap
	        double halfGap = getVerticalSpacing() * 0.5;
	        
	        // F2 is the 22nd key on the piano
	        final double KEYS_BELOW_F2 = 21;

	        // Calculate the y position of the lowest key (A0, key 1)
	        double highestY = f2LineY + ( halfGap * KEYS_BELOW_F2 );

	        // Assign y positions to each key, starting from the lowest key
	        for ( int i = 0; i < keyYVals.length; i++ ) {
	            keyYVals[i] = highestY - ( halfGap * i );
	        }
	    }
	}
	
	void staveRectangleUpdated() {
		calculateStaffLineYPositions();
	}
	
	double getNearestYVal( Point p ) {
	    // Get the y value from the point
	    double y = p.getY();
	    
	    double halfLineGap = getVerticalSpacing() / 2.0;

	    // Initialize the nearest value to the first element in keyYVals
	    double nearestY = keyYVals[0];
	    
	    double minY = keyYVals[keyYVals.length - 1];
	    double maxY = keyYVals[0];
	    
	    //System.out.println( "minY: " + minY + "  y: " + y + "  maxY: " + maxY );
	    
	    int nearestIndex = 0;
	    
	    if ( y >= minY && y <= maxY ) {
	    	// Calculate the index
	        nearestIndex = (int) Math.round( ( maxY - y ) / halfLineGap );
	        
	        // Calculate the nearest Y value using the index
	        nearestY = maxY - ( nearestIndex * halfLineGap );
	    }
	    else if ( y < minY ) {
	    	nearestY = minY;
	    	nearestIndex = keyYVals.length - 1;
	    }
	    else if ( y > maxY ) {
	    	nearestY = maxY;
	    	nearestIndex = 0;
	    }
	    
	    // Return the nearest y value
	    return nearestY;
	}
	
    void setScale( double scale ) {
    	vertResizableRect.setScale( scale );
    }
    
    double getScale() {
    	return vertResizableRect.getScale();
    }
    
    @Override
    public String toString() {
    	String locationString = isUpperStaff ? "Upper " : "Lower ";
    	return locationString + "Stave";
    }
}
