package com.braintreegateway.encryption;

public class Braintree {
    private final String publicKey;

    public Braintree(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String encrypt(String payload) throws BraintreeEncryptionException {
        byte[] aesKey = Random.secureRandomBytes(Aes.KEY_LENGTH);
        byte[] aesIV = Random.secureRandomBytes(Aes.IV_LENGTH);
        String encryptedPayload = Aes.encrypt(payload, aesKey, aesIV);
        String encryptedAesKey = Rsa.encrypt(aesKey, publicKey);
        return getPrefix() + encryptedAesKey + "$" + encryptedPayload;
    }

    private String getPrefix() {
        return "$bt3|android_" + BuildConfig.VERSION_NAME.replace(".", "_") + "$";
    }
}
