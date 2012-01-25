package com.braintreegateway.encryption.test;

import java.util.Arrays;

import com.braintree.org.bouncycastle.util.encoders.Base64;

import android.test.AndroidTestCase;

import com.braintreegateway.encryption.Aes;
import com.braintreegateway.encryption.util.AesDecrypter;

public class AesTest extends AndroidTestCase {
    private Aes aes;

    @Override
    public void setUp() {
        aes = new Aes();
    }

    public void testAesKeysAreUnique() {
        byte[] aesKey = aes.generateKey();
        assertEquals(32, aesKey.length);
        byte[] aesKey2 = aes.generateKey();
        assertFalse(Arrays.equals(aesKey, aesKey2));
    }

    public void testIVsAreUnique() {
        byte[] iv = aes.generateIV();
        assertEquals(16, iv.length);
        byte[] iv2 = aes.generateIV();
        assertFalse(Arrays.equals(iv, iv2));
    }

    public void testAesEncryptionSize() {
        byte[] aesKey = aes.generateKey();
        String encryptedData = aes.encrypt("test data", aesKey);
        assertEquals(44, encryptedData.length());
        assertTrue(encryptedData.substring(43, 44).equals("="));
        assertFalse(encryptedData.substring(42, 43).equals("="));
    }

    public void testAesEncryptionWithSuppliedAesKeyAndIV() {
        String dataToEncrypt = "test data";
        byte[] aesKey = aes.generateKey();
        byte[] iv = aes.generateIV();
        String encryptedData = aes.encrypt(dataToEncrypt, aesKey, iv);
        byte[] decryptedData = AesDecrypter.decrypt(encryptedData, Base64.encode(aesKey), iv);
        assertTrue(Arrays.equals(dataToEncrypt.getBytes(), decryptedData));
    }

    public void testAesEncryptionWithSuppliedAesKey() {
        String dataToEncrypt = "test data";
        byte[] aesKey = aes.generateKey();
        String encryptedData = aes.encrypt(dataToEncrypt, aesKey);
        byte[] decryptedData = AesDecrypter.decrypt(encryptedData, Base64.encode(aesKey));
        assertTrue(Arrays.equals(dataToEncrypt.getBytes(), decryptedData));
    }
}
