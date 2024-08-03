package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.geom.Rectangle2D;
import java.util.*;
import javax.swing.*;

//
//	PageController is the main handle to the Page MVC classes.
//	It mediates between the PageModel and PageView.
//
class PageController implements PanelEditable, SheetMusicNode, Zoomable {
	// The model and view components
    private PageModel model;
    private PageView view;
    
    // The strategy for interfacing with the page, handling mouse events
    private final PageInterface pageInterface;
    
	// The panel to inspect and configure the page
	private PageInspectorPanel pageInspectorPanel;
	
	private List<StaffSystemController> staffSystemControllers;
    private int nextStaffSystemIndex;
    
    // TODO: DELETE THIS: for development only
    private Rectangle2D.Double rect1 = new Rectangle2D.Double(180, 395, 2400, 530);
    private Rectangle2D.Double rect2 = new Rectangle2D.Double(180, 940, 2400, 530);
    private List<Rectangle2D.Double> cannedRectangles = new ArrayList<Rectangle2D.Double>();
    {
    	cannedRectangles.add(rect1);
    	cannedRectangles.add(rect2);
    }
	
    // Magnification values for the page
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

    
    // C'tor
    public PageController(String fileImagePath, int pageNumber) {
        model = new PageModel(fileImagePath, pageNumber);
        view = new PageView(model.getImage());
        
        pageInterface = new PanPage(view.getSheetMusicPanel());
        view.setPageInterface(pageInterface);
        
		pageInspectorPanel = new PageInspectorPanel(this);
		
		staffSystemControllers = new ArrayList<>();
        nextStaffSystemIndex = 1;
    }
    
    public int getPageNumber() {
        return model.getPageNumber();
    }

    @Override
    public JComponent getPanelEditableView() {
        return view.getScrollPane();
    }
    
    @Override
    public JComponent getInspectorView() {
    	return pageInspectorPanel;
    }
    
    @Override
    public PageInterface getPageInterface() {
    	return pageInterface;
    }
    
    // Sets the strategy to interface with the page
    public void setPageInterface(PageInterface pageInterface) {
    	// Pass the page interface to the view
    	view.setPageInterface(pageInterface);
    }
    
    public StaffSystemController addStaffSystem() {
    	int index = staffSystemControllers.size();
    	Rectangle2D.Double newRect = createStaffSystemRectangle(index);
        StaffSystemModel model = new StaffSystemModel(nextStaffSystemIndex, newRect);
        StaffSystemView view = new StaffSystemView(model.getBounds());
        view.setScale(this.view.getScale()); // Set the current scale

        StaffSystemController staffSystem = new StaffSystemController(model, view);
        staffSystemControllers.add(staffSystem);
        this.view.addStaffSystemView(view);

        nextStaffSystemIndex++;
        
        return staffSystem;
    }
    
    public List<StaffSystemController> getStaffSystems() {
        return staffSystemControllers;
    }

    @Override
    public void setScale(double scale) {
        view.setScale(scale);
        
        for (StaffSystemController controller : staffSystemControllers) {
            controller.getView().setScale(scale);
        }
    }
    
    @Override
    public double getScale() {
		return view.getScale();
	}
	
	@Override
	public String toString() {
		return "Page " + model.getPageNumber();
	}
	
	@Override
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
    	view.setScale( magnification.getValue() );
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

	@Override
	public void select() {
	}
	
	@Override
	public void deselect() {
	}
	
	// Helper method to create a new rectangle with predefined dimensions
    public Rectangle2D.Double createStaffSystemRectangle(int index) {
//        double width = 2400;
//        double height = 530;
//        return new Rectangle2D.Double(x, y, width, height);
    	return cannedRectangles.get(index);
    }
}