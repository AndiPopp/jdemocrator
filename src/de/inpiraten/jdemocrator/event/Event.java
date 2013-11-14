/**
 * 
 */
package de.inpiraten.jdemocrator.event;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Vector;

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
<<<<<<< HEAD
	
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
	
	public Event(String configFileLocation) throws IOException, IllegalEntryException{
		//Create buffered reader to read lines from config file. Throws FileNotFoundException if there is no file a location
		BufferedReader configFileIn = new BufferedReader(new InputStreamReader(new FileInputStream(configFileLocation)));
	
		//Read EventConfigEntries
		Vector<EventConfigEntry> entries = new Vector<>();
		while(true){
			String line = configFileIn.readLine();
			if (line == null) break;
			if (line.trim().length() == 0 || line.trim().charAt(0) == '#');
			else{
				String[] rawEntry = line.trim().split(":", 2);
				try {
					entries.add(new EventConfigEntry(rawEntry[0], rawEntry[1]));
				} catch (ArrayIndexOutOfBoundsException e) {
					throw new IllegalEntryException("Illegal Entry: "+line);
				}
			}
		}
		
		//Fill Entries
		this.eventName = entries.remove(findEntry(entries, "eventName")).data;
		this.eventURLIdentifier = entries.remove(findEntry(entries, "evenURLIdentifier")).data;
			

		
		
	}
	
	private int findEntry(Vector<EventConfigEntry> entries, String identifier) throws IllegalEntryException{
		for (int i = 0; i < entries.size(); i++){
			if (entries.elementAt(i).identifier.trim().equalsIgnoreCase(identifier)) return i;
		}
		
		throw new IllegalEntryException("Missing entry for "+identifier);
	}
	
	/**
	 * Writes the event parameters into a config file
	 * @param configFileLocation the location where the config file is to be written
	 * @return true if the config file was succesfully written, false if the writing of the config file was aborted
	 * @throws IOException if the config file cannot be written
	 */
	public boolean writeToConfigFile(String configFileLocation) throws IOException{
		BufferedReader commandLineInput = new BufferedReader(new InputStreamReader(System.in));
		
		File configFile = new File(configFileLocation);
		if (configFile.exists()){
			System.out.print("File "+configFileLocation+" already exists. Overwrite? (y/n): ");
			String answer = "";
			while (answer.length() <= 0) answer = commandLineInput.readLine();
			if (!answer.substring(0, 1).equalsIgnoreCase("y")) return false;
		}
		else{
			configFile.createNewFile();
		}
		
		FileWriter out = new FileWriter(configFile);
		out.write("eventName:"+this.eventName+"\n");
		out.write("evenURLIdentifier:"+this.eventURLIdentifier+"\n");
		out.write("keyDerivationFunction:"+this.keyDerivationFunction+"\n");
		out.write("numberOfElections:"+this.numberOfElections+"\n");
		out.write("numberOfVoters:"+this.numberOfVoters+"\n");
		out.write("minVotingDelay:"+this.minVotingDelay+"\n");
		out.write("maxVotingDelay:"+this.maxVotingDelay+"\n");
		out.write("TANType:"+this.TANType.toString()+"\n");
		
		out.write("eventTANAuthority:");
		boolean firstEntry = true;
		for (int i = 0; i < this.eventTANAuthority.length; i++){
			if (firstEntry){
				firstEntry = false;
			}
			else{
				out.write("|");
			}
			out.write(this.eventTANAuthority[i].toString());
		}
		out.write("\n");
		
		out.write("ballotBoxServerAddress:");
		firstEntry = true;
		for (int i = 0; i < this.ballotBoxServerAddress.length; i++){
			if (firstEntry){
				firstEntry = false;
			}
			else{
				out.write("|");
			}
			out.write(this.ballotBoxServerAddress[i].toString());
		}
		out.write("\n");
		
		out.close();
		
		return true;
	}
=======
	
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
	
	
>>>>>>> d6761e8bbd95a80bc9a4fc037b606bded9d46de4
}
