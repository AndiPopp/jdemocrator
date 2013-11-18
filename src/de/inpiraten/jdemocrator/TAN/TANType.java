package de.inpiraten.jdemocrator.TAN;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import de.inpiraten.jdemocrator.TAN.generator.KeyDerivationFunction;
import de.inpiraten.jdemocrator.event.Event;

/**
 * An abstract class for TAN types
 * @author Andi Popp
 *
 */
public abstract class TANType {

	/**
	 * A String to identify the TAN type
	 * @return A String to identify the TAN type
	 */
	public abstract String getTypeName();
	
	/**
	 * A float to identify the version of the Type
	 * @return A float to identify the version of the Type
	 */
	public abstract float getVersion ();
	
	/**
	 * A method to make the TAN Type into a string
	 */
	public abstract String toString();

	/**
	 * Genrates TANs of the given type using a master TAN
	 * @param masterTAN the master TAN the TANs will be derived from
	 * @param salt a cryptographic salt
	 * @param kdf the key derivation function used to derive the TANs from the master TAN
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 */
	public abstract TAN[] generateFromMasterTAN(String masterTAN, Event event, int numberOfKeys) throws NoSuchAlgorithmException, InvalidKeySpecException;

	/**
	 * Creates a TAN object of this type from a string
	 * @param s
	 * @return
	 * @throws TANChecksumException 
	 */
	public abstract TAN fromString(String s) throws TANChecksumException;
}
