package com.chac.util;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Security;

public class HoppedDecryptByText {

    public HoppedDecryptByText() {
    }


    public static void main(String[] args) throws Exception {
        String filePath = "******/xxx.txt"; // 替换为你的文件路径
        //字符串过长时，字段不允许声明，可以借助外部txt文件 读取txt文件内容
        String s2 = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        System.out.println(s2);
        String s1 = HoppedEncryptUtil.decrypt(s2, HoppedEncryptUtil.APP_SECRET, HoppedEncryptUtil.AES_IV);
        System.out.println(s1);
    }

    static {
        Security.addProvider(new BouncyCastleProvider());
    }
}
