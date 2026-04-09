package com.polymer.framework.encrypt.core.utils;

import com.polymer.framework.common.utils.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


/**
 * 安全相关工具类
 *
 * @author polymer
 */
public class EncryptUtils {

    /**
     * Base64加密
     *
     * @param data 待加密数据
     * @return 加密后字符串
     */
    public static String encryptByBase64(String data) {
        return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Base64解密
     *
     * @param data 待解密数据
     * @return 解密后字符串
     */
    public static String decryptByBase64(String data) {
        byte[] decodedBytes = Base64.getDecoder().decode(data);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

    /**
     * AES加密
     *
     * @param data       待加密数据
     * @param encryptAes 秘钥字符串
     * @return 加密后字符串, 采用Base64编码
     */
    public static String encryptByAes(String data, String encryptAes) {
        if (StringUtils.isBlank(encryptAes)) {
            throw new IllegalArgumentException("AES需要传入秘钥信息");
        }
        try {
            byte[] keyBytes = Base64.getDecoder().decode(encryptAes);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

            // 加密配置
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException("密钥长度无效（需 16/24/32 字节）", e);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("JCE 环境不支持 AES 算法", e);
        } catch (Exception e) {
            throw new RuntimeException("加密过程异常", e);
        }
    }

    /**
     * AES解密
     *
     * @param encryptedData 待解密数据
     * @param base64Key     秘钥字符串
     * @return 解密后字符串
     */
    public static String decryptByAes(String encryptedData, String base64Key) {
        if (StringUtils.isBlank(base64Key)) {
            throw new IllegalArgumentException("AES需要传入秘钥信息");
        }

        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec); // 直接使用密钥

            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(base64Str(encryptedData)));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("解密过程异常", e);
        }

    }

    /**
     * rsa公钥加密
     *
     * @param data         待加密数据
     * @param publicKeyPEM 公钥
     * @return 加密后字符串, 采用Base64编码
     */
    public static String encryptByRsa(String data, String publicKeyPEM) {
        if (StringUtils.isBlank(publicKeyPEM)) {
            throw new IllegalArgumentException("RSA需要传入公钥进行加密");
        }
        // Base64 解码
        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
        // 生成公钥对象
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(encoded));
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * rsa私钥解密
     *
     * @param encryptedData 待解密数据
     * @param privateKeyPEM 私钥
     * @return 解密后字符串
     */
    public static String decryptByRsa(String encryptedData, String privateKeyPEM) {
        if (StringUtils.isBlank(privateKeyPEM)) {
            throw new IllegalArgumentException("RSA需要传入私钥进行解密");
        }
        // Base64 解码
        byte[] encodedKey = Base64.getDecoder().decode(privateKeyPEM);
        // 生成公钥对象
        try {
            // 生成 PKCS8 密钥规范
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成随机秘钥
     *
     * @param keyBitSize 字节大小
     * @return 创建密匙
     */
    public static String generateAesKey(int keyBitSize) {
        KeyGenerator kg;
        try {
            kg = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            String msg = "Unable to acquire AES algorithm.  This is required to function.";
            throw new IllegalStateException(msg, e);
        }
        kg.init(keyBitSize, new SecureRandom()); // AES可选长度：128/192/256
        SecretKey secretKey = kg.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static String base64Str(String base64Str) {
        return base64Str
                .replace("\"", "")  // 移除双引号
                .replace(" ", "")    // 移除空格
                .replace("\n", "")   // 移除换行符
                .replace("\r", "");  // 移除回车符
    }


}
