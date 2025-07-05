package com.chac.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;

@Slf4j
public class VnServiceUtil {

    /**
     * MD5加密
     *
     * @param string
     * @return
     */
    public static String getMD5(String string) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        try {
            byte[] btInput = string.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char c[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                c[k++] = hexDigits[byte0 >>> 4 & 0xf];
                c[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(c);
        } catch (Exception e) {
            log.error("MD5加密失败,原字符串为" + string + "  " + e.getMessage());
            return null;
        }
    }

    /**
     * 和多号要求手机号格式
     * 手机号前加86 如 8613511112222
     *
     * @param telephoneNo
     * @return
     */
    public static String transferPhoneNo(String telephoneNo) {
        if (StringUtils.isBlank(telephoneNo)) {
            return null;
        }
        if (telephoneNo.length() == 11) {
            return "86" + telephoneNo;
        }
        if (telephoneNo.startsWith("+86")) {
            return telephoneNo.substring(1);
        }
        if (telephoneNo.startsWith("86") && telephoneNo.length() == 13) {
            return telephoneNo;
        }
        return telephoneNo;
    }

    public static String transferPhoneToDial(String telephone) {
        if (StringUtils.isNotBlank(telephone) && telephone.startsWith("86") && telephone.length() == 13) {
            return "+" + telephone;
        }
        return telephone;
    }
}
