package com.braintree.encryption;

import java.io.IOException;
import org.spongycastle.asn1.*;
import org.spongycastle.asn1.x509.RSAPublicKeyStructure;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.spongycastle.util.encoders.Base64;

public class Rsa {
	private String publicKeyString;

	public Rsa(String publicKeyString) {
		this.publicKeyString = publicKeyString;
	}

	public String encrypt(byte[] data) {
        Cipher rsa;
		try {
			rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			PublicKey publicKey = publicKey();
			rsa.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] encodedData = Base64.encode(data);
			byte[] encryptedData = rsa.doFinal(encodedData);
			return new String(Base64.encode(encryptedData));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private PublicKey publicKey() {
		try {
			byte[] decodedPublicKey = Base64.decode(publicKeyString);
			ASN1InputStream in = new ASN1InputStream(decodedPublicKey);
			DERObject obj = in.readObject();
			RSAPublicKeyStructure keyStruct = RSAPublicKeyStructure.getInstance(obj);
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(keyStruct.getModulus(), keyStruct.getPublicExponent());
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
