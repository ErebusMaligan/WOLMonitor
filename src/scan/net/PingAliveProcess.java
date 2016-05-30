package scan.net;

import scan.genericprocess.GenericProcess;
import wol.Destination;
import app.AC;
import app.state.AppState;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jan 7, 2016, 5:30:55 AM 
 */
public class PingAliveProcess extends GenericProcess {

	private AppState state;
	
	private String ipv4 = null;
	
	public PingAliveProcess( AppState state, String id ) {
		super( AC.PING_ALIVE + id, AC.PING_COMMAND );
		this.state = state;
	}
	
	@Override
	protected void executeCommand( Object additional ) {
		if ( additional != null ) {
			Destination d = (Destination)additional;
			process.sendCommand( command + " " + d.ipv4 + " " + AC.PING_SINGLE );
		}
	}
	
	@Override
	public void handleLine( String line ) {
		if ( line != null ) {
			if ( line.contains( AC.PING_ADD_LINE ) ) {
				String pair = line.substring( line.lastIndexOf( AC.PING_ADD_LINE ) + AC.PING_ADD_LINE.length() + 1, line.lastIndexOf( AC.PING_WITH ) );
				String[] split = pair.split( AC.PING_SPLIT );
				ipv4 = split[ 0 ].trim().toUpperCase();
			}
		}
		if ( line.contains( AC.UNREACHABLE_WIN ) || line.contains( AC.UNREACHABLE_LIN ) ) {
			state.getStatusManager().setStatus( state.getDestinationManager().getDestinationByIP( ipv4 ), false );
		}
		if ( line.contains( AC.TTL_WIN ) || line.contains( AC.TTL_LIN ) ) {
			state.getStatusManager().setStatus( state.getDestinationManager().getDestinationByIP( ipv4 ), true );			
		}
	}
}