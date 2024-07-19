package com.funcodelic.pianpiano.gui;

import com.funcodelic.pianpiano.sheetmusicnotation.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.*;


//
//	This class provides the frame for the sheet music editor GUI.
//
//	It is the public interface class to the GUI package.
//
public class ScoreBuilderGUI extends JFrame {
	
	// The menu bar
	PianPianoGUIMenuBar menuBar = new PianPianoGUIMenuBar(this);
	
	// The panels
	PianPianoToolbarPanel toolbarPanel = new PianPianoToolbarPanel();
	PianPianoUtilityPanel utilityPanel = new PianPianoUtilityPanel();
	PianPianoEditorPanel editorPanel = new PianPianoEditorPanel();
	PianPianoNavigatorPanel navigatorPanel = new PianPianoNavigatorPanel();
	
	// The piano score being built
	ScoreController theScore;
	
	// The selected sheet music object
	Object selectedObject;
	
	
	// C'tor
	public ScoreBuilderGUI() {
		// Install the menu bar
		setJMenuBar( menuBar );
		
		// Set their preferred sizes
		toolbarPanel.setPreferredSize( new Dimension( 1400, 50 ) );
		editorPanel.setPreferredSize( new Dimension( 900, 600 ) );
		navigatorPanel.setPreferredSize( new Dimension( 250, 600 ) );
		utilityPanel.setPreferredSize( new Dimension( 250, 600 ) );
		
		// Add the panels to the frame
		add( toolbarPanel, BorderLayout.NORTH );
		add( editorPanel, BorderLayout.CENTER );
		add( navigatorPanel, BorderLayout.WEST );
		add( utilityPanel, BorderLayout.EAST );
	}
	
	public void go() {
		// Configure the frame and display it
		setTitle( "Pian Piano Sheet Music Builder" );
		
		// Prompt the user to create a new score in the toolbar panel
		JLabel promptLabel = new JLabel("Cmd + B to build a new score");
		toolbarPanel.setView(promptLabel);
				
		setLocation( 0, 0 );
		pack();
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setVisible( true );
	}
	
	public void createNewScore() {
		// Instantiate a new score
		theScore = new ScoreController();
		
		//
		// 	Get the score's tree and listen for selections
		//
		//	When a node in the tree is selected, display its view and inspector
		//	and display the tree's path to the object in the toolbar
		//
		ScoreTree scoreTree = theScore.getTree();
		
		scoreTree.addTreeSelectionListener( new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				TreePath path = e.getPath();
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)path.getLastPathComponent();
				selectedObject = selectedNode.getUserObject();
				
				// Display panel-editable views in the editor panel
				if (selectedObject instanceof PanelEditable) {
				    PanelEditable editable = (PanelEditable) selectedObject;
				    editorPanel.setView( editable.getView() );
				}
				
				// Display the inspector panel in the utility panel
				if (selectedObject instanceof Inspectable) {
					Inspectable inspectable = (Inspectable) selectedObject;
					utilityPanel.setView( inspectable.getInspectorView() );
				}
				
				// Build the full path string
		        StringBuilder fullPath = new StringBuilder();
		        Object[] nodes = path.getPath();
		        for (int i = 0; i < nodes.length; i++) {
		            DefaultMutableTreeNode node = (DefaultMutableTreeNode) nodes[i];
		            if (i > 0) {
		                fullPath.append("  >  ");
		            }
		            fullPath.append(node.getUserObject().toString());
		        }
		        
		        // Display the full path in the toolbar
		        toolbarPanel.setView( new JLabel( fullPath.toString() ) );
			}
		});
		
		//
		//	Display the score's panels so the user can build it further
		//
		// Pass the score tree to the navigator panel for display
		navigatorPanel.setView( scoreTree );
		
		// Install the score's view in the editor panel
		editorPanel.setView( theScore.getView() );
		
		// Install the score's inspector panel
		utilityPanel.setView( theScore.getInspectorPanel() );
		
		// Update the toolbar panel to display the score's path
		toolbarPanel.setView( new JLabel("Add pages to the score") );
		
		// Show the navigation and utility panels
		showToolbarPanel(true);
		showNavigationPanel(true);
		showUtilityPanel(true);
	}
	
	public void showToolbarPanel( boolean showPanel ) {
		toolbarPanel.setVisible( showPanel );
	}
	
	public void showNavigationPanel( boolean showPanel ) {
		navigatorPanel.setVisible( showPanel );
	}
	
	public void showUtilityPanel( boolean showPanel ) {
		utilityPanel.setVisible( showPanel );
	}
	
	public void zoomIn( boolean zoomIn ) { // false = zoom out
		if ( selectedObject instanceof Zoomable ) {
			Zoomable zoomable = (Zoomable) selectedObject;
			zoomable.zoom(zoomIn);
		}
	}
	
}
