package com.funcodelic.pianpiano.sheetmusicnotation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//
//	A concrete page interface strategy for panning a page (panel) of sheet music
//
class PanPage implements PageInterface {
    private JComponent sheetMusicPanel;
    private Point dragStartPoint;

    PanPage( JComponent sheetMusicPanel ) {
        this.sheetMusicPanel = sheetMusicPanel;
    }

    @Override
    public void mousePressed( MouseEvent e ) {
        dragStartPoint = e.getPoint();
    }

    @Override
    public void mouseDragged( MouseEvent e ) {
    	// The sheet music panel is housed in a view port
        JViewport viewport = (JViewport) sheetMusicPanel.getParent();
        if ( viewport != null && dragStartPoint != null ) {
        	// Get the current position of the view port
            Point viewPosition = viewport.getViewPosition();
            
            // Calculate the deltas for x and y
            int deltaX = dragStartPoint.x - e.getX();
            int deltaY = dragStartPoint.y - e.getY();
            
            // Update the view port position
            viewPosition.translate( deltaX, deltaY );
            
            // Scroll to the new position
            sheetMusicPanel.scrollRectToVisible( new Rectangle( viewPosition, viewport.getSize() ) );
        }
    }
    
    @Override
	public void mouseReleased( MouseEvent e ) {
    	// Do nothing
	}
    
    @Override
	public void mouseMoved( MouseEvent e ) {
    	// Do nothing
	}
    
    @Override
    public String toString() {
    	return "Pan Page";
    }

}
