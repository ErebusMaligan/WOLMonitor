package ui.destination;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import app.state.AppState;
import gui.dialog.OKCancelDialog;
import listeners.BasicObservable;
import listeners.BasicObserver;
import statics.GU;
import statics.UIUtils;
import ui.UIC;
import ui.UIUtilities;
import wol.Destination;


/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jan 6, 2016, 5:54:59 AM 
 */
public class DynamicDestinationDialog extends OKCancelDialog implements BasicObserver {

	private static final long serialVersionUID = 1L;

	private JPanel center = new JPanel();
	
	private AppState as;
	
	private Frame owner;
	
	public DynamicDestinationDialog( AppState as, Frame owner ) {
		super( owner, "Add Detected Devices:", true );
//		this.setUndecorated( true );
		this.as = as;
		this.owner = owner;
		GU.center( this );
		this.setLayout( new BorderLayout() );
		center.setLayout( new BoxLayout( center, BoxLayout.Y_AXIS ) );
		reloadCenter();
		JScrollPane scroll = new JScrollPane( center );
		UIUtils.setJScrollPane( scroll );
		this.add( scroll, BorderLayout.CENTER );
		getButtonPanel(); // not actually using the button panel, just the ok button, but this must be called to set it up
		this.add( ok, BorderLayout.SOUTH );
		this.pack();
		as.getDestinationManager().addObserver( this );
		UIUtils.setColorsRecursive( this );
		UIUtils.setJButton( ok );
	}
	
	@Override
	public void ok() {
		as.getDestinationManager().deleteObserver( this );
		super.ok();
	}
	
	private synchronized void reloadCenter() {
		center.removeAll();
		center.add( Box.createGlue() );
		as.getDestinationManager().getDynamicDestinations().forEach( d -> {
			JButton b = GU.createButton( UIC.ADD_DEVICE, e -> new AddDestinationDialog( as, d, owner ).setVisible( true ) );
			GU.setSizes( b, GU.SHORT );
			center.add( UIUtilities.createDestinationDisplayPanel( d, b ) );
		} );
		center.add( Box.createGlue() );
		UIUtils.setColorsRecursive( center );
		center.revalidate();
	}

	@Override
	public void update( BasicObservable o, Object arg ) {
		if ( o.equals( as.getDestinationManager() ) ) {
			if ( arg != null ) {
				if ( arg instanceof Destination ) {
					reloadCenter();
				}
			}
		}
	}
}