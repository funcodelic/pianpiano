package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.*;
import java.awt.geom.Rectangle2D;

//
//	A rectangle with handles on the sides and corners for adjusting
//
class VerticallyResizableRectangle {
    private Rectangle2D.Double rectangle;
    private static final int HANDLE_SIZE = 10;
    private int handleIndex = -1;
    private Point dragStart;
    protected double scale = 1.0;

    public VerticallyResizableRectangle(Rectangle2D.Double rectangle) {
        this.rectangle = rectangle;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getScale() {
        return scale;
    }

    public void draw(Graphics2D g2d) {
        // Draw the resize handles
        g2d.setColor(Color.RED);
        for (Point handle : getHandles()) {
            int scaledHandleSize = (int) (HANDLE_SIZE / scale);
            g2d.fillRect(handle.x - scaledHandleSize / 2, handle.y - scaledHandleSize / 2, scaledHandleSize, scaledHandleSize);
        }
    }

    public void startResizing(Point point) {
        Point scaledPoint = new Point((int) (point.x / scale), (int) (point.y / scale));
        handleIndex = getHandleIndex(scaledPoint);
        if (handleIndex != -1) {
            dragStart = scaledPoint;
        }
    }

    public void resize(Point point) {
        if (dragStart == null || handleIndex == -1) return;

        Point scaledPoint = new Point((int) (point.x / scale), (int) (point.y / scale));
        int dy = scaledPoint.y - dragStart.y;

        switch (handleIndex) {
            case 0: // Top-left
            case 1: // Top-center
            case 2: // Top-right
                if (rectangle.height - dy >= 40) {
                    rectangle.y += dy;
                    rectangle.height -= dy;
                }
                break;
            case 3: // Bottom-left
            case 4: // Bottom-center
            case 5: // Bottom-right
                if (rectangle.height + dy >= 40) {
                    rectangle.height += dy;
                }
                break;
        }
        dragStart = scaledPoint;
    }

    public void stopResizing() {
        handleIndex = -1;
    }

    public int getHandleIndex(Point point) {
        for (int i = 0; i < getHandles().length; i++) {
            Point handle = getHandles()[i];
            if (point.distance(handle) < HANDLE_SIZE / scale) {
                return i;
            }
        }
        return -1;
    }

    public Cursor getCursor(Point point) {
        int index = getHandleIndex(point);
        switch (index) {
            case 0:
            case 1:
            case 2:
                return Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
            case 3:
            case 4:
            case 5:
                return Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
            default:
                return Cursor.getDefaultCursor();
        }
    }

    private Point[] getHandles() {
        Point[] handles = new Point[6];
        handles[0] = new Point((int) rectangle.x, (int) rectangle.y); // Top-left
        handles[1] = new Point((int) (rectangle.x + rectangle.width / 2), (int) rectangle.y); // Top-center
        handles[2] = new Point((int) (rectangle.x + rectangle.width), (int) rectangle.y); // Top-right
        handles[3] = new Point((int) rectangle.x, (int) (rectangle.y + rectangle.height)); // Bottom-left
        handles[4] = new Point((int) (rectangle.x + rectangle.width / 2), (int) (rectangle.y + rectangle.height)); // Bottom-center
        handles[5] = new Point((int) (rectangle.x + rectangle.width), (int) (rectangle.y + rectangle.height)); // Bottom-right

        return handles;
    }

    public Rectangle2D.Double getRectangle() {
        return rectangle;
    }
    
    public void setRectangle(Rectangle2D.Double rect) {
    	this.rectangle = rect;
    }
}
