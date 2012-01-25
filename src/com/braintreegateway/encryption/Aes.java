package com.braintreegateway.encryption;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.util.ByteArrayBuffer;
import org.bouncycastle.crypto.util.encoders.Base64;

public class Aes {
    private final String ALGORITHM = "AES";
    private final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private final int KEY_LENGTH = 32;
    private final int IV_LENGTH = 16;

    public byte[] generateKey() {
        return secureRandomBytes(KEY_LENGTH);
    }

    public byte[] generateIV() {
        return secureRandomBytes(IV_LENGTH);
    }

    public String encrypt(String data, byte[] aesKey) {
        byte[] generatedIV = generateIV();
        return encrypt(data, aesKey, generatedIV);
    }

    public String encrypt(String data, byte[] aesKey, byte[] iv) {
        SecretKeySpec key = new SecretKeySpec(aesKey, ALGORITHM);
        Cipher cipher = aesCipher();
        try {
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParamSpec);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            ByteArrayBuffer buffer = new ByteArrayBuffer(encryptedBytes.length + iv.length);
            buffer.append(iv, 0, iv.length);
            buffer.append(encryptedBytes, 0, encryptedBytes.length);
            return new String(Base64.encode(buffer.toByteArray()));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Cipher aesCipher() {
        try {
            return Cipher.getInstance(TRANSFORMATION);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] secureRandomBytes(int size) {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[size];
        random.nextBytes(keyBytes);
        return keyBytes;
    }
}
