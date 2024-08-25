package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.*;
import java.awt.geom.Rectangle2D;

//
//	A rectangle with handles on the sides and corners for adjusting
//
class ResizableRectangle {
    private Rectangle2D.Double rectangle;
    private final int HANDLE_SIZE = 10;
    private int handleIndex = -1;
    private Point dragStart;
    private double scale = 1.0;

    ResizableRectangle( Rectangle2D.Double rectangle, double scale ) {
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
        int dy = point.y - dragStart.y;
        
    	switch ( handleIndex ) {
            case 0: // Top-left
                rectangle.x += dx;
                rectangle.y += dy;
                rectangle.width -= dx;
                rectangle.height -= dy;
                break;
            case 1: // Top-center
                rectangle.y += dy;
                rectangle.height -= dy;
                break;
            case 2: // Top-right
                rectangle.width += dx;
                rectangle.y += dy;
                rectangle.height -= dy;
                break;
            case 3: // Center-left
                rectangle.x += dx;
                rectangle.width -= dx;
                break;
            case 4: // Center-right
                rectangle.width += dx;
                break;
            case 5: // Bottom-left
                rectangle.x += dx;
                rectangle.width -= dx;
                rectangle.height += dy;
                break;
            case 6: // Bottom-center
                rectangle.height += dy;
                break;
            case 7: // Bottom-right
                rectangle.width += dx;
                rectangle.height += dy;
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

    Cursor getCursor( Point point ) {
        int index = getHandleIndex( point );
        switch ( index ) {
            case 0:
                return Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
            case 1:
                return Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
            case 2:
                return Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
            case 3:
                return Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
            case 4:
                return Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
            case 5:
                return Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
            case 6:
                return Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
            case 7:
                return Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
            default:
                return Cursor.getDefaultCursor();
        }
    }

    Point[] getHandles() {
        Point[] handles = new Point[8];
        handles[0] = new Point((int) rectangle.x, (int) rectangle.y); // Top-left
        handles[1] = new Point((int) (rectangle.x + rectangle.width / 2), (int) rectangle.y); // Top-center
        handles[2] = new Point((int) (rectangle.x + rectangle.width), (int) rectangle.y); // Top-right
        handles[3] = new Point((int) rectangle.x, (int) (rectangle.y + rectangle.height / 2)); // Center-left
        handles[4] = new Point((int) (rectangle.x + rectangle.width), (int) (rectangle.y + rectangle.height / 2)); // Center-right
        handles[5] = new Point((int) rectangle.x, (int) (rectangle.y + rectangle.height)); // Bottom-left
        handles[6] = new Point((int) (rectangle.x + rectangle.width / 2), (int) (rectangle.y + rectangle.height)); // Bottom-center
        handles[7] = new Point((int) (rectangle.x + rectangle.width), (int) (rectangle.y + rectangle.height)); // Bottom-right
        
        return handles;
    }

    Rectangle2D.Double getRectangle() {
        return rectangle;
    }

}

