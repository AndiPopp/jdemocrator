/**
 * 
 */
package de.inpiraten.jdemocrator.event;

import java.net.URL;

import de.inpiraten.jdemocrator.TAN.TANAuthority;
import de.inpiraten.jdemocrator.TAN.TANType;

/**
 * @author Andi Popp
 *
 */
public class Event {
	
	/**
	 * Gets the name of the event as string
	 * @return the name of the event
	 */
	public String getEventName(){
		return this.eventName;
	}
	private String eventName;
	
	/**
	 * Gets the name of the the url identifier for this event
	 * @return the name of the the url identifier for this event
	 */
	public String getEventURLIdentifier(){
		return this.eventURLIdentifier;
	}
	private String eventURLIdentifier;
	
	/**
	 * Gets the TAN type used for this event
	 * @return the TAN type used for this event
	 */
	public TANType getTANType(){
		return this.TANType;
	}
	private TANType TANType;
	
	/**
	 * Gets the key derivation function used for this event
	 * @return a string representing the key derivation function used for this event
	 */
	public String getKeyDerivationFunction(){
		return this.keyDerivationFunction;
	}
	private String keyDerivationFunction;
	
	/**
	 * Gets the number of elections for this event
	 * @return the number of elections for this event
	 */
	public int getNumberOfElections(){
		return this.numberOfElections;
	}
	private int numberOfElections;
	
	/**
	 * Gets the number of voters for this event
	 * @return the number of voters for this event
	 */
	public int getNumberOfVoters(){
		return this.numberOfVoters;
	}
	private int numberOfVoters;
	
	/**
	 * Gets the minimal voting delay to balance load on ballotbox servers
	 * @return the minimal voting delay in milliseconds
	 */
	public int getMinVotingDelay(){
		return this.minVotingDelay;
	}
	private int minVotingDelay;
	
	/**
	 * Gets the maximal voting delay to balance load on ballotbox servers
	 * @return the maximal voting delay in milliseconds
	 */
	public int getMaxVotingDelay(){
		return this.maxVotingDelay;
	}
	private int maxVotingDelay;
	
	/**
	 * Gets the addresses of the ballot box server addresses for this event
	 * @return an array containing the addresses of the ballot box server addresses for this event
	 */
	public URL[] getBallotBoxServerAddresses(){
		return this.ballotBoxServerAddress;
	}
	private URL[] ballotBoxServerAddress;
	
	/**
	 * Gets the TANAuthorities for this event
	 * @return an array containing the TANAuthorities for this event
	 */
	public TANAuthority[] getEventTANAuhtorities(){
		return this.eventTANAuthority;
	}
	private TANAuthority[] eventTANAuthority;
}
