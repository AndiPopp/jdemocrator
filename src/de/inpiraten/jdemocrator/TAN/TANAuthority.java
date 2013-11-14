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
<<<<<<< HEAD
	 */
	public final URL announceServerAddress;

	/**
	 * Full Parameter Constructor
	 * @param name
	 * @param announceServerAddress
	 */
=======
	 */
	public final URL announceServerAddress;

	/**
	 * Full Parameter Constructor
	 * @param name
	 * @param announceServerAddress
	 */
>>>>>>> d6761e8bbd95a80bc9a4fc037b606bded9d46de4
	public TANAuthority(String name, URL announceServerAddress) {
		super();
		this.name = name;
		this.announceServerAddress = announceServerAddress;
<<<<<<< HEAD
	}
	
	public String toString(){
		return this.name+","+this.announceServerAddress.toString();
	}
=======
	}
	
>>>>>>> d6761e8bbd95a80bc9a4fc037b606bded9d46de4
	
}
