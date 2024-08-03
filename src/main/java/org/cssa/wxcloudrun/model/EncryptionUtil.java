package org.cssa.wxcloudrun.model;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Component;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

/**
 * 加密工具类，用于生成基于用户OpenID和注册时间的加密ID。
 */
@Component
@Hidden
public class EncryptionUtil {
    private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@#$%!?&*:/<>~+-()[]{};";
    private static final Random random = new Random();

    /**
     * 根据用户的OpenID和注册时间戳生成一个随机的、唯一的12位加密ID。
     * 每位字符都有可能是字母、数字或特殊符号。
     *
     * @param openID 用户的OpenID
     * @param timestamp 用户的注册时间戳
     * @return 生成的12位加密ID
     * @throws NoSuchAlgorithmException 如果指定的加密算法不存在
     */
    public String generateEncryptedID(String openID, long timestamp) {
        // 将OpenID和时间戳结合，提高唯一性
        String input = openID + timestamp;
        String base64Encoded = "";

        try {
            // 使用SHA-256进行哈希
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            // Base64编码以获得一个较长的字符串
            base64Encoded = Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            // 异常处理：记录日志或者使用备用的加密算法
            System.err.println("加密算法SHA-256不可用");
            // 可以选择返回null或特定错误值，或者抛出一个运行时异常
            throw new RuntimeException("加密算法不可用", e);
        }

        // 生成最终的25位ID，随机从CHAR_LIST和base64Encoded中选择字符
        StringBuilder encryptedID = new StringBuilder();
        for (int i = 0; i < 25; i++) {
            if (i % 3 == 0) {  // 每三个字符中有一个来自base64Encoded
                encryptedID.append(base64Encoded.charAt(random.nextInt(base64Encoded.length())));
            } else {  // 其他来自CHAR_LIST
                encryptedID.append(CHAR_LIST.charAt(random.nextInt(CHAR_LIST.length())));
            }
        }

        return encryptedID.toString();
    }
}
