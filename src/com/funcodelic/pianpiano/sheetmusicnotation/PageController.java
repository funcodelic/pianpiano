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
	
	private List<StaffSystemController> staffSystems = new ArrayList<>();
    
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
    PageController( String fileImagePath, int pageNumber ) {
        model = new PageModel( fileImagePath, pageNumber );
        view = new PageView( model.getImage() );
        
        pageInterface = new PanPage( view.getSheetMusicPanel() );
        view.setPageInterface( pageInterface );
        
		pageInspectorPanel = new PageInspectorPanel( this );
    }
    
    void setPageNumber( int pageNumber ) {
    	this.model.setPageNumber( pageNumber );
    }
    
    int getPageNumber() {
        return model.getPageNumber();
    }
    
    int getNumStaffSystems() {
    	return staffSystems.size();
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
    void setPageInterface( PageInterface pageInterface ) {
    	// Pass the page interface to the view
    	view.setPageInterface( pageInterface );
    }
    
    StaffSystemController createStaffSystem() {
    	// Determine the index of the new staff system
    	int index = staffSystems.size();
    	
    	// Get the current scale
        double scale = this.view.getScale();
    	
    	// Create a bounds rectangle for the system
    	Rectangle2D.Double rect = createStaffSystemRectangle( index );
    	
    	// Create the staff system
    	StaffSystemController staffSystem = new StaffSystemController( index, rect, scale );
    	
    	// Add it to the list
        staffSystems.add( staffSystem );
        
        // Pass the page view the new staff system view for display
        this.view.addStaffSystemView( staffSystem.getView() );
        
        // Return the new staff system
        return staffSystem;
    }
    
    List<StaffSystemController> getStaffSystems() {
        return staffSystems;
    }

    @Override
    public void setScale( double scale ) {
    	// Set the page view scale
        view.setScale( scale );
        
        // Set the staff systems scale
        for ( StaffSystemController staffSystem : staffSystems ) {
        	staffSystem.setScale( scale );
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
    		Magnification highestMag = magnifications[ magLength - 1 ];
    		int comparison = highestMag.compareTo( magnification );
    		
    		// Zoom in if there's room
    		if ( comparison > 0 ) {
    			int currentMagIndex = getMagnificationIndex( magnification );
    			currentMagIndex++;
    			magnification = magnifications[ currentMagIndex ];
    			setMagnification( magnification );
    		}
    	}
    	else {
    		Magnification lowestMag = magnifications[0];
    		
    		int comparison = lowestMag.compareTo(magnification);
    		
    		// Zoom out if there's room
    		if ( comparison < 0 ) {
    			int currentMagIndex = getMagnificationIndex( magnification );
    			currentMagIndex--;
    			magnification = magnifications[ currentMagIndex ];
    			setMagnification( magnification );
    		}
    	}
    }
    
    private void setMagnification(Magnification magnification) {
    	//view.setScale( magnification.getValue() );
    	setScale( magnification.getValue() );
    }
    
    private static int getMagnificationIndex( Magnification magnification ) {
        Magnification[] values = Magnification.values();
        for ( int i = 0; i < values.length; i++ ) {
            if ( values[i] == magnification ) {
                return i;
            }
        }
        return -1; // Return -1 if not found
    }

	@Override
	public void select() {
		// Do nothing
	}
	
	@Override
	public void deselect() {
		// Do nothing
	}
	
	// Helper method to create a new rectangle with predefined dimensions
    private Rectangle2D.Double createStaffSystemRectangle( int index ) {
    	return cannedRectangles.get( index );
    }
}