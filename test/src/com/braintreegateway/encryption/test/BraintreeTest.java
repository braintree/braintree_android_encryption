package com.braintreegateway.encryption.test;

import android.test.AndroidTestCase;

import com.braintree.encryption.Braintree;

public class BraintreeTest extends AndroidTestCase {
    private String publicKey = "MIIBCgKCAQEA8wQ3PXFYuBn9RBtOK3lW4V+7HNjik7FFd0qpPsCVd4KeiIfhuzupSevHUOLjbRSqwvAaZK3/icbBaM7" +
            "CMAR5y0OjAR5lmmEEkcw+A7pmKQK6XQ8j3fveJCzC3MPiNiFfr+vER7O4diTxGhoXjFFJQpzKkCwFgwhKrW8uJLmWqVhQRVNphii1GpxI4fjFN" +
            "c4h1w2W2CJ9kkv+9e3BnCpdVe1w7gBQZMkgjCzxbuAg8XaKlKD48M9kr8iE8kNt1eXV0jbmhCY3vZrckCUv26r2X4cD5lDvUtC1Gj6jBFobm/M" +
            "elAfoFqNeq+/9VyMdYfhIecQimiBYr7Vm5VH9m69TXwIDAQAB";

    public void testHasAPublicEncryptionKey() {
        assertEquals(publicKey, new Braintree(publicKey).getPublicKey());
    }

    public void testEncryptedDataPrependsWith$bt2$() {
        Braintree braintree = new Braintree(publicKey);
        String encryptedData = braintree.encrypt("test data");
        assertEquals("$bt2$", encryptedData.substring(0, 5));
    }
}
