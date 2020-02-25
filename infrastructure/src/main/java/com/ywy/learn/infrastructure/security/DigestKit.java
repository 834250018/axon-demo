package com.ywy.learn.infrastructure.security;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.util.encoders.Hex;

import java.security.MessageDigest;

/**
 * 消息摘要
 *
 * @author ve
 * @date 2020/2/17 13:56
 */
public class DigestKit {

    /**
     * sha3
     *
     * @param digestLenth 摘要长度,可为224, 256, 384 and 512
     * @param bytes
     * @return
     */
    public static byte[] sha3(int digestLenth, byte[] bytes) {
        Digest digest = new SHA3Digest(digestLenth);
        digest.update(bytes, 0, bytes.length);
        byte[] rsData = new byte[digest.getDigestSize()];
        digest.doFinal(rsData, 0);
        return rsData;
    }

    /**
     * sha
     *
     * @param digestLenth 摘要长度,可为1, 224, 256, 384 and 512
     * @param bytes
     * @return
     */
    public static byte[] sha(int digestLenth, byte[] bytes) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("sha-" + digestLenth);
        return messageDigest.digest(bytes);
    }

    public static byte[] sm3(byte[] bytes) {
        Digest digest = new SM3Digest();
        digest.update(bytes, 0, bytes.length);
        byte[] rsData = new byte[digest.getDigestSize()];
        digest.doFinal(rsData, 0);
        return rsData;
    }

    public static void main(String[] args) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance("md5");

        String str = "hello world!";
        System.out.println("md5已被破解: " + Hex.toHexString(md5.digest(str.getBytes())));
        System.out.println("sha1已被破解: " + Hex.toHexString(sha(1, str.getBytes())));
        System.out.println("sha224: " + Hex.toHexString(sha(224, str.getBytes())));
        System.out.println("sha256: " + Hex.toHexString(sha(256, str.getBytes())));
        System.out.println("sha384: " + Hex.toHexString(sha(384, str.getBytes())));
        System.out.println("sha512: " + Hex.toHexString(sha(512, str.getBytes())));

        System.out.println("sha3-224: " + Hex.toHexString(sha3(224, str.getBytes())));
        System.out.println("sha3-256: " + Hex.toHexString(sha3(256, str.getBytes())));
        System.out.println("sha3-384: " + Hex.toHexString(sha3(384, str.getBytes())));
        System.out.println("sha3-512: " + Hex.toHexString(sha3(512, str.getBytes())));

        System.out.println("sm3: " + Hex.toHexString(sm3(str.getBytes())));
    }
}
