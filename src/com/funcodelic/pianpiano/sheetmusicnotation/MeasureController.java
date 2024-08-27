package com.funcodelic.pianpiano.sheetmusicnotation;

import javax.sound.midi.*;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

//
//	The Measure Controller class of the MVC paradigm
//
class MeasureController implements SheetMusicNode {
	
	// The model and view
	MeasureModel model;
	MeasureView  view;
	
	// Inspector panel
	MeasureInspectorPanel inspectorPanel;
	
	// The page interface adapter
	PageInterface pageInterface;
	
	// The staves used for note placement and pitch assignment
	StaveController upperStave;
	StaveController lowerStave;
	
	// The notes of the measure
	List<NoteController> notes;
	
	// The currently selected note
	NoteController selectedNote;
	
	// The concrete Page Interface to place the selected note
	PageInterface placeNoteInterface;
	
	// The scale
	double scale;
	
	
	// C'tor
	MeasureController( 	int index, 
						Rectangle2D.Double bounds, 
						double scale, 
						StaveController upperStave, 
						StaveController lowerStave ) {
		// Initialize the model
		model = new MeasureModel( index, bounds, scale );
		
		// Initialize the view
		view = new MeasureView( model.getRectangle(), upperStave.getView(), lowerStave.getView() );
		
		// Store the staves
		this.upperStave = upperStave;
		this.lowerStave = lowerStave;
		
		// Initialize the notes list
		notes = new ArrayList<>();
		
		// Initialize the interface for adjusting the measure
		pageInterface = new SlideMeasure( this );
		
		// Initialize the interface for placing notes
		placeNoteInterface = new PlaceNote( this );
		
		// Initialize the inspector panel
		inspectorPanel = new MeasureInspectorPanel( this );
	}
	
	Rectangle2D.Double getBounds() {
		return model.getRectangle().getRectangle();
	}
	
	NoteController createNote( boolean isUpperStaff ) {
		// Get centerX and centerY
        int centerX = (int) model.getRectangle().getRectangle().getCenterX();
        int centerY = (int) model.getRectangle().getRectangle().getCenterY();

        // Create a new Point using the center coordinates
        Point noteCenter = new Point( centerX, centerY );
        
        // Get the vertical spacing between staff lines from the corresponding stave
        double vGap = isUpperStaff ? upperStave.getVerticalSpacing() : lowerStave.getVerticalSpacing();
        double noteDiameter = 0.9 * vGap;
        
        // Get the scale
        double scale = getScale();
        
        // Create the note and assign it to the selected note
        selectedNote = new NoteController( isUpperStaff, noteCenter, noteDiameter, scale );
        selectedNote.setSelected( true );
        
        // Add the note to the view for display
        view.addNoteToList( selectedNote.getView() );
        
        return selectedNote;
	}
	
	int getNumNotes() {
		return notes.size();
	}
	
	void placeNote( Point p ) {
		// Scale the point's coordinates to the logical dimensions of the measure
		Point scaledPoint = getScaledPoint( p );
		
		if ( selectedNote != null && getBounds().contains( scaledPoint ) ) {
			double nearestYVal;
			// TODO: Determine the nearest half line to place the note
			if ( selectedNote.isUpperStaff() ) {
				nearestYVal = upperStave.getNearestYVal( scaledPoint );
			}
			else {
				nearestYVal = lowerStave.getNearestYVal( scaledPoint );
			}
			
			scaledPoint.y = (int)nearestYVal;
			selectedNote.setNoteLocation( scaledPoint );
			
			// Deselect the note
			selectedNote.setSelected( false );
			
			// Add the note to the list
			notes.add( selectedNote );
			
			// Determine the note's stave
			StaveController stave = selectedNote.isUpperStaff() ? upperStave : lowerStave;
			
			// Get the MIDI key for the note
			int midiKey = stave.getMidiKeyForNote( selectedNote );
			
			// Set the note's pitch
			int keyIndex = stave.getKeyIndex( selectedNote );
			String pitch = stave.getPitch( keyIndex );
			
			System.out.println( "note has key index " + keyIndex + " and pitch " + pitch );
			
			// Play the note
			playNote( midiKey );
		}
	}
	
