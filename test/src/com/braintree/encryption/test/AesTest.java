package com.braintree.encryption.test;

import android.test.AndroidTestCase;
import com.braintree.encryption.Aes;
import java.util.Arrays;

public class AesTest extends AndroidTestCase {
	public void testGeneratesAesKey() {
		Aes aes = new Aes();
	    byte[] aesKey = aes.generateKey();
	    assertEquals(32, aesKey.length);
	    byte[] aesKey2 = aes.generateKey();
	    assertFalse(Arrays.equals(aesKey, aesKey2));
	}

	public void testAesEncryption() {
		Aes aes = new Aes();
	    String encryptedString = aes.encrypt("test data", aes.generateKey());
	    assertEquals(44, encryptedString.length());
	    assertTrue(encryptedString.substring(43, 44).equals("="));
	    assertFalse(encryptedString.substring(42, 43).equals("="));
	}
}
