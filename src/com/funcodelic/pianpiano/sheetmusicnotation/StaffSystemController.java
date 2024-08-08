package com.funcodelic.pianpiano.sheetmusicnotation;

import static com.funcodelic.pianpiano.sheetmusicnotation.ViewState.*;

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
	StaffSystemInspectorPanel inspectorPanel;
	
	// The page interface adapter
	PageInterface pageInterface;
	
	// The staff lines managed by this staff system
	private StaveController upperStave;
	private StaveController lowerStave;
	
	// The measures of the staff system
	private List<MeasureController> measures;
	
	public enum InterfaceSelection {
		NO_SELECTION,
		STAFF_SYSTEM,
		UPPER_STAFF,
		LOWER_STAFF,
		MEASURES
	}
	
	public InterfaceSelection interfaceSelection;
	
	
	
	// C'tor
	public StaffSystemController(StaffSystemModel model, StaffSystemView view) {
        this.model = model;
        this.view = view;
        
        // Create the page interface mouse handler for this sheet music entity
        pageInterface = new ResizeStaffSystem(this.view);
        
        // Select the staff system by default
        interfaceSelection = InterfaceSelection.STAFF_SYSTEM;
        
        //
        // Instantiate the upper and lower staves (staff lines)
        //
        // Upper stave
        StaveModel upperStaveModel = new StaveModel( this.model.getBounds() );
        StaveView upperStaveView = new StaveView( upperStaveModel.getBounds() );
        upperStave = new StaveController( upperStaveModel, upperStaveView );
        
        // Lower stave
        StaveModel lowerStaveModel = new StaveModel( this.model.getBounds() );
        StaveView lowerStaveView = new StaveView( lowerStaveModel.getBounds() );
        lowerStave = new StaveController( lowerStaveModel, lowerStaveView );
        
        // Measures
        this.measures = new ArrayList<MeasureController>();
        
        // Set this view's staff lines view
        view.setStaveViews( upperStave.getView(), lowerStave.getView() );
        
        // Inspector panel
        inspectorPanel = new StaffSystemInspectorPanel(this);
    }
	
	public PageInterface selectItem( InterfaceSelection selectedItem ) {
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
			setViewState( HIDDEN );
			upperStave.setViewState( EDITING );
			lowerStave.setViewState( VIEWING );
			setMeasuresViewState( HIDDEN );
			theInterface = upperStave.getPageInterface();
		}
		else if ( selectedItem == InterfaceSelection.LOWER_STAFF ) {
			setViewState( HIDDEN );
			upperStave.setViewState( VIEWING );
			lowerStave.setViewState( EDITING );
			setMeasuresViewState( HIDDEN );
			theInterface = lowerStave.getPageInterface();
		}
		else if ( selectedItem == InterfaceSelection.MEASURES ) {
			setViewState( GRAYED );
			upperStave.setViewState( HIDDEN );
			lowerStave.setViewState( HIDDEN );
		}
		
		return theInterface;
	}
	
	private void setMeasuresViewState( ViewState measuresState ) {
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
	
	public StaffSystemView getView() {
        return view;
    }
	
	public void setViewState( ViewState viewState ) {
		view.setState( viewState );
	}
	
	public ViewState getViewState() {
		return view.getState();
	}

    public StaffSystemModel getModel() {
        return model;
    }
	
	@Override
	public String toString() {
		return "Staff System " + Integer.toString( model.getIndex() );
	}

	@Override
	public JComponent getInspectorView() {
		return inspectorPanel;
	}

	@Override
	public void select() {
		setViewState( VIEWING );
		upperStave.setViewState( VIEWING );
		lowerStave.setViewState( VIEWING );
		inspectorPanel.clearSelections();
	}
	
	@Override
	public void deselect() {
		setViewState( VIEWING );
		upperStave.setViewState( VIEWING );
		lowerStave.setViewState( VIEWING );
		setMeasuresViewState( GRAYED );
		inspectorPanel.clearSelections();
	}
	
	@Override
	public PageInterface getPageInterface() {
    	return this.pageInterface;
    }
	
	// Convenience method to add a new measure to the staff system
	MeasureController addMeasure() {
		int index = measures.size();
		
		Rectangle2D.Double newMeasureRect = createMeasureRectangle();
		MeasureModel newMeasureModel = new MeasureModel( newMeasureRect , index );
		MeasureView  newMeasureView  = new MeasureView( newMeasureModel.getRectangle() );
		
		MeasureController newMeasure = new MeasureController( newMeasureModel, newMeasureView );
		
		// Set the current scale
		newMeasureView.setScale( getView().getScale() );
		
		// Add the new measure to the list
		measures.add( newMeasure );
		
		// Add the new measure's view to the staff system view
		this.view.addMeasureView( newMeasureView );
		
		return newMeasure;
	}
	
	private Rectangle2D.Double createMeasureRectangle() {
		// If the index is 0, use the staff system rectangle as the starting reference
		// Otherwise, use the previous measure as the starting reference
		// Get the staff system rectangle
		Rectangle2D.Double staffSystemRect = view.getRectangle();
		
		double measureX;
		double measureY = staffSystemRect.y; // all measures have the same y origin value
		double measureWidth = 80.0; // start with a width of 20
		double measureHeight = staffSystemRect.height; // all measures have the same height
		
		// If there is one or more measure already in the staff system,
		// Update the rectangle's x value accordingly
		if ( measures.size() > 0 ) {
			Rectangle2D.Double lastMeasureRect = measures.getLast().getView().getRectangle();
			
			measureX = lastMeasureRect.x + lastMeasureRect.width;
		}
		else { // else use the staff system origin
			measureX = staffSystemRect.x;
			
			// Add a little room before the left side of the staff system
			measureX += 4.0;
		}
		
		// Add breathing room
		measureY += 4.0;
		measureHeight -= 8.0;
		
		// Create a fresh new rectangle with the dimensions of the containing staff system
		Rectangle2D.Double measureRect = new Rectangle2D.Double( measureX, measureY, measureWidth, measureHeight );
		
		// Return the rectangle
		return measureRect;
	}
	
}
