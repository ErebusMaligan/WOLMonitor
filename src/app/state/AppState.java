package app.state;

import ui.WOLFrame;
import wol.DestinationManager;
import wol.OnlineStatusManager;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jan 6, 2016, 2:13:55 AM 
 */
public class AppState {
	
	private DestinationManager d = new DestinationManager();
	
	private OnlineStatusManager o = new OnlineStatusManager( this );

	private WOLFrame frame;

	public AppState() {
		frame = new WOLFrame( this );
		frame.setVisible( true );
	}
	
	public DestinationManager getDestinationManager() {
		return d;
	}
	
	public OnlineStatusManager getStatusManager() {
		return o;
	}
}