package com.braintreegateway.encryption.test;

import java.util.Arrays;

import org.spongycastle.util.encoders.Base64;

import android.test.AndroidTestCase;

import com.braintreegateway.encryption.Rsa;
import com.braintreegateway.encryption.util.RsaDecrypter;

public class RsaTest extends AndroidTestCase {
    public void testRSAEncryptionUsing2048BitPublicKey() {
        String publicKeyIn2048 = "MIIBCgKCAQEA8wQ3PXFYuBn9RBtOK3lW4V+7HNjik7FFd0qpPsCVd4KeiIfhuzupSevHUOLjbRSqwvAaZK3/i" +
                "cbBaM7CMAR5y0OjAR5lmmEEkcw+A7pmKQK6XQ8j3fveJCzC3MPiNiFfr+vER7O4diTxGhoXjFFJQpzKkCwFgwhKrW8uJLmWqVhQRVNphii1Gpx" +
                "I4fjFNc4h1w2W2CJ9kkv+9e3BnCpdVe1w7gBQZMkgjCzxbuAg8XaKlKD48M9kr8iE8kNt1eXV0jbmhCY3vZrckCUv26r2X4cD5lDvUtC1Gj6jB" +
                "Fobm/MelAfoFqNeq+/9VyMdYfhIecQimiBYr7Vm5VH9m69TXwIDAQAB";
        String privateKeyIn2048 = "MIIEowIBAAKCAQEA8wQ3PXFYuBn9RBtOK3lW4V+7HNjik7FFd0qpPsCVd4KeiIfhuzupSevHUOLjbRSqwvAa" +
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
        Rsa rsa = new Rsa(publicKeyIn2048);
        byte[] dataToEncrypt = new String("test data").getBytes();
        String encryptedData = rsa.encrypt(dataToEncrypt);
        byte[] decryptedData = RsaDecrypter.decrypt(encryptedData, privateKeyIn2048);
        assertTrue(Arrays.equals(dataToEncrypt, Base64.decode(decryptedData)));
    }

    public void testRSAEncryptionUsing1024BitPublicKey() {
        String publicKeyIn1024 = "MIGJAoGBAM6+2/YxsuItoU6j4zzsunSc+LMgNB1sruRAqXluAJVEgF2Bu3c38A4IjG9oWk6J+V9QFIeVhRBIbxoMp" +
                "HDlGdTgNID5ggJUt0jzgXB3ZkWs/0iuMc/1bNwJX7TmVD6TW7wLIMV0hmNHnJIjMqE6qxE5DtmwJhHgKCRVO/uUdxSpAgMBAAE=";
        String privateKeyIn1024 = "MIICXQIBAAKBgQDOvtv2MbLiLaFOo+M87Lp0nPizIDQdbK7kQKl5bgCVRIBdgbt3N/AOCIxvaFpOiflfUBSHlYUQ" +
                "SG8aDKRw5RnU4DSA+YICVLdI84Fwd2ZFrP9IrjHP9WzcCV+05lQ+k1u8CyDFdIZjR5ySIzKhOqsROQ7ZsCYR4CgkVTv7lHcUqQIDAQABAo" +
                "GBAINfLA5ozi6CqDl8UmzoUCLBjBbmo7b+1LMdk5MhnyU6fgbs5N6AoP2J2RMB0ECP0/IIxMLS89bA8DgxSFykd6B0nXU0QLYo9wdH81ep" +
                "zKDpQi+UYdh3FSaX/Q0dVRJPbzAc0ueIxWFbA33wcW6bBPaZbXtXv0tzdnQGZlGrBpABAkEA8IK76ttxDuxfzk3hNQmamTyNnM1OENN8Fq" +
                "qWhd8WLZd8PgBzZ3j8Ep60haOIU6P2+axbyeIjZrLSzcahJTFBKQJBANwPcbBQZDbu7sIKeR8sPDEs1ChskUe7yiSAz24j90PMqV0HgfP9" +
                "exKilYUSYsQjWISN3NsoyjArk/0tjUb4J4ECQQDhnHF05TbQHfHdT/cTTpgEaOYakghKBmjfxlP+7n8ac4DrlHatOLOVL+T9e6L1etjB6u" +
                "oLniNBQjw3Jb2iaurJAkBxNVTEYqcbh5G2q1KiUcxpc+l1Hl0i7R3R555OyBvlej1KyZj2H1oYPSH0gn/i7VgVYTHUYUiavWB1p+B9OR6B" +
                "AkBJnthsIS16zqQRzbUtI6tuLIpMqthwTCpND+ZoIk0kWYOHwI7ousb28iMax6DYASLYA1SpKhjWskcPIF8WkfJE";
        Rsa rsa = new Rsa(publicKeyIn1024);
        byte[] dataToEncrypt = new String("test data").getBytes();
        String encryptedData = rsa.encrypt(dataToEncrypt);
        byte[] decryptedData = RsaDecrypter.decrypt(encryptedData, privateKeyIn1024);
        assertTrue(Arrays.equals(dataToEncrypt, Base64.decode(decryptedData)));
    }
}
