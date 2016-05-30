package wol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import scan.net.ARPProcess;
import scan.net.NetViewProcess;
import scan.net.PingAddressProcess;
import xml.XMLExpansion;
import xml.XMLValues;
import app.AC;
import app.xml.WOLDestinationDocumentHandler;
import app.xml.XC;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Dec 20, 2015, 12:50:50 PM 
 */
public class DestinationManager extends Observable implements Observer, XMLValues {

	private List<Destination> destinations = new ArrayList<>();
	
	private List<Destination> dynamic = new ArrayList<>();
	
	private NetViewProcess net = new NetViewProcess();
	
	private PingAddressProcess ping = new PingAddressProcess();
	
	private ARPProcess arp = new ARPProcess();
	
	private CountDownLatch refreshCDL = null;
	
	private WOLDestinationDocumentHandler doc = new WOLDestinationDocumentHandler( this );
	
	public DestinationManager() {
		net.addObserver( this );
		ping.addObserver( this );
		arp.addObserver( this );
		doc.loadDoc();
	}
	
	/**
	 * NOTE: This method intentionally blocks until the refresh is complete
	 */
	public void refreshDynamicAddresses() {
		if ( refreshCDL == null ) {
			refreshCDL = new CountDownLatch( 1 );
			refreshDynamic();
			try {
				refreshCDL.await();
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}
			refreshCDL = null;
		}
	}
	
	private void refreshComplete() {
		net.closeResources();
		ping.closeResources();
		arp.closeResources();
		refreshCDL.countDown();
		setChanged();
		try {
			notifyObservers( null );
			clearChanged();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	private void refreshDynamic() {
		net.execute();
	}
	
	public void removeDynamicDestination( Destination d ) {
		dynamic.remove( d );
		setChanged();
		try {
			notifyObservers( d );
			clearChanged();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
	public synchronized void removeDestination( Destination d ) {
		destinations.remove( d );
		setChanged();
		try {
			notifyObservers( d );
			clearChanged();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		doc.createDoc();
	}
	
	public synchronized void addDestination( Destination d ) {
		destinations.add( d );
		setChanged();
		try {
			notifyObservers( d );
			clearChanged();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		doc.createDoc();
	}
	
	public List<Destination> getDynamicDestinations() {
		return Collections.unmodifiableList( dynamic );
	}
	
	public List<Destination> getDestinations() {
		return Collections.unmodifiableList( destinations );
	}
	
	public Destination getDestinationByName( String name ) {
		Optional<Destination> dest = destinations.stream().filter( d -> d.hostname.equals( name ) ).findFirst();
		Destination ret = null;
		if ( dest.isPresent() ) {
			ret = dest.get();
		}
		return ret;
	}
	
	public Destination getDestinationByIP( String ipv4 ) {
		Optional<Destination> dest = destinations.stream().filter( d -> d.ipv4.equals( ipv4 ) ).findFirst();
		Destination ret = null;
		if ( dest.isPresent() ) {
			ret = dest.get();
		}
		return ret;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void update( Observable o, Object arg ) {
		if ( o.equals( net ) ) {
			ping.execute( arg );
		} else if ( o.equals( ping ) ) {
			arp.execute( arg );
		} else if ( o.equals( arp ) ) {
			dynamic.clear();
			for ( Object s : (ArrayList<Object>)arg ) {
				Destination d = (Destination)s;
				d.port = AC.PORT_DEFAULT;
				d.broadcast = AC.BROADCAST_DEFAULT;
				if ( !destinations.contains( d ) ) { //not returning devices that already have entries
					dynamic.add( d );
				}
			}
			refreshComplete();
		}
	}
	
	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<String, Map<String, String[]>>();
		Map<String, String[]> values = new HashMap<>();
		ret.put( XC.DESTINATIONS, values );
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<XMLValues> getChildNodes() {
		return (List<XMLValues>)(List<?>)destinations;
	}

	@Override
	public void loadParamsFromXMLValues( XMLExpansion e ) {
		for ( XMLExpansion x : e.getChild( XC.DESTINATIONS ).getChildren( XC.DESTINATION ) ) {
			Destination d = new Destination();
			d.loadParamsFromXMLValues( x );
			addDestination( d );
		}
	}
}