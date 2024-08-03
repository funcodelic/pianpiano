package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.event.MouseEvent;

//
//	Common interface for page interface handling strategies
//
public interface PageInterface {

	void mousePressed(MouseEvent e);
    void mouseDragged(MouseEvent e);
    void mouseReleased(MouseEvent e);
    
}