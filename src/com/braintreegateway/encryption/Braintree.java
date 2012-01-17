package com.braintreegateway.encryption;

public class Braintree {
    private final String VERSION = "1.0.0";
    private final String publicKey;

    public Braintree(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getVersion() {
        return VERSION;
    }

    public String encrypt(String payload) {
        Aes aes = new Aes();
        Rsa rsa = new Rsa(publicKey);
        byte[] aesKey = aes.generateKey();
        String encryptedPayload = aes.encrypt(payload, aesKey);
        String encryptedAesKey = rsa.encrypt(aesKey);
        return getPrefix() + encryptedAesKey + "$" + encryptedPayload;
    }

    private String getPrefix() {
        return "$bt3|android_" + VERSION.replace(".", "_") + "$";
    }
}
