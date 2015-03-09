import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

/**
 * Created by egreb on 04.03.15.
 */
public class Main {
    public static void main( String[]args ) {
        GUI gui = new GUI();
        
		gui.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent evt) {
		        	gui.save();
		            System.exit(0);
		    }
		});
    }
}