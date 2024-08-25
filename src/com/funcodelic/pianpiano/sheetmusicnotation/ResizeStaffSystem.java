package com.funcodelic.pianpiano.sheetmusicnotation;


import java.awt.event.*;

//
//	A concrete implementation of the PageInterface mouse event handling strategy
//	to resize the bounds of the staff system
//
class ResizeStaffSystem implements PageInterface {
	
	// The staff system to resize
    private StaffSystemController staffSystem;

    
    // C'tor
    ResizeStaffSystem( StaffSystemController staffSystem ) {
        this.staffSystem = staffSystem;
    }
    
    @Override
    public void mousePressed( MouseEvent e ) {
    	staffSystem.startResizing( e.getPoint() );
    }

    @Override
    public void mouseDragged( MouseEvent e ) {
        staffSystem.resize( e.getPoint() );
    }
    
    @Override
	public void mouseReleased( MouseEvent e ) {
		staffSystem.stopResizing( e.getPoint() );
	}

	@Override
	public void mouseMoved( MouseEvent e ) {
		// Do nothing
	}
	
    @Override
    public String toString() {
    	return "Resize Staff System";
    }
 
}
