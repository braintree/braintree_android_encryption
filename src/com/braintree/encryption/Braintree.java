package com.braintree.encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.util.ByteArrayBuffer;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Braintree {
    private String publicKey;
    private byte[] aesKey;
	public byte[] iv;

    public Braintree(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public byte[] getAesKey() {
        SecureRandom random = new SecureRandom();
        return random.generateSeed(32);
    }

    public void setAesKey(byte[] key) {
        this.aesKey = key;
    }

    public String encrypt(String data) {
    	SecretKeySpec key = new SecretKeySpec(getAesKey(), "AES");
    	Cipher cipher = aesCipher();
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] encryptedBytes = cipher.doFinal(data.getBytes());
			iv = cipher.getIV();
			ByteArrayBuffer buffer = new ByteArrayBuffer(encryptedBytes.length + iv.length);
			buffer.append(iv, 0, iv.length);
			buffer.append(encryptedBytes, 0, encryptedBytes.length);
			System.out.println(encryptedBytes.length);
			return Base64.encodeToString(buffer.toByteArray(), Base64.NO_WRAP);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }

	private Cipher aesCipher() {
		try {
			return Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
    
    
}