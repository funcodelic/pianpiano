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

    private JScrollPane scrollPane;
    private SheetMusicPanel sheetMusicPanel;
    private BufferedImage sheetMusicImage;
    private double scale = 1.0;

    // The staff system views managed by the page view
    private List<StaffSystemView> staffSystemViews;
    
    // The mouse event handler strategy for interfacing with the page
    private PageInterface currentPageInterface;

    
    // C'tor
    public PageView(BufferedImage image) {
        this.sheetMusicImage = image;
        this.sheetMusicPanel = new SheetMusicPanel();

        // Create the JScrollPane and add the inner panel
        this.scrollPane = new JScrollPane(sheetMusicPanel);
        this.scrollPane.getViewport().setBackground(Color.DARK_GRAY);

        staffSystemViews = new ArrayList<>();
        
        setPageInterface(new PanPage(sheetMusicPanel)); // Default strategy
    }

    public void addStaffSystemView(StaffSystemView view) {
        staffSystemViews.add(view);
        sheetMusicPanel.repaint();
    }

    public List<StaffSystemView> getStaffSystemViews() {
        return staffSystemViews;
    }

    public JComponent getScrollPane() {
        return scrollPane;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
        
        // Propagate scale to all StaffSystemViews
        for (StaffSystemView staffSystemView : staffSystemViews) {
            staffSystemView.setScale(scale);
        }
        
        sheetMusicPanel.revalidate();
        sheetMusicPanel.repaint();
    }
    
    public void setPageInterface(PageInterface pageInterface) {
        this.currentPageInterface = pageInterface;
    }

    //
    // Inner class to hold the sheet music panel
    //
    private class SheetMusicPanel extends JPanel {

        public SheetMusicPanel() {
            setLayout(new BorderLayout());
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (currentPageInterface != null) {
                        currentPageInterface.mousePressed(e);
                        repaint();
                    }
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (currentPageInterface != null) {
                        currentPageInterface.mouseReleased(e);
                        repaint();
                    }
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (currentPageInterface != null) {
                        currentPageInterface.mouseDragged(e);
                        repaint();
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

                for (StaffSystemView system : staffSystemViews) {
                    system.draw(g2d);
                }
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return sheetMusicImage == null ? super.getPreferredSize() :
                    new Dimension((int) (sheetMusicImage.getWidth() * scale),
                            (int) (sheetMusicImage.getHeight() * scale));
        }
    }
    
    public PageInterface getCurrentPageInterface() {
    	return currentPageInterface;
    }
    
    public SheetMusicPanel getSheetMusicPanel() {
    	return sheetMusicPanel;
    }
}