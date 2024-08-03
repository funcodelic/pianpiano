package com.funcodelic.pianpiano.sheetmusicnotation;

import static com.funcodelic.pianpiano.sheetmusicnotation.ViewState.*;

import java.awt.event.MouseEvent;


//
//	A concrete implementation of the PageInterface mouse event handling strategy
//	to adjust the staff lines of the staff system
//
class AdjustStave implements PageInterface {
	
	// The staff lines to adjust
	private StaveView staveView;
	
	
	// C'tor
	AdjustStave( StaveView staveView ) {
		this.staveView = staveView;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		staveView.startResizing(e.getPoint());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		staveView.resize(e.getPoint());
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		staveView.configureStaffLines();
	}

	@Override
    public String toString() {
    	return "Adjust Stave";
    }
}
