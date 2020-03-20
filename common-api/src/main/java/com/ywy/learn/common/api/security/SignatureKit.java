package com.ywy.learn.common.api.security;

import lombok.extern.slf4j.Slf4j;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

/**
 * sha256withRSA签名验签
 *
 * @author ve
 * @date 2020/2/17 15:19
 */
@Slf4j
public enum SignatureKit {
    ;

    public static void main(String[] args) throws Exception {
        String str = "hello world!";

        KeyPair keyPair = AsymmetricEncryptionKit.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] signature = sign(str.getBytes(), privateKey);

        boolean bool = checkSign(str.getBytes(), signature, publicKey);
        log.info("", bool);
    }

    /**
     * 签名
     *
     * @param bytes      待签名数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static byte[] sign(byte[] bytes, PrivateKey privateKey) {
        // 签名1:摘要
        byte[] digestBytes = DigestKit.sha(256, bytes);
        // 签名2:私钥签名
        return AsymmetricEncryptionKit.encrypt(digestBytes, privateKey);
    }

    /**
     * 验签
     *
     * @param bytes     待验签数据
     * @param signature 电子签名
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    public static boolean checkSign(byte[] bytes, byte[] signature, PublicKey publicKey) {
        // 验签1:摘要1,对待签名数据进行摘要,直接拿digestBytes
        byte[] digestBytes = DigestKit.sha(256, bytes);
        // 验名2:摘要2,对电子签名进行公钥解密
        byte[] digestBytes2 = AsymmetricEncryptionKit.decrypt(signature, publicKey);

        // 验名3:对比两个摘要
        return Arrays.equals(digestBytes2, digestBytes);
    }
}
