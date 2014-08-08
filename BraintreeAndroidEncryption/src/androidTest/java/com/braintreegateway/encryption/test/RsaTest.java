package com.braintreegateway.encryption.test;

import java.util.Arrays;
import com.braintree.org.bouncycastle.util.encoders.Base64;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.BadPaddingException;
import android.test.AndroidTestCase;
import com.braintreegateway.encryption.Rsa;
import com.braintreegateway.encryption.BraintreeEncryptionException;
import com.braintreegateway.encryption.util.RsaDecrypter;

public class RsaTest extends AndroidTestCase {
    public void testRSAEncryptionUsing2048BitPublicKey() throws BraintreeEncryptionException,
                                                                NoSuchAlgorithmException,
                                                                InvalidKeyException,
                                                                InvalidKeySpecException,
                                                                IllegalBlockSizeException,
                                                                NoSuchProviderException,
                                                                NoSuchPaddingException,
                                                                BadPaddingException {
        String publicKey = "MIIBCgKCAQEA8wQ3PXFYuBn9RBtOK3lW4V+7HNjik7FFd0qpPsCVd4KeiIfhuzupSevHUOLjbRSqwvAaZK3/icbBa"
            + "M7CMAR5y0OjAR5lmmEEkcw+A7pmKQK6XQ8j3fveJCzC3MPiNiFfr+vER7O4diTxGhoXjFFJQpzKkCwFgwhKrW8uJLmWqVhQRVNphii1G"
            + "pxI4fjFNc4h1w2W2CJ9kkv+9e3BnCpdVe1w7gBQZMkgjCzxbuAg8XaKlKD48M9kr8iE8kNt1eXV0jbmhCY3vZrckCUv26r2X4cD5lDvU"
            + "tC1Gj6jBFobm/MelAfoFqNeq+/9VyMdYfhIecQimiBYr7Vm5VH9m69TXwIDAQAB";
        String privateKey = "MIIEowIBAAKCAQEA8wQ3PXFYuBn9RBtOK3lW4V+7HNjik7FFd0qpPsCVd4KeiIfhuzupSevHUOLjbRSqwvAaZK3/"
            + "icbBaM7CMAR5y0OjAR5lmmEEkcw+A7pmKQK6XQ8j3fveJCzC3MPiNiFfr+vER7O4diTxGhoXjFFJQpzKkCwFgwhKrW8uJLmWqVhQRVNp"
            + "hii1GpxI4fjFNc4h1w2W2CJ9kkv+9e3BnCpdVe1w7gBQZMkgjCzxbuAg8XaKlKD48M9kr8iE8kNt1eXV0jbmhCY3vZrckCUv26r2X4cD"
            + "5lDvUtC1Gj6jBFobm/MelAfoFqNeq+/9VyMdYfhIecQimiBYr7Vm5VH9m69TXwIDAQABAoIBAEvL/9LJPKvHZ2hLv/jtUrze2ASqXRlF"
            + "zG3lup4ZAUWSVxIsl6qHdEjbIoLHEbpfHNfKfeDzKGX3uTGQc574dmiAwyHBMl2RbxRuiNUu2VhnQmtuInjFa0cLMwgajL7nb+n19nWK"
            + "x7kJ0q2af8fDPr9pGgEXyexRtMEdkV3hCO3uQxA0MlX/61LK4Gssk7hlXcNw6k4fIRt9xANnN3KUrGIYtmaCk9kKsX8HhW9yrVm0WWXH"
            + "nzm6o5O+3BeP+3cWe+NHeRJEVEXwIPqWtdQa6e0hDtLpCQPOSlpr4yJHssT2BHpkPaHi6OnGIHa0HD7ibyfwc1KQjcwA8jg4OabmT7EC"
            + "gYEA/2Y1m1zG5B0nB9Mixu/3K7d+FYpBrlTKElJ0rFNcNBgvt32dPBD6E6ZrL8MokXSy8HrhhR4epQYolyfHHtpzva1gZ8XRO1wZO++T"
            + "fJwfUd1epDGcdMOfw++dZFW1EaWrnC3YPxrvfd/DuilwXg1QUb9aIiXCMpmQw/sm0VNk2ycCgYEA85aMzCSR2pMNip9WDQnsrP+6nYhS"
            + "T3BrJlJAwLNrWS9zQFfXLvufIJ0OQkdP2mAM9yN9vV8FmAt7CSAPY2UsMvKpriyv5vlqZZF7VwMr1bOaIOllBA+IIY/x3c7iF5Ezt1hJ"
            + "yNegjmts+Fz39G6PN1WDrCGcmcZbXOEYhs2eyQkCgYEAgANqITpqkpIuKxTgHJjQ6j+p2gAXldr4AiEETA/oalApMq6qrh3QSyMiHKmU"
            + "XvwAaNseyMtlDtA8bi9I9iUG2G7boIgdrMQn/cvCwDW82Rq9Qk1/n2MiZGJpII55GKRSlRDBkDffDNeo0lnM8cd4l9Dyy6TjZttkHWd4"
            + "eHl1VwcCgYAt9VC5T4kJUUdzyR5GNYInHdTK1iaZgF9nCovXD8MIP7CiCjC6V5UtZRSEosnJLOglVNfre9slVb0v+pGMslEFh81F5H6H"
            + "uLU/VpSL1ThXCJzi6sY5XujTVEJRFDCKO8YjKJA7SZusY05bCcdqodV5njPKrUjLpqYkPwAOpwr3aQKBgGie+R5Xk1t0IEdTnnY/aZHN"
            + "HR6fn5elFArgRN6fixx82kQDfgMaeQbtOW4Z8RxDDUeGhc11S1filfVZT2DHayoQLr6ORU/nODhHe6KedsUNFy1IRgoR1Si+2Y1g3Ijr"
            + "xqAFFdmgBNsxc1JMoFUDMJe2KlaF3nEk3OWuPc/A5G12";
        byte[] dataToEncrypt = new String("test data").getBytes();
        String encryptedData = Rsa.encrypt(dataToEncrypt, publicKey);
        byte[] decryptedData = RsaDecrypter.decrypt(encryptedData, privateKey);
        assertTrue(Arrays.equals(dataToEncrypt, Base64.decode(decryptedData)));
    }
}
