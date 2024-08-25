package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.geom.Ellipse2D;
import java.awt.Point;

//
//	Smart Circle adjusts its location given a center point and diameter
//	or adjusts its center given x, y coordinates and diameter
//
class SmartCircle {
	// Attributes
    private Ellipse2D.Double circle;
    private double diameter;
    private double scale = 1.0;
    private boolean isSelected = false;

    
    // C'tor
    SmartCircle( Point center, double diameter, double scale ) {
    	// Calculate the x, y coordinates
        double x = center.x - diameter / 2.0;
        double y = center.y - diameter / 2.0;
        
        // Create the circle
        this.circle = new Ellipse2D.Double( x, y, diameter, diameter );
        
        // Store the diameter and scale
        this.diameter = diameter;
        this.scale = scale;
    }

    // Set the center location of the circle with a point
    void setCenter( Point center ) {
        double x = center.x - diameter / 2.0;
        double y = center.y - diameter / 2.0;
        circle.setFrame( x, y, diameter, diameter );
    }
    
    // Set the center location of the circle with a x, y values
    void setCenter( double x, double y ) {
        double topLeftX = x - ( diameter / 2.0 );
        double topLeftY = y - ( diameter / 2.0 );
        circle.setFrame( topLeftX, topLeftY, diameter, diameter );
    }

    // Get the Ellipse2D object representing the circle
    Ellipse2D.Double getCircle() {
        return this.circle;
    }
    
    // Get the scaled circle (same center point)
    Ellipse2D.Double getScaledCircle() {
        // Calculate the scaled diameter
        double scaledDiameter = diameter * scale;

        // Get the center point of the current circle
        Point center = getCenter();

        // Calculate the new x, y coordinates to keep the circle centered
        double x = center.x - scaledDiameter / 2.0;
        double y = center.y - scaledDiameter / 2.0;

        // Return the new scaled circle
        return new Ellipse2D.Double( x, y, scaledDiameter, scaledDiameter );
    }

    // Get the center of the circle as a Point
    Point getCenter() {
        return new Point( (int)circle.getCenterX(), (int)circle.getCenterY() );
    }

    // Set the diameter of the circle and update its position
    void setDiameter( double diameter ) {
        this.diameter = diameter;
    }

    // Get the diameter of the circle
    double getDiameter() {
        return diameter;
    }
    
    // Set the scale
    void setScale( double scale ) {
        this.scale = scale;
    }

    // Get the scale
    double getScale() {
        return scale;
    }
    
    // Select/deselect
    void setSelected( boolean selected ) {
		isSelected = selected;
	}
	
    // Check if selected
	boolean isSelected() {
		return isSelected;
	}
    
    // Check if a point is inside the circle
	boolean contains( Point point ) {
	    return circle.contains( point );
	}
	
	void printCircle() {
		// Get the center point
		int x = getCenter().x;
		int y = getCenter().y;
		
		System.out.println( "center: (" + x + ", " + y + " )  " + " diameter: " + diameter );
	}
}
