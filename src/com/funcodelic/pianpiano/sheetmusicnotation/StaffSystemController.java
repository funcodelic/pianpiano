package com.funcodelic.pianpiano.sheetmusicnotation;

import static com.funcodelic.pianpiano.sheetmusicnotation.ViewState.*;

import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

//
//	Staff System Controller is the main handle to the Staff System MVC classes.
//	It mediates between the model and view.
//
class StaffSystemController implements SheetMusicNode {

	// The model and view
	private StaffSystemModel model;
    private StaffSystemView view;
	
	// The inspector panel
	private StaffSystemInspectorPanel inspectorPanel;
	
	// The page interface adapter
	private PageInterface pageInterface;
	
	// The staves
	private StaveController upperStave;
	private StaveController lowerStave;
	
	// The measures
	private List<MeasureController> measures;
	
	enum InterfaceSelection {
		NO_SELECTION,
		STAFF_SYSTEM,
		UPPER_STAFF,
		LOWER_STAFF,
		MEASURES
	}
	

	
	// C'tor
	StaffSystemController( int index, Rectangle2D.Double bounds, double scale ) {
		// Initialize the model
		model = new StaffSystemModel( index, bounds, scale );
		
		// Initialize the staves
        initializeStaves();
		
		// Initialize the view
		view = new StaffSystemView( model.getResizableRect(), 
									upperStave.getView(), 
									lowerStave.getView() );
		
		// Instantiate the measures list
        this.measures = new ArrayList<MeasureController>();
	
        // Create the page interface mouse handler for the staff system
        pageInterface = new ResizeStaffSystem( this );
        
        // Inspector panel
        inspectorPanel = new StaffSystemInspectorPanel(this);
    }
	
	void initializeStaves() {
		// Calculate the upper and lower stave rectangles from this staff system's rectangle
		Rectangle2D.Double[] staveRects = calculateStaveRectangles();
		
		// Get the current scale
		double scale = this.model.getScale();
		
		// Initialize the upper stave
        StaveModel upperStaveModel = new StaveModel( staveRects[0], true, scale );
        StaveView upperStaveView = new StaveView( upperStaveModel.getVertResizableRect(), true );
        upperStave = new StaveController( upperStaveModel, upperStaveView );
        
        // Initialize the lower stave
        StaveModel lowerStaveModel = new StaveModel( staveRects[1], false, scale );
        StaveView lowerStaveView = new StaveView( lowerStaveModel.getVertResizableRect(), false );
        lowerStave = new StaveController( lowerStaveModel, lowerStaveView );
	}
	
	int getNumberOnPage() {
		return model.getNumberOnPage();
	}
	
	PageInterface selectItem( InterfaceSelection selectedItem ) {
		PageInterface theInterface = null;
		
		if ( selectedItem == InterfaceSelection.NO_SELECTION ) {
			setViewState( VIEWING );
			upperStave.setViewState( VIEWING );
			lowerStave.setViewState( VIEWING );
			theInterface = getPageInterface();
		}
		else if ( selectedItem == InterfaceSelection.STAFF_SYSTEM ) {
			setViewState( EDITING );
			upperStave.setViewState( HIDDEN );
			lowerStave.setViewState( HIDDEN );
			setMeasuresViewState( HIDDEN );
			theInterface = getPageInterface();
		}
		else if ( selectedItem == InterfaceSelection.UPPER_STAFF ) {
			setViewState( GRAYED );
			upperStave.setViewState( EDITING );
			lowerStave.setViewState( GRAYED );
			setMeasuresViewState( GRAYED );
			theInterface = upperStave.getPageInterface();
		}
		else if ( selectedItem == InterfaceSelection.LOWER_STAFF ) {
			setViewState( GRAYED );
			upperStave.setViewState( GRAYED );
			lowerStave.setViewState( EDITING );
			setMeasuresViewState( GRAYED );
			theInterface = lowerStave.getPageInterface();
		}
		else if ( selectedItem == InterfaceSelection.MEASURES ) {
			setViewState( GRAYED );
			upperStave.setViewState( GRAYED );
			lowerStave.setViewState( GRAYED );
		}
		
		return theInterface;
	}
	
	void setMeasuresViewState( ViewState measuresState ) {
		for ( MeasureController measure : measures ) {
			measure.setViewState( measuresState );
		}
	}
	
	PageInterface selectMeasure( int index ) {
		PageInterface pgInterface = null;
		
		// Gray out all the measures except the one selected
		for ( MeasureController measure : measures ) {
			if ( measure.getIndex() != index ) {
				measure.setViewState( GRAYED );
			}
			else {
				measure.setViewState( EDITING );
				pgInterface = measure.getPageInterface();
			}
		}
		
		// Return the page interface of the selected measure
		return pgInterface;
	}
	
	StaffSystemModel getModel() {
        return model;
    }
	
	StaffSystemView getView() {
        return view;
    }
	
	void setViewState( ViewState viewState ) {
		view.setState( viewState );
	}
	
	ViewState getViewState() {
		return view.getState();
	}
	
	boolean isEditing() {
		return view.isEditing();
	}
    
