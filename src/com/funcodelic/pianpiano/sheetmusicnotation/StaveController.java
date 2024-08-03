package com.funcodelic.pianpiano.sheetmusicnotation;

import javax.swing.JComponent;

//
//	StaveController is the main handle of the Stave
//	and the controller class of the MVC paradigm
//
class StaveController implements SheetMusicNode {
	
	// The model and view
	private StaveModel model;
	private StaveView view;
	
	// The inspector panel
	private StaveInspectorPanel inspectorPanel;
	
	// The page interface adapter
	PageInterface pageInterface;
	
	
	// C'tor
	public StaveController(StaveModel model, StaveView view) {
		this.model = model;
		this.view = view;
		
		// Instantiate the interface strategy
		pageInterface = new AdjustStave(view);
		
		// Instantiate the inspector panel
		inspectorPanel = new StaveInspectorPanel(this);
	}
	
	public StaveView getView() {
        return view;
    }

    public StaveModel getModel() {
        return model;
    }
    
    public void setViewState( ViewState viewState ) {
		view.setState( viewState );
	}
	
	public ViewState getViewState() {
		return view.getState();
	}
	
	@Override
	public String toString() {
		return "Stave Controller";
	}

	//@Override
	public JComponent getInspectorView() {
		return inspectorPanel;
	}

	@Override
	public void select() {
	}
	
	@Override
	public void deselect() {
	}
	
	@Override
	public PageInterface getPageInterface() {
    	return pageInterface;
    }

}
