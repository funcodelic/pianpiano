package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.geom.Rectangle2D;

//
//	The Staff System model class of the MVC paradigm
//
class StaffSystemModel {
    private int index;
    private Rectangle2D.Double bounds;

    public StaffSystemModel(int index, Rectangle2D.Double bounds) {
        this.index = index;
        this.bounds = bounds;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Rectangle2D.Double getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle2D.Double bounds) {
        this.bounds = bounds;
    }
}
