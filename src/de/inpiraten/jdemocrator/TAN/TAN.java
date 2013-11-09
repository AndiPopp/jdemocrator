/**
 * 
 */
package de.inpiraten.jdemocrator.TAN;

/**
 * @author andi
 *
 */
public abstract class TAN {
	
	/**
	 * Describes the type of the TAN
	 */
	public abstract TANType geType();
	
	/**
	 * The number of the election the TAN is viable for
	 */
	public abstract int getElectionNumber();
	
	
}
