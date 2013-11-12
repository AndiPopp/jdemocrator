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
	 * The Name of the TAN Authority
	 */
	public final String name;
	
	/**
	 * The address of this TAN Authority's Master TAN announce Server
	 */
	public final URL announceServerAddress;

	/**
	 * Full Parameter Constructor
	 * @param name
	 * @param announceServerAddress
	 */
	public TANAuthority(String name, URL announceServerAddress) {
		super();
		this.name = name;
		this.announceServerAddress = announceServerAddress;
	}
	
	
}
