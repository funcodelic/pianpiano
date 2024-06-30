import javax.swing.*;
import com.funcodelic.pianpiano.gui.*;


//
//	This class represents the main method and main entry point for the GUI editor
//
public class PianPiano {

	public static void main(String[] args) {
		//System.out.println("Running Pian Piano v 0.2 ...\n");
		
		// Schedule the launch of the GUI from the event-dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	PianPianoEditorGUI editorGUI = new PianPianoEditorGUI();
        		editorGUI.go();
            }
        });
	}

}
