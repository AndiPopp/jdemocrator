/**
 * 
 */
package de.inpiraten.jdemocrator.event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import de.inpiraten.jdemocrator.TAN.TANAuthority;
import de.inpiraten.jdemocrator.TAN.TANType;
import de.inpiraten.jdemocrator.TAN.XORTANType;
import de.inpiraten.jdemocrator.license.GPL;

/**
 * @author Andi Popp
 * A class with a main method to create a command line interface for creating
 * events
 */
public class EventCreator {

	private final BufferedReader commandLineInput;
	
	/**
	 * @param commandLineInput
	 */
	public EventCreator(BufferedReader commandLineInput) {
		super();
		this.commandLineInput = commandLineInput;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GPL.printLicenseNotice();

		System.out.println("\nEvent Creator");
		System.out.println("=============\n");
		
		BufferedReader commandLineInput = new BufferedReader(new InputStreamReader(System.in));
		EventCreator EC = new EventCreator(commandLineInput);
		
		System.out.println("What do you want to do?");
		System.out.println("(1) Create a new event and save it to a config file");
		System.out.println("(2) Check and echo an existing config file");
		System.out.print("> ");
		
		while(true){
			try {
				byte input = Byte.parseByte(commandLineInput.readLine());
				if (input == 1){
					EC.createEvent();
					break;
				}
				else if (input == 2){
					EC.checkEvent();
					break;
				}
				else throw new NumberFormatException("Command input out of range");
			} catch (NumberFormatException e) {
				System.out.println("Invalid Input. Please try again.");
				System.out.print("> ");
			} catch (IOException e) {
				System.out.println("I/O error occured. Exiting.");
				System.exit(1);
			}
		}
		
				
	}

	
	private boolean createEvent() throws IOException{
		//Event name
		System.out.println("\nEnter the name of your event");
		System.out.print("> ");
		String EventName = commandLineInput.readLine();
		
		//URL identifier
		String URLIdentifier = "";
		while (true){
			System.out.print("\nEnter the URL identifier for your event\n> ");
			URLIdentifier = commandLineInput.readLine();
			if(checkURLSafety(URLIdentifier)){
				break;
			}
			else{
				System.out.println("\nPlease use only alphanumeric characters.");
			}
		}
		
		
		//TAN Type
		TANType tanType = null;
		while (tanType == null){
			try {
				System.out.println("\nChoose the type of TAN used for your event");
				System.out.println("(1) XOR-TAN (Standard)");
				System.out.print("> ");
				byte input = inputOption();
				if (input == 0) input = 1; //standard option
				if (input == 1){
					tanType = XORTANType.createTANType(commandLineInput);
					if (tanType == null) System.out.println("TAN Type could not be created");
				}
				else throw new NumberFormatException("Command input out of range");
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please try again.");
			}
		}
		
		//Key Derivation Function
		String keyDerivationFunction = null;
		while (keyDerivationFunction == null){
			try {
				System.out.println("\nChoose a key derivation function fot his event");
				System.out.println("(1) PBKDF2 with HMAC-SHA1 (Standard)");
				System.out.print("> ");
				byte input = inputOption();
				if (input == 0) input = 1; //standard option
				if (input == 1){
					keyDerivationFunction = "PBKDF2WithHmacSHA1";
				}
				else throw new NumberFormatException("Command input out of range");
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please try again.");
			}
		}
		
		//Key Derivation Iterations
		int keyDerivationIterations = -1;
		while (keyDerivationIterations < 0){
			try {
				System.out.println("\nEnter the number of key derivation iterations for this event (Standard: 128)");
				System.out.print("> ");
				int input = inputInteger();
				if (input == 0) keyDerivationIterations = 128; //standard
				else if (input > 0) keyDerivationIterations = input;
				else throw new NumberFormatException("Number of key derivation iterations cannot be negative");
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please try again.");
			}
		}
		
		//Number of elections
		int numberOfElections = -1;
		while (numberOfElections < 0){
			try {
				System.out.println("\nEnter the number of elections for this event");
				System.out.print("> ");
				int input = inputInteger();
				if (input > -1)	numberOfElections = input;
				else throw new NumberFormatException("Number of elections cannot be negative");
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please try again.");
			}
		}
		
		//Number of voters
		int numberOfVoters = -1;
		while (numberOfVoters < 0){
			try {
				System.out.println("\nEnter the number of voters for this event");
				System.out.print("> ");
				int input = inputInteger();
				if (input > -1)	numberOfVoters = input;
				else throw new NumberFormatException("Number of voters cannot be negative");
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please try again.");
			}
		}
		
		//Min Voting delay
		int minVotingDelay = -1;
		while (minVotingDelay < 0){
			try {
				System.out.println("\nEnter the min voting delay for this event");
				System.out.print("> ");
				int input = inputInteger();
				if (input > -1)	minVotingDelay = input;
				else throw new NumberFormatException("min voting delay cannot be negative");
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please try again.");
			}
		}
		
		//Max Voting delay
		int maxVotingDelay = -1;
		while (maxVotingDelay < 0){
			try {
				System.out.println("\nEnter the max voting delay for this event");
				System.out.print("> ");
				int input = inputInteger();
				if (input > -1)	maxVotingDelay = input;
				else throw new NumberFormatException("max voting delay cannot be negative");
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please try again.");
			}
		}
		
		//Ballot box server adresses
		int numberOfBallotBoxServers = -1;
		while (numberOfBallotBoxServers < 1){
			try {
				System.out.println("\nEnter the number of ballot box servers for this event");
				System.out.print("> ");
				int input = inputInteger();
				if (input > 0)	numberOfBallotBoxServers = input;
				else throw new NumberFormatException("numberOfBallotBoxServers must be positive");
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please try again.");
			}
		}
		URL[] ballotBoxServerAdress = new URL[numberOfBallotBoxServers];
		for (int i = 0; i < ballotBoxServerAdress.length; i++){
			while (ballotBoxServerAdress[i] == null){
				try {
					System.out.print("\nEnter the URL for ballot box server number "+(i+1)+"\n> ");
					String adressInput = commandLineInput.readLine();
					if (!checkConfigSafety(adressInput)) throw new MalformedURLException("Illegal character in URL: "+adressInput);
					ballotBoxServerAdress[i] = new URL(adressInput);
				} catch (MalformedURLException e) {
					System.out.println("Invalid input. Please try again. (Note: characters ',' and ';' not allowed.)");
				}
			}
		}
		
		//TAN Authorities
		int numberOfTANAuthorities = -1;
		while (numberOfTANAuthorities < 1){
			try {
				System.out.println("\nEnter the number of TAN Authorities for this event");
				System.out.print("> ");
				int input = inputInteger();
				if (input > 0)	numberOfTANAuthorities = input;
				else throw new NumberFormatException("numberOfTANAuthorities must be positive");
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please try again.");
			}
		}
		TANAuthority[] tanAuthorities = new TANAuthority[numberOfTANAuthorities];
		for (int i = 0; i < tanAuthorities.length; i++){
			while (tanAuthorities[i] == null){
				try {
					System.out.print("\nEnter the name of TAN Authority "+(i+1)+"\n> ");
					String name = commandLineInput.readLine();
					if (!checkConfigSafety(name)) throw new MalformedURLException("Illegal character in name: "+name);
					System.out.print("\nEnter the URL for the announce server for TAN Authority "+(i+1)+"\n> ");
					URL announceServerAdress = new URL(commandLineInput.readLine());
					if (!checkConfigSafety(announceServerAdress.toString())) throw new MalformedURLException("Illegal character in URL: "+announceServerAdress);
					tanAuthorities[i] = new TANAuthority(name, announceServerAdress);
				} catch (MalformedURLException e) {
					System.out.println("Invalid input. Please try again. (Note: characters ',' and ';' not allowed.)");
				}
			}
		}
		
		Event E = new Event(EventName, URLIdentifier, tanType, keyDerivationFunction, keyDerivationIterations, numberOfElections, numberOfVoters, minVotingDelay, maxVotingDelay, ballotBoxServerAdress, tanAuthorities);
		System.out.println("\nEvent overview:");
		System.out.println("---------------");
		E.echo(System.out);
		
		
		while (true){
			System.out.println("\nDo you want to write this config into a config file? (y/n) ");
			String inputString = commandLineInput.readLine();
			if (inputString.equalsIgnoreCase("y") || inputString.equalsIgnoreCase("yes")){
				boolean wasWritten = false;
				while (!wasWritten){
					System.out.print("Please enter config file location\n> ");
					String fileLocationInput = commandLineInput.readLine();
					try {
						wasWritten = E.writeToConfigFile(fileLocationInput);
					} catch (IOException e) {
						System.out.println("Config file could not be written");
					}
				}
				if (wasWritten) {
					System.out.println("Event succesfully written to config file.");
					System.out.println("Goodbye");
					break;
				}
			}
			else if (inputString.equalsIgnoreCase("n") || inputString.equalsIgnoreCase("no")){
				System.out.println("OK. Goodbye.");
				break;
			}
			else{
				System.out.println("Invalid input. Please try again.");
			}
		}
		
		
		return true;
	}

