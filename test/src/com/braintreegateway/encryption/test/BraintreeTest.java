package com.braintreegateway.encryption.test;

import java.util.Arrays;

import android.test.AndroidTestCase;

import com.braintreegateway.encryption.Braintree;
import com.braintreegateway.encryption.util.AesDecrypter;
import com.braintreegateway.encryption.util.RsaDecrypter;

public class BraintreeTest extends AndroidTestCase {
    private String publicKey = "MIIBCgKCAQEA8wQ3PXFYuBn9RBtOK3lW4V+7HNjik7FFd0qpPsCVd4KeiIfhuzupSevHUOLjbRSqwvAaZK3/icbBaM7" +
            "CMAR5y0OjAR5lmmEEkcw+A7pmKQK6XQ8j3fveJCzC3MPiNiFfr+vER7O4diTxGhoXjFFJQpzKkCwFgwhKrW8uJLmWqVhQRVNphii1GpxI4fjFN" +
            "c4h1w2W2CJ9kkv+9e3BnCpdVe1w7gBQZMkgjCzxbuAg8XaKlKD48M9kr8iE8kNt1eXV0jbmhCY3vZrckCUv26r2X4cD5lDvUtC1Gj6jBFobm/M" +
            "elAfoFqNeq+/9VyMdYfhIecQimiBYr7Vm5VH9m69TXwIDAQAB";
    private String privateKey = "MIIEowIBAAKCAQEA8wQ3PXFYuBn9RBtOK3lW4V+7HNjik7FFd0qpPsCVd4KeiIfhuzupSevHUOLjbRSqwvAa" +
            "ZK3/icbBaM7CMAR5y0OjAR5lmmEEkcw+A7pmKQK6XQ8j3fveJCzC3MPiNiFfr+vER7O4diTxGhoXjFFJQpzKkCwFgwhKrW8uJLmWqVhQRVNphi" +
            "i1GpxI4fjFNc4h1w2W2CJ9kkv+9e3BnCpdVe1w7gBQZMkgjCzxbuAg8XaKlKD48M9kr8iE8kNt1eXV0jbmhCY3vZrckCUv26r2X4cD5lDvUtC1" +
            "Gj6jBFobm/MelAfoFqNeq+/9VyMdYfhIecQimiBYr7Vm5VH9m69TXwIDAQABAoIBAEvL/9LJPKvHZ2hLv/jtUrze2ASqXRlFzG3lup4ZAUWSVx" +
            "Isl6qHdEjbIoLHEbpfHNfKfeDzKGX3uTGQc574dmiAwyHBMl2RbxRuiNUu2VhnQmtuInjFa0cLMwgajL7nb+n19nWKx7kJ0q2af8fDPr9pGgEX" +
            "yexRtMEdkV3hCO3uQxA0MlX/61LK4Gssk7hlXcNw6k4fIRt9xANnN3KUrGIYtmaCk9kKsX8HhW9yrVm0WWXHnzm6o5O+3BeP+3cWe+NHeRJEVE" +
            "XwIPqWtdQa6e0hDtLpCQPOSlpr4yJHssT2BHpkPaHi6OnGIHa0HD7ibyfwc1KQjcwA8jg4OabmT7ECgYEA/2Y1m1zG5B0nB9Mixu/3K7d+FYpB" +
            "rlTKElJ0rFNcNBgvt32dPBD6E6ZrL8MokXSy8HrhhR4epQYolyfHHtpzva1gZ8XRO1wZO++TfJwfUd1epDGcdMOfw++dZFW1EaWrnC3YPxrvfd" +
            "/DuilwXg1QUb9aIiXCMpmQw/sm0VNk2ycCgYEA85aMzCSR2pMNip9WDQnsrP+6nYhST3BrJlJAwLNrWS9zQFfXLvufIJ0OQkdP2mAM9yN9vV8F" +
            "mAt7CSAPY2UsMvKpriyv5vlqZZF7VwMr1bOaIOllBA+IIY/x3c7iF5Ezt1hJyNegjmts+Fz39G6PN1WDrCGcmcZbXOEYhs2eyQkCgYEAgANqIT" +
            "pqkpIuKxTgHJjQ6j+p2gAXldr4AiEETA/oalApMq6qrh3QSyMiHKmUXvwAaNseyMtlDtA8bi9I9iUG2G7boIgdrMQn/cvCwDW82Rq9Qk1/n2Mi" +
            "ZGJpII55GKRSlRDBkDffDNeo0lnM8cd4l9Dyy6TjZttkHWd4eHl1VwcCgYAt9VC5T4kJUUdzyR5GNYInHdTK1iaZgF9nCovXD8MIP7CiCjC6V5" +
            "UtZRSEosnJLOglVNfre9slVb0v+pGMslEFh81F5H6HuLU/VpSL1ThXCJzi6sY5XujTVEJRFDCKO8YjKJA7SZusY05bCcdqodV5njPKrUjLpqYk" +
            "PwAOpwr3aQKBgGie+R5Xk1t0IEdTnnY/aZHNHR6fn5elFArgRN6fixx82kQDfgMaeQbtOW4Z8RxDDUeGhc11S1filfVZT2DHayoQLr6ORU/nOD" +
            "hHe6KedsUNFy1IRgoR1Si+2Y1g3IjrxqAFFdmgBNsxc1JMoFUDMJe2KlaF3nEk3OWuPc/A5G12";
    private String encryptedPrefix = "$bt2$";

    public void testHasAPublicEncryptionKey() {
        assertEquals(publicKey, new Braintree(publicKey).getPublicKey());
    }

    public void testEncryptedDataPrependsWith$bt2$() {
        Braintree braintree = new Braintree(publicKey);
        String encryptedData = braintree.encrypt("test data");
        assertEquals(encryptedPrefix, encryptedData.substring(0, 5));
    }

    public void testBraintreeEncryption() {
        Braintree braintree = new Braintree(publicKey);
        String dataToEncrypt = "test data";
        String encryptedData = braintree.encrypt(dataToEncrypt);
        String encryptedAesKeyAndCipherText = encryptedData.substring(encryptedPrefix.length());
        String encryptedAesKey = encryptedAesKeyAndCipherText.split("\\$")[0];
        String encryptedIvAndCipherText = encryptedAesKeyAndCipherText.split("\\$")[1];

        byte[] aesKey = RsaDecrypter.decrypt(encryptedAesKey, privateKey);
        byte[] decryptedData = AesDecrypter.decrypt(encryptedIvAndCipherText, aesKey);
        assertTrue(Arrays.equals(dataToEncrypt.getBytes(), decryptedData));
    }
}
