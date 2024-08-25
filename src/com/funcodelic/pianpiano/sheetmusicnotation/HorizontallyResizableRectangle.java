package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.*;
import java.awt.geom.Rectangle2D;

class HorizontallyResizableRectangle {
    private Rectangle2D.Double rectangle;
    private static final int HANDLE_SIZE = 10;
    private int handleIndex = -1;
    private Point dragStart;
    private double scale = 1.0;

    HorizontallyResizableRectangle( Rectangle2D.Double rectangle, double scale ) {
    	this.rectangle = rectangle;
        this.scale = scale;
    }

    void setScale( double scale ) {
        this.scale = scale;
    }

    double getScale() {
        return scale;
    }

    void startResizing( Point point ) {
        handleIndex = getHandleIndex( point );
        
        if ( handleIndex != -1 ) {
            dragStart = point;
        }
    }

    void resize( Point point ) {
        if ( dragStart == null || handleIndex == -1 ) return;

        int dx = point.x - dragStart.x;

        switch ( handleIndex ) {
            case 0: // Top-left
            case 3: // Bottom-left
            case 1: // Center-left
                if ( rectangle.width - dx >= 40 ) {
                    rectangle.x += dx;
                    rectangle.width -= dx;
                }
                break;
            case 2: // Top-right
            case 5: // Bottom-right
            case 4: // Center-right
                if ( rectangle.width + dx >= 40 ) {
                    rectangle.width += dx;
                }
                break;
        }
        dragStart = point;
    }

    void stopResizing() {
        handleIndex = -1;
    }

    int getHandleIndex( Point point ) {
        for ( int i = 0; i < getHandles().length; i++ ) {
            Point handle = getHandles()[i];
            
            double handleRadius = (double)getScaledHandleSize() / 2.0;
            if ( point.distance( handle ) < handleRadius ) {
                return i;
            }
        }
        return -1;
    }
    
    int getScaledHandleSize() {
    	int scaledHandleSize = (int)( (double)HANDLE_SIZE / scale );
    	return scaledHandleSize;
    }

    Cursor getCursor(Point point) {
        int index = getHandleIndex(point);
        switch (index) {
            case 0:
            case 1:
            case 3:
                return Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
            case 2:
            case 4:
            case 5:
                return Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
            default:
                return Cursor.getDefaultCursor();
        }
    }

    Point[] getHandles() {
        Point[] handles = new Point[6];
        handles[0] = new Point((int) rectangle.x, (int) rectangle.y); // Top-left
        handles[1] = new Point((int) rectangle.x, (int) (rectangle.y + rectangle.height / 2)); // Center-left
        handles[2] = new Point((int) (rectangle.x + rectangle.width), (int) rectangle.y); // Top-right
        handles[3] = new Point((int) rectangle.x, (int) (rectangle.y + rectangle.height)); // Bottom-left
        handles[4] = new Point((int) (rectangle.x + rectangle.width), (int) (rectangle.y + rectangle.height / 2)); // Center-right
        handles[5] = new Point((int) (rectangle.x + rectangle.width), (int) (rectangle.y + rectangle.height)); // Bottom-right

        return handles;
    }

    Rectangle2D.Double getRectangle() {
        return rectangle;
    }
    
    // Check if a point is inside the circle
    boolean contains(Point point) {
    	return getRectangle().contains( point );
    }
}
