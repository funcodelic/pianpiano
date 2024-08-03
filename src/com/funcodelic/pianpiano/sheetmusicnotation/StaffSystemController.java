package com.funcodelic.pianpiano.sheetmusicnotation;

import static com.funcodelic.pianpiano.sheetmusicnotation.ViewState.*;
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
	
	public enum InterfaceSelection {
		NO_SELECTION,
		STAFF_SYSTEM,
		UPPER_STAFF,
		LOWER_STAFF
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
			theInterface = getPageInterface();
		}
		else if ( selectedItem == InterfaceSelection.UPPER_STAFF ) {
			setViewState( HIDDEN );
			upperStave.setViewState( EDITING );
			lowerStave.setViewState( VIEWING );
			theInterface = upperStave.getPageInterface();
		}
		else if ( selectedItem == InterfaceSelection.LOWER_STAFF ) {
			setViewState( HIDDEN );
			upperStave.setViewState( VIEWING );
			lowerStave.setViewState( EDITING );
			theInterface = lowerStave.getPageInterface();
		}
		
		return theInterface;
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
		inspectorPanel.clearSelections();
	}
	
	@Override
	public PageInterface getPageInterface() {
    	return this.pageInterface;
    }
	
}
