package com.shenyuan.superapp.base.utils;


import android.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * AES加密解密工具（前台传递密码解析）
 *
 * @author weijb
 * @date 2021/2/7 9:32
 */
public class AesEncryptUtil {

    private final static String key = "ABFDCADD20210207";//十六位十六进制数作为密钥
    private final static String IV = "20210207DDFFEDBA";//十六位十六进制数作为密钥偏移量
    private final static String Algorithm = "AES";
    private final static String AlgorithmProvider = "AES/CBC/PKCS5Padding"; //算法/模式/补码方式

    /**
     * 加密使用
     *
     * @param content 加密后字符串
     * @return java.lang.String  明文
     * @author weijb
     * @date 2021/2/7 13:28
     */
    public static String encrypt(String content) {
        SecretKeySpec skeySpec = null;
        skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.US_ASCII), Algorithm);
        IvParameterSpec iv = null;
        iv = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(AlgorithmProvider);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        } catch (InvalidAlgorithmParameterException | InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] cipherBytes = new byte[0];
        try {
            cipherBytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return new String(Base64.encode(byteToHexString(cipherBytes).getBytes(), Base64.DEFAULT), StandardCharsets.UTF_8).trim();
    }

    /**
     * 密码解密使用
     * content 为明文
     *
     * @return java.lang.String  加密后字符串
     * @author weijb
     * @date 2021/2/7 13:28
     */
    public static String decryptAES(String content) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.US_ASCII), Algorithm);
            Cipher cipher = Cipher.getInstance(AlgorithmProvider);
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] hexBytes = hexStringToBytes(new String(Base64.decode(content, Base64.DEFAULT)));
            return new String(cipher.doFinal(hexBytes));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将byte转换为16进制字符串
     */
    public static String byteToHexString(byte[] src) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xff;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                sb.append("0");
            }
            sb.append(hv);
        }
        return sb.toString();
    }

    /**
     * 将16进制字符串装换为byte数组
     */
    public static byte[] hexStringToBytes(String hexString) {
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] b = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            b[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return b;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(encrypt("中文"));
        System.out.println(decryptAES("YWYxNGMzZmY4MmU0YzhiNGRkNzBjMzBkODJlMTEyNDk="));
    }
}
