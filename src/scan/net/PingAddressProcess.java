package scan.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import scan.genericprocess.GenericProcess;
import wol.Destination;
import app.AC;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jan 5, 2016, 2:25:14 AM 
 */
public class PingAddressProcess extends GenericProcess {
	
	private Map<String, Destination> dest = new HashMap<>();
	
	public PingAddressProcess() {
		super( AC.PING, AC.PING_COMMAND_1 );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void executeCommand( Object additional ) {
		dest.clear();
		if ( additional != null ) {
			for ( Object s : (ArrayList<Object>)additional ) {
				Destination d = (Destination)s;
				dest.put( d.hostname.toUpperCase(), d );
				process.sendCommand( command + " " + d.hostname + " " + AC.PING_COMMAND_IPV4 );
				process.sendCommand( command + " " + d.hostname + " " + AC.PING_COMMAND_IPV6 );
			}
		}
	}
	
	@Override
	public void handleLine( String line ) {
		if ( line != null ) {
			if ( line.contains( AC.PING_ADD_LINE ) ) {
				String pair = line.substring( line.lastIndexOf( AC.PING_ADD_LINE ) + AC.PING_ADD_LINE.length() + 1, line.lastIndexOf( AC.PING_LAST_CHAR ) );
				String[] split = pair.split( AC.PING_SPLIT );
				String hn = split[ 0 ].trim().toUpperCase();
				if ( dest.containsKey( hn ) ) {
					String ip = split[ 1 ].substring( 1 ).trim();
					if ( ip.matches( AC.IPV4_PATTERN ) ) {
						dest.get( hn ).ipv4 = ip;
					} else {
						dest.get( hn ).ipv6 = ip;
					}
				}
			}
		}
	}
	
	@Override
	public void onComplete() {
		skimmed.addAll( dest.values() );
		super.onComplete();
	}
}