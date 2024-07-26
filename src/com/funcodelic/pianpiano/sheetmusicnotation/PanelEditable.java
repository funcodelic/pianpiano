package com.funcodelic.pianpiano.sheetmusicnotation;

import javax.swing.JComponent;

//
//  An interface implemented by sheet music entities that provide
//	a panel to be added to the Editor panel for editing
//
public interface PanelEditable {
	
    JComponent getPanelEditableView();
    
}