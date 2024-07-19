package com.funcodelic.pianpiano.sheetmusicnotation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

//
//	This class represents a page of sheet music.
//
//	It is a JPanel with a buffered image of a page of sheet music drawn in it.
//
class PageView {
	
    private JScrollPane scrollPane;
    private SheetMusicPanel sheetMusicPanel;
    private BufferedImage sheetMusicImage;
    private double scale = 1.0;

    // TODO: Replace these with actual Staff Systems
    private Rectangle2D.Double staffSystem1;
    private Rectangle2D.Double staffSystem2;

    public PageView(BufferedImage image) {
    	this.sheetMusicImage = image;
        this.sheetMusicPanel = new SheetMusicPanel();

        // Create the JScrollPane and add the inner panel
        this.scrollPane = new JScrollPane(sheetMusicPanel);
        this.scrollPane.getViewport().setBackground(Color.DARK_GRAY);
    	

        // Initialize the new rectangles
        staffSystem1 = new Rectangle2D.Double(180, 395, 2400, 530);
        staffSystem2 = new Rectangle2D.Double(180, 940, 2400, 530);
    }
    
    public JComponent getView() {
        return scrollPane;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        System.out.println(scale + "x");
        this.scale = scale;
        sheetMusicPanel.revalidate();
        sheetMusicPanel.repaint();
    }
    
    //
    //	Inner class to hold the sheet music panel
    //
    private class SheetMusicPanel extends JPanel {
        private Point dragStartPoint;

        public SheetMusicPanel() {
        	setLayout( new BorderLayout() );

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
    }
}
