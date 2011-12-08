package com.braintree.encryption;

public class Braintree {
    private String publicKey;

    public Braintree(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }
}