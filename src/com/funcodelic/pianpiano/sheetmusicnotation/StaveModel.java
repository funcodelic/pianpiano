package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.geom.Rectangle2D;

//
//	The Stave model class of the MVC paradigm
//
class StaveModel {

	// The bounds of the stave
	private Rectangle2D.Double bounds;
	
	
	// C'tor
	StaveModel( Rectangle2D.Double bounds ) {
		this.bounds = bounds;
	}
	
	Rectangle2D.Double getBounds() {
        return bounds;
    }

    void setBounds(Rectangle2D.Double bounds) {
        this.bounds = bounds;
    }
    
    @Override
    public String toString() {
    	return "Stave Model";
    }
}
