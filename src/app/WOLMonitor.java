package app;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import ui.terminal.os.OSTerminalSettings;
import ui.terminal.panel.TerminalWindowManager;
import app.state.AppState;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jan 5, 2016, 12:49:56 AM 
 */
public class WOLMonitor {
	
	public static void main( String[] args ) {
		TerminalWindowManager.getInstance().OS = System.getProperty( "os.name" ).contains( "Win" ) ? OSTerminalSettings.WINDOWS : OSTerminalSettings.LINUX;
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ( info.getName().contains( "Nimbus" ) ) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e ) {
			System.err.println( "Critical JVM Failure!" );
			e.printStackTrace();
		}
		new AppState();
	}
}