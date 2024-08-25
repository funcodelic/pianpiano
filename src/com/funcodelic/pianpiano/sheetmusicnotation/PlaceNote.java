package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.event.MouseEvent;


//
//	The concrete interface class to place a note in a measure
//
class PlaceNote implements PageInterface {
	
	// The measure to interface with
	MeasureController measure;
	
	
	// C'tor
	PlaceNote( MeasureController measure ) {
		this.measure = measure;
	}

	@Override
	public void mousePressed( MouseEvent e ) {
	}

	@Override
	public void mouseDragged( MouseEvent e ) {
	}

	@Override
	public void mouseMoved( MouseEvent e ) {
		measure.previewNotePlacement( e.getPoint() );
	}
	@Override
	public void mouseReleased( MouseEvent e ) {
		measure.placeNote( e.getPoint() );
	}

}
