package com.funcodelic.pianpiano.sheetmusicnotation;

import javax.swing.JComponent;

//
//	StaffSystemController is the main handle to the Staff System MVC classes.
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
	
	
	// C'tor
	public StaffSystemController(StaffSystemModel model, StaffSystemView view) {
        this.model = model;
        this.view = view;
        
        pageInterface = new ResizeStaffSystem(view);
        
        inspectorPanel = new StaffSystemInspectorPanel(this);
    }
	
	public StaffSystemView getView() {
        return view;
    }

    public StaffSystemModel getModel() {
        return model;
    }
	
	@Override
	public String toString() {
		return "Staff System " + Integer.toString(model.getIndex());
	}

	@Override
	public JComponent getInspectorView() {
		return inspectorPanel;
	}

	@Override
	public void select() {
		//System.out.println(toString() + " selected");
		view.setState(ViewState.EDITING);
	}
	
	@Override
	public void deselect() {
		//System.out.println(toString() + " deselected");
		view.setState(ViewState.VIEWING);
	}
	
	@Override
	public PageInterface getPageInterface() {
    	return pageInterface;
    }
}
