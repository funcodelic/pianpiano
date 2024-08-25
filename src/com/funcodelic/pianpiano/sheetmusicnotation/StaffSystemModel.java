package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.Point;
import java.awt.geom.Rectangle2D;

//
//	The Staff System model class of the MVC paradigm
//
class StaffSystemModel {
	// The staff system bounds
	private final ResizableRectangle resizableRect;
    private int index;
    
    // The number of the page within the score
    int numberOnPage = -1;
    

    // C'tor
    StaffSystemModel( int index, Rectangle2D.Double bounds, double scale ) {
        this.resizableRect = new ResizableRectangle( bounds, scale );
        this.index = index;
        this.numberOnPage = index + 1;
    }
    
    int getNumberOnPage() {
    	return numberOnPage;
    }

    int getIndex() {
        return index;
    }

    void setIndex(int index) {
        this.index = index;
    }
    
    void startResizing( Point p ) {
    	resizableRect.startResizing( p );
    }
    
    void resize( Point p ) {
    	resizableRect.resize( p );
    }
    
    void setScale( double scale ) {
    	resizableRect.setScale( scale );
    }
    
    double getScale() {
    	return resizableRect.getScale();
    }

    ResizableRectangle getResizableRect() {
        return resizableRect;
    }
    
    @Override
    public String toString() {
    	return "Staff System " + getNumberOnPage();
    }
}
