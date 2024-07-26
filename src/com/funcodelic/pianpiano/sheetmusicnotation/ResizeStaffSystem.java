package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.event.*;

//
//	A concrete implementation of the PageInterface mouse event handling strategy
//
public class ResizeStaffSystem implements PageInterface {
    private StaffSystemView staffSystemView;

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
    public String toString() {
    	return "ResizeStaffSystem";
    }
 
}
