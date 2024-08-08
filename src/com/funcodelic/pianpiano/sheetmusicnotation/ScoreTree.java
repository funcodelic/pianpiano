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
        
        // Call the superclass constructor with the tree model
        super.setModel(treeModel);
        
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
	public void refresh() {
        treeModel.reload();
    }
	
	// Add a page node
	public void addPageNode(Object pageObj, int pageIndex) {
		// Create a new page node and add it to the root
		MutableTreeNode pageNode = new DefaultMutableTreeNode(pageObj);
		root.insert(pageNode, pageIndex);
		treeModel.reload();
	}
	
	// Add a node to a parent node
	public void addObjectToParentNode(Object childObject, DefaultMutableTreeNode parentNode, int index) {
		// Create a new node with the child object and insert it into the parent node of the tree
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(childObject);
		getModel().insertNodeInto(childNode, parentNode, index);
		refresh();
		
		// Expand the selected node to show the newly added child
        TreePath parentPath = new TreePath(parentNode.getPath());
        expandPath(parentPath);
        
        // Create a path to the new node
        TreePath childNodePath = parentPath.pathByAddingChild(childNode);
        
        // Select the new node
        //setSelectionPath(childNodePath);
        
        // Make sure the new node is visible
        //scrollPathToVisible(childNodePath);
        
        // Expand the tree
        expandAllNodes();
	}
	
	// Method to set a new score
	public void setScore(ScoreController theScore) {
		root.setUserObject(theScore);
		root.removeAllChildren();
		treeModel.reload();
	}
	
	// Access the model
	public DefaultTreeModel getModel() {
		return treeModel;
	}
	
	// Method to expand all nodes starting from the root
    public void expandAllNodes() {
        expandAllNodes(root);
    }

    private void expandAllNodes(DefaultMutableTreeNode node) {
        // Expand the current node
        this.expandPath(new TreePath(node.getPath()));

        // Traverse children
        for (int i = 0; i < node.getChildCount(); i++) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(i);
            expandAllNodes(childNode);
        }
    }
}
