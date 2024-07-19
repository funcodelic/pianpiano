package com.funcodelic.pianpiano.sheetmusicnotation;

import javax.swing.*;

//import com.funcodelic.pianpiano.gui.PianPianoEditorPanel.Magnification;

//
//	PageController is the main handle to the Page MVC classes.
//	It mediates between the PageModel and PageView.
//
class PageController implements PanelEditable, Inspectable, Zoomable {
	// The model and view components
    private PageModel pageModel;
    private PageView pageView;
    
	// The panel to inspect and configure the page
	private PageInspectorPanel pageInspectorPanel;
	
	public enum Magnification {
        HALF(0.5),
        ONE(1.0),
        TWO(2.0);

        private final double value;

        Magnification(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }
    
    private Magnification magnification = Magnification.ONE;

    public PageController(String fileImagePath, int pageNumber) {
        pageModel = new PageModel(fileImagePath, pageNumber);
        pageView = new PageView(pageModel.getImage());
        
		pageInspectorPanel = new PageInspectorPanel(this);
    }

    public JComponent getView() {
        return pageView.getView();
    }
    
    public JComponent getInspectorView() {
    	return pageInspectorPanel;
    }

    public void setScale(double scale) {
        pageView.setScale(scale);
    }
    public double getScale() {
		return pageView.getScale();
	}
    
	public JPanel getInspectorPanel() {
		return pageInspectorPanel;
	}
	
	@Override
	public String toString() {
		return "Page " + pageModel.getPageNumber();
	}
	
	public void zoom( boolean zoomIn ) {
    	Magnification [] magnifications = Magnification.values();
    	
    	if ( zoomIn ) {
    		int magLength = magnifications.length;
    		Magnification highestMag = magnifications[magLength - 1];
    		int comparison = highestMag.compareTo( magnification );
    		
    		// Zoom in if there's room
    		if ( comparison > 0 ) {
    			int currentMagIndex = getMagnificationIndex(magnification);
    			currentMagIndex += 1;
    			magnification = magnifications[currentMagIndex];
    			setMagnification(magnification);
    		}
    	}
    	else {
    		Magnification lowestMag = magnifications[0];
    		
    		int comparison = lowestMag.compareTo(magnification);
    		
    		// Zoom out if there's room
    		if ( comparison < 0 ) {
    			int currentMagIndex = getMagnificationIndex(magnification);
    			currentMagIndex -= 1;
    			magnification = magnifications[currentMagIndex];
    			setMagnification(magnification);
    		}
    	}
    }
    
    private void setMagnification(Magnification magnification) {
    	pageView.setScale( magnification.getValue() );
    }
    
    private static int getMagnificationIndex(Magnification magnification) {
        Magnification[] values = Magnification.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i] == magnification) {
                return i;
            }
        }
        return -1; // Return -1 if not found
    }
}