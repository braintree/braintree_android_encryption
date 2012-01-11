package com.braintreegateway.encryption.test;

import java.util.Arrays;

import org.spongycastle.util.encoders.Base64;

import android.test.AndroidTestCase;

import com.braintreegateway.encryption.Aes;
import com.braintreegateway.encryption.util.AesDecrypter;

public class AesTest extends AndroidTestCase {
    public void testAesKeysAreUnique() {
        Aes aes = new Aes();
        byte[] aesKey = aes.generateKey();
        assertEquals(32, aesKey.length);
        byte[] aesKey2 = aes.generateKey();
        assertFalse(Arrays.equals(aesKey, aesKey2));
    }

    public void testAesEncryptionSize() {
        Aes aes = new Aes();
        byte[] aesKey = aes.generateKey();
        String encryptedData = aes.encrypt("test data", aesKey);
        assertEquals(44, encryptedData.length());
        assertTrue(encryptedData.substring(43, 44).equals("="));
        assertFalse(encryptedData.substring(42, 43).equals("="));
    }

    public void testAesEncryption() {
        String dataToEncrypt = "test data";
        Aes aes = new Aes();
        byte[] aesKey = aes.generateKey();
        String encryptedData = aes.encrypt(dataToEncrypt, aesKey);

        byte[] decryptedData = AesDecrypter.decrypt(encryptedData, Base64.encode(aesKey));
        assertTrue(Arrays.equals(dataToEncrypt.getBytes(), decryptedData));
    }
}
