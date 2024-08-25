package com.funcodelic.pianpiano.sheetmusicnotation;

import static com.funcodelic.pianpiano.sheetmusicnotation.ViewState.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

//
//	The View component of the Staff System MVC classes
//
class StaffSystemView {
	
	// The resizable rectangle that is the boundary of the staff system
	private final ResizableRectangle resizableRect;

	private ViewState state;
	
	// The upper and lower staves (staff lines) of the system
	private StaveView upperStaveView;
	private StaveView lowerStaveView;
	
	// The measures of the system
	private List<MeasureView> measureViews;
	
	
	// C'tor
	StaffSystemView( ResizableRectangle resizableRectangle, 
						StaveView upperStaveView, 
						StaveView lowerStaveView ) {
		// Store the resizable rectangle
		this.resizableRect = resizableRectangle;
		
		// Store the staves
		this.upperStaveView = upperStaveView;
		this.lowerStaveView = lowerStaveView;
        
		// Instantiate the list of measures
        measureViews = new ArrayList<MeasureView>();
    }
	
	Rectangle2D.Double getRectangle() {
		return resizableRect.getRectangle();
	}
	
	void setState( ViewState state ) {
		this.state = state;
    }
	
	ViewState getState() {
		return state;
	}
	
	boolean isEditing() {
		return state == EDITING;
	}
	
	void addMeasureView( MeasureView measureView ) {
		measureViews.add( measureView );
	}

	// Draw the staff system and its staves and measures
    void draw( Graphics2D g2d ) {
    	// Save the original color and stroke
    	Color originalColor = g2d.getColor();
        Stroke originalStroke = g2d.getStroke();
        
        // Set the stroke size according to the scale
        float strokeWidth = (float) ( 2.0 / resizableRect.getScale() );
        g2d.setStroke( new BasicStroke( strokeWidth ) );
    	
        //
        // Draw the staff system
        //
        if ( state != HIDDEN ) {
        	if ( state == EDITING ) {
        		// Draw the rectangle in blue
        		g2d.setColor( Color.BLUE );
        		g2d.draw( resizableRect.getRectangle() );
        		
        		// Draw the resize handles in red
        		g2d.setColor( Color.RED );
        		
        		// Origin offset from the center point
                int originOffset = (int)( resizableRect.getScaledHandleSize() / 2.0 );
                
                // Handle size accounting for the scale
                int scaledHandleSize = (int)resizableRect.getScaledHandleSize();
                
                for ( Point handle : resizableRect.getHandles() ) {
                	g2d.fillRect( handle.x - originOffset, 
                    			  handle.y - originOffset, 
                    			  scaledHandleSize, 
                    			  scaledHandleSize );
                }
            } else {
            	if ( state == VIEWING ) {
            		g2d.setColor( Color.BLUE );
            	}
            	else if ( state == GRAYED ) {
            		g2d.setColor( Color.LIGHT_GRAY );
            	}
                
                g2d.draw( resizableRect.getRectangle() );
            }
        }
        
        //
        // Draw the staves
        //
        if ( upperStaveView != null && lowerStaveView != null ) {
            upperStaveView.draw( g2d );
            lowerStaveView.draw( g2d );
        }
        
        //
        // Draw the measures
        //
        // Capture the measure being edited to draw it last so it appears on top
        MeasureView measureBeingEdited = null;
        
        for ( MeasureView measure : measureViews ) {
        	if ( measure.isEditing() ) {
        		measureBeingEdited = measure;
        		continue;
        	}
        	
        	// Draw the non-editing measures
        	measure.draw( g2d );
        }
        
        // Draw the measure being edited last so it appears on top of the others
        if ( measureBeingEdited != null ) {
        	measureBeingEdited.draw( g2d );
        }
        
        // Restore the original color and stroke
        g2d.setColor( originalColor );
        g2d.setStroke( originalStroke );
    }
    
    @Override
    public String toString() {
    	return "Staff System View";
    }
}
