package scan.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import scan.genericprocess.GenericProcess;
import utils.StringUtils;
import wol.Destination;
import app.AC;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jan 5, 2016, 1:11:11 AM 
 */
public class ARPProcess extends GenericProcess  {

	private Map<String, Destination> dest = new HashMap<>();
	
	public ARPProcess() {
		super( AC.ARP, AC.ARP_COMMAND );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void executeCommand( Object additional ) {
		dest.clear();
		if ( additional != null ) {
			for ( Object s : (ArrayList<Object>)additional ) {
				Destination d = (Destination)s;
				dest.put( d.hostname.toUpperCase(), d );
			}
		}
		super.executeCommand( additional );
	}

	@Override
	public void handleLine( String line ) {
		if ( line != null ) {
			if ( line.contains( AC.ARP_MATCH ) ) {
				String[] split = StringUtils.formatColumnString( line ).split( AC.ARP_SPLIT );
				if ( split.length >= 2 ) {
					String ip = split[ 0 ];
					for ( Destination d : dest.values() ) {
						if ( ( d.ipv4 != null && d.ipv4.equals( ip ) ) || ( d.ipv6 != null && d.ipv6.equals( ip ) ) ) {
							d.mac = split[ 1 ];
							break;
						}
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