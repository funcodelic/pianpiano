package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.*;
import java.awt.geom.*;

//
//	The View component of the Staff System MVC classes
//
class StaffSystemView extends ResizableRectangle {

	private ViewState state;
	
	// C'tor
	public StaffSystemView(Rectangle2D.Double initialBounds) {
        super(initialBounds);
        this.state = ViewState.EDITING; // Default state
    }
	
	public void setState(ViewState state) {
        this.state = state;
    }

    @Override
    public void draw(Graphics2D g2d) {
    	// Save the original stroke
        Stroke originalStroke = g2d.getStroke();
        
        // Set the stroke size according to the scale
        float strokeWidth = (float) (2.0 / scale);
        g2d.setStroke(new BasicStroke(strokeWidth));
    	
        // Draw depending on the editing state
        if (state == ViewState.EDITING) {
            super.draw(g2d); // Draw resizable rectangle with handles
        } else {
            g2d.setColor(Color.BLUE);
            g2d.draw(getRectangle()); // Draw simple blue rectangle
        }
        
        // Restore the original stroke
        g2d.setStroke(originalStroke);
    }
}
