package ui.destination;

import java.awt.Frame;

import wol.Destination;
import app.state.AppState;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jan 7, 2016, 1:38:20 AM 
 */
public class EditDestinationDialog extends DestinationInfoDialog {

	private static final long serialVersionUID = 1L;
	
	public EditDestinationDialog( AppState as, Destination d, Frame owner ) {
		super( as, d, owner );
	}
	
	@Override
	public void preOK() {
		as.getDestinationManager().removeDestination( d );
		info.writeToData();
		as.getDestinationManager().addDestination( d );
	}
}