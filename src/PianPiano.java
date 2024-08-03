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
        
        
//        JFrame frame = new JFrame("Vertically Resizable Rectangle");
//        Rectangle2D.Double rect = new Rectangle2D.Double(50, 50, 200, 100);
//        
//        VerticallyResizableRectangleComponent component = new VerticallyResizableRectangleComponent(rect);
//        
//        frame.add(component);
//        frame.setSize(400, 300);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
        
        
	}//end main()

}//end class
