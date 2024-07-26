package com.funcodelic.pianpiano.sheetmusicnotation;

import java.util.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

//
//	This class represents the score.
//
//  It is the main handle to the score and the controller component
//  of MVC paradigm, and mediates between the score view and model.
//
public class ScoreController implements PanelEditable, SheetMusicNode {
	
	// The model and view components
	private ScoreModel scoreModel;
	private ScoreView scoreView;
	
	// The score tree for navigation/selection
	private ScoreTree scoreTree;
	
	// The panel to inspect and configure the score
	private ScoreInspectorPanel scoreInspectorPanel;
	
	// The pages managed by the score
	private List<PageController> pages;
	
	// The score's page number as opposed to sheet music page numbers
	public final int SCORE_PAGE_NUMBER = -1;
	
	
	// C'tor
	public ScoreController() {
		// Instantiate this controller's associated model and view components
		scoreModel = new ScoreModel();
		scoreView = new ScoreView();
		
		// Initialize the score tree with this score
        scoreTree = new ScoreTree(this);
        
        // Configure the score's inspector panel
        scoreInspectorPanel = new ScoreInspectorPanel(this);
        
        // Initialize the list of pages
        pages = new ArrayList<>();
	}
    
    public PageController getPage(int index) {
        if (index >= 0 && index < pages.size()) {
            return pages.get(index);
        } else {
            throw new IndexOutOfBoundsException("Accessing invalid page index");
        }
    }
    
    public int getNumberOfPages() {
    	return pages == null ? 0 : pages.size();
    }
    
    public void addPage(String fileImagePath) {
    	// Determine the page number
    	int pageNumber = pages.size() + 1;
    	
    	// Create a new page
    	PageController newPage = new PageController(fileImagePath, pageNumber);
    	
    	// Add the new page to the list
        pages.add(newPage);
        
        // Update the number of pages in the view
        String numPages = Integer.toString(pages.size());
        scoreView.setNumPages(numPages);
        
        // Add a node for the page to the score tree's root
        int pageIndex = pageNumber - 1;
        scoreTree.addPageNode(newPage, pageIndex);
        
        // Refresh the inspector panel
        scoreInspectorPanel.refresh();
    }
    
    public void removePage(int index) {
        if (index >= 0 && index < pages.size()) {
            pages.remove(index);
        } else {
            throw new IndexOutOfBoundsException("Removing invalid page index");
        }
        
        // TODO: Remove the page node from the score tree
        
        // Update the number of pages in the view
        String numPages = Integer.toString(pages.size());
        scoreView.setNumPages(numPages);
        
        // Refresh the inspector panel
        scoreInspectorPanel.refresh();
    }
	
    @Override
	public JPanel getPanelEditableView() {
		return scoreView;
	}
	
	@Override
	public JComponent getInspectorView() {
    	return scoreInspectorPanel;
    }
	
	public ScoreTree getTree() {
        return scoreTree;
    }
	
    public void setScoreName(String name) {
    	scoreModel.setName(name);
    	scoreView.setName(name);
        scoreTree.refresh();
        scoreInspectorPanel.refresh();
    }
    
    public void setScoreComposer(String composer) {
    	scoreModel.setComposer(composer);
    	scoreView.setComposer(composer);
    }
	
	@Override
	public String toString() {
		return scoreModel.getName();
	}
	
	@Override
	public void select() {
		//System.out.println(toString() + " selected");
	}
	
	@Override
	public void deselect() {
		//System.out.println(toString() + " deselected");
	}
	
	@Override
	public PageInterface getPageInterface() {
    	return null;
    }
	
	public PageController findPage(TreePath path) {
	    Object[] nodes = path.getPath();
	    for (Object node : nodes) {
	        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
	        Object userObject = treeNode.getUserObject();
	        if (userObject instanceof PageController) {
	            return (PageController) userObject;
	        }
	    }
	    return null;
	}
	
	// Return the page number or the score's page number if no page was found
	public int getPageNumberForPath(TreePath path) {
		PageController page = findPage(path);
	    return (page != null) ? page.getPageNumber() : SCORE_PAGE_NUMBER;
	}
	
	// Set the page interface for the specified page
	public void setPageInterfaceForPage(PageInterface pageInterface, int pageNumber) {
		int pageIndex = pageNumber -1;
		
		if ( pageIndex >= 0 && pages.size() > 0 ) {
			pages.get(pageIndex).setPageInterface(pageInterface);
		}
	}
	
	public void expandTree() {
		scoreTree.expandAllNodes();
	}
	
	public JComponent getEditorPanelForPage(int pageNumber) {
		JComponent pageNotFound = new JLabel("Page not found");
		
		if ( pageNumber == -1 ) {
			// Return the panel for the score itself
			return getPanelEditableView();
		}
		else if ( pageNumber > 0 && pageNumber <= pages.size() ) {
			int pageIndex = pageNumber -1;
			return pages.get(pageIndex).getPanelEditableView();
		}
		else {
			return pageNotFound;
		}
	}
	
}