	public static boolean checkURLSafety(String s){
		byte[] chars = s.getBytes();
		for (int i = 0; i < chars.length; i++){
			if (chars[i] < 48) return false;
			if (chars[i] > 57 && chars[i] < 65) return false;
			if (chars[i] > 90 && chars[i] < 97) return false;
			if (chars[i] > 122) return false;
		}
		return true;
	}
	
	public static boolean checkConfigSafety(String s){
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++){
			if (chars[i] == ',') return false;
			if (chars[i] == ';') return false;
		}
		return true;
	}
	
	private byte inputOption() throws NumberFormatException, IOException{
		String inputString = commandLineInput.readLine();
//		System.out.println("DEBUG: Read the following line: "+inputString);
		if (inputString.equals("")) return 0;
		else return Byte.parseByte(inputString);
	}
	
	private int inputInteger() throws NumberFormatException, IOException{
		String inputString = commandLineInput.readLine();
//		System.out.println("DEBUG: Read the following line: "+inputString);
		if (inputString.equals("")) return 0;
		else return Integer.parseInt(inputString);
	}
	
	private void checkEvent() throws IOException{
		System.out.print("Enter the location of the config file\n> ");
		String configFileLocation = commandLineInput.readLine();
		Event E = null;
		try {
			E = new Event(configFileLocation);
		} catch (IOException e) {
			System.out.println("Config file could not be read");
			return;
		} catch (IllegalEntryException e) {
			System.out.println("Incorrect config file. Error message: "+e.getMessage());
			return;
		}
		System.out.println("\nEvent overview:");
		System.out.println("---------------");
		E.echo(System.out);
	}
}