	// Method to play a single MIDI note (Middle C as a quarter note)
    public void playNote( int midiKey ) {
    	
    	// Constant for Middle C (MIDI note number 60)
        //final int MIDDLE_C = 60;
        
        // Quarter note duration in milliseconds (assuming 120 bpm)
        final int QUARTER_NOTE_DURATION = 3000;
        
        // Control Change number for sustain pedal
        final int SUSTAIN_PEDAL = 64;
        
        try {
            // Get and open the synthesizer
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();

            // Get the default channel from the synthesizer (usually channel 0)
            MidiChannel[] channels = synth.getChannels();
            MidiChannel channel = channels[0];
            
            // Press the sustain pedal (CC 64 with value 127)
            channel.controlChange(SUSTAIN_PEDAL, 127);
            
            // Print the note being played for debugging
            System.out.println("Playing MIDI Key: " + midiKey);

            // Play the note (Middle C) with velocity (volume) of 80
            channel.noteOn(midiKey, 80);

            // Sleep for the duration of a quarter note
            Thread.sleep(QUARTER_NOTE_DURATION);

            // Turn off the note after the duration
            channel.noteOff(midiKey);

            // Close the synthesizer
            synth.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	void previewNotePlacement( Point p ) {
		// Scale the point's coordinates to the logical dimensions of the measure
		Point scaledPoint = getScaledPoint( p );
		
		if ( selectedNote != null && selectedNote.isSelected() && getBounds().contains( scaledPoint ) ) {
			double nearestYVal;

			if ( selectedNote.isUpperStaff() ) {
				nearestYVal = upperStave.getNearestYVal( scaledPoint );
			}
			else {
				nearestYVal = lowerStave.getNearestYVal( scaledPoint );
			}
			
			scaledPoint.y = (int)nearestYVal;
			selectedNote.setNoteLocation( scaledPoint );
		}
	}
	
	MeasureModel getModel() {
		return model;
	}
	
	MeasureView getView() {
		return view;
	}
	
    public void setScale( double scale ) {
    	// Propagate the scale to the model
    	model.setScale( scale );
        
        // Propagate the scale to the notes
        for ( NoteController note : notes ) {
        	note.setScale( scale );
        }
    }
    
    public double getScale() {
    	return model.getScale();
	}
	
	int getIndex() {
		return model.getIndex();
	}
	
	public void setViewState( ViewState viewState ) {
		view.setState( viewState );
	}
	
	public ViewState getViewState() {
		return view.getState();
	}
	
	boolean isEditing() {
		return view.isEditing();
	}
	
	Rectangle2D.Double getBoundsRectangle() {
		return model.getRectangle().getRectangle();
	}
	
	// Adjustment method
	void startResizing( Point p ) {
		// Scale the point to the logical dimensions and start resizing
		if ( isEditing() ) {
			model.startResizing( getScaledPoint( p ) );
		}
	}
	
	// Adjustment method
	void resize( Point p ) {
		// Scale the point to the logical dimensions and resize
		if ( isEditing() ) {
			model.resize( getScaledPoint( p ) );
		}
	}
	
	// Adjustment method
	void stopResizing( Point p ) {
	}
	
	private Point getScaledPoint( Point p ) {
		int x = (int)( (double)p.x / model.getScale() );
		int y = (int)( (double)p.y / model.getScale() );
		return new Point( x, y );
	}
	
	//@Override
	public JComponent getInspectorView() {
		return inspectorPanel;
	}
	
	@Override
	public void select() {
		setViewState( ViewState.VIEWING );
	}
	
	@Override
	public void deselect() {
		setViewState( ViewState.GRAYED );
	}
	
	@Override
	public PageInterface getPageInterface() {
    	return pageInterface;
    }
	
	PageInterface getPlaceNoteInterface() {
		return placeNoteInterface;
	}
	
	@Override
	public String toString() {
		return model.toString();
	}

}
