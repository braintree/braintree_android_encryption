package com.braintreegateway.encryption.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.util.ByteArrayBuffer;
import org.spongycastle.util.encoders.Base64;

public class AesDecrypter {
    private static int IV_LENGTH = 16;

    public static byte[] decrypt(String data, byte[] aesKey) {
        byte[] keyBytes = Base64.decode(aesKey);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = aesCipher();
        try {
            byte[] decodedData = Base64.decode(data);
            byte[] encryptedData = getEncryptedData(decodedData);
            IvParameterSpec ivParamSpec = new IvParameterSpec(getIV(decodedData));
            cipher.init(Cipher.DECRYPT_MODE, key, ivParamSpec);
            return cipher.doFinal(encryptedData);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] getIV(byte[] data) {
        ByteArrayBuffer buffer = new ByteArrayBuffer(IV_LENGTH);
        for (int i = 0; i < IV_LENGTH; i++) {
            byte[] singleByte = { data[i] };
            buffer.append(singleByte, 0, 1);
        }
        return buffer.toByteArray();
    }

    private static byte[] getEncryptedData(byte[] data) {
        int dataLength = data.length - IV_LENGTH;
        ByteArrayBuffer buffer = new ByteArrayBuffer(dataLength);
        for (int i = IV_LENGTH; i < data.length; i++) {
            byte[] singleByte = { data[i] };
            buffer.append(singleByte, 0, 1);
        }
        return buffer.toByteArray();
    }

    private static Cipher aesCipher() {
        try {
            return Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
