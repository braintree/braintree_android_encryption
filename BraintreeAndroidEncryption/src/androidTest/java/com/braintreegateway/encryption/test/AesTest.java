package com.braintreegateway.encryption.test;

import java.util.Arrays;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;
import android.test.AndroidTestCase;
import com.braintree.org.bouncycastle.util.encoders.Base64;
import com.braintreegateway.encryption.Aes;
import com.braintreegateway.encryption.Random;
import com.braintreegateway.encryption.BraintreeEncryptionException;
import com.braintreegateway.encryption.util.AesDecrypter;

public class AesTest extends AndroidTestCase {
    private byte[] aesKey;
    private byte[] aesIV;
    private final String dataToEncrypt = "test data";

    @Override
    public void setUp() {
        aesKey = Random.secureRandomBytes(Aes.KEY_LENGTH);
        aesIV = Random.secureRandomBytes(Aes.IV_LENGTH);
    }

    public void testAesEncryptionSize() throws BraintreeEncryptionException {
        String encryptedData = Aes.encrypt(dataToEncrypt, aesKey, aesIV);
        assertEquals(44, encryptedData.length());
        assertTrue(encryptedData.substring(43, 44).equals("="));
        assertFalse(encryptedData.substring(42, 43).equals("="));
    }

    public void testAesEncryption() throws BraintreeEncryptionException,
                                           InvalidKeyException,
                                           IllegalBlockSizeException,
                                           BadPaddingException,
                                           InvalidAlgorithmParameterException,
                                           NoSuchAlgorithmException,
                                           NoSuchPaddingException {
        String encryptedData = Aes.encrypt(dataToEncrypt, aesKey, aesIV);
        byte[] decryptedData = AesDecrypter.decrypt(encryptedData, Base64.encode(aesKey), aesIV);
        assertTrue(Arrays.equals(dataToEncrypt.getBytes(), decryptedData));
    }
}
