/**
 * 
 */
package de.inpiraten.jdemocrator.TAN.generator;

import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * @author andi
 *
 */
public class JavaxCryptoBasedKeyDerivationFunction extends
		KeyDerivationFunction {

	public final SecretKeyFactory secretKeyFactory;
	
	/**
	 * Full parameter constructor
	 * @param secretKeyFactory
	 */
	public JavaxCryptoBasedKeyDerivationFunction(
			SecretKeyFactory secretKeyFactory) {
		super();
		this.secretKeyFactory = secretKeyFactory;
	}

	/* (non-Javadoc)
	 * @see de.inpiraten.jdemocrator.TAN.generator.KeyDerivationFunction#generateKeyData(javax.crypto.spec.PBEKeySpec)
	 */
	@Override
	public byte[] generateKeyData(PBEKeySpec keySpec) throws InvalidKeySpecException {
		return this.secretKeyFactory.generateSecret(keySpec).getEncoded();
	}

	/* (non-Javadoc)
	 * @see de.inpiraten.jdemocrator.TAN.generator.KeyDerivationFunction#getAlgorithm()
	 */
	@Override
	public String getAlgorithm() {
		return this.secretKeyFactory.getAlgorithm();
	}

}
