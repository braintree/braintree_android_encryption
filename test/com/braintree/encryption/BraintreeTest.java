package com.braintree.encryption;

import org.junit.Test;
import static junit.framework.Assert.assertEquals;

public class BraintreeTest {
    private String publicKey =
        "MIGJAoGBCy5TeOIU3yxaOOT8K+GkFS4pNW8N0A3v7c/48AnGR66r9z7/4XYp5bxpbgAGa07rL5Ni28kJW2Fr+bHiuv1GdRrfT2wF6RpdXeCO" +
        "TmP3X/Pb883Oa+4eefZgA5RmE4z/bNr62uIuWb+Hnaq7KgQvkHnAfdeDEBpZHGIe8sfroHUHAgMBAAE=";

    @Test
    public void hasAPublicEncryptionKey() {
        assertEquals(publicKey, new Braintree(publicKey).getPublicKey());
    }
}