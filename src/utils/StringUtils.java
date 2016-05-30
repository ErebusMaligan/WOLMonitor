package utils;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jan 5, 2016, 7:22:53 PM 
 */
public class StringUtils {
	
	public static String formatColumnString( String in ) {
		return in.trim().replaceAll( " +", " " );
	}
}