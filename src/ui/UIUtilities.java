package ui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import statics.GU;
import statics.UIUtils;
import ui.destination.DestinationDisplayPanel;
import wol.Destination;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jan 6, 2016, 7:08:40 AM 
 */
public class UIUtilities {

	public static JPanel createDestinationDisplayPanel( Destination d, JComponent...comp ) {
		JPanel p = new JPanel();
		p.setLayout( new BoxLayout( p, BoxLayout.X_AXIS ) );
		p.add( new DestinationDisplayPanel( d ) );
		boolean once = false;
		for ( JComponent b : comp ) {
			if ( b instanceof JButton ) {
				UIUtils.setJButton( (JButton)b );
			}
			p.add( b );
			if ( !once ) {
				GU.spacer( p );
			}
		}
		int b = 2;
		p.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( b, b, b, b ), BorderFactory.createLineBorder( UIC.FOREGROUND_DARKER ) ) );
		return p;
	}
	
}
