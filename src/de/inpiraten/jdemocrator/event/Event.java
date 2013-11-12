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
	 * The name of the event
	 */
	public final String eventName;
	
	/**
	 * The url identifier for this event
	 */
	public final String eventURLIdentifier;
	
	/**
	 * The TAN type used for this event
	 */
	public final TANType TANType;
	
	/**
	 * The key derivation function used for this event
	 */
	public final String keyDerivationFunction;
	
	/**
	 * The number of elections for this event
	 */
	public final int numberOfElections;
	
	/**
	 * The number of voters for this event
	 */
	public final int numberOfVoters;
	
	/**
	 * The minimal voting delay to balance load on ballot box servers
	 */
	public final int minVotingDelay;
	
	/**
	 * Gets the maximal voting delay to balance load on ballot box servers
	 * @return the maximal voting delay in milliseconds
	 */
	public final int maxVotingDelay;
	
	/**
	 * The addresses of the ballot box server addresses for this event
	 */
	public final URL[] ballotBoxServerAddress;
	
	/**
	 * The TANAuthorities for this event
	 */
	public final TANAuthority[] eventTANAuthority;

	/**
	 * Full Parameter Constructor
	 * @param eventName
	 * @param eventURLIdentifier
	 * @param tANType
	 * @param keyDerivationFunction
	 * @param numberOfElections
	 * @param numberOfVoters
	 * @param minVotingDelay
	 * @param maxVotingDelay
	 * @param ballotBoxServerAddress
	 * @param eventTANAuthority
	 */
	public Event(String eventName, String eventURLIdentifier,
			de.inpiraten.jdemocrator.TAN.TANType tANType,
			String keyDerivationFunction, int numberOfElections,
			int numberOfVoters, int minVotingDelay, int maxVotingDelay,
			URL[] ballotBoxServerAddress, TANAuthority[] eventTANAuthority) {
		super();
		this.eventName = eventName;
		this.eventURLIdentifier = eventURLIdentifier;
		TANType = tANType;
		this.keyDerivationFunction = keyDerivationFunction;
		this.numberOfElections = numberOfElections;
		this.numberOfVoters = numberOfVoters;
		this.minVotingDelay = minVotingDelay;
		this.maxVotingDelay = maxVotingDelay;
		this.ballotBoxServerAddress = ballotBoxServerAddress;
		this.eventTANAuthority = eventTANAuthority;
	}
	
	
}
