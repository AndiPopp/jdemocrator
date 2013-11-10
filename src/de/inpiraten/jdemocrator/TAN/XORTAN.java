/**
 * 
 */
package de.inpiraten.jdemocrator.TAN;

import de.inpiraten.jdemocrator.number.Base64Number;
import de.inpiraten.jdemocrator.number.NoBase64NumberException;

public class XORTAN extends TAN {

	/**
	 * The type of the TAN
	 */
	private final XORTANType type;
	
	/**
	 * Full parameter constructor
	 * @param type
	 * @param tanIndex
	 * @param key
	 * @param salt
	 * @param checksum
	 * @param electionNumber
	 * @throws TANParameterException 
	 */
	public XORTAN(XORTANType type, long key, int salt,
			int checksum, int electionNumber) throws TANSizeException {
		super();
		this.type = type;
		this.key = key;
		this.salt = salt;
		this.checksum = checksum;
		this.electionNumber = electionNumber;
		
		if (Long.toBinaryString(key).length() > type.getKeyLength()) throw new TANSizeException("Key to long");
		if (Integer.toBinaryString(salt).length() > type.getSaltLength()) throw new TANSizeException("Salt to long");
		if (Integer.toBinaryString(checksum).length() > type.getChecksumLength()) throw new TANSizeException("Checksum to long");
	}
	
	/**
	 * Creates a TAN from a Base64 String
	 * @param type
	 * @param base64TANString
	 * @param electioNumber
	 * @throws TANParameterException
	 * @throws TANSizeException
	 */
	public XORTAN(XORTANType type, String base64TANString, int electioNumber) 
			throws TANParameterException, TANSizeException{
		
		this.type = type;
		
		try {
			//Get binary string 
			String bits = Base64Number.toBinaryString(base64TANString);
			
			//Get checksum part
			String checkSumString = bits.substring(bits.length()-this.type.getChecksumLength(), bits.length());
			this.checksum = Integer.parseInt(checkSumString, 2);
			
			//Get salt part
			String saltString = bits.substring(bits.length()-this.type.getChecksumLength()-this.type.getSaltLength(), bits.length()-this.type.getChecksumLength());
			this.salt = Integer.parseInt(saltString, 2);
			
			//Get key part
			String keyString = bits.substring(bits.length()-this.type.getChecksumLength()-this.type.getSaltLength()-this.type.getKeyLength(), bits.length()-this.type.getChecksumLength()-this.type.getSaltLength());
			this.key = Integer.parseInt(keyString, 2);
			
			
		} catch (NoBase64NumberException e) {
			throw new TANParameterException("Illegal characters in TAN: "+e.getMessage());
		} catch (IndexOutOfBoundsException e) {
			throw new TANSizeException("TAN String to long for XORTAN");
		}
	
		
		
	}
	
	/**
	 * The key part of the TAN
	 */
	long key;
	
	/**
	 * The salt part of the TAN
	 */
	int salt;
	
	/**
	 * The checksum part of the TAN
	 */
	int checksum;

	/**
	 * The number of the election the TAN is viable for
	 */
	int electionNumber;
	
	@Override
	public TANType geType() {
		return this.type;
	}

	@Override
	public int getElectionNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String toBase64String(){
		String output ="";
		
		//TODO
		
		return output;
	}
	
}
