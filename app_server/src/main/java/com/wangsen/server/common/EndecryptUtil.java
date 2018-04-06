package com.wangsen.server.common;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author wangsen
 * @Description 加密工具类
 * @data 2018/3/13 22:37
 */
public class EndecryptUtil {

    /**
     *base64进制加密
     */
    public static String encrytBase64(String abc){
        return Base64.encodeToString(abc.getBytes());
    }

    /**
     *  base64进制解密
     */
    public static String decryptBase64(String cipherText) {
        return Base64.decodeToString(cipherText);
    }

    /**
     * 16进制加密
     *
     * @param password
     * @return
     */
    public static String encrytHex(String password) {
        byte[] bytes = password.getBytes();
        return Hex.encodeToString(bytes);
    }
    /**
     * 16进制解密
     * @param cipherText
     * @return
     */
    public static String decryptHex(String cipherText) {
        return new String(Hex.decode(cipherText));
    }


    /**
     * 随机盐
     */
    public static String getRadomSalt(){
        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        return randomNumberGenerator.nextBytes().toHex();
    }

    /**
     * 生成散列密码
     * @param algorithmName 散列方式 md5 SHA256 SHA1 SHA512
     * @param password 要加密的密码
     * @param salt 盐
     * @param hashIterations 散列次数
     * @return
     */
    public static String getEncryPassword(String algorithmName,String password,String salt,Integer hashIterations){
        return new SimpleHash(algorithmName, password, salt, hashIterations).toHex();
    }

}
