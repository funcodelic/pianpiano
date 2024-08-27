import javax.swing.*;
import com.funcodelic.pianpiano.gui.ScoreBuilderGUI;
import com.funcodelic.pianpiano.virtualpiano.VirtualPiano;

//
//	The main entry point for the Score Builder/Editor GUI
//
public class PianPiano {

	public static void main(String[] args) {
		
		// Schedule the launch of the GUI from the event-dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	ScoreBuilderGUI scoreBuilderGUI = new ScoreBuilderGUI();
            	scoreBuilderGUI.go();
            }
        });
        
        JFrame frame = new JFrame("PianoView");
        VirtualPiano pianoView = new VirtualPiano();
        frame.add(pianoView);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        
	}//end main()

}//end class
