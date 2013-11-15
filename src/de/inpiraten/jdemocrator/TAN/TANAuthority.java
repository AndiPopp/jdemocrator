/**
 * 
 */
package de.inpiraten.jdemocrator.TAN;

import java.net.MalformedURLException;
import java.net.URL;

import de.inpiraten.jdemocrator.event.IllegalEntryException;

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
	
	public String toString(){
		return this.name+","+this.announceServerAddress.toString();
	}
	
	/**
	 * Gives a TANAuthority represented by the given string
	 * @param s
	 * @throws IllegalEntryException if the string cannot be parsed into a TAN Authority object
	 */
	public TANAuthority (String s) throws IllegalEntryException{
		String[] data = s.split(",", 2);
		if (data.length != 2) throw new IllegalEntryException(s+" is not a valid TAN authority description");
		
		this.name = data[0];
		try {
			this.announceServerAddress = new URL(data[1]);
		} catch (MalformedURLException e) {
			throw new IllegalEntryException(data[1]+" is not a valid URL");
		}
		
	}
	
}
