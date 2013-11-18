package de.inpiraten.jdemocrator.TAN.generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;

import de.inpiraten.jdemocrator.TAN.TAN;
import de.inpiraten.jdemocrator.TAN.TANAuthority;
import de.inpiraten.jdemocrator.event.Event;
import de.inpiraten.jdemocrator.event.IllegalEntryException;
import de.inpiraten.jdemocrator.license.GPL;

/**
 * A class with a static main function to provide a command line interface for creating TANs
 * @author Andi Popp
 *
 */
public class TANGenerator {
	
	BufferedReader commandLineInput;
	Event event;
	TANAuthority tanAuthority;
	String[] masterTAN;
	
	/**
	 * Creates a TANGenerator instance from a command line
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
		
		//Intro message
		GPL.printLicenseNotice();
		System.out.println("\njDemocrator Authority TAN Generator");
		System.out.println(  "===================================\n");
		
		System.out.println("This programm will generate voting TANs for a semi-online secrete voting.");
		
		try {
			//Initialize TAN Generator
			TANGenerator G = new TANGenerator(new BufferedReader(new InputStreamReader(System.in)));
			
			//Randomly generate master TANs
			G.generateMasterTANs();
			
			//Generate TANs from master TANs
			TAN[][] tans = new TAN[G.event.numberOfElections][];
			for (int i = 0; i < tans.length; i++){
				try {
					tans[i] = G.event.TANType.generateFromMasterTAN(G.masterTAN[i], G.event, G.event.numberOfVoters);
				} catch (NoSuchAlgorithmException e) {
					System.out.println("The Key Derivation function described as "+G.event.keyDerivationFunction+" is not supported. Exiting.");
					System.exit(2);
				} catch (InvalidKeySpecException e) {
					System.out.println("Invalied Key Spec Exception while generating TANs. Exiting.");
					System.exit(3);
				}
			}
			
			//Mix up the TANs
			for (int i = 0; i < tans.length; i++){
				shuffle(tans[i]);
			}
			
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

	private static void shuffle(TAN[] array){
		Random r = new Random();
		for (int i = array.length-1; i >= 0; i--){
			int j = r.nextInt(i+1);
			TAN h = array[j];
			array[j] = array[i];
			array[i] = h;
		}
	}
}
