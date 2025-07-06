package com.chac.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Base64;
import java.util.UUID;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class HoppedEncryptUtil {
    public static final String ALGORITHM = "AES";
    public static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";
    //对应有活生产密钥邮件中的 app_secret
    public static final String APP_SECRET = "688ec8e3907333989e23ca019ad1934a";
    //对应有活生产密钥邮件中的 aes_iv
    public static final String AES_IV = "ff465fdecc764337";

    public HoppedEncryptUtil() {
    }

    public static String encrypt(String str) throws Exception {
        return encrypt(str, APP_SECRET, AES_IV);
    }

    public static String encrypt(String str, String key, String aesIV) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        cipher.init(1, new SecretKeySpec(keyBytes, ALGORITHM), new IvParameterSpec(aesIV.getBytes(StandardCharsets.UTF_8)));
        byte[] doFinal = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.getEncoder().encode(doFinal));
    }

    public static String decrypt(String str, String key, String aesIV) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        cipher.init(2, new SecretKeySpec(keyBytes, ALGORITHM), new IvParameterSpec(aesIV.getBytes(StandardCharsets.UTF_8)));
        byte[] doFinal = cipher.doFinal(Base64.getDecoder().decode(str));
        return new String(doFinal);
    }

    /**
     * <dependency>
     * <groupId>org.bouncycastle</groupId>
     * <artifactId>bcprov-jdk15on</artifactId>
     * <version>1.64</version>
     * </dependency>
     * * 使用BouncyCastleProvider提供的AES/CBC/PKCS7Padding加密方式
     */
    public static void main(String[] args) throws Exception {
        int randomNum = (int) (Math.random() * 1000);
        String data = "{\n" +
                "    \"job_list\": [\n" +
                "        {\n" +
                "            \"customer_job_id\": \"" + UUID.randomUUID().toString().replace("-", "") + "\",\n" +
                "            \"position_title\": \"兼职分拣员751\",\n" +
                "            \"position_type\": \"240206\",\n" +
                "            \"mapping_type\": 1,\n" +
                "            \"company_name\": \"深圳高灯云科技有限公司\",\n" +
                "            \"contact\": null,\n" +
                "            \"contact_phone\": null,\n" +
                "            \"province\": \"四川省\",\n" +
                "            \"city\": \"海口市\",\n" +
                "            \"district\": \"\",\n" +
                "            \"work_address\": \"海南海口琼山区金柱园电信大楼1\",\n" +
                "            \"position_desc\": \"日结11（可只做一天）龙泉快递分拣扫描日结\\n【班次自选】\\n要求：男女不限｜18-55岁以内，身体健康｜包工作餐\\n工作内容：小件包裹分拣，扫描，工作轻松简单\\n⚠要求：提前一小时集合，上班一定带上身份证 （消磁不行）不要穿短裤、拖鞋、裙子、凉鞋。\\n龙泉驿区周边\",\n" +
                "            \"work_days_type\": 1,\n" +
                "            \"work_time_type\": 1,\n" +
                "            \"work_period_type\": 1,\n" +
                "            \"salary_type\": 2,\n" +
                "            \"salary\": 0,\n" +
                "            \"min_salary\": \"1001\",\n" +
                "            \"max_salary\": \"2500\",\n" +
                "            \"salary_payment_type\": 1,\n" +
                "            \"recruit_number\": \"500\",\n" +
                "            \"experience_require\": 0,\n" +
                "            \"education_require\": 0,\n" +
                "            \"position_benefit\": \"包吃、大小周、加班补助\",\n" +
                "            \"online_time\": \"2024-09-19 09:53:48\",\n" +
                "            \"data_type\": \"add\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        String s = encrypt(data, APP_SECRET, AES_IV);
        // 打印加密后的字符串
        System.out.println(s);

        String s1 = decrypt(s, APP_SECRET, AES_IV);
        // 打印解密后的字符串
        System.out.println(s1);
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }
}
