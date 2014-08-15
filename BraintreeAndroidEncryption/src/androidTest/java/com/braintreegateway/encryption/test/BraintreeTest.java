package com.braintreegateway.encryption.test;

import android.test.AndroidTestCase;

import com.braintreegateway.encryption.Braintree;
import com.braintreegateway.encryption.BraintreeEncryptionException;
import com.braintreegateway.encryption.util.AesDecrypter;
import com.braintreegateway.encryption.util.RsaDecrypter;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class BraintreeTest extends AndroidTestCase {
    private String publicKey = "MIIBCgKCAQEA8wQ3PXFYuBn9RBtOK3lW4V+7HNjik7FFd0qpPsCVd4KeiIfhuzupSevHUOLjbRSqwvAaZK3/icbBaM7CM"
        + "AR5y0OjAR5lmmEEkcw+A7pmKQK6XQ8j3fveJCzC3MPiNiFfr+vER7O4diTxGhoXjFFJQpzKkCwFgwhKrW8uJLmWqVhQRVNphii1GpxI4fjFNc4"
        + "h1w2W2CJ9kkv+9e3BnCpdVe1w7gBQZMkgjCzxbuAg8XaKlKD48M9kr8iE8kNt1eXV0jbmhCY3vZrckCUv26r2X4cD5lDvUtC1Gj6jBFobm/Mel"
        + "AfoFqNeq+/9VyMdYfhIecQimiBYr7Vm5VH9m69TXwIDAQAB";
    private String privateKey = "MIIEowIBAAKCAQEA8wQ3PXFYuBn9RBtOK3lW4V+7HNjik7FFd0qpPsCVd4KeiIfhuzupSevHUOLjbRSqwvAaZK3/icbB"
        + "aM7CMAR5y0OjAR5lmmEEkcw+A7pmKQK6XQ8j3fveJCzC3MPiNiFfr+vER7O4diTxGhoXjFFJQpzKkCwFgwhKrW8uJLmWqVhQRVNphii1GpxI4f"
        + "jFNc4h1w2W2CJ9kkv+9e3BnCpdVe1w7gBQZMkgjCzxbuAg8XaKlKD48M9kr8iE8kNt1eXV0jbmhCY3vZrckCUv26r2X4cD5lDvUtC1Gj6jBFob"
        + "m/MelAfoFqNeq+/9VyMdYfhIecQimiBYr7Vm5VH9m69TXwIDAQABAoIBAEvL/9LJPKvHZ2hLv/jtUrze2ASqXRlFzG3lup4ZAUWSVxIsl6qHdE"
        + "jbIoLHEbpfHNfKfeDzKGX3uTGQc574dmiAwyHBMl2RbxRuiNUu2VhnQmtuInjFa0cLMwgajL7nb+n19nWKx7kJ0q2af8fDPr9pGgEXyexRtMEd"
        + "kV3hCO3uQxA0MlX/61LK4Gssk7hlXcNw6k4fIRt9xANnN3KUrGIYtmaCk9kKsX8HhW9yrVm0WWXHnzm6o5O+3BeP+3cWe+NHeRJEVEXwIPqWtd"
        + "Qa6e0hDtLpCQPOSlpr4yJHssT2BHpkPaHi6OnGIHa0HD7ibyfwc1KQjcwA8jg4OabmT7ECgYEA/2Y1m1zG5B0nB9Mixu/3K7d+FYpBrlTKElJ0"
        + "rFNcNBgvt32dPBD6E6ZrL8MokXSy8HrhhR4epQYolyfHHtpzva1gZ8XRO1wZO++TfJwfUd1epDGcdMOfw++dZFW1EaWrnC3YPxrvfd/DuilwXg"
        + "1QUb9aIiXCMpmQw/sm0VNk2ycCgYEA85aMzCSR2pMNip9WDQnsrP+6nYhST3BrJlJAwLNrWS9zQFfXLvufIJ0OQkdP2mAM9yN9vV8FmAt7CSAP"
        + "Y2UsMvKpriyv5vlqZZF7VwMr1bOaIOllBA+IIY/x3c7iF5Ezt1hJyNegjmts+Fz39G6PN1WDrCGcmcZbXOEYhs2eyQkCgYEAgANqITpqkpIuKx"
        + "TgHJjQ6j+p2gAXldr4AiEETA/oalApMq6qrh3QSyMiHKmUXvwAaNseyMtlDtA8bi9I9iUG2G7boIgdrMQn/cvCwDW82Rq9Qk1/n2MiZGJpII55"
        + "GKRSlRDBkDffDNeo0lnM8cd4l9Dyy6TjZttkHWd4eHl1VwcCgYAt9VC5T4kJUUdzyR5GNYInHdTK1iaZgF9nCovXD8MIP7CiCjC6V5UtZRSEos"
        + "nJLOglVNfre9slVb0v+pGMslEFh81F5H6HuLU/VpSL1ThXCJzi6sY5XujTVEJRFDCKO8YjKJA7SZusY05bCcdqodV5njPKrUjLpqYkPwAOpwr3"
        + "aQKBgGie+R5Xk1t0IEdTnnY/aZHNHR6fn5elFArgRN6fixx82kQDfgMaeQbtOW4Z8RxDDUeGhc11S1filfVZT2DHayoQLr6ORU/nODhHe6Keds"
        + "UNFy1IRgoR1Si+2Y1g3IjrxqAFFdmgBNsxc1JMoFUDMJe2KlaF3nEk3OWuPc/A5G12";
    private String encryptedPrefix;
    private Braintree braintree;
    private String dataToEncrypt = "test data";

    @Override
    public void setUp() {
        braintree = new Braintree(publicKey);
        String formattedVersion = BuildConfig.VERSION_NAME.replace(".", "_");
        encryptedPrefix = "$bt3|android_" + formattedVersion + "$";
    }

    public void testHasAPublicKey() {
        assertEquals(publicKey, braintree.getPublicKey());
    }

    public void testEncryptedDataPrependsWithPlatformAndVersion() throws BraintreeEncryptionException {
        String encryptedData = braintree.encrypt(dataToEncrypt);
        assertEquals(encryptedPrefix, encryptedData.substring(0, encryptedPrefix.length()));
    }

    public void testBraintreeEncryption() throws BraintreeEncryptionException,
                                                 NoSuchAlgorithmException,
                                                 InvalidKeyException,
                                                 InvalidKeySpecException,
                                                 IllegalBlockSizeException,
                                                 NoSuchProviderException,
                                                 NoSuchPaddingException,
                                                 BadPaddingException,
                                                 InvalidAlgorithmParameterException {
        String encryptedData = braintree.encrypt(dataToEncrypt);
        String encryptedAesKeyAndCipherText = encryptedData.substring(encryptedPrefix.length());
        String encryptedAesKey = encryptedAesKeyAndCipherText.split("\\$")[0];
        String encryptedIvAndCipherText = encryptedAesKeyAndCipherText.split("\\$")[1];

        byte[] aesKey = RsaDecrypter.decrypt(encryptedAesKey, privateKey);
        byte[] decryptedData = AesDecrypter.decrypt(encryptedIvAndCipherText, aesKey);
        assertTrue(Arrays.equals(dataToEncrypt.getBytes(), decryptedData));
    }
}
