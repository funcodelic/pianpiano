package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.event.*;

//
//	The concrete interface for sliding measures in the staff system
//
class SlideMeasure implements PageInterface {
	
	// The measure to slide
	private MeasureView measureView;
	
	
	// C'tor
	SlideMeasure( MeasureView measureView ) {
		this.measureView = measureView;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		measureView.startResizing(e.getPoint());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		measureView.resize(e.getPoint());
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// Do nothing
	}

	@Override
    public String toString() {
    	return "Slide Measure";
    }
}
