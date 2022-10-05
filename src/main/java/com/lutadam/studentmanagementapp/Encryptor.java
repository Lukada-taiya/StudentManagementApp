package com.lutadam.studentmanagementapp;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {

    private static String encrypt(String pass) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] passByte = md.digest(pass.getBytes(StandardCharsets.UTF_8));
        BigInteger bigInteger = new BigInteger(1,passByte);
        return bigInteger.toString(16);
    }
}
