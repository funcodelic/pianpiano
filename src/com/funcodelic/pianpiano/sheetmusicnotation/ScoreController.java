package com.funcodelic.pianpiano.sheetmusicnotation;

import javax.swing.*;

//
//	This class represents the score.
//
//  It is the main handle to the score and the controller component
//  of MVC paradigm, and mediates between the score view and model.
//
public class ScoreController {
	
	// The model and view components
	private ScoreModel scoreModel;
	private ScoreView scoreView;
	
	// The score tree for navigation/selection
	private ScoreTree scoreTree;
	
	// The panel to inspect and configure the score
	private ScoreInspectorPanel scoreInspectorPanel;
	
	
	// C'tor
	public ScoreController() {
		// Instantiate this controller's associated model and view components
		scoreModel = new ScoreModel();
		scoreView = new ScoreView();
		
		// Initialize the score tree with this score
        scoreTree = new ScoreTree(this);
        
        // Configure the score's inspector panel
        scoreInspectorPanel = new ScoreInspectorPanel(this);
	}
	
	public JPanel getView() {
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
        scoreTree.updateRootName();
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
