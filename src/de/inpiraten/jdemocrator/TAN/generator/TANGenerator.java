package de.inpiraten.jdemocrator.TAN.generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;

import de.inpiraten.jdemocrator.TAN.TANAuthority;
import de.inpiraten.jdemocrator.event.Event;
import de.inpiraten.jdemocrator.event.IllegalEntryException;
import de.inpiraten.jdemocrator.license.GPL;


public class TANGenerator {
	
	BufferedReader commandLineInput;
	Event event;
	TANAuthority tanAuthority;
	String[] masterTAN;
	
	/**
	 * Creates a TANGenerator instance from thi
	 * @param commandLineInput
	 * @throws IOException 
	 */
	public TANGenerator(BufferedReader commandLineInput) throws IOException  {
		this.commandLineInput = commandLineInput;
		
		//Get the event
		System.out.print("\nEnter the location of the event config file.\n> ");
		String configFileLocation = commandLineInput.readLine();

		try {
			this.event = new Event(configFileLocation);
		} catch (IOException e) {
			System.out.println("Config file could not be opened. Exiting.");
			System.exit(2);
		} catch (IllegalEntryException e) {
			System.out.println("Error in config file. Error message: "+e.getMessage());
			System.out.println("Exiting");
			System.exit(3);
		}
	
		//Get the Authority
		while(this.tanAuthority == null){
			System.out.println("\nChoose the TAN Authority to generate TANs for");
			for (int i = 0; i < this.event.eventTANAuthority.length; i++){
				System.out.println("("+(i+1)+") "+this.event.eventTANAuthority[i].name+" ["+this.event.eventTANAuthority[i].announceServerAddress+"]");
			}
			System.out.print("> ");
			try {
				byte choice = inputOption();
				this.tanAuthority = this.event.eventTANAuthority[choice-1];
			} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
				System.out.println("Invalid input. Please try again.");
			}
		}
		
	}

	public static void main (String[] args) {
		
		//Intromessage
		GPL.printLicenseNotice();
		System.out.println("\njDemocrator Authority TAN Generator");
		System.out.println(  "===================================\n");
		
		System.out.println("This programm will generate voting TANs for a semi-online secrete voting.");
		
		try {
			TANGenerator G = new TANGenerator(new BufferedReader(new InputStreamReader(System.in)));
			G.generateMasterTANs();
			//TODO Generate the TANs
			//TODO Mix up the TANs
			//TODO Write the TANs and master TANs to (printable) files
		} catch (IOException e) {
			System.out.println("An I/O Error occured");
			System.exit(1);
		}
		
		
		
	}
	
	public void generateMasterTANs() throws IOException {
		//Read seed from System.in
		System.out.println("You will be asked to specify a random seed. Please enter a number of random");
		System.out.println("characters to your liking. This will help to seed a secure random number");
		System.out.println("generator which will generate the master TANs for every vote and derive the");
		System.out.println("TANs. The longer your seed the stronger your master TANs.");
		System.out.print("\nEnter the seed for the TAN generator.\n> ");
		String seed = commandLineInput.readLine();
		SecureRandom random = new SecureRandom(ArrayUtils.addAll(seed.getBytes(), (""+System.currentTimeMillis()).getBytes()));
	
		//Get length of master TANs
		int masterTANlength = -1;
		while (masterTANlength < 6){
			System.out.print("\nPlease enter the length of the master TANs in bytes. (Standard: 18)\n>");
			int input;
			try {
				input = inputInteger();
				if (input > 5){
					masterTANlength = input;
				}
				else if (input > 0){
					System.out.println("Lengths shorter than 6 byte are too insecure. Please chose another length.");
				}
				else if (input == 0){
					masterTANlength = 18; //standard option
				}
				else throw new NumberFormatException("Length of master TAN must be positive");
			} catch (NumberFormatException e) {
				System.out.println("Invalid input. Please try again.");
			}
		}
		
		this.masterTAN = new String[this.event.numberOfElections];
		byte[] rawTAN = new byte[masterTANlength];
		for (int i = 0; i < this.masterTAN.length; i++){
			random.nextBytes(rawTAN);
			this.masterTAN[i] = Base64.encodeBase64String(rawTAN);
		}
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
}
