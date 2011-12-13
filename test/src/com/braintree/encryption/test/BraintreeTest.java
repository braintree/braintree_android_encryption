package com.braintree.encryption.test;

import android.test.AndroidTestCase;
import com.braintree.encryption.Braintree;

public class BraintreeTest extends AndroidTestCase {
    private	String publicKey = "publicKey";

    public void testHasAPublicEncryptionKey() {
        assertEquals(publicKey, new Braintree(publicKey).getPublicKey());
    }
}
