package com.braintreegateway.encryption.util;

import com.braintree.org.bouncycastle.util.encoders.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class AesDecrypter {
    private static int IV_LENGTH = 16;

    public static byte[] decrypt(String data, byte[] aesKey) throws InvalidKeyException,
                                                                    IllegalBlockSizeException,
                                                                    BadPaddingException,
                                                                    InvalidAlgorithmParameterException,
                                                                    NoSuchAlgorithmException,
                                                                    NoSuchPaddingException {
        byte[] decodedData = Base64.decode(data);
        byte[] derivedIV = getIV(decodedData);
        return decrypt(data, aesKey, derivedIV);
    }

    public static byte[] decrypt(String data, byte[] aesKey, byte[] suppliedIV) throws InvalidKeyException,
                                                                                       IllegalBlockSizeException,
                                                                                       BadPaddingException,
                                                                                       InvalidAlgorithmParameterException,
                                                                                       NoSuchAlgorithmException,
                                                                                       NoSuchPaddingException {
        byte[] keyBytes = Base64.decode(aesKey);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = aesCipher();
        byte[] decodedData = Base64.decode(data);
        byte[] encryptedData = getEncryptedData(decodedData);
        IvParameterSpec ivParamSpec = new IvParameterSpec(suppliedIV);
        cipher.init(Cipher.DECRYPT_MODE, key, ivParamSpec);
        return cipher.doFinal(encryptedData);
    }

    private static byte[] getIV(byte[] data) {
        byte[] buffer = new byte[IV_LENGTH];
        for (int i = 0; i < IV_LENGTH; i++) {
            buffer[i] = data[i];
        }
        return buffer;
    }

    private static byte[] getEncryptedData(byte[] data) {
        byte[] buffer = new byte[data.length - IV_LENGTH];
        int bufferIndex = 0;
        for (int i = IV_LENGTH; i < data.length; i++) {
            buffer[bufferIndex] = data[i];
            bufferIndex++;
        }
        return buffer;
    }

    private static Cipher aesCipher() throws NoSuchAlgorithmException, NoSuchPaddingException {
        return Cipher.getInstance("AES/CBC/PKCS5Padding");
    }
}
