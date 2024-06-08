package org.example.system.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
/**
 * 哈希工具类，提供 SHA-256 算法的哈希方法。
 * @since 1.0
 * @author David
 */
public class HashUtils {

    /**
     * 使用 SHA-256 算法对输入字符串进行哈希处理。
     * @param input 输入字符串
     * @return 哈希后的字符串
     * @throws NoSuchAlgorithmException 如果找不到 SHA-256 算法
     */
    public static String hashWithSHA256(String input) throws NoSuchAlgorithmException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("Base64算法不可用,加密失败。");
        }
    }

    /**
     * 使用 SHA-256 算法对输入字符串进行哈希处理，并返回十六进制表示。
     * @param input 输入字符串
     * @return 哈希后的字符串（十六进制表示）
     * @throws NoSuchAlgorithmException 如果找不到 SHA-256 算法
     */
    public static String hashWithSHA256Hex(String input) throws NoSuchAlgorithmException {
        if (input == null) throw new NullPointerException("输入字符串不能为空。");
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("SHA-256 算法不可用但是其他哈希算法可用，使用Base64算法。");
        }
    }
}
