package com.funcodelic.pianpiano.gui;

import javax.swing.*;
import java.awt.event.*;

//
//	This class holds the menu bar for the Editor GUI.
//
//	It configures the menus and submenus and their mnemonics and accelerators.
//
class PianPianoGUIMenuBar extends JMenuBar {
	
	private ScoreBuilderGUI theGui;

    public PianPianoGUIMenuBar( ScoreBuilderGUI gui ) {
    	// Store the GUI
    	this.theGui = gui;
    	
        // Create the menus
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu viewMenu = new JMenu("View");
        JMenu windowMenu = new JMenu("Window");
        JMenu helpMenu = new JMenu("Help");

        // Add menus to the menu bar
        this.add(fileMenu);
        this.add(editMenu);
        this.add(viewMenu);
        this.add(windowMenu);
        this.add(helpMenu);

        // Get the platform-specific modifier used to set up key accelerators
        int modifier = System.getProperty("os.name").toLowerCase().contains("mac") ? 
    			ActionEvent.META_MASK : ActionEvent.CTRL_MASK;
        
        //
        // Configure the File menu
        //
        // Set the mnemonic for the File menu to 'F'
        fileMenu.setMnemonic(KeyEvent.VK_F);
        
        //
        // Create a menu item and accelerator for creating a new score to build
        //
        JMenuItem newScoreMenuItem = new JMenuItem("Build New Score");
        newScoreMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, modifier));
        newScoreMenuItem.addActionListener(e -> {
        	// Create a new score
        	theGui.createNewScore();
        });
        
        
        //
        // Configure the File Menu
        //
        fileMenu.add(newScoreMenuItem);
        
        
        //
        // Create a quit item and configure its accelerator and action listener
        //
 		JMenuItem quitMenuItem = new JMenuItem("Quit");
 		
 		// Set the mnemonic for the File menu to 'F'
 		quitMenuItem.setMnemonic(KeyEvent.VK_Q);
 		
 		// Set the accelerator to Cmd+Q (Mac) or Ctrl+Q (Windows/Linux)
        quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, modifier));
 		
 		// Add action listener to quit the application
        quitMenuItem.addActionListener(e -> System.exit(0));
        
        // Add the quit menu item to the File menu
        fileMenu.add( quitMenuItem );
        
        
        //
        // Configure the Edit menu
        //
        editMenu.setMnemonic(KeyEvent.VK_E);
        editMenu.add(new JMenuItem("Undo"));
        editMenu.add(new JMenuItem("Redo"));
        editMenu.addSeparator();
        editMenu.add(new JMenuItem("Cut"));
        editMenu.add(new JMenuItem("Copy"));
        editMenu.add(new JMenuItem("Paste"));
        
        
        //
        // Configure the View menu
        //
        viewMenu.setMnemonic(KeyEvent.VK_V);
        
        // Create menu items to show/hide panels other than the editor panel
        JCheckBoxMenuItem showNavItem = new JCheckBoxMenuItem("Show Navigator");
        showNavItem.setSelected(true);
        JCheckBoxMenuItem showUtilItem = new JCheckBoxMenuItem("Show Inspector");
        showUtilItem.setSelected(true);
        JCheckBoxMenuItem showToolBarItem = new JCheckBoxMenuItem("Show Toolbar");
        showToolBarItem.setSelected(true);
        
        // Set mnemonics and accelerators
        showNavItem.setMnemonic(KeyEvent.VK_N);
        showNavItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, modifier));
        showUtilItem.setMnemonic(KeyEvent.VK_U);
        showUtilItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, modifier));
        showToolBarItem.setMnemonic(KeyEvent.VK_T);
        showToolBarItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, modifier));
        
        // Add action listeners to toggle visibility
        showNavItem.addActionListener(e -> {
            theGui.showNavigationPanel( showNavItem.isSelected() );
            theGui.revalidate();
            theGui.repaint();
        });

        showUtilItem.addActionListener(e -> {
            //utilityPanel.setVisible(showUtilItem.isSelected());
        	theGui.showUtilityPanel( showUtilItem.isSelected() );
            theGui.revalidate();
            theGui.repaint();
        });

        showToolBarItem.addActionListener(e -> {
            //toolBar.setVisible(showToolBarItem.isSelected());
        	theGui.showToolbarPanel( showToolBarItem.isSelected() );
            theGui.revalidate();
            theGui.repaint();
        });
        
        // Create zoom in/out items and configure their accelerators and action listeners
        JMenuItem zoomInMenuItem = new JMenuItem("Zoom In");
        JMenuItem zoomOutMenuItem = new JMenuItem("Zoom Out");

        // Set the accelerator (the equals key is the plus key)
        zoomInMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, modifier));
        zoomOutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, modifier));
        
        // Add action listeners for zooming  functionality
        zoomInMenuItem.addActionListener(e -> zoomIn(true));
        zoomOutMenuItem.addActionListener(e -> zoomIn(false));

        // Add the zoom in menu item to the View menu
        viewMenu.addSeparator();
        viewMenu.add(zoomInMenuItem);
        viewMenu.add(zoomOutMenuItem);
        
        // Add the menu items to View menu
        viewMenu.addSeparator();
        viewMenu.add(showNavItem);
        viewMenu.add(showUtilItem);
        viewMenu.add(showToolBarItem);

        
        //
        // Configure the Window menu
        //
        windowMenu.setMnemonic(KeyEvent.VK_W);
        windowMenu.add( new JMenuItem("Close") );
        
        
        //
        // Configure the Help menu
        //
        helpMenu.setMnemonic(KeyEvent.VK_H);
        helpMenu.add(new JMenuItem("Documentation"));
        helpMenu.add(new JMenuItem("About"));
    }
    
    private void zoomIn( boolean zoomIn ) { // false = zoom out
    	theGui.zoomIn( zoomIn );
    }
    
}
