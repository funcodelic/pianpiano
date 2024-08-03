package com.funcodelic.pianpiano.sheetmusicnotation;

import static com.funcodelic.pianpiano.sheetmusicnotation.ViewState.*;

import java.awt.BasicStroke;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

//
//	The stave view class of the MVC paradigm
//
class StaveView extends VerticallyResizableRectangle {
	
	private ViewState state;
	
	// The lines
	private List<Line2D.Double> staffLines;
	
	public static final int NUMBER_OF_STAFF_LINES = 5;
	private double verticalSpacing;

	
	// C'tor
	public StaveView(Rectangle2D.Double initialBounds) {
		super(initialBounds);
		
		state = HIDDEN;
		
		//
		// Initialize the staff lines
		//
		this.staffLines = new ArrayList<>();
		
		// Calculate the vertical spacing between lines
		Rectangle2D.Double bounds = getRectangle();
		verticalSpacing = bounds.height / (double)(NUMBER_OF_STAFF_LINES - 1);
		
		// Initialize the staff lines
        for (int i = 0; i < NUMBER_OF_STAFF_LINES; i++) {
            double y = bounds.y + i * verticalSpacing;
            
            Line2D.Double line = new Line2D.Double(
                bounds.x,                // x1 (top-left corner x)
                y,                       // y1
                bounds.x + bounds.width, // x2 (top-right corner x)
                y                        // y2
            );
            staffLines.add(line);
        }
	}
	
	public void setState(ViewState state) {
		this.state = state;
    }
	
	public ViewState getState() {
		return state;
	}
	
	@Override
	public void setRectangle(Rectangle2D.Double rect) {
		super.setRectangle(rect);
		
		// Update the staff lines
		configureStaffLines();
	}
	
	@Override
    public void draw( Graphics2D g2d ) {
		if ( state != HIDDEN ) {
			// Draw the lines gray for now
	        Color originalColor = g2d.getColor();
	        g2d.setColor( Color.GREEN );
	        
	        // Draw each staff line
	        for ( Line2D.Double line : staffLines ) {
	            g2d.draw( line );
	        }
	        
	        // Restore the original color
	        g2d.setColor( originalColor );
	        
	    	// Save the original stroke
	        Stroke originalStroke = g2d.getStroke();
	        
	        // Set the stroke size according to the scale
	        float strokeWidth = (float) ( 2.0 / scale );
	        g2d.setStroke(new BasicStroke( strokeWidth ));
	    	
	        // Draw depending on the editing state
	        if ( state == EDITING ) {
	        	//g2d.setColor(Color.GREEN);
	            super.draw( g2d ); // Draw vertically resizable rectangle with handles
	        }
	        
	        // Restore the original stroke
	        g2d.setStroke( originalStroke );
		}
    }
	
	public void configureStaffLines() {
		// Calculate the vertical spacing between lines
		Rectangle2D.Double bounds = getRectangle();
		verticalSpacing = bounds.height / (double)(NUMBER_OF_STAFF_LINES - 1);
		
		// Update the staff lines
        for (int i = 0; i < NUMBER_OF_STAFF_LINES; i++) {
            double y = bounds.y + i * verticalSpacing;
            staffLines.get(i).setLine(bounds.x, y, bounds.x + bounds.width, y);
        }
	}
	
	@Override
	public void resize(Point p) {
		super.resize( p );
		configureStaffLines();
	}
	
	@Override
    public String toString() {
    	return "Stave View";
    }

}
