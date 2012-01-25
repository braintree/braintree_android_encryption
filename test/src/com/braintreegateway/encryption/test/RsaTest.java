package com.braintreegateway.encryption.test;

import java.util.Arrays;

import org.bouncycastle.crypto.util.encoders.Base64;

import android.test.AndroidTestCase;

import com.braintreegateway.encryption.Rsa;
import com.braintreegateway.encryption.util.RsaDecrypter;

public class RsaTest extends AndroidTestCase {
    private byte[] dataToEncrypt = new String("test data").getBytes();

    public void testRSAEncryptionUsing2048BitPublicKey() {
        String publicKeyIn2048 = "MIIBCgKCAQEA8wQ3PXFYuBn9RBtOK3lW4V+7HNjik7FFd0qpPsCVd4KeiIfhuzupSevHUOLjbRSqwvAaZK3/icbBa"
                + "M7CMAR5y0OjAR5lmmEEkcw+A7pmKQK6XQ8j3fveJCzC3MPiNiFfr+vER7O4diTxGhoXjFFJQpzKkCwFgwhKrW8uJLmWqVhQRVNphii1G"
                + "pxI4fjFNc4h1w2W2CJ9kkv+9e3BnCpdVe1w7gBQZMkgjCzxbuAg8XaKlKD48M9kr8iE8kNt1eXV0jbmhCY3vZrckCUv26r2X4cD5lDvU"
                + "tC1Gj6jBFobm/MelAfoFqNeq+/9VyMdYfhIecQimiBYr7Vm5VH9m69TXwIDAQAB";
        String privateKeyIn2048 = "MIIEowIBAAKCAQEA8wQ3PXFYuBn9RBtOK3lW4V+7HNjik7FFd0qpPsCVd4KeiIfhuzupSevHUOLjbRSqwvAaZK3/"
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
        Rsa rsa = new Rsa(publicKeyIn2048);
        String encryptedData = rsa.encrypt(dataToEncrypt);
        byte[] decryptedData = RsaDecrypter.decrypt(encryptedData, privateKeyIn2048);
        assertTrue(Arrays.equals(dataToEncrypt, Base64.decode(decryptedData)));
    }

    public void testRSAEncryptionUsing1024BitPublicKey() {
        String publicKeyIn1024 = "MIGJAoGBAM6+2/YxsuItoU6j4zzsunSc+LMgNB1sruRAqXluAJVEgF2Bu3c38A4IjG9oWk6J+V9QFIeVhRBIbxoMp"
                + "HDlGdTgNID5ggJUt0jzgXB3ZkWs/0iuMc/1bNwJX7TmVD6TW7wLIMV0hmNHnJIjMqE6qxE5DtmwJhHgKCRVO/uUdxSpAgMBAAE=";
        String privateKeyIn1024 = "MIICXQIBAAKBgQDOvtv2MbLiLaFOo+M87Lp0nPizIDQdbK7kQKl5bgCVRIBdgbt3N/AOCIxvaFpOiflfUBSHlYUQ"
                + "SG8aDKRw5RnU4DSA+YICVLdI84Fwd2ZFrP9IrjHP9WzcCV+05lQ+k1u8CyDFdIZjR5ySIzKhOqsROQ7ZsCYR4CgkVTv7lHcUqQIDAQAB"
                + "AoGBAINfLA5ozi6CqDl8UmzoUCLBjBbmo7b+1LMdk5MhnyU6fgbs5N6AoP2J2RMB0ECP0/IIxMLS89bA8DgxSFykd6B0nXU0QLYo9wdH"
                + "81epzKDpQi+UYdh3FSaX/Q0dVRJPbzAc0ueIxWFbA33wcW6bBPaZbXtXv0tzdnQGZlGrBpABAkEA8IK76ttxDuxfzk3hNQmamTyNnM1O"
                + "ENN8FqqWhd8WLZd8PgBzZ3j8Ep60haOIU6P2+axbyeIjZrLSzcahJTFBKQJBANwPcbBQZDbu7sIKeR8sPDEs1ChskUe7yiSAz24j90PM"
                + "qV0HgfP9exKilYUSYsQjWISN3NsoyjArk/0tjUb4J4ECQQDhnHF05TbQHfHdT/cTTpgEaOYakghKBmjfxlP+7n8ac4DrlHatOLOVL+T9"
                + "e6L1etjB6uoLniNBQjw3Jb2iaurJAkBxNVTEYqcbh5G2q1KiUcxpc+l1Hl0i7R3R555OyBvlej1KyZj2H1oYPSH0gn/i7VgVYTHUYUia"
                + "vWB1p+B9OR6BAkBJnthsIS16zqQRzbUtI6tuLIpMqthwTCpND+ZoIk0kWYOHwI7ousb28iMax6DYASLYA1SpKhjWskcPIF8WkfJE";
        Rsa rsa = new Rsa(publicKeyIn1024);
        String encryptedData = rsa.encrypt(dataToEncrypt);
        byte[] decryptedData = RsaDecrypter.decrypt(encryptedData, privateKeyIn1024);
        assertTrue(Arrays.equals(dataToEncrypt, Base64.decode(decryptedData)));
    }
}
