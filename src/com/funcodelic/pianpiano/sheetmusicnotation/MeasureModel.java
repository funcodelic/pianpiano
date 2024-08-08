package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.geom.Rectangle2D;

//
//	The Measure model class of the MVC paradigm
//
class MeasureModel {
	
	private Rectangle2D.Double rectangle;
    
	private int index; // zero-based index
	
	
	// C'tor
	MeasureModel( Rectangle2D.Double rectangle, int index ) {
		this.rectangle = rectangle;
		this.index = index;
		
	}
	
	void setRectangle( Rectangle2D.Double rectangle ) {
		this.rectangle = rectangle;
	}
	
	Rectangle2D.Double getRectangle() {
		return rectangle;
	}
	
	int getIndex() {
		return index;
	}
	
	@Override
	public String toString() {
		return "Measure " + Integer.toString( index + 1 );
	}

}
