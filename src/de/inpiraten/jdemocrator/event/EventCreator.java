/**
 * 
 */
package de.inpiraten.jdemocrator.event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import de.inpiraten.jdemocrator.license.GPL;

/**
 * @author Andi Popp
 * A static class with a main method to create a command line interface for creating
 * events
 */
public class EventCreator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GPL.printLicenseNotice();

		System.out.println("\nEvent Creator");
		System.out.println("=============\n");
		
		BufferedReader commandLineInput = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("What do you want to do?");
		System.out.println("(1) Create a new event and save it to a config file");
		System.out.println("(2) Check and echo an existing config file");
		System.out.print("> ");
		
		while(true){
			try {
				int input = Integer.parseInt(commandLineInput.readLine());
				if (input == 1){
					createEvent(commandLineInput);
					break;
				}
				else if (input == 2){
					checkEvent(commandLineInput);
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

	
	private static void createEvent(BufferedReader commandLineInput) throws IOException{
		System.out.println("Enter the name of your event");
		System.out.print("> ");
		String EventName = commandLineInput.readLine();
		
		String URLIdentifier = "";
		while (true){
			System.out.print("Enter the URL identifier for your event\n> ");
			URLIdentifier = commandLineInput.readLine();
			if(checkURL(URLIdentifier)){
				break;
			}
			else{
				System.out.println("Please use only alphanumeric characters.");
			}
		}
		
		//TODO further fields, create the object, echo the object, write the object to file
		
	}

	private static boolean checkURL(String s){
		byte[] chars = s.getBytes();
		for (int i = 0; i < chars.length; i++){
			if (chars[i] < 48) return false;
			if (chars[i] > 57 && chars[i] < 65) return false;
			if (chars[i] > 90 && chars[i] < 97) return false;
			if (chars[i] > 122) return false;
		}
		return true;
	}
	
	private static void checkEvent(BufferedReader commandLineInput){
		//TODO Complete procedure
	}
}
