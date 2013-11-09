package de.inpiraten.jdemocrator.TAN;

/**
 * An abstract class for TAN types
 * @author andi
 *
 */
public abstract class TANType {

	/**
	 * A String to identify the TAN type
	 * @return
	 */
	public abstract String getTypeName();
	
	/**
	 * An integer to identifies the version of the Type
	 * @return
	 */
	public abstract int getVersion ();
	
}
