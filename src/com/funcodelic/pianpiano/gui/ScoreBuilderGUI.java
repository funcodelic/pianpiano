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
	JPanel toolbarPanel = new JPanel(); // north
	JPanel utilityPanel	= new JPanel(); // east
	PianPianoEditorPanel editorPanel = new PianPianoEditorPanel(); // center
	PianPianoNavigatorPanel navigatorPanel = new PianPianoNavigatorPanel(); // west
	
	// The piano score being built
	ScoreController theScore;
	
	
	// C'tor
	public ScoreBuilderGUI() {
		// Install the menu bar
		setJMenuBar( menuBar );
		
		// Name the panels
		toolbarPanel.setName( "Toolbar" );
		editorPanel.setName( "Editor" );
		navigatorPanel.setName( "Navigator" );
		utilityPanel.setName( "Utility" );
		
		// Set the layout managers
		navigatorPanel.setLayout( new BorderLayout() );
		utilityPanel.setLayout( new BorderLayout() );
		
		// Set their preferred sizes
		toolbarPanel.setPreferredSize( new Dimension( 1400, 50 ) );
		editorPanel.setPreferredSize( new Dimension( 900, 600 ) );
		navigatorPanel.setPreferredSize( new Dimension( 250, 600 ) );
		utilityPanel.setPreferredSize( new Dimension( 250, 600 ) );
		
		// Set their background colors to delineate them
		toolbarPanel.setBackground( Color.LIGHT_GRAY );
		editorPanel.setBackground( Color.DARK_GRAY );
		navigatorPanel.setBackground( Color.BLACK );
		utilityPanel.setBackground( Color.GRAY );
		
		// Add the panels to the frame
		add( toolbarPanel, BorderLayout.NORTH );
		add( editorPanel, BorderLayout.CENTER );
		add( navigatorPanel, BorderLayout.WEST );
		add( utilityPanel, BorderLayout.EAST );
	}
	
	public void go() {
		// Create the page view and add it to the editor panel
		String path = "/Users/troymulder/java-workspace/Pian Piano/Moonlight.PNG";
		PageView pageView = new PageView(path);
		editorPanel.setPageView(pageView);
		
		// Configure the frame and display it
		setTitle( "Pian Piano Sheet Music Builder" );
		setLocation( 0, 0 );
		pack();
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setVisible( true );
	}
	
	public void createNewScore() {
		// Instantiate a new score
		theScore = new ScoreController();
		
		// Get the score's tree and listen for selections
		ScoreTree scoreTree = theScore.getTree();
		
		scoreTree.addTreeSelectionListener( new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				TreePath path = e.getPath();
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)path.getLastPathComponent();
				Object userObject = selectedNode.getUserObject();
				System.out.println("\nSelected node: " + userObject.toString());
				
				if (userObject instanceof ScoreController) {
					ScoreController selectedScore = (ScoreController) userObject;
					// Do something with the selected score
					System.out.println("Selected score: " + selectedScore.toString());
              }
			}
		});
		
		// Pass the score tree to the navigator panel for display
		navigatorPanel.removeAll();
		navigatorPanel.setScoreTree(scoreTree);
		
		// Install the score's view in the editor panel
		editorPanel.removeAll();
		editorPanel.add( theScore.getView() );
		editorPanel.revalidate();
		editorPanel.repaint();
		
		// Install the score's inspector panel
		utilityPanel.removeAll();
		utilityPanel.add(theScore.getInspectorPanel(), BorderLayout.NORTH);
		utilityPanel.revalidate();
		utilityPanel.repaint();
		
		// Show the navigation and utility panels
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
		editorPanel.zoom( zoomIn );
	}
	
	public void printViewportInfo() {
		editorPanel.printViewportInfo();
	}
}
