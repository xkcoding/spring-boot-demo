package com.xkcoding.ldap.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * LdapUtils
 *
 * @author fxbin
 * @version v1.0
 * @since 2019-08-26 1:03
 */
public class LdapUtils {

    /**
     * 校验密码
     *
     * @param ldapPassword  ldap 加密密码
     * @param inputPassword 用户输入
     * @return boolean
     * @throws NoSuchAlgorithmException 加解密异常
     */
    public static boolean verify(String ldapPassword, String inputPassword) throws NoSuchAlgorithmException {

        // MessageDigest 提供了消息摘要算法，如 MD5 或 SHA，的功能，这里LDAP使用的是SHA-1
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        // 取出加密字符
        if (ldapPassword.startsWith("{SSHA}")) {
            ldapPassword = ldapPassword.substring(6);
        } else if (ldapPassword.startsWith("{SHA}")) {
            ldapPassword = ldapPassword.substring(5);
        }
        // 解码BASE64
        byte[] ldapPasswordByte = Base64.decode(ldapPassword);
        byte[] shaCode;
        byte[] salt;

        // 前20位是SHA-1加密段，20位后是最初加密时的随机明文
        if (ldapPasswordByte.length <= 20) {
            shaCode = ldapPasswordByte;
            salt = new byte[0];
        } else {
            shaCode = new byte[20];
            salt = new byte[ldapPasswordByte.length - 20];
            System.arraycopy(ldapPasswordByte, 0, shaCode, 0, 20);
            System.arraycopy(ldapPasswordByte, 20, salt, 0, salt.length);
        }
        // 把用户输入的密码添加到摘要计算信息
        md.update(inputPassword.getBytes());
        // 把随机明文添加到摘要计算信息
        md.update(salt);

        // 按SSHA把当前用户密码进行计算
        byte[] inputPasswordByte = md.digest();

        // 返回校验结果
        return MessageDigest.isEqual(shaCode, inputPasswordByte);
    }

    /**
     * Ascii转换为字符串
     *
     * @param value Ascii串
     * @return 字符串
     */
    public static String asciiToString(String value) {
        StringBuilder sbu = new StringBuilder();
        String[] chars = value.split(",");
        for (String aChar : chars) {
            sbu.append((char) Integer.parseInt(aChar));
        }
        return sbu.toString();
    }

}
