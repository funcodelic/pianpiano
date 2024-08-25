package com.funcodelic.pianpiano.gui;

import com.funcodelic.pianpiano.sheetmusicnotation.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import java.awt.*;

//
//	This class provides the frame for the sheet music editor GUI.
//	It is the public interface class to the GUI package.
//
// TODO: FIX THIS: Remove the NoteAdder business
public class ScoreBuilderGUI extends JFrame implements NodeAdder {
	// The menu bar
	PianPianoGUIMenuBar menuBar = new PianPianoGUIMenuBar(this);
	
	// The panels
	PianPianoToolbarPanel toolbarPanel;
	PianPianoUtilityPanel utilityPanel;
	PianPianoEditorPanel editorPanel;
	PianPianoNavigatorPanel navigatorPanel;
	
	// The piano score being built
	ScoreController theScore;
	
	// The selected sheet music object and the selected node
	Object selectedObject;
	DefaultMutableTreeNode selectedNode;
	
	// The currently and previously selected sheet music node
	SheetMusicNode selectedSheetMusicNode;
	SheetMusicNode lastSelectedSheetMusicNode;
    
    // Track the page number
    int currentPageNumber;
	
	
	// C'tor
	public ScoreBuilderGUI() {
		// Install the menu bar
		setJMenuBar( menuBar );
		
		// Instantiate the panels
		toolbarPanel = new PianPianoToolbarPanel();
		utilityPanel = new PianPianoUtilityPanel(this); //TODO: Fix this coupling
		editorPanel = new PianPianoEditorPanel();
		navigatorPanel = new PianPianoNavigatorPanel();
		
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
		
		// Prompt the user to create a new score in the tool bar panel
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
		//	Display the new score's panels to build it
		//
		// Pass the score tree to the navigator panel for display
		ScoreTree scoreTree = theScore.getTree();
		navigatorPanel.setView( scoreTree );
		editorPanel.setView( theScore.getPanelEditableView() );
		utilityPanel.setView( theScore.getInspectorView() );
		toolbarPanel.setView( new JLabel("Name the score and add sheet music 🎼") );
		
		// Set the selected sheet music node to the score
		selectedSheetMusicNode = (SheetMusicNode) theScore;
		
		// Show the navigation and utility panels
		showToolbarPanel(true);
		showNavigationPanel(true);
		showUtilityPanel(true);

		// Reset the current page number
		currentPageNumber = theScore.SCORE_PAGE_NUMBER;
		
		// Sore the score's node as the last selected node
		lastSelectedSheetMusicNode = selectedSheetMusicNode;
		
		//
		// 	Listen for selections of the score's many sheet music notation entities
		//	and update the GUI views to let the user inspect and configure them
		//
		scoreTree.addTreeSelectionListener( new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// Get the selected object and sheet music node and select it
				TreePath path = e.getPath();
				selectedNode = (DefaultMutableTreeNode)path.getLastPathComponent();
				selectedObject = selectedNode.getUserObject();
				selectedSheetMusicNode = (SheetMusicNode)selectedObject;
				
				if ( selectedSheetMusicNode != lastSelectedSheetMusicNode ) {
					newObjectSelected( path );
				}
				
				// Expand all nodes of the tree
				theScore.expandTree();
				
				// Refresh the editor panel
				editorPanel.revalidate();
				editorPanel.repaint();
			}
		});//end addTreeSelectionListener()
		
		// Set the focus in the utility panel
		utilityPanel.focus();
		
	}//end createNewScore()
	
	private void newObjectSelected(TreePath path) {
		// Deselect the previous node and select the new one
		lastSelectedSheetMusicNode.deselect();
		selectedSheetMusicNode.select();
		
		//
		//	Turn to the page of the newly selected object
		//
		int lastPageNumber = currentPageNumber;
		currentPageNumber = theScore.getPageNumberForPath(path);
		if ( currentPageNumber != lastPageNumber ) {
			turnThePage(currentPageNumber);
		}
		
		// Set the page interface event handling strategy for the selected object
		theScore.setPageInterfaceForPage(selectedSheetMusicNode.getPageInterface(), currentPageNumber);
		
		// Update the inspector panel
		utilityPanel.setView( selectedSheetMusicNode.getInspectorView() );
		
		// Update the tool bar
		updateToolbar(path);
		
		// Set the last selected sheet music node to the new currently selected node
		lastSelectedSheetMusicNode = selectedSheetMusicNode;
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
		theScore.zoomIn( selectedNode, zoomIn );
	}
	
	private void turnThePage(int currentPage) {
		editorPanel.setView( theScore.getEditorPanelForPage(currentPage));
	}

	@Override
	public void addNodeAtIndex(Object nodeObject, int index) {
		theScore.getTree().addObjectToParentNode(nodeObject, selectedNode, index);
	}
	
	private void updateToolbar(TreePath path) {
		// Build the full path string and display it in the tool bar
        StringBuilder fullPath = new StringBuilder();
        Object[] nodes = path.getPath();
        for (int i = 0; i < nodes.length; i++) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) nodes[i];
            if (i > 0) {
                fullPath.append("  >  ");
            }
            fullPath.append(node.getUserObject().toString());
        }
        toolbarPanel.setView( new JLabel( fullPath.toString() ) );
	}
	
	@Override
	public void setPageInterface(PageInterface pageInterface ) {
		theScore.setPageInterfaceForPage(pageInterface, currentPageNumber);
		
		editorPanel.revalidate();
		editorPanel.repaint();
	}
	
	@Override
	public String toString() {
		return "Score Builder GUI Frame";
	}
	
}
