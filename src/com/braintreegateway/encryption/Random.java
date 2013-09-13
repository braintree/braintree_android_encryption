package com.braintreegateway.encryption;

import java.security.SecureRandom;

public final class Random {
    public static byte[] secureRandomBytes(int size) {
        PRNGFixes.apply();
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[size];
        random.nextBytes(keyBytes);
        return keyBytes;
    }
}
