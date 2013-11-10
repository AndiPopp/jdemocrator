package de.inpiraten.jdemocrator.TAN;

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
	private final int keyLength;
	
	/**
	 * The length of the pepper part of the key in byte
	 */
	private final int pepperLength;
	
	/**
	 * The length of the checksum part of the key in byte 
	 */
	public final int checksumLength;

	public XORTANType(int keyLength, int pepperLength,
			int checksumLength, int version) throws IllegalArgumentException {
		super();
		this.version = version;
		
		if (version >= 1){

			this.keyLength = keyLength;
			this.pepperLength = pepperLength;
			this.checksumLength = checksumLength;
			if (checksumLength > 32) throw new IllegalArgumentException("Checksum length to long. "+typeName+" in Version "+version+" supports only up to 32 byte.");
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
	public int getKeyLength(){
		return this.keyLength;
	}
	
	/**
	 * Get the length of the pepper part of the TAN in byte
	 */
	public int getPepperLength(){
		return this.pepperLength;
	}
	
	/**
	 * Get the length of the checksum part of the TAN in byte
	 */
	public int getChecksumLength(){
		return this.checksumLength;
	}
	
	public int getTotalLength(){
		return this.keyLength + this.pepperLength + this.checksumLength;
	}
}
