package com.braintree.encryption;

public class Braintree {
    private String publicKey;

    public Braintree(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String encrypt(String payload) {
        Aes aes = new Aes();
        Rsa rsa = new Rsa(publicKey);
        byte[] aesKey = aes.generateKey();
        String encryptedPayload = aes.encrypt(payload, aesKey);
        String encryptedAesKey = rsa.encrypt(aesKey);
        return "$bt2$" + encryptedAesKey + "$" + encryptedPayload;
    }
}