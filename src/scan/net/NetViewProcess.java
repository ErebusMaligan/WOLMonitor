package scan.net;

import scan.genericprocess.GenericProcess;
import wol.Destination;
import app.AC;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jan 5, 2016, 2:21:41 AM 
 */
public class NetViewProcess extends GenericProcess {

	public NetViewProcess() {
		super( AC.NET, AC.NET_VIEW_COMMAND );
	}
	
	@Override
	public void handleLine( String line ) {
		if ( line != null ) {
			if ( line.startsWith( AC.NET_VIEW_MATCH ) ) {
				String[] l = line.split( AC.NET_VIEW_SPLIT );
				if ( l.length >= 1 ) {
					Destination d = new Destination();
					d.hostname = l[ 0 ].replaceAll( AC.NET_VIEW_MATCH, AC.NET_VIEW_REPLACE ).trim();
					skimmed.add( d );
				}
			}
		}
	}
}