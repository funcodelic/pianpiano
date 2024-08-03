package com.funcodelic.pianpiano.sheetmusicnotation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//
//	A concrete page interface strategy for panning a page (panel) of sheet music
//
public class PanPage implements PageInterface {
    private JComponent sheetMusicPanel;
    private Point dragStartPoint;

    public PanPage(JComponent sheetMusicPanel) {
        this.sheetMusicPanel = sheetMusicPanel;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        dragStartPoint = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        JViewport viewport = (JViewport) sheetMusicPanel.getParent();
        if (viewport != null && dragStartPoint != null) {
            Point viewPosition = viewport.getViewPosition();
            int deltaX = dragStartPoint.x - e.getX();
            int deltaY = dragStartPoint.y - e.getY();
            viewPosition.translate(deltaX, deltaY);
            sheetMusicPanel.scrollRectToVisible(new Rectangle(viewPosition, viewport.getSize()));
        }
    }
    
    @Override
	public void mouseReleased(MouseEvent e) {
	}
    
    @Override
    public String toString() {
    	return "Pan Page";
    }

}
