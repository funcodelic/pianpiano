package com.funcodelic.pianpiano.sheetmusicnotation;

import javax.swing.JComponent;

//
//	The Measure Controller class of the MVC paradigm
//
class MeasureController implements SheetMusicNode {
	
	// The model and view
	MeasureModel model;
	MeasureView  view;
	
	// Inspector panel
	MeasureInspectorPanel inspectorPanel;
	
	// The page interface adapter
	PageInterface pageInterface;
	
	
	// C'tor
	MeasureController( MeasureModel model, MeasureView view ) {
		// Instantiate the model and view
		this.model = model;
		this.view = view;
		
		// Instantiate the interface strategy
		pageInterface = new SlideMeasure(view);
		
		// Instantiate the inspector panel
		inspectorPanel = new MeasureInspectorPanel( this );
	}
	
	MeasureModel getModel() {
		return model;
	}
	
	MeasureView getView() {
		return view;
	}
	
	int getIndex() {
		return model.getIndex();
	}
	
	public void setViewState( ViewState viewState ) {
		view.setState( viewState );
	}
	
	public ViewState getViewState() {
		return view.getState();
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
	
	@Override
	public String toString() {
		return model.toString();
	}

}
