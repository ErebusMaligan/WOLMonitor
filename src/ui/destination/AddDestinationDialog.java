package ui.destination;

import java.awt.Frame;

import wol.Destination;
import app.state.AppState;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jan 6, 2016, 6:24:30 AM 
 */
public class AddDestinationDialog extends DestinationInfoDialog {

	private static final long serialVersionUID = 1L;
	
	public AddDestinationDialog( AppState as, Destination d, Frame owner ) {
		super( as, d, owner );
	}
	
	@Override
	public void preOK() {
		info.writeToData();
		as.getDestinationManager().removeDynamicDestination( d );
		as.getDestinationManager().addDestination( d );
	}
}