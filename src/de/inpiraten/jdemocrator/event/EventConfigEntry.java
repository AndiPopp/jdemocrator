/**
 * 
 */
package de.inpiraten.jdemocrator.event;

/**
 * @author Andi Popp
 *
 */
public class EventConfigEntry {
	
	/**
	 * The String identifying the entry type
	 */
	public String identifier;
	
	/**
	 * The configuration data in a strig
	 */
	public String data;
	
	/**
	 * Empty constructor
	 */
	public EventConfigEntry(){
		super();
	}

	/**
	 * Full parameter constructor
	 * @param identifier
	 * @param data
	 */
	public EventConfigEntry(String identifier, String data) {
		super();
		this.identifier = identifier;
		this.data = data;
	}
	
	
}
