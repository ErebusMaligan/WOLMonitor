package wol;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel J. Rivers
 *         2015
 *
 * Created: Dec 20, 2015, 7:13:20 AM 
 */
public class WOLUtils {

	public static void wakeup( Destination d ) {
		if ( d.mac != null && d.broadcast != null && d.port != null ) {
			wakeup( d.mac, d.broadcast, d.port );
		}
	}
	
	public static void wakeup( String inputMAC, String ip, String port ) {
		System.out.println( "Attempting to send..." );
		List<String> macs = new ArrayList<String>();		
		try ( DatagramSocket socket = new DatagramSocket() ) {
			byte[] bytes = getWOLBytes( getMacBytes( inputMAC ) );
			InetAddress address = InetAddress.getByName( ip );
			socket.send( new DatagramPacket( bytes, bytes.length, address, Integer.parseInt( port ) ) );
			System.out.println( "Wake-on-LAN packet sent: " + inputMAC + " (" + address.getHostName() + ")" );
			if ( macs.size() > 1 ) {
				Thread.sleep( 500 );
			}
		} catch ( Exception e ) {
			System.out.println( "Failed to send Wake-on-LAN packet: " + e.getMessage() );
		}
	}
	
	
	private static byte[] getWOLBytes( byte[] macBytes ) {
		byte[] bytes = new byte[6 + 16 * macBytes.length];
		for ( int i = 0; i < 6; i++ ) {
			bytes[ i ] = (byte)0xff;
		}
		for ( int i = 6; i < bytes.length; i += macBytes.length ) {
			System.arraycopy( macBytes, 0, bytes, i, macBytes.length );
		}
		return bytes;
	}

	private static byte[] getMacBytes( String macStr ) throws IllegalArgumentException {
		byte[] bytes = new byte[6];
		String[] hex = macStr.split( "(\\:|\\-)" );
		if ( hex.length != 6 ) {
			throw new IllegalArgumentException( "Invalid MAC address." );
		}
		try {
			for ( int i = 0; i < 6; i++ ) {
				bytes[ i ] = (byte)Integer.parseInt( hex[ i ], 16 );
			}
		} catch ( NumberFormatException e ) {
			throw new IllegalArgumentException( "Invalid hex digit in MAC address." );
		}
		return bytes;
	}
}