package ui.destination;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import statics.GU;
import wol.Destination;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jan 6, 2016, 5:07:45 AM 
 */
public class DestinationDisplayPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Destination d;
	
	public DestinationDisplayPanel( Destination d ) {
		this.d = d;
		this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
		GU.vp( this, new JLabel( d.hostname ), new JLabel( d.mac ) );
	}
	
	public Destination getDestination() {
		return d;
	}
}