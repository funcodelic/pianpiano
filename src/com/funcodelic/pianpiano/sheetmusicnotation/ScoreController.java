package com.funcodelic.pianpiano.sheetmusicnotation;

import java.util.*;
import javax.swing.*;

//
//	This class represents the score.
//
//  It is the main handle to the score and the controller component
//  of MVC paradigm, and mediates between the score view and model.
//
public class ScoreController implements PanelEditable, Inspectable {
	
	// The model and view components
	private ScoreModel scoreModel;
	private ScoreView scoreView;
	
	// The score tree for navigation/selection
	private ScoreTree scoreTree;
	
	// The panel to inspect and configure the score
	private ScoreInspectorPanel scoreInspectorPanel;
	
	// The pages managed by the score
	private List<PageController> pages;
	
	
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
        
        // Update the number of pages
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
    }
	
	public JPanel getView() {
		return scoreView;
	}
	
	public JComponent getInspectorView() {
    	return scoreInspectorPanel;
    }
	
	public JPanel getEditorPanel() {
		return scoreView;
	}
	
	public ScoreTree getTree() {
        return scoreTree;
    }
	
	public JPanel getInspectorPanel() {
		return scoreInspectorPanel;
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
	
}
