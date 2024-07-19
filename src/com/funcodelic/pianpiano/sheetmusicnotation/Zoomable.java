package com.funcodelic.pianpiano.sheetmusicnotation;

//
//	Implemented by objects capable of zooming in and out
//
public interface Zoomable {
	
	void zoom(boolean zoomIn);
	void setScale(double scale);
	double getScale();
	
}