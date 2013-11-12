package de.inpiraten.jdemocrator.TAN;

/**
 * An abstract class for TAN types
 * @author andi
 *
 */
public abstract class TANType {

	/**
	 * A String to identify the TAN type
	 * @return A String to identify the TAN type
	 */
	public abstract String getTypeName();
	
	/**
	 * A float to identify the version of the Type
	 * @return A float to identify the version of the Type
	 */
	public abstract float getVersion ();
	
	/**
	 * A method to make the TAN Type into a string
	 */
	public abstract String toString();
	
}
