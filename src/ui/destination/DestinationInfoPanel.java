package ui.destination;

import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import statics.GU;
import statics.UIUtils;
import ui.UIC;
import wol.Destination;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jan 6, 2016, 5:26:10 AM 
 */
public class DestinationInfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Destination d;
	
	private JTextField mac, broadcast, port, ipv4, ipv6;
	
	public DestinationInfoPanel( Destination d ) {
		this.d = d;
		mac = new JTextField( d.mac );
		broadcast = new JTextField( d.broadcast );
		port = new JTextField( d.port );
		ipv4 = new JTextField( d.ipv4 );
		ipv6 = new JTextField( d.ipv6 );
		this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
		GU.hp( this, new JLabel( UIC.HOSTNAME ), new JLabel( d.hostname ) );
		GU.hp( this, new JLabel( UIC.MAC ), mac );
		GU.hp( this, new JLabel( UIC.BROADCAST ), broadcast );
		GU.hp( this, new JLabel( UIC.PORT ), port );
		GU.hp( this, new JLabel( UIC.IPV4), ipv4 );
		GU.hp( this, new JLabel( UIC.IPV6 ), ipv6 );
		Arrays.asList( new JTextField[] { mac, broadcast, port, ipv4, ipv6 } ).forEach( t -> t.setBorder( BorderFactory.createLineBorder( UIUtils.FOREGROUND.darker().darker() ) ) );
	}
	
	public void writeToData() {
		d.mac = mac.getText();
		d.broadcast = broadcast.getText();
		d.port = port.getText();
		d.ipv4 = ipv4.getText();
		d.ipv6 = ipv6.getText();
	}
}