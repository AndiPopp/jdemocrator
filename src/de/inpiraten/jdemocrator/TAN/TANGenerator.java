package de.inpiraten.jdemocrator.TAN;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.lang3.ArrayUtils;


public class TANGenerator {
	
	SecretKeyFactory kdf;
	byte[] salt;
	
	/**
	 * Constructs a TANGenerator based on PBKDF2 with HMAC-SHA1 using a specific password and salt
	 * @param password
	 * @param salt
	 */
	public TANGenerator(String password, String salt) {
		
		try {
			this.kdf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			this.salt = salt.getBytes("UTF-8");
	    } catch (NoSuchAlgorithmException e) {
	    	throw new RuntimeException(e);
	    } catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} 
	}

	public static void main (String[] args) throws IOException, InvalidKeySpecException{
		
		//Read seed from System.in
		System.out.print("Enter the seed for the TAN generator: ");
		BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
		String seed = b.readLine();

		TANGenerator G = new TANGenerator(seed, "todo");
		
		
		XORTANType type = new XORTANType(6, 2, 1, 1);
		XORTAN[] tans = G.generateXORTANs(type, 1000, seed);

		
		for (int i = 0; i < tans.length; i++){
			System.out.println(tans[i].toBase64String());
		}
	}
	
	/**
	 * Generates raw key data from the given key derivation function
	 * @param password
	 * @param Iteration
	 * @param numberOfBytes The 
	 * @return an array of raw byte key data
	 * @throws InvalidKeySpecException if the parameters are not applicable for PBEKeySpec
	 */
	public byte[] generateKeyData (String password, int Iteration, int numberOfBytes){
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(),salt,Iteration,numberOfBytes*8);
		try {
			return this.kdf.generateSecret(keySpec).getEncoded();
		} catch (InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Generates an array of XORTANs
	 * @param type the type specification for the XORTANs
	 * @param number the number of XORTANs to be generated
	 * @param masterTAN the MasterTAN for this array of XORTANs
	 * @return a XORTAN array of length number
	 */
	public XORTAN[] generateXORTANs (XORTANType type, int number, String masterTAN){
		int TANSize = type.getKeyLength() + type.getPepperLength();
		byte[] rawKeyData = generateKeyData(masterTAN, 128, number*TANSize);
		XORTAN[] output = new XORTAN[number];
		int runningIndex = 0;
		for (int i = 0; i < output.length; i++){
			
			byte[] key = ArrayUtils.subarray(rawKeyData, runningIndex, runningIndex+type.getKeyLength());
			runningIndex += type.getKeyLength();
			
			byte[] pepper = ArrayUtils.subarray(rawKeyData, runningIndex, runningIndex+type.getPepperLength());
			runningIndex += type.getPepperLength();
			
			output[i] = new XORTAN(type, key, pepper);
		}
		
		return output;
	}
}
