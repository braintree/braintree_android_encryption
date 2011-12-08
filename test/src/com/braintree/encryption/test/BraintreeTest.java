package com.braintree.encryption.test;

import android.test.AndroidTestCase;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import com.braintree.encryption.Braintree;

import java.io.ByteArrayInputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;

import static javax.crypto.Cipher.PRIVATE_KEY;
import static javax.crypto.Cipher.getInstance;


public class BraintreeTest extends AndroidTestCase {
    private String publicKey =
            "MIGJAoGBCy5TeOIU3yxaOOT8K+GkFS4pNW8N0A3v7c/48AnGR66r9z7/4XYp5bxpbgAGa07rL5Ni28kJW2Fr+bHiuv1GdRrfT2wF6RpdXeCO" +
                    "TmP3X/Pb883Oa+4eefZgA5RmE4z/bNr62uIuWb+Hnaq7KgQvkHnAfdeDEBpZHGIe8sfroHUHAgMBAAE=";
    private String privateKey =
            "MIICXgIBAAKBgQsuU3jiFN8sWjjk/CvhpBUuKTVvDdAN7+3P+PAJxkeuq/c+/+F2KeW8aW4ABmtO6y+TYtvJCVtha/mx4rr9RnUa309sBeka" +
                    "XV3gjk5j91/z2/PNzmvuHnn2YAOUZhOM/2za+triLlm/h52quyoEL5B5wH3XgxAaWRxiHvLH66B1BwIDAQABAoGBCc4ACNtIrkP4gjfbIqfl" +
                    "WTXYjIWjMIMCiD8DZKku6tixZgLTy0NpJZKZdnDx0oXV0sJv+5VNDsEMpxZVNxRcs3V8UTDJZu6QnKLkH3gGP9kfSMncfrz1jt1riBBd0PKG" +
                    "kgGU3FxjEIq7EawTv/xus7A+K2RLPZePAEN57N6tWvhAkEDb9B8NhAenON3FiN9IraCKAvRnHgbZOaJhFV+zaGIzv0bxx1GivQ9eHPmk0xPl" +
                    "x2k9hfPm0FcmqntgDxlcuNl/wJBA0DaMoeO79UMWwcicM08n39OHEzxD0DhZBeRcTVhJWMOntVBFDwkgBgPrrNEOFs2s8ZdhThXsTr5soCUL" +
                    "44NwPkCQQDYN9puxuNfFx+rFymnoEa4ZL8svu+simN9XC1/h5VBmT58Xpt5hrCcq48c4AInVye1OwDQXO3PLLerbixYYb4tAkAsh34MIWhRS" +
                    "8fSKdU+I++jLtn0gy79mQ9w8yXKZNdK5I05ebFLRehTYQNGMm+Q8OvLv1RQHuAq9w7EMSgZwEKBAkECZ3PhNh7S8KIPyrVjzdQZP+XXvpZj7" +
                    "yZbKosskk2cFUUc5zXOgIrMXCu2hyMWZF1qxKYHus5z1hbo3oNMYDeDrQ==";

    private String decrypt(String encrypted) throws Exception {
        byte[] keyBytes = Base64.decode(privateKey, Base64.DEFAULT);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        Cipher rsa = getInstance("RSA/ECB/PKCS1Padding");
        rsa.init(Cipher.DECRYPT_MODE, privateKey);

        return encrypted;
    }

    public void testHasAPublicEncryptionKey() {
        assertEquals(publicKey, new Braintree(publicKey).getPublicKey());
    }
    
    public void testGeneratesAesKey() {
    	Braintree braintree = new Braintree(publicKey);
        byte[] aesKey = braintree.getAesKey();
        assertEquals(32, aesKey.length);
        byte[] aesKey2 = braintree.getAesKey();
        assertFalse(Arrays.equals(aesKey, aesKey2));
    }
    
    public void testEncryptsWithAKnownAESKey() {
        Braintree braintree = new Braintree(publicKey);
        String encryptedString = braintree.encrypt("test data");
        assertEquals(44, encryptedString.length());
        String sub = encryptedString.substring(43, 44);
        assertTrue(sub.equals("="));
        assertFalse(encryptedString.substring(42, 43).equals("="));        
    }

//    @Test
//    public void encryptsGivenTextWithPublicKey() throws Exception, NoSuchPaddingException {
//        Braintree braintree = new Braintree(publicKey);
//        String encrypted = braintree.encrypt("test data");
//
//        assertFalse(encrypted.equals("test data"));
//        assertEquals("test data", decrypt(encrypted));
//    }
}