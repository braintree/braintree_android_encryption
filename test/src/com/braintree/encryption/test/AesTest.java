package com.braintree.encryption.test;

import android.test.AndroidTestCase;
import com.braintree.encryption.Aes;
import java.util.Arrays;


public class AesTest extends AndroidTestCase {
	public void testAesKeysAreUnique() {
		Aes aes = new Aes();
	    byte[] aesKey = aes.generateKey();
	    assertEquals(32, aesKey.length);
	    byte[] aesKey2 = aes.generateKey();
	    assertFalse(Arrays.equals(aesKey, aesKey2));
	}

	public void testAesEncryption() {
		Aes aes = new Aes();
		byte[] aesKey = aes.generateKey();
	    String encryptedData = aes.encrypt("test data", aesKey);
	    assertEquals(44, encryptedData.length());
	    assertTrue(encryptedData.substring(43, 44).equals("="));
	    assertFalse(encryptedData.substring(42, 43).equals("="));
	}
}
