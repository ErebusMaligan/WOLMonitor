package ui.destination;

import gui.dialog.OKCancelDialog;

import java.awt.BorderLayout;
import java.awt.Frame;

import statics.GU;
import statics.UIUtils;
import wol.Destination;
import app.state.AppState;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jan 7, 2016, 1:21:11 AM 
 */
public abstract class DestinationInfoDialog extends OKCancelDialog {

	private static final long serialVersionUID = 1L;

	protected DestinationInfoPanel info;
	
	protected AppState as;
	
	protected Destination d;
	
	public DestinationInfoDialog( AppState as, Destination d, Frame owner ) {
		super( owner, "Device Definition:", true );
		GU.center( this );
		this.as = as;
		this.d = d;
		this.setLayout( new BorderLayout() );
		info = new DestinationInfoPanel( d );
		this.add( info, BorderLayout.CENTER );
		this.add( getButtonPanel(), BorderLayout.SOUTH );
		this.pack();
		UIUtils.setColorsRecursive( this );
		UIUtils.setJButton( ok );
		UIUtils.setJButton( cancel );
	}
	
	@Override
	public void ok() {
		preOK();
		super.ok();
	}
	
	public abstract void preOK();
}