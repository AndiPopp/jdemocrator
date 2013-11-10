package de.inpiraten.jdemocrator.TAN;

public class XORTANType extends TANType {

	/**
	 * A string the identifies the type of TAN used
	 */
	private final static String typeName = "xorTAN";
	
	/**
	 * An integer to identifies the version of the Type
	 */
	private final int version;
	
	/**
	 * The length of the key part of the key in Bit
	 */
	private final int keyLength;
	
	/**
	 * The length of the salt part of the key in Bit
	 */
	private final int saltLength;
	
	/**
	 * The length of the checksum part of the key in Bit 
	 */
	public final int checksumLength;

	public XORTANType(int indexLength, int keyLength, int saltLength,
			int checksumLength, int version) throws TANParameterException {
		super();
		this.version = version;
		
		if (version >= 10){
			if (saltLength > 30 || saltLength <1) throw new TANParameterException("Salt length "+saltLength+" illegal in "+typeName+" TAN type version "+version);
			if (checksumLength > 30 || checksumLength <1) throw new TANParameterException("Checksum length "+checksumLength+" illegal in "+typeName+" TAN type version "+version);
			if (keyLength > 60 || keyLength <1) throw new TANParameterException("Key length "+keyLength+" illegal in "+typeName+" TAN type version "+version);
		
			this.keyLength = keyLength;
			this.saltLength = saltLength;
			this.checksumLength = checksumLength;
		}
		else {
			throw new TANParameterException("Unknown version for "+typeName+": "+version);
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
	public int getVersion() {
		return this.version;
	}

	/**
	 * Get the length of the key part of the TAN in bits
	 */
	public int getKeyLength(){
		return this.keyLength;
	}
	
	/**
	 * Get the length of the salt part of the TAN in bits
	 */
	public int getSaltLength(){
		return this.saltLength;
	}
	
	/**
	 * Get the length of the checksum part of the TAN in bits
	 */
	public int getChecksumLength(){
		return this.saltLength;
	}
}
