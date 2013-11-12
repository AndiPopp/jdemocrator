/**
 * 
 */
package de.inpiraten.jdemocrator.TAN;

import java.net.URL;

/**
 * @author Andi Popp
 * A class describing a TAN Authority
 */
public class TANAuthority {
	
	/**
	 * Gets the Name of the TAN Authority
	 * @return the Name of the TAN Authority
	 */
	public String getName(){
		return this.name;
	}
	private String name;
	
	/**
	 * Gets the addresse of this TAN Authority's Master TAN announce Server
	 * @return
	 */
	public URL getAnnounceServerAddress(){
		return this.announceServerAddress;
	}
	private URL announceServerAddress;
	
}
