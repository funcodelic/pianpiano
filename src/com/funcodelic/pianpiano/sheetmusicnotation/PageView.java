package com.funcodelic.pianpiano.sheetmusicnotation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//
//	This class represents a page of sheet music.
//
//  It is a JPanel with a buffered image of a page of sheet music drawn in it.
//
public class PageView extends JPanel {
    private BufferedImage sheetMusicImage;
    private double scale = 1.0;
    private Point dragStartPoint;
    
    // TODO: Replace these with actual Staff Systems
    private Rectangle2D.Double staffSystem1;
    private Rectangle2D.Double staffSystem2;
    
    
    public PageView(String imagePath) {
    	// Read the image file into the buffered image
        try {
            sheetMusicImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            sheetMusicImage = null;
        }
        
        // Initialize the new rectangles
        staffSystem1 = new Rectangle2D.Double(180, 395, 2400, 530); // New code
        staffSystem2 = new Rectangle2D.Double(180, 940, 2400, 530); // New code
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dragStartPoint = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                JViewport viewport = (JViewport) getParent();
                if (viewport != null && dragStartPoint != null) {
                    Point viewPosition = viewport.getViewPosition();
                    int deltaX = dragStartPoint.x - e.getX();
                    int deltaY = dragStartPoint.y - e.getY();
                    viewPosition.translate(deltaX, deltaY);
                    scrollRectToVisible(new Rectangle(viewPosition, viewport.getSize()));
                }
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sheetMusicImage != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.scale(scale, scale);
            g2d.drawImage(sheetMusicImage, 0, 0, this);
            
            // Draw the Staff Systems
            g2d.setColor(Color.BLUE);
            g2d.draw(staffSystem1);
            g2d.draw(staffSystem2);
        }
    }

    @Override
    public Dimension getPreferredSize() {
    	return sheetMusicImage == null ? super.getPreferredSize() : 
        	new Dimension((int) (sheetMusicImage.getWidth() * scale), 
        			(int) (sheetMusicImage.getHeight() * scale));
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
    	System.out.println(scale + "x");
        this.scale = scale;
        revalidate();
    }
    
    public void printViewHeirarchy() {
    	printComponentInfo(this);
    	printComponentInfo(this.getParent());
    }
    
    public static void printComponentInfo(Component component) {
    	// Get the component's class name
    	String className = component.getClass().getName();
    	
    	// Get the origin point (location) of the component
        Point origin = component.getLocation();
        
        // Get the size of the component
        Dimension size = component.getSize();
        
        // Get the preferred size of the component
        Dimension prefSize = component.getPreferredSize();
        
        // Format/label the values into strings
        String originString = "Origin: (" + origin.x + ", " + origin.y + ")";
        String sizeString   = "Size: (" + size.width + ", " + size.height + ")";
        String prefString   = "Pref: (" + prefSize.width + ", " + prefSize.height + ")";
        
        // Print the component's origin and size strings
        System.out.println(className + ":\n" + originString + "  " + sizeString + "  " + prefString);
    }
    
}

