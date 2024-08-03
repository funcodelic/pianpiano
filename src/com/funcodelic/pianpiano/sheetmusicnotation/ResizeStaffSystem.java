package com.funcodelic.pianpiano.sheetmusicnotation;


import java.awt.event.*;

//
//	A concrete implementation of the PageInterface mouse event handling strategy
//	to resize the bounds of the staff system
//
public class ResizeStaffSystem implements PageInterface {
	
	// The staff system to resize
    private StaffSystemView staffSystemView;

    
    // C'tor
    public ResizeStaffSystem(StaffSystemView staffSystemView) {
        this.staffSystemView = staffSystemView;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    	staffSystemView.startResizing(e.getPoint());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        staffSystemView.resize(e.getPoint());
    }
    
    @Override
	public void mouseReleased(MouseEvent e) {
		staffSystemView.updateBounds();
	}
    
    @Override
    public String toString() {
    	return "Resize Staff System";
    }
 
}
