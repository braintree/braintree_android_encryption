package com.braintreegateway.encryption.util;

import static javax.crypto.Cipher.getInstance;
import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import com.braintree.org.bouncycastle.util.encoders.Base64;

public final class RsaDecrypter {
    public static byte[] decrypt(String encryptedData, String privateKey) throws NoSuchAlgorithmException,
                                                                                 InvalidKeySpecException,
                                                                                 NoSuchAlgorithmException,
                                                                                 InvalidKeyException,
                                                                                 IllegalBlockSizeException,
                                                                                 NoSuchProviderException,
                                                                                 NoSuchPaddingException,
                                                                                 BadPaddingException {
        byte[] keyBytes = Base64.decode(privateKey);
        KeyFactory keyFactory;
        keyFactory = KeyFactory.getInstance("RSA", "BC");
        PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
        PrivateKey privKey = keyFactory.generatePrivate(encodedKeySpec);
        Cipher rsa = getInstance("RSA/ECB/PKCS1Padding");
        rsa.init(Cipher.DECRYPT_MODE, privKey);
        byte[] decodedEncrypted = Base64.decode(encryptedData);
        return rsa.doFinal(decodedEncrypted);
    }
}
