package ui;

import gui.dialog.BusyDialog;
import gui.progress.DigitalJProgressBar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

import statics.GU;
import statics.UIUtils;
import ui.destination.DynamicDestinationDialog;
import ui.destination.EditDestinationDialog;
import wol.Destination;
import wol.WOLUtils;
import app.state.AppState;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jan 6, 2016, 1:37:48 AM 
 */
public class WOLFrame extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	
	private AppState as;
	
	private JPanel center;
	
	private Map<Destination, JButton> wakeButtons = new HashMap<>();
	
	public WOLFrame( AppState as ) {
		this.as = as;
		this.setIconImage( new ImageIcon( getClass().getResource( "WoL.gif" ) ).getImage() );
		this.setTitle( "Wake on Lan Monitor" );
		setSize( new Dimension( 800, 600 ) );
		this.setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		this.setLayout( new BorderLayout() );
		this.add( getNorthPanel(), BorderLayout.NORTH );
		JScrollPane scroll = new JScrollPane( getCenterPanel() );
		UIUtils.setJScrollPane( scroll );
		this.add( scroll, BorderLayout.CENTER );
		this.add( getSouthPanel(), BorderLayout.SOUTH );
		as.getDestinationManager().addObserver( this );
		as.getStatusManager().addObserver( this );
		UIUtils.setColorsRecursive( this );
		addWindowListener( new WindowAdapter() {
			public void windowClosing( WindowEvent e ) {
				as.getStatusManager().stop();
			}
		} );
		this.pack();
	}
	
	private JPanel getNorthPanel() {
		JPanel north = new JPanel();
		north.setLayout( new BoxLayout( north, BoxLayout.X_AXIS ) );
		north.add( Box.createGlue() );
		JButton b = new JButton( UIC.AUTO_DETECT );
		UIUtils.setJButton( b );
		GU.setSizes( b, GU.SHORT );
		b.addActionListener( e -> {
			new BusyDialog( WOLFrame.this, UIC.BUSY_SCANNING ) {
				
				private static final long serialVersionUID = 1L;
				
				@Override
				public void executeTask() {
					as.getDestinationManager().refreshDynamicAddresses();
				}
				
				@Override
				public JProgressBar getProgressBar() {
					DigitalJProgressBar bar = new DigitalJProgressBar( 0, 1000 );
					bar.setSegments( 10 );
					UIUtils.setColorsRecursive( this );
					UIUtils.setColors( bar );
					return bar;
				}
			};
		} );
		north.add( b );
		return north;
	}
	
	private JPanel getCenterPanel() {
		center = new JPanel();
		center.setLayout( new BoxLayout( center, BoxLayout.Y_AXIS ) );
		reloadCenter();
		return center;
	}
	
	private JPanel getSouthPanel() {
		JPanel south = new JPanel();
		return south;
	}
	
	private void reloadCenter() {
		center.removeAll();
		center.add( Box.createGlue() );
		as.getDestinationManager().getDestinations().forEach( d -> {
			JButton edit = GU.createButton( UIC.EDIT, e -> new EditDestinationDialog( as, d, this ).setVisible( true ) );
			JButton wake = GU.createButton( UIC.WAKE, e -> new Thread( () -> WOLUtils.wakeup( d ) ).start() );
			JButton delete = GU.createButton( UIC.DELETE, e -> as.getDestinationManager().removeDestination( d ) );
			Arrays.asList( new JButton[] { edit, wake, delete } ).forEach( b -> GU.setSizes( b, GU.SHORT ) );
			wake.setEnabled( true );
			wakeButtons.put( d, wake );
			center.add( UIUtilities.createDestinationDisplayPanel( d, edit, wake, delete ) );
		} );
		center.add( Box.createGlue() );
		UIUtils.setColorsRecursive( center );
		center.revalidate();
		this.pack();
	}

	@Override
	public void update( Observable o, Object arg ) {
		if ( o.equals( as.getDestinationManager() ) ) {
			if ( arg != null ) {
				if ( arg instanceof Destination ) {
					reloadCenter();
				} else {
					
				}
			} else {
				new DynamicDestinationDialog( as, this ).setVisible( true );
			}
		} else if ( o.equals( as.getStatusManager() ) ) {
			if ( arg instanceof Destination ) {
				Destination d = (Destination)arg;
				wakeButtons.get( d ).setEnabled( !as.getStatusManager().getStatus( d ) );
			}
		}
	}
}