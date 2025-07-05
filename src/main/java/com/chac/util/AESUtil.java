package com.chac.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    public static String encrypt(String text, String password) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(password.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] result = cipher.doFinal(text.getBytes());
        java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
        return encoder.encodeToString(result);
    }

    public static String decrypt( String text, String password) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(password.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
        byte[] result = cipher.doFinal(decoder.decode(text));
        return new String(result);
    }
}
