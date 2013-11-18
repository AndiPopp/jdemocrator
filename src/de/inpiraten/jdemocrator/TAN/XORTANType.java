package de.inpiraten.jdemocrator.TAN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.spec.PBEKeySpec;
import javax.management.RuntimeErrorException;

import org.apache.commons.lang3.ArrayUtils;

import com.sun.crypto.provider.PBKDF2HmacSHA1Factory;

import de.inpiraten.jdemocrator.TAN.generator.KeyDerivationFunction;
import de.inpiraten.jdemocrator.event.Event;
import de.inpiraten.jdemocrator.event.IllegalEntryException;

public class XORTANType extends TANType {

	/**
	 * A string the identifies the type of TAN used
	 */
	private final static String typeName = "xorTAN";
	
	/**
	 * An integer to identifies the version of the Type
	 */
	private final float version;
	
	/**
	 * The length of the key part of the key in byte
	 */
	private final short keyLength;
	
	/**
	 * The length of the pepper part of the key in byte
	 */
	private final short pepperLength;
	
	/**
	 * The length of the checksum part of the key in byte 
	 */
	private final byte checksumLength;

	public XORTANType(short keyLength, short pepperLength,
			byte checksumLength, float version) throws IllegalArgumentException {
		super();
		this.version = version;
		
		if (version >= 1){

			this.keyLength = keyLength;
			this.pepperLength = pepperLength;
			this.checksumLength = checksumLength;
			if (keyLength > 4096) throw new IllegalArgumentException("Key length to long. "+typeName+" in Version "+version+" supports only up to 4096 byte.");
			if (pepperLength > 4096) throw new IllegalArgumentException("Pepper length to long. "+typeName+" in Version "+version+" supports only up to 4096 byte.");
			if (checksumLength > 32) throw new IllegalArgumentException("Checksum length to long. "+typeName+" in Version "+version+" supports only up to 32 byte.");
			if (keyLength < 1 || pepperLength < 1 || checksumLength < 1) throw new IllegalArgumentException("Length for all attributes needs to be greater than 0");
		}
		else {
			throw new IllegalArgumentException("Unknown version for "+typeName+": "+version);
		}
	}

	@Override
	public String getTypeName() {
		return typeName;
	}

	@Override
	/**
	 * Give the version of this key as integer
	 * @return version / 10
	 */
	public float getVersion() {
		return this.version;
	}

	/**
	 * Get the length of the key part of the TAN in byte
	 */
	public short getKeyLength(){
		return this.keyLength;
	}
	
	/**
	 * Get the length of the pepper part of the TAN in byte
	 */
	public short getPepperLength(){
		return this.pepperLength;
	}
	
	/**
	 * Get the length of the checksum part of the TAN in byte
	 */
	public byte getChecksumLength(){
		return this.checksumLength;
	}
	
	public int getTotalLength(){
		return this.keyLength + this.pepperLength + this.checksumLength;
	}

	@Override
	public String toString() {
		return this.getTypeName()+","+this.version+","+this.keyLength+","+this.pepperLength+","+this.checksumLength;
	}
	
	/**
	 * Creates a XORTANType from a string written by {@link #toString()}
	 * @param s
	 * @throws IllegalEntryException
	 */
	public XORTANType(String s) throws IllegalEntryException{
		String[] data = s.split(",");
		if (data.length != 5) throw new IllegalEntryException(s+" is not a valid TAN type string (to many ,)");
		this.version = Float.parseFloat(data[1]);
		this.keyLength = Short.parseShort(data[2]);
		this.pepperLength = Short.parseShort(data[3]);
		this.checksumLength = Byte.parseByte(data[4]);
	}
	
	/**
	 * Creates a new XORTANType by a command line input
	 * @param commandLineInput the commandLineInput used. Usually a BufferedReader of System.in
	 * @return the XORTANType created in the command line input processs. null if the creation of the XORTANType object failed with an IllegalArgumentException
	 * @throws IOException
	 */
	public static XORTANType createTANType(BufferedReader commandLineInput) throws IOException{
		short keyLength;
		short pepperLength;
		byte checksumLength;
		
		while (true){
			try {
				System.out.print("Please enter key length\n>");
				keyLength = Short.parseShort(commandLineInput.readLine());
				if (keyLength > 4096 || keyLength < 1) throw new NumberFormatException("Checksum length has to be between 1 and 4096");
				break;
			} catch (NumberFormatException e) {
				System.out.println("Invalid Input. Please try again.  (Please note that key length needs to be between 1 and 4096 (inclusive))");
			}
		}
		
		while (true){
			try {
				System.out.print("Please enter pepper length\n>");
				pepperLength = Short.parseShort(commandLineInput.readLine());
				if (pepperLength > 4096 || pepperLength < 1) throw new NumberFormatException("Pepper length has to be between 1 and 4096");
				break;
			} catch (NumberFormatException e) {
				System.out.println("Invalid Input. Please try again. (Please note that pepper length needs to be between 1 and 4096 (inclusive))");
			}
		}
		
		while (true){
			try {
				System.out.print("Please enter checksum length\n>");
				checksumLength = Byte.parseByte(commandLineInput.readLine());
				if (checksumLength > 32 || checksumLength < 1) throw new NumberFormatException("Checksum length has to be between 1 and 32.");
				break;
			} catch (NumberFormatException e) {
				System.out.println("Invalid Input. Please try again. (Please note that pepper length needs to be between 1 and 32 (inclusive))");
			}
		}
		
		try {
			return new XORTANType(keyLength, pepperLength, checksumLength, 1.0f);
		} catch (IllegalArgumentException e) {
			System.out.println("TANType could not be created because of illegal arguments");
			return null;
		}
	}

	@Override
	public XORTAN[] generateFromMasterTAN(String masterTAN, Event event, int numberOfKeys) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PBEKeySpec keySpec = null;
		try {
			keySpec = new PBEKeySpec(masterTAN.toCharArray(), event.eventName.getBytes("UTF-8"), event.keyDerivationIterations, ((this.keyLength+this.pepperLength)*8*numberOfKeys));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		KeyDerivationFunction kdf = KeyDerivationFunction.getKeyDerivationFunction(event.keyDerivationFunction);
		
		byte[] rawKeyData = kdf.generateKeyData(keySpec);
		
		XORTAN[] output = new XORTAN[numberOfKeys];
		int runningIndex = 0;
		for (int i = 0; i < output.length; i++){
			
			byte[] key = ArrayUtils.subarray(rawKeyData, runningIndex, runningIndex+this.getKeyLength());
			runningIndex += this.getKeyLength();
			
			byte[] pepper = ArrayUtils.subarray(rawKeyData, runningIndex, runningIndex+this.getPepperLength());
			runningIndex += this.getPepperLength();
			
			output[i] = new XORTAN(this, key, pepper);
		}
		
		return output;
	}

	@Override
	public TAN fromString(String s) throws TANChecksumException {
		return new XORTAN(this, s);
	}


}
