/**
 * 
 */
package de.inpiraten.jdemocrator.TAN.generator;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * @author Andi Popp
 *
 */
public abstract class KeyDerivationFunction {

	/**
	 * Generate byte data specified by the given key spec
	 * @param keySpec
	 * @return
	 * @throws InvalidKeySpecException
	 */
	public abstract byte[] generateKeyData(PBEKeySpec keySpec) throws InvalidKeySpecException;
	
	/**
	 * Get the name of the algroithm used for this object
	 * @return
	 */
	public abstract String getAlgorithm();
	
	public static KeyDerivationFunction getKeyDerivationFunction(String algorithm) throws NoSuchAlgorithmException{
		if (false){ //Placeholder for implementing other key derivation functions
			return null;
		}
		else{
			SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm);
			return new JavaxCryptoBasedKeyDerivationFunction(skf);
		}
	}
}
