package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;

public class HorizResizRectComponent extends JComponent {
    private HorizontallyResizableRectangle rect;

    public HorizResizRectComponent(Rectangle2D.Double initialRectangle) {
    	rect = new HorizontallyResizableRectangle(initialRectangle);

        // Add mouse listeners to handle resizing
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                rect.startResizing(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                rect.stopResizing();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                rect.resize(e.getPoint());
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        rect.draw((Graphics2D) g);
    }
}
