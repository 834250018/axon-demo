package com.ywy.learn.common.api.security;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

/**
 * RSA非对称加解密
 *
 * @author ve
 * @date 2020/2/17 14:44
 */
public class AsymmetricEncryptionKit {
    /** todo
     * RSA
     * EC
     * DSA
     * Ed25519, Ed448
     * SM2
     * X25519, X448
     */
    /**
     * 密钥长度，用来初始化
     */
    private static final int KEYSIZE = 1024;


    public static byte[] encrypt(byte[] bytes, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(SecurityConsts.RSA);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(bytes);
    }

    public static byte[] decrypt(byte[] bytes, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(SecurityConsts.RSA);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(bytes);
    }

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(SecurityConsts.RSA);
        keyPairGenerator.initialize(KEYSIZE, new SecureRandom());
        return keyPairGenerator.genKeyPair();
    }
}
