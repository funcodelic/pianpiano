package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

//
//	The Measure model class of the MVC paradigm
//
class MeasureModel {
	// The rectangle that is the bounds of the measure
	private HorizontallyResizableRectangle rectangle;
    
	// The index, zero-based
	private int index;
	
	
	// C'tor
	MeasureModel( int index, Rectangle2D.Double rectangle, double scale ) {
		this.rectangle = new HorizontallyResizableRectangle( rectangle, scale );
		this.index = index;
	}
	
	HorizontallyResizableRectangle getRectangle() {
		return rectangle;
	}
	
	int getIndex() {
		return index;
	}
	
	int getMeasureNumber() {
		return index + 1;
	}
	
	void setScale( double scale ) {
    	rectangle.setScale( scale );
    }
    
    double getScale() {
    	return rectangle.getScale();
    }
	
	void startResizing( Point p ) {
		rectangle.startResizing( p );
    }
    
    void resize( Point p ) {
    	// Resize the rectangle
    	rectangle.resize( p );
    }
	
	@Override
	public String toString() {
		return "Measure " + getMeasureNumber();
	}

}
