/**
 * 
 */
package de.inpiraten.jdemocrator.TAN;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author Andi Popp
 *
 */
public class XORTAN extends TAN {

	private final XORTANType type;
	
	public byte[] key;
	
	public byte[] pepper;
	
	
	/**
	 * Full parameter constructor
	 * @param type
	 * @param key
	 * @param pepper
	 */
	public XORTAN(XORTANType type, byte[] key, byte[] pepper) throws IllegalArgumentException{
		super();
		this.type = type;
		this.key = key;
		this.pepper = pepper;
		
		if (key.length != this.type.getKeyLength()) throw new IllegalArgumentException("Wrong key size");
		if (pepper.length != this.type.getPepperLength()) throw new IllegalArgumentException("Wrong pepper size");
		
		
		
	}

	public XORTAN(XORTANType type, String base64String) throws TANChecksumException{
		this.type = type;
		
		byte[] abc = Base64.decodeBase64(base64String);
		if (abc.length != this.type.getTotalLength()) throw new IllegalArgumentException("TAN sizes do not match");
	
		this.key = ArrayUtils.subarray(abc, 0, this.type.getKeyLength());
		this.pepper = ArrayUtils.subarray(abc, this.type.getKeyLength(), this.type.getKeyLength()+this.type.getPepperLength());
		byte[] checksum = ArrayUtils.subarray(abc, this.type.getKeyLength()+this.type.getPepperLength(), this.type.getKeyLength()+this.type.getPepperLength()+this.type.getChecksumLength());

		if (!this.checkChecksum(checksum)) throw new TANChecksumException();
	}
	
	/* (non-Javadoc)
	 * @see de.inpiraten.jdemocrator.TAN.TAN#geType()
	 */
	@Override
	public TANType getType() {
		return this.type;
	}

	/**
	 * Compares the given checksum with the calculated checksum
	 * @param givenChecksum to checksum to compare with the calculated checksum
	 * @return true if the TAN's checksum equals the result of calculateChecksum(); false otherwise
	 */
	public boolean checkChecksum(byte[] givenChecksum){
		
		byte[] checksum = this.calculateChecksum();
		
		if (checksum.length != givenChecksum.length) return false;
	
		for (int i = 0; i < checksum.length; i++){
			if (givenChecksum[i] != checksum[i]) return false;
		}
		
		return true;
	}
	
	/**
	 * Calculates the checksum of the TAN in the given checksum length n
	 * @return the first n bytes of the the SHA256 hash of the combined array of key and pepper
	 */
	public byte[] calculateChecksum(){
		byte[] keyAndPepper = ArrayUtils.addAll(this.key, this.pepper);
		return ArrayUtils.subarray(DigestUtils.sha256(keyAndPepper), 0, this.type.getChecksumLength());
	}

	/**
	 * Gives a base64 String representation of the TAN
	 * @return a base64 String representation of the TAN
	 */
	public String toBase64String(){
		byte[] ab = ArrayUtils.addAll(this.key, this.pepper);
		byte[] abc = ArrayUtils.addAll(ab, this.calculateChecksum());
		return Base64.encodeBase64String(abc);
	}
}
