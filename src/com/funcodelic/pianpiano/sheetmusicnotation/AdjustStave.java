package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.event.MouseEvent;

//
//	A concrete implementation of the PageInterface mouse event handling strategy
//	to adjust the staff lines of the staff system
//
class AdjustStave implements PageInterface {
	
	// The stave to adjust
	private StaveController stave;
	
	
	// C'tor
	AdjustStave( StaveController stave ) {
		this.stave = stave;
	}

	@Override
	public void mousePressed( MouseEvent e ) {
		stave.startResizing( e.getPoint() );
	}

	@Override
	public void mouseDragged( MouseEvent e ) {
		stave.resize( e.getPoint() );
	}
	
	@Override
	public void mouseReleased( MouseEvent e ) {
		stave.stopResizing( e.getPoint() );
	}

	@Override
    public String toString() {
    	return "Adjust Stave";
    }

	@Override
	public void mouseMoved(MouseEvent e) {
		// Do nothing
	}
}
