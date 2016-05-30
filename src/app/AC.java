package app;


/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jan 5, 2016, 1:24:18 AM 
 */
public class AC {
	
	public static final String ECHO_CMD = "echo";

	public static final String COMPLETE = "complete";

	public static final String IPV4_PATTERN = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
	
	public static final String BROADCAST_DEFAULT = "192.168.10.255";
	
	public static final String PORT_DEFAULT = "9";
	
	//arp - windows
	public static final String ARP = "ARP";

	public static final String ARP_COMMAND = "arp -a";

	public static final String ARP_SPLIT = " ";

	public static final String ARP_MATCH = "dynamic";

	//net - windows
	public static final String NET = "NET";

	public static final String NET_VIEW_COMMAND = "net view";

	public static final String NET_VIEW_MATCH = "\\\\";

	public static final String NET_VIEW_REPLACE = "";

	public static final String NET_VIEW_SPLIT = " ";

	//ping - windows
	public static final String PING = "PING";
	
	public static final String PING_ALIVE ="PINGALIVE";
	
	public static final String PING_COMMAND = "ping";
	
	public static final String PING_SINGLE = "-n 1";

	public static final String PING_COMMAND_1 = PING_COMMAND + " -a";

	public static final String PING_COMMAND_IPV4 = PING_SINGLE + " -4";

	public static final String PING_COMMAND_IPV6 = PING_SINGLE + " -6";

	public static final String PING_LAST_CHAR = "]";
	
	public static final String PING_WITH = "with";

	public static final String PING_ADD_LINE = "Pinging";

	public static final String PING_SPLIT = " ";
	
	public static final String UNREACHABLE_WIN = "unreachable";
	
	public static final String UNREACHABLE_LIN = "Unreachable";
	
	public static final String TTL_WIN = "TTL";
	
	public static final String TTL_LIN = "ttl";

}