package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

//
//	A rectangle with handles on the sides and corners for adjusting
//
class VerticallyResizableRectangle {
    private Rectangle2D.Double rectangle;
    private final int HANDLE_SIZE = 10;
    private int handleIndex = -1;
    private Point dragStart;
    private double scale = 1.0;
    
    // lines
    private final int NUMBER_OF_LINES = 5;
    private final Line2D.Double[] lines = new Line2D.Double[NUMBER_OF_LINES];
    

    // C'tor
    VerticallyResizableRectangle( Rectangle2D.Double rectangle, double scale ) {
        this.rectangle = rectangle;
        this.scale = scale;
        
        for ( int i = 0; i < NUMBER_OF_LINES; i++ ) {
        	lines[i] = new Line2D.Double();
        }
    }
    
    Line2D.Double[] getLines() {
    	double x1Pos = rectangle.x;
        double x2Pos = rectangle.x + rectangle.width;
        double yPos;
        
        double vGap = rectangle.height / (double)( NUMBER_OF_LINES - 1 );
        
        for ( int i = 0; i < NUMBER_OF_LINES; i++ ) {
        	yPos = rectangle.y + ( (double)i * vGap );
        	
        	lines[i].x1 = x1Pos;
        	lines[i].x2 = x2Pos;
        	lines[i].y1 = yPos;
        	lines[i].y2 = yPos;
        }
    	
    	return lines;
    }
    
    float getScaledStroke() {
    	return 2.0f / (float)scale;
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

        int dy = point.y - dragStart.y;

        switch ( handleIndex ) {
            case 0: // Top-left
            case 1: // Top-center
            case 2: // Top-right
                if ( rectangle.height - dy >= 40 ) {
                    rectangle.y += dy;
                    rectangle.height -= dy;
                }
                break;
            case 3: // Bottom-left
            case 4: // Bottom-center
            case 5: // Bottom-right
                if ( rectangle.height + dy >= 40 ) {
                    rectangle.height += dy;
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

    Cursor getCursor( Point point ) {
        int index = getHandleIndex( point );
        switch ( index ) {
            case 0:
            case 1:
            case 2:
                return Cursor.getPredefinedCursor( Cursor.N_RESIZE_CURSOR );
            case 3:
            case 4:
            case 5:
                return Cursor.getPredefinedCursor( Cursor.S_RESIZE_CURSOR );
            default:
                return Cursor.getDefaultCursor();
        }
    }

    Point[] getHandles() {
        Point[] handles = new Point[6];
        handles[0] = new Point((int) rectangle.x, (int) rectangle.y); // Top-left
        handles[1] = new Point((int) (rectangle.x + rectangle.width / 2), (int) rectangle.y); // Top-center
        handles[2] = new Point((int) (rectangle.x + rectangle.width), (int) rectangle.y); // Top-right
        handles[3] = new Point((int) rectangle.x, (int) (rectangle.y + rectangle.height)); // Bottom-left
        handles[4] = new Point((int) (rectangle.x + rectangle.width / 2), (int) (rectangle.y + rectangle.height)); // Bottom-center
        handles[5] = new Point((int) (rectangle.x + rectangle.width), (int) (rectangle.y + rectangle.height)); // Bottom-right

        return handles;
    }

    Rectangle2D.Double getRectangle() {
        return rectangle;
    }

}
