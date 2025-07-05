//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.chac.util;

import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class HoppedSignatureUtil {
    //对应有活生产密钥邮件中的 app_key
    private static final String APP_KEY = "21fa5f775ed240b4";
    //对应有活生产密钥邮件中的 app_secret
    private static final String APP_SECRET = "688ec8e3907333989e23ca019ad1934a";

    public HoppedSignatureUtil() {
    }

    public static String signature(String appKey, String appSecret) {
        return signature(appKey, appSecret, Instant.now().getEpochSecond());
    }

    public static String signature(String appKey, String appSecret, Long timestamp) {
        String originalStr = appKey + timestamp + appSecret;
        HMac hMac = new HMac(HmacAlgorithm.HmacSHA256, appSecret.getBytes(StandardCharsets.UTF_8));
        return hMac.digestHex(originalStr);
    }

    public static void main(String[] args) throws Exception {
        String signature = signature(APP_KEY, APP_SECRET, 1562912285L);
        long l = System.currentTimeMillis() / 1000L;
        System.out.println(l);
        System.out.println(signature);
    }
}
