package com.braintreegateway.encryption;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.util.ByteArrayBuffer;
import org.spongycastle.util.encoders.Base64;

public class Aes {
    public byte[] generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32];
        random.nextBytes(keyBytes);
        return keyBytes;
    }

    public String encrypt(String payload, byte[] rawAesKey) {
        SecretKeySpec key = new SecretKeySpec(rawAesKey, "AES");
        Cipher cipher = aesCipher();
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(payload.getBytes());
            byte[] iv = cipher.getIV();
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
        }
        return null;
    }

    private Cipher aesCipher() {
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
