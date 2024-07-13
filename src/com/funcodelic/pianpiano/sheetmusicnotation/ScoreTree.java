package com.funcodelic.pianpiano.sheetmusicnotation;

import javax.swing.JTree;
import javax.swing.tree.*;
import java.awt.Color;

//
//	This class encapsulates the score as a tree of sheet music entities
//
public class ScoreTree extends JTree {

	// The model and root node of the tree
	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode root;
	
	
	// C'tor
	public ScoreTree( ScoreController score ) {
		// Initialize the root node with the score
        root = new DefaultMutableTreeNode(score);
        treeModel = new DefaultTreeModel(root);
        setModel(treeModel);
        
        // Customize the tree cell renderer to change text color
 		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
 		renderer.setTextNonSelectionColor(Color.WHITE);
 		renderer.setTextSelectionColor(Color.WHITE);
 		renderer.setBackgroundNonSelectionColor(Color.BLACK);
 		renderer.setBackgroundSelectionColor(Color.BLUE);
 		setCellRenderer(renderer);
 		
 		// Set the tree's background color
 		setBackground(Color.BLACK);
 		
 		// Set the selection mode to SINGLE_TREE_SELECTION
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}
	
	// Update the root node
	public void updateRootName() {
        treeModel.nodeChanged(root);
    }
	
	// Method to set a new score
	public void setScore(ScoreController theScore) {
		root.setUserObject(theScore);
		root.removeAllChildren();
		treeModel.reload();
	}
}
