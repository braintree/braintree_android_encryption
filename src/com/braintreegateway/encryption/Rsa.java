package com.braintreegateway.encryption;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.braintree.org.bouncycastle.asn1.ASN1InputStream;
import com.braintree.org.bouncycastle.asn1.DERObject;
import com.braintree.org.bouncycastle.asn1.x509.RSAPublicKeyStructure;
import com.braintree.org.bouncycastle.util.encoders.Base64;

public final class Rsa {
    private static final String ALGORITHM = "RSA";
    private static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    public static String encrypt(byte[] data, String publicKeyString) throws BraintreeEncryptionException {
        Cipher rsa;
        try {
            rsa = Cipher.getInstance(TRANSFORMATION);
            PublicKey publicKey = publicKey(publicKeyString);
            rsa.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encodedData = Base64.encode(data);
            byte[] encryptedData = rsa.doFinal(encodedData);
            return new String(Base64.encode(encryptedData));
        } catch (NoSuchAlgorithmException e) {
            throw new BraintreeEncryptionException("No Such Algorithm: " + e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new BraintreeEncryptionException("No Such Padding: " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new BraintreeEncryptionException("Invalid Key: " + e.getMessage());
        } catch (IllegalBlockSizeException e) {
            throw new BraintreeEncryptionException("Illegal Block Size: " + e.getMessage());
        } catch (BadPaddingException e) {
            throw new BraintreeEncryptionException("Bad Padding: " + e.getMessage());
        }
    }

    private static PublicKey publicKey(String publicKeyString) throws BraintreeEncryptionException {
        ASN1InputStream in = null;
        try {
            byte[] decodedPublicKey = Base64.decode(publicKeyString);
            in = new ASN1InputStream(decodedPublicKey);
            DERObject obj = in.readObject();
            RSAPublicKeyStructure keyStruct = RSAPublicKeyStructure.getInstance(obj);
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(keyStruct.getModulus(), keyStruct.getPublicExponent());
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new BraintreeEncryptionException("No Such Algorithm: " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            throw new BraintreeEncryptionException("Invalid Key Spec: " + e.getMessage());
        } catch (IOException e) {
            throw new BraintreeEncryptionException("IO Exception: " + e.getMessage());
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                throw new BraintreeEncryptionException("IO Exception: " + e.getMessage());
            }
        }
    }
}
