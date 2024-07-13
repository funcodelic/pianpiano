import javax.swing.*;
import com.funcodelic.pianpiano.gui.ScoreBuilderGUI;

//
//	This is main entry point for the Score Builder/Editor GUI
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
        
	}//end main()

}
