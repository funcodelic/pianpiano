package com.funcodelic.pianpiano.sheetmusicnotation;

public interface SheetMusicNode extends Inspectable, PageInterfaceHandler {

	void select();
	void deselect();
	String toString();
	
}
