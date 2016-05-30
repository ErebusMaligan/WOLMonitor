package wol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xml.XMLExpansion;
import xml.XMLValues;
import app.xml.XC;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Dec 20, 2015, 11:51:37 AM 
 */
public class Destination implements XMLValues {
	
	public String hostname;
	
	public String ipv6;
	
	public String ipv4;
	
	public String mac;
	
	public String broadcast;
	
	public String port;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( hostname == null ) ? 0 : hostname.hashCode() );
		result = prime * result + ( ( mac == null ) ? 0 : mac.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		Destination other = (Destination)obj;
		if ( hostname == null ) {
			if ( other.hostname != null )
				return false;
		} else if ( !hostname.equals( other.hostname ) )
			return false;
		if ( mac == null ) {
			if ( other.mac != null )
				return false;
		} else if ( !mac.equals( other.mac ) )
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return hostname + " [" + ipv4 + "] [" + ipv6 + "] " + mac;
	}

	@Override
	public List<XMLValues> getChildNodes() { return null; }

	@Override
	public void loadParamsFromXMLValues( XMLExpansion e ) {
		hostname = e.get( XC.HOSTNAME );
		mac = e.get( XC.MAC );
		ipv4 = e.get( XC.IPV4 );
		ipv6 = e.get( XC.IPV6 );
		broadcast = e.get( XC.BROADCAST );
		port = e.get( XC.PORT );
	}

	@Override
	public Map<String, Map<String, String[]>> saveParamsAsXML() {
		Map<String, Map<String, String[]>> ret = new HashMap<String, Map<String, String[]>>();
		Map<String, String[]> values = new HashMap<>();
		values.put( XC.HOSTNAME, new String[] { hostname } );
		values.put( XC.MAC, new String[] { mac } );
		values.put( XC.IPV4, new String[] { ipv4 } );
		values.put( XC.IPV6, new String[] { ipv6 } );
		values.put( XC.BROADCAST, new String[] { broadcast } );
		values.put( XC.PORT, new String[] { port } );		
		ret.put( XC.DESTINATION, values );
		return ret;
	}
}