package com.funcodelic.pianpiano.gui;

import javax.swing.*;
import java.awt.*;

import com.funcodelic.pianpiano.sheetmusicnotation.*;

//
//	This class provides the frame for the sheet music editor GUI.
//
//	It is the public interface class to the GUI package.
//
public class PianPianoEditorGUI extends JFrame {
	
	// The menu bar
	PianPianoGUIMenuBar menuBar = new PianPianoGUIMenuBar(this);
	
	// The panels
	JPanel toolbarPanel 	= new JPanel(); // north
	JPanel navigationPanel 	= new JPanel(); // west
	JPanel utilityPanel		= new JPanel(); // east
	PianPianoEditorPanel editorPanel = new PianPianoEditorPanel(); // center
	
	
	public PianPianoEditorGUI() {
		// Install the menu bar
		setJMenuBar( menuBar );
		
		// Name the panels
		toolbarPanel.setName( "Toolbar" );
		editorPanel.setName( "Editor" );
		navigationPanel.setName( "Navigator" );
		utilityPanel.setName( "Utility" );
		
		// Set their preferred sizes
		toolbarPanel.setPreferredSize( new Dimension( 1400, 50 ) );
		editorPanel.setPreferredSize( new Dimension( 900, 600 ) );
		navigationPanel.setPreferredSize( new Dimension( 250, 600 ) );
		utilityPanel.setPreferredSize( new Dimension( 250, 600 ) );
		
		// Set their background colors to delineate them
		toolbarPanel.setBackground( Color.LIGHT_GRAY );
		editorPanel.setBackground( Color.DARK_GRAY );
		navigationPanel.setBackground( Color.BLACK );
		utilityPanel.setBackground( Color.GRAY );
		
		// Add the panels to the frame
		add( toolbarPanel, BorderLayout.NORTH );
		add( editorPanel, BorderLayout.CENTER );
		add( navigationPanel, BorderLayout.WEST );
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
	
	public void showToolbarPanel( boolean showPanel ) {
		toolbarPanel.setVisible( showPanel );
	}
	
	public void showNavigationPanel( boolean showPanel ) {
		navigationPanel.setVisible( showPanel );
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
