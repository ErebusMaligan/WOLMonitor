package wol;

import java.util.HashMap;
import java.util.Map;

import app.state.AppState;
import listeners.BasicObservable;
import listeners.BasicObserver;
import scan.genericprocess.GenericProcess;
import scan.net.PingAliveProcess;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jan 9, 2016, 12:18:18 AM 
 */
public class OnlineStatusManager extends BasicObservable implements BasicObserver {
	
	private Map<Destination, Boolean> status = new HashMap<>();

	private boolean stop = false;
	
	public OnlineStatusManager( AppState as ) {
		new Thread( () -> {
			while ( !stop ) {
				as.getDestinationManager().getDestinations().forEach( d -> {
					if ( !stop ) {
						new Thread( () -> { 
							PingAliveProcess ping = new PingAliveProcess( as, d.hostname );
							ping.addObserver( this );
							ping.execute( d ); 
						} ).start();
					}
				}  );
				if ( !stop ) {
					try {
						Thread.sleep( 10000 );
					} catch ( Exception e ) {
						e.printStackTrace();
					}
				}
			}
		} ).start();
	}
	
	public void stop() {
		stop = true;
	}
	
	public boolean getStatus( Destination d ) {
		return status.get( d );
	}
	
	public void setStatus( Destination d, Boolean b ) {
		status.put( d, b );
		try {
			notifyObservers( d );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update( BasicObservable o, Object arg ) {
		if ( o instanceof GenericProcess ) {
			( (GenericProcess)o ).closeResources();
		}
	}
}