    void setScale( double scale ) {
    	// Propagate the scale to the model
    	model.setScale( scale );
    	
    	// Propagate the scale to the staves
    	upperStave.setScale( scale );
    	lowerStave.setScale( scale );
    	
    	// Propagate the scale to the measures
    	for ( MeasureController measure : measures ) {
    		measure.setScale( scale );
    	}
    	
    }
	
	@Override
	public String toString() {
		return model.toString();
	}

	@Override
	public JComponent getInspectorView() {
		return inspectorPanel;
	}

	@Override
	public void select() {
		setViewState( VIEWING );
		upperStave.setViewState( HIDDEN );
		lowerStave.setViewState( HIDDEN );
		setMeasuresViewState( HIDDEN );
		inspectorPanel.clearSelections();
		//inspectorPanel.selectStaffSystem();
	}
	
	@Override
	public void deselect() {
		setViewState( GRAYED );
		upperStave.setViewState( HIDDEN );
		lowerStave.setViewState( HIDDEN );
		setMeasuresViewState( HIDDEN );
		inspectorPanel.clearSelections();
	}
	
	@Override
	public PageInterface getPageInterface() {
    	return this.pageInterface;
    }
	
	// Adjustment method
	void startResizing( Point p ) {
		if ( isEditing() ) {
			// Scale the point to the logical dimensions of the bounds and start resizing
			model.startResizing( getScaledPoint( p ) );
		}
	}
	
	// Adjustment method
	void resize( Point p ) {
		if ( isEditing() ) {
			// Scale the point to the logical dimensions of the bounds and resize
			model.resize( getScaledPoint( p ) );
		}
	}
	
	// Adjustment method
	void stopResizing( Point p ) {
		// Update the staves after the staff system is resized
		updateStaves();
	}
	
	private Point getScaledPoint( Point p ) {
		int x = (int)( (double)p.x / model.getScale() );
		int y = (int)( (double)p.y / model.getScale() );
		return new Point( x, y );
	}
	
	private Rectangle2D.Double[] calculateStaveRectangles() {
	    // The upper and lower stave rectangles to calculate
	    Rectangle2D.Double upperRect;
	    Rectangle2D.Double lowerRect;
	    
	    // Get the staff system's rectangle width and height
 		Rectangle2D.Double staffSystemRect = model.getResizableRect().getRectangle();
 		double fullHeight = staffSystemRect.height;
 		double fullWidth  = staffSystemRect.width;
 		
 		// Give both staves the same width, height, and x position
 		double staveWidth = fullWidth;
 		double staveHeight = fullHeight / 5.0; // 20% of the total height
 		double staveXPos = staffSystemRect.x;
 		
 		// Upper and lower y positions
 		double upperYPos = staffSystemRect.y + staveHeight; // 20% downward
 		double lowerYPos = staffSystemRect.y + staveHeight * 3.0; // 40% downward
 		
 		// Create the rectangles
 		upperRect = new Rectangle2D.Double( staveXPos, upperYPos, staveWidth, staveHeight );
 		lowerRect = new Rectangle2D.Double( staveXPos, lowerYPos, staveWidth, staveHeight );

	    // Return the rectangles
	    return new Rectangle2D.Double[]{ upperRect, lowerRect };
	}
	
	// Convenience method to add a new measure to the staff system
	MeasureController addMeasure() {
		// Calculate the index
		int index = measures.size();
		
		// Create the measure's rectangular bounds
		Rectangle2D.Double rect = createMeasureRectangle();
		
		// Create the new measure
		MeasureController measure = new MeasureController( 	index, 
															rect, 
															model.getScale(), 
															upperStave, 
															lowerStave );
		
		// Add the new measure to the list
		measures.add( measure );
		
		// Add the new measure's view to the staff view for display
		this.view.addMeasureView( measure.getView() );
		
		// Return the new measure
		return measure;
	}
	
	private Rectangle2D.Double createMeasureRectangle() {
		// If the index is 0, use the staff system rectangle as the starting reference
		// Otherwise, use the previous measure as the starting reference
		// Get the staff system rectangle
		Rectangle2D.Double staffSystemRect = view.getRectangle();
		
		double xLoc;
		double yLoc = staffSystemRect.y; // all measures share the same y location
		double w = 200.0; // start with a width of 200
		double h = staffSystemRect.height; // all measures have the same height
		
		// If there is one or more measure already in the staff system,
		// Update the rectangle's x value accordingly
		if ( measures.size() > 0 ) {
			Rectangle2D.Double lastMeasureRect = measures.getLast().getBoundsRectangle();
			
			xLoc = lastMeasureRect.x + lastMeasureRect.width;
		}
		else { // else use the staff system origin
			xLoc = staffSystemRect.x;
			
			// Add a little room before the left side of the staff system
			xLoc += 4.0;
		}
		
		// Create the rectangle with the calculated coordinates and dimensions
		Rectangle2D.Double measureRect = new Rectangle2D.Double( xLoc, yLoc, w, h );
		
		// Return the rectangle
		return measureRect;
	}
	
	private void updateStaves() {
		// Calculate the new stave rects from the staff system's rectangle
		Rectangle2D.Double[] updatedRects = calculateStaveRectangles();
		
		// Update the staves' rectangles
		upperStave.resizeToRectangle( updatedRects[0] );
		lowerStave.resizeToRectangle( updatedRects[1] );
	}
	
}
