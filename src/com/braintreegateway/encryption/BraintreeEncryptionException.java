package com.braintreegateway.encryption;

public class BraintreeEncryptionException extends Exception {
    public BraintreeEncryptionException(String message) {
        super(message);
    }

    public BraintreeEncryptionException(String message, Throwable cause) {
	super(message, cause);
    }
}
