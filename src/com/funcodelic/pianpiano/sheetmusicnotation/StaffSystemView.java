package com.funcodelic.pianpiano.sheetmusicnotation;

import static com.funcodelic.pianpiano.sheetmusicnotation.ViewState.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

//
//	The View component of the Staff System MVC classes
//
class StaffSystemView extends ResizableRectangle {

	private ViewState state;
	
	// The upper and lower staves (staff lines) of the system
	StaveView upperStaveView;
	StaveView lowerStaveView;
	
	// The measures of the system
	List<MeasureView> measureViews;
	
	
	// C'tor
	public StaffSystemView(Rectangle2D.Double initialBounds) {
        super(initialBounds);
        
        measureViews = new ArrayList<MeasureView>();
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
		
		// Set the scale for the staves
		if ( upperStaveView != null && lowerStaveView != null ) {
			upperStaveView.setScale( scale );
			lowerStaveView.setScale( scale );
		}
		
		// Set the scale for the measures
		for ( MeasureView measureView : measureViews ) {
			measureView.setScale( scale );
		}
	}
	
	public void updateBounds() {
		Rectangle2D.Double staffSystemRect = getRectangle();
		double fullHeight = staffSystemRect.height;
		double fullWidth  = staffSystemRect.width;
		
		// Both staves
		final double WIDTH_SPACE = 16.0;
		double staveWidth = fullWidth - WIDTH_SPACE;
		double staveHeight = fullHeight / 5.0;
		double staveXPos = staffSystemRect.x + WIDTH_SPACE / 2.0;
		
		// Upper stave
		double upperYPos = staffSystemRect.y + staveHeight;
		Rectangle2D.Double upperRect = new Rectangle2D.Double( staveXPos, upperYPos, staveWidth, staveHeight );
		upperStaveView.setRectangle( upperRect );
		
		// Lower stave
		double lowerYPos = staffSystemRect.y + staveHeight * 3.0;
		Rectangle2D.Double lowerRect = new Rectangle2D.Double( staveXPos, lowerYPos, staveWidth, staveHeight );
		lowerStaveView.setRectangle( lowerRect );
	}
	
	public void setStaveViews( StaveView upperStaveView, StaveView lowerStaveView ) {
        this.upperStaveView = upperStaveView;
        this.upperStaveView.setScale( scale );
        
        this.lowerStaveView = lowerStaveView;
        this.lowerStaveView.setScale( scale );
        
        updateBounds();
    }
	
	void addMeasureView( MeasureView measureView ) {
		measureViews.add( measureView );
	}

    @Override
    public void draw( Graphics2D g2d ) {
    	// Save the original stroke
        Stroke originalStroke = g2d.getStroke();
        
        // Set the stroke size according to the scale
        float strokeWidth = (float) ( 2.0 / scale );
        g2d.setStroke( new BasicStroke( strokeWidth ) );
    	
        // Draw depending on the editing state
        if ( state == EDITING ) {
            super.draw( g2d ); // Draw resizable rectangle with handles
        } else {
            g2d.setColor( Color.BLUE );
            g2d.draw( getRectangle() ); // Draw simple blue rectangle
        }
        
        // Draw the staves
        if ( upperStaveView != null && lowerStaveView != null ) {
            upperStaveView.draw( g2d );
            lowerStaveView.draw( g2d );
        }
        
        // Draw the measures
        for ( MeasureView measure : measureViews ) {
        	measure.draw( g2d );
        }
        
        // Restore the original stroke
        g2d.setStroke( originalStroke );
    }
    
    @Override
    public String toString() {
    	return "Staff System View";
    }
}
