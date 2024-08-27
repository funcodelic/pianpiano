package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.Point;

//
//	The Note Model class of the MVC paradigm
//
class NoteModel {
	// The note body
	private SmartCircle noteBody; // note body
	
	// The staff the note is on (false = lower staff)
	private boolean isUpperStaff;
	
	// The note's key/pitch
	String pitch;
	
	
	// C'tor
    NoteModel( boolean isUpperStaff, Point center, double diameter, double scale ) {
        // Construct the note body
        noteBody = new SmartCircle( center, diameter, scale );
        
        // Set the staff
        this.isUpperStaff = isUpperStaff;
    }
    
    void setPitch( String pitch ) {
    	this.pitch = pitch;
    }
    
    String getPitch() {
    	return pitch;
    }
    
    boolean isUpperStaff() {
    	return isUpperStaff;
    }
    
    SmartCircle getNoteBody() {
    	return noteBody;
    }
    
    void setSelected( boolean selected ) {
		noteBody.setSelected( selected );
	}
	
	boolean isSelected() {
		return noteBody.isSelected();
	}
	
	double getCenterY() {
		return noteBody.getCenter().getY();
	}
    
    void setNoteLocation( Point p ) {
    	noteBody.setCenter( p );
    }
    
    void setNoteLocation( double x, double y ) {
    	noteBody.setCenter( x, y );
    }
	
	void setScale( double scale ) {
		noteBody.setScale( scale );
	}
	
	double getScale() {
		return noteBody.getScale();
	}
    
	@Override
	public String toString() {
		String staffString = isUpperStaff ? "Upper" : "Lower";
		return staffString + " Staff Note";
	}
	
}
