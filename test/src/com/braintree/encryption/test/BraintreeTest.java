package com.braintree.encryption.test;
import com.braintree.encryption.Braintree;

import android.test.AndroidTestCase;

public class BraintreeTest extends AndroidTestCase {
    private	String publicKey = "publicKey";

    public void testHasAPublicEncryptionKey() {
        assertEquals(publicKey, new Braintree(publicKey).getPublicKey());
    }

}
