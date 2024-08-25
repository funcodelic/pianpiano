package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.event.*;

//
//	The concrete interface for sliding measures in the staff system
//
class SlideMeasure implements PageInterface {
	
	// The measure to slide
	private MeasureController measure;
	
	
	// C'tor
	SlideMeasure( MeasureController measure ) {
		this.measure = measure;
	}

	@Override
	public void mousePressed( MouseEvent e ) {
		measure.startResizing( e.getPoint() );
	}

	@Override
	public void mouseDragged( MouseEvent e ) {
		measure.resize( e.getPoint() );
	}
	
	@Override
	public void mouseReleased( MouseEvent e ) {
		measure.stopResizing( e.getPoint() );
	}

	@Override
	public void mouseMoved( MouseEvent e ) {
		// Do nothing
	}
	@Override
    public String toString() {
    	return "Slide Measure";
    }
}
