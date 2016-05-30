package app.xml;

import wol.DestinationManager;
import xml.SimpleFileXMLDocumentHandler;

/**
 * @author Daniel J. Rivers
 *         2016
 *
 * Created: Jan 6, 2016, 5:34:15 PM 
 */
public class WOLDestinationDocumentHandler extends SimpleFileXMLDocumentHandler {
	public WOLDestinationDocumentHandler( DestinationManager dest ) {
		this.val = dest;
		FILE = XC.DEST_FILE_NAME;
		ROOT_NODE_NAME = XC.DEST_FILE_ROOT;
		EXT = XC.DEST_FILE_EXTENSION;
		READABLE = XC.DEST_FILE_READABLE;
		DIR = XC.DEST_FILE_DIR;
		init( null, EXT, READABLE + " (" + EXT + ")", DIR, ROOT_NODE_NAME );
	}
}