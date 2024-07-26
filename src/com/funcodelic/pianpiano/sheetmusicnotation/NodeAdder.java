package com.funcodelic.pianpiano.sheetmusicnotation;

//
//	TODO: Rework this
//	An interface used as a a design crutch for now
//	Used to keep classes package private and pass new nodes
//	through the inspector panel to the Score Builder GUI
//
public interface NodeAdder {
    void addNodeAtIndex(Object nodeObject, int index);
}
