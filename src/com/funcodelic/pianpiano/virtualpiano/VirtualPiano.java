package com.funcodelic.pianpiano.virtualpiano;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import javax.sound.midi.*;

public class VirtualPiano extends JPanel {
    
    // Constants
    private static final int NUMBER_OF_NATURAL_KEYS = 52;
    private static final int NUMBER_OF_KEYS = 88;
    
    // List to hold the keys (Ivory and Ebony)
    private List<Key> keys;
    private List<IvoryKey> ivoryKeys;
    private List<EbonyKey> ebonyKeys;
    
    // Array to hold the note names for the keyboard
    private String[] keyboardKeys = new String[NUMBER_OF_KEYS];
    
    // Synthesizer and MIDI channels array to play the key's sound
    private Synthesizer synthesizer;
    private MidiChannel[] channels;

    
    // C'tor
    public VirtualPiano() {
        // Initialize the empty list for keys
        keys = new ArrayList<>();
        ivoryKeys = new ArrayList<>();
        ebonyKeys = new ArrayList<>();
        
        // Initialize keyboard keys array
        initializeKeyboardKeys();
        
        // Initialize the keys after the keyboard keys array has been initialized
        initializeKeys();
        
        // Add mouse listener for detecting key presses
        addMouseListener( new MouseAdapter() {
            @Override
            public void mousePressed( MouseEvent e ) {
                Point point = e.getPoint();
                detectKeyPress( point );
            }
        });
        
        // Initialize the synthesizer here to minimize delay when playing a key
        try {
            synthesizer = MidiSystem.getSynthesizer();
            synthesizer.open();
            channels = synthesizer.getChannels();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
    
    void playKey( Key key ) {
    	// The MIDI note number to play
    	final int midiNoteNumber = findMidiNoteNumber( key.pitch );
    	
    	new Thread(() -> {
            try {
            	key.play( true );  // Change color to blue
                channels[0].noteOn( midiNoteNumber, 800 );  // Play the note

                Thread.sleep( 2000 );  // Play for 2 seconds

                channels[0].noteOff( midiNoteNumber );  // Stop the note
                key.play( false );  // Revert color
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }).start();
    }
    
    private int findMidiNoteNumber( String pitch ) {
        // Calculate the MIDI note number
        for ( int i = 0; i < keyboardKeys.length; i++ ) {
            if ( pitch.equals( keyboardKeys[i] ) ) {
                return i + 21; // MIDI A0 starts at 21
            }
        }
        return -1;  // Return an invalid note number if not found
    }

    @Override
    protected void paintComponent( Graphics g ) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Draw the ivory keys first
        for ( IvoryKey ivoryKey : ivoryKeys ) {
        	ivoryKey.draw( g2 );
        }
        
        // Draw the ebony keys on top
        for ( EbonyKey ebonyKey : ebonyKeys ) {
        	ebonyKey.draw( g2 );
        }
    }
    
    private void detectKeyPress( Point point ) {
    	// Search for a hit on one of the ebony keys first, as they are on top
        for ( EbonyKey ebonyKey : ebonyKeys ) {
            if ( ebonyKey.contains( point ) ) {
                System.out.println( "Ebony: " + ebonyKey.pitch );
                playKey( ebonyKey );
                return;
            }
        }

        for ( IvoryKey ivoryKey : ivoryKeys ) {
            if ( ivoryKey.contains( point ) ) {
                System.out.println( "Ivory: " + ivoryKey.pitch );
                playKey( ivoryKey );
                return;
            }
        }
    }

    // Initialize the keys
    private void initializeKeys() {
    	// Begin with the origin of the VirtualPiano JPanel itself
    	double xVal = 0.0; // 0.0 within the context of the JPanel that is the Virtual Piano
    	final double yVal = 0.0; // 0.0 within the context of the JPanel that is the Virtual Piano
    	
    	// Keyboard key index, incremented once or twice within the outer for loop
    	// depending on whether an ebony key was also created for the pass within the loop
    	int keyboardKeyIndex = 0;
    	
    	// For all the natural keys...
    	for ( int i = 0; i < NUMBER_OF_NATURAL_KEYS; i++ ) {
    		//
    		// Initialize the ivory key
    		//
    		// Get the pitch from keyboardKeys (we know the first one is "A0")
    		String ivoryKeyPitch = keyboardKeys[keyboardKeyIndex];
    		
    		// Instantiate an ivory key
    		IvoryKey ivoryKey = new IvoryKey( xVal, yVal, ivoryKeyPitch );
    		
    		// Add the ivory key to the lists
    		keys.add( ivoryKey );
    		ivoryKeys.add( ivoryKey );
    		
    		//
    		// Initialize the ebony key if there is one
    		//
    		if ( isNextKeyEbony( ivoryKeyPitch ) ) {
    			
    			if ( i == NUMBER_OF_NATURAL_KEYS - 1 ) {
    				break;
    			}
    			
    			// Get the pitch for the ebony key with the incremented index
    			String ebonyKeyPitch = keyboardKeys[ ++keyboardKeyIndex ];
    			
    			// The xVal is the upper right corner of the preceding ivory key
    			double tempXVal = xVal + ivoryKey.keyRect.width;
    			
    			// Create the top center point for the ebony key
    			Point2D.Double topCenter = new Point2D.Double( tempXVal, yVal );
    			
    			// Instantiate the ebony key
    			EbonyKey ebonyKey = new EbonyKey( topCenter, ebonyKeyPitch );
    			
    			// Add the ebony key to the lists
    			keys.add( ebonyKey );
    			ebonyKeys.add( ebonyKey );
    		}
    		
    		// Increment the xVal to the following ivory keys upper left origin point
    		xVal += ivoryKey.keyRect.width;
    		
    		// Move on to the next pitch
    		keyboardKeyIndex++;
    	}
    }
    
    private boolean isNextKeyEbony( String ivoryPitchString ) {
        // Ebony keys on a piano are found after natural keys A, C, D, F, and G
        char firstChar = ivoryPitchString.charAt(0);
        return firstChar == 'A' || firstChar == 'C' || firstChar == 'D' || 
               firstChar == 'F' || firstChar == 'G';
    }

    // Initialize the keyboardKeys array
    private String[] initializeKeyboardKeys() {
        // Array of note names in an octave (excluding octave number)
        String[] noteNames = { "A", "A♯/B♭", "B", "C", "C♯/D♭", "D", "D♯/E♭", 
        					   "E", "F", "F♯/G♭", "G", "G♯/A♭" };

        // Start with octave 0 for A0 and B0
        int octave = 0;

        // Initialize index for the keyboard keys array
        int keyIndex = 0;

        // Loop through the keys and assign note names and octaves
        for ( int i = 0; i < NUMBER_OF_KEYS; i++ ) {
            // Assign the key name with its octave
            keyboardKeys[i] = noteNames[keyIndex] + octave;

            // Move to the next note in the octave
            keyIndex++;

            // When we reach the end of the B note (before C), increment the octave
            if ( noteNames[keyIndex - 1].equals( "B" ) ) {
                octave++;
            }

            // Reset the note name index after G♯/A♭ and continue with the next octave
            if ( keyIndex == noteNames.length ) {
                keyIndex = 0; // Reset for the next cycle through note names
            }
        }
        
        for ( int i = 0; i < keyboardKeys.length; i++ ) {
        	System.out.println( keyboardKeys[i] );
        }

        return keyboardKeys;
    }
    
    @Override
    public Dimension getPreferredSize() {
        // Adjust size as needed (this should be large enough for the piano keys)
        return new Dimension( (int)Key.IVORY_WIDTH * NUMBER_OF_NATURAL_KEYS, (int)Key.IVORY_HEIGHT );
        
    }
    
    
    //
    //	Key classes
    //
    // Abstract base class for a key
    private abstract class Key {
        protected Rectangle2D.Double keyRect;
        protected String pitch;
        protected Color fillColor;

        public static final double IVORY_WIDTH = 20.0;
        public static final double IVORY_HEIGHT = IVORY_WIDTH * 5;

        public Key( double x, double y, double width, double height, String pitch ) {
            this.keyRect = new Rectangle2D.Double( x, y, width, height );
            this.pitch = pitch;
        }
        
        public Key( Point2D.Double topCenter, double width, double height, String pitch ) {
            // Calculate the upper-left corner based on the topCenter point
            double x = topCenter.getX() - ( width / 2 );
            double y = topCenter.getY();
            this.keyRect = new Rectangle2D.Double( x, y, width, height );
            this.pitch = pitch;
        }

        public boolean contains( Point p ) {
            return keyRect.contains(p);
        }

        public abstract void draw( Graphics2D g2 );
        
        public abstract void play( boolean play );
    }

    // Private subclass for Ivory Key
    private class IvoryKey extends Key {
        public IvoryKey( double x, double y, String pitch ) {
            super( x, y, IVORY_WIDTH, IVORY_HEIGHT, pitch );
            
            fillColor = Color.WHITE;
        }

        @Override
        public void draw( Graphics2D g2 ) {
            g2.setColor( fillColor );
            g2.fill( keyRect );
            g2.setColor( Color.BLACK );
            g2.draw( keyRect );
        }
        
        @Override
        public void play( boolean play ) {
        	if ( play ) {
    	        fillColor = Color.BLUE;
    	    } else {
    	        fillColor = Color.WHITE;
    	    }
    	    repaint();
        }
    }

    // Private subclass for Ebony Key
    private class EbonyKey extends Key {
        public static final double EBONY_WIDTH = IVORY_WIDTH / 2;
        public static final double EBONY_HEIGHT = IVORY_WIDTH * 3;
        
        // Updated constructor in the EbonyKey class
        public EbonyKey( Point2D.Double topCenter, String pitch ) {
            super( topCenter, EBONY_WIDTH, EBONY_HEIGHT, pitch );
            
            fillColor = Color.BLACK;
        }

        @Override
        public void draw( Graphics2D g2 ) {
            g2.setColor( fillColor );
            g2.fill( keyRect );
            g2.setColor( Color.BLACK );
            g2.draw( keyRect );
        }
        
        @Override
        public void play( boolean play ) {
        	if ( play ) {
    	        fillColor = Color.BLUE;
    	    } else {
    	        fillColor = Color.BLACK;
    	    }
    	    repaint();
        }
    }
}
