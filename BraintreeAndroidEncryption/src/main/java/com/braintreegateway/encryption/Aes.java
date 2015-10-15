package com.braintreegateway.encryption;

import com.braintree.org.bouncycastle.util.encoders.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class Aes {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    public static final int KEY_LENGTH = 32;
    public static final int IV_LENGTH = 16;

    public static String encrypt(String data, byte[] aesKey, byte[] iv) throws BraintreeEncryptionException {
        SecretKeySpec key = new SecretKeySpec(aesKey, ALGORITHM);
        Cipher cipher = aesCipher();
        try {
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParamSpec);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            byte[] buffer = Arrays.copyOf(iv, iv.length + encryptedBytes.length);
            System.arraycopy(encryptedBytes, 0, buffer, iv.length, encryptedBytes.length);
            return new String(Base64.encode(buffer));
        } catch (InvalidKeyException e) {
            throw new BraintreeEncryptionException("Invalid Key: " + e.getMessage());
        } catch (BadPaddingException e) {
            throw new BraintreeEncryptionException("Bad Padding: " + e.getMessage());
        } catch (IllegalBlockSizeException e) {
            throw new BraintreeEncryptionException("Illegal Block Size: " + e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            throw new BraintreeEncryptionException("Invalid Algorithm: " + e.getMessage());
        }
    }

    private static Cipher aesCipher() throws BraintreeEncryptionException {
        try {
            return Cipher.getInstance(TRANSFORMATION);
        } catch (NoSuchAlgorithmException e) {
            throw new BraintreeEncryptionException("No Such Algorithm: " + e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new BraintreeEncryptionException("No Such Padding: " + e.getMessage());
        }
    }
}
