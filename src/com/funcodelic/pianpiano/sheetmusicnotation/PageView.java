package com.funcodelic.pianpiano.sheetmusicnotation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

//
//	The view class of the page's MVC classes, which displays and manages
//	the sheet music image and all the sheet music notation objects in it
//
class PageView {
	// View related attributes for the page
    private JScrollPane scrollPane;
    private SheetMusicPanel sheetMusicPanel;
    private BufferedImage sheetMusicImage;
    private double scale = 1.0;

    // The staff system views managed and drawn by the page view
    private List<StaffSystemView> staffSystemViews;
    
    // The mouse event handler strategy for interfacing with the page
    private PageInterface currentPageInterface;

    
    // C'tor
    PageView( BufferedImage image ) {
    	// Store the image of the sheet music
        this.sheetMusicImage = image;
        
        // Create a new sheet music panel
        this.sheetMusicPanel = new SheetMusicPanel();

        // Create the scroll pane to house the sheet music panel
        this.scrollPane = new JScrollPane( sheetMusicPanel );
        this.scrollPane.getViewport().setBackground( Color.DARK_GRAY );

        // Instantiate the list of staff systems
        staffSystemViews = new ArrayList<>();
        
        // Set the page interface to this page's interface by default
        setPageInterface( new PanPage( sheetMusicPanel ) );
    }

    // Add a staff system view for this page view to display
    void addStaffSystemView( StaffSystemView view ) {
        staffSystemViews.add( view );
        sheetMusicPanel.repaint();
    }

    List<StaffSystemView> getStaffSystemViews() {
        return staffSystemViews;
    }

    JComponent getScrollPane() {
        return scrollPane;
    }

    double getScale() {
        return scale;
    }

    void setScale( double scale ) {
    	// Set this view's scale
        this.scale = scale;
        
        // Refresh the view
        sheetMusicPanel.revalidate();
        sheetMusicPanel.repaint();
    }
    
    // Set this page's interface adapter
    void setPageInterface( PageInterface pageInterface ) {
        this.currentPageInterface = pageInterface;
    }

    //
    // Inner class to hold the sheet music panel
    //
    class SheetMusicPanel extends JPanel {

        public SheetMusicPanel() {
            setLayout( new BorderLayout() );
            
            // Add a mouse event adapters and call the page interface methods
            // to handle the mouse events as appropriate
            addMouseListener( new MouseAdapter() {
                @Override
                public void mousePressed( MouseEvent e ) {
                    if ( currentPageInterface != null ) {
                        currentPageInterface.mousePressed(e);
                        repaint();
                    }
                }
                
                @Override
                public void mouseReleased( MouseEvent  e ) {
                    if ( currentPageInterface != null ) {
                        currentPageInterface.mouseReleased(e);
                        repaint();
                    }
                }
            });

            addMouseMotionListener( new MouseMotionAdapter() {
                @Override
                public void mouseDragged( MouseEvent e ) {
                    if ( currentPageInterface != null ) {
                        currentPageInterface.mouseDragged(e);
                        repaint();
                    }
                }
                
                @Override
                public void mouseMoved( MouseEvent e ) {
                	if ( currentPageInterface != null ) {
                		currentPageInterface.mouseMoved(e);
                		repaint();
                	}
                }
            });
        }

        @Override
        protected void paintComponent( Graphics g ) { // the page's version of draw()
            super.paintComponent(g);
            
            if ( sheetMusicImage != null ) {
                Graphics2D g2d = (Graphics2D) g;
                
                // Scale the graphics context to zoom in and out
                g2d.scale( scale, scale );
                
                // Draw the sheet music image
                g2d.drawImage( sheetMusicImage, 0, 0, this );

                // Draw the Staff Systems
                g2d.setColor(Color.BLUE);
                for ( StaffSystemView system : staffSystemViews ) {
                    system.draw( g2d );
                }
            }
        }

        @Override
        public Dimension getPreferredSize() {
        	return sheetMusicImage == null ? super.getPreferredSize() :
                    new Dimension(	(int)( sheetMusicImage.getWidth() * scale ),
                            		(int)( sheetMusicImage.getHeight() * scale ));
        }
    }
    
    PageInterface getCurrentPageInterface() {
    	return currentPageInterface;
    }
    
    SheetMusicPanel getSheetMusicPanel() {
    	return sheetMusicPanel;
    }
}