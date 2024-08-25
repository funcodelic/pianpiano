package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

//
//	Stave Controller is the main handle of the Stave
//	and the controller class of the MVC paradigm
//
class StaveController { // Stave Controller is a sheet music node
	
	// The model and view
	private StaveModel model;
	private StaveView view;
	
	// The page interface adapter
	PageInterface pageInterface;
	
	
	// C'tor
	StaveController( StaveModel model, StaveView view ) {
		this.model = model;
		this.view = view;
		
		// Instantiate the interface strategy
		pageInterface = new AdjustStave( this );
	}
	
	StaveView getView() {
        return view;
    }

    StaveModel getModel() {
        return model;
    }
    
    void setViewState( ViewState viewState ) {
		view.setState( viewState );
	}
	
	ViewState getViewState() {
		return view.getState();
	}
	
	void setScale( double scale ) {
		model.setScale( scale );
	}
	
	boolean isEditing() {
		return view.isEditing();
	}
	
	PageInterface getPageInterface() {
    	return pageInterface;
    }
	
	double getVerticalSpacing() {
		return model.getVerticalSpacing();
	}
	
	Rectangle2D.Double getRectangle() {
		return model.getVertResizableRect().getRectangle();
	}
	
	void resizeToRectangle( Rectangle2D.Double rect ) {
		// Get the stave's rectangle
		Rectangle2D.Double staveRect = model.getVertResizableRect().getRectangle();
		
		// Set the stave's rectangle to the new coordinates and dimensions
		staveRect.x = rect.x;
		staveRect.y = rect.y;
		staveRect.width = rect.width;
		staveRect.height = rect.height;
		
		// Inform the model of the update
		model.staveRectangleUpdated();
		
		// Pass the newly calculate lines to the view
		view.setKeyYVals( model.getKeyYVals() );
	}
	
	// Adjustment method
	void startResizing( Point p ) {
		// DEBUG: Don't show the key lines while resizing
		//view.showKeyLines( false );
		
		// Scale the point to the logical dimensions of the rectangle and start resizing
		if ( isEditing() ) {
			model.startResizing( getScaledPoint( p ) );
		}
	}
	
	// Adjustment method
	void resize( Point p ) {
		// Scale the point to the logical dimensions of the rectangle and resize
		if ( isEditing() ) {
			model.resize( getScaledPoint( p ) );
		}
	}
	
	// Adjustment method
	void stopResizing( Point p ) {
		// Stop resizing
		model.stopResizing();
		
		// Pass the view the key Y values for display
		view.setKeyYVals( model.getKeyYVals() );
		
		// DEBUG: Show the key lines after resizing
		//view.showKeyLines( true );
	}
	
	private Point getScaledPoint( Point p ) {
		int x = (int)( (double)p.x / model.getScale() );
		int y = (int)( (double)p.y / model.getScale() );
		return new Point( x, y );
	}
	
	double getNearestYVal( Point p ) {
		return model.getNearestYVal( p );
	}
	
	@Override
	public String toString() {
		return model.toString();
	}
	
}
