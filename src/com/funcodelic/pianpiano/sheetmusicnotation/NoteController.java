package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.Point;

//
//	Note Controller is the controller of the MVC paradigm, and the main note handle
//
class NoteController {
	// The model and view
	NoteModel model;
	NoteView  view;
	
	
	// C'tor
	NoteController( boolean isUpperStaffNote, Point center, double diameter, double scale ) {
		// Create the model and view
		model = new NoteModel( isUpperStaffNote, center, diameter, scale );
		view = new NoteView( model.getNoteBody() );
	}
	
	boolean isUpperStaff() {
		return model.isUpperStaff();
	}
	
	void setPitch( String pitch ) {
		System.out.println( "pitch: " + pitch );
		model.setPitch( pitch );
	}
	
	String getPitch() {
		return model.getPitch();
	}
	
	NoteModel getModel() {
		return model;
	}
	
	NoteView getView() {
		return view;
	}
	
	double getCenterY() {
		return model.getCenterY();
	}
	
	void setNoteLocation( Point p ) {
		model.setNoteLocation( p );
	}
	
	void setNoteLocation( double x, double y ) {
    	model.setNoteLocation( x, y );
    }
	
	void setScale( double scale ) {
		model.setScale( scale );
	}
	
	double getScale() {
		return model.getScale();
	}
	
	void setSelected( boolean isSelected ) {
		model.setSelected( isSelected );
	}
	
	boolean isSelected() {
		return model.isSelected();
	}
	
	@Override
	public String toString() {
		return model.toString();
	}

}
