package com.funcodelic.pianpiano.gui;

import javax.swing.*;
import java.awt.*;
import com.funcodelic.pianpiano.sheetmusicnotation.*;

//
//	This class encapsulates the editor panel of the GUI.
//
//	It holds a Page View wrapped in a JScrollPane
//	and helps with zooming and panning to facilitate building the score.
//
class PianPianoEditorPanel extends JPanel {

    private JScrollPane scrollPane;
    private PageView pageView;
    
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
    

    public PianPianoEditorPanel() {
    	// Set the layout
        setLayout(new BorderLayout());
        
        // Create a scroll pane to house the page view and add it to the panel
        scrollPane = new JScrollPane();
        scrollPane.getViewport().setBackground(Color.DARK_GRAY);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setPageView(PageView pageView) {
        this.pageView = pageView;
        scrollPane.setViewportView(pageView);
        revalidate();
        repaint();
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
        revalidate();
        repaint();
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
    
    public void printViewportInfo() {
    	Point viewPosition = scrollPane.getViewport().getViewPosition();
    	int x = viewPosition.x;
    	int y = viewPosition.y;
    	String locString = "(" + x + ", " + y + ")";
    	
    	Dimension dimensions = scrollPane.getViewport().getExtentSize();
    	int w = dimensions.width;
    	int h = dimensions.height;
    	String dimString = w + " x " + h;
    	
    	System.out.println("location: " + locString + "  extent: " + dimString);
    	
    }
}
