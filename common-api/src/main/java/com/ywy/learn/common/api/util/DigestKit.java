package com.ywy.learn.common.api.util;

import com.ywy.learn.common.api.exception.BusinessError;
import com.ywy.learn.common.api.exception.BusinessException;
import org.springframework.util.Base64Utils;

import java.security.MessageDigest;

/**
 * @author ve
 * @date 2020/2/23 22:40
 */
public class DigestKit {

    public static String sha256(String string) {
        try {
            return Base64Utils.encodeToString((sha(256, string.getBytes())));
        } catch (Exception e) {
            throw new RuntimeException("算法不支持");
        }
    }

    public static String sha256AndSalt(String string, String salt) {
        try {
            return Base64Utils.encodeToString((sha(256, (sha256(string) + salt).getBytes())));
        } catch (Exception e) {
            throw new RuntimeException("算法不支持");
        }
    }

    /**
     * sha
     *
     * @param digestLenth 摘要长度,可为1, 224, 256, 384 and 512
     * @param bytes
     * @return
     */
    public static byte[] sha(int digestLenth, byte[] bytes) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("sha-" + digestLenth);
            return messageDigest.digest(bytes);
        } catch (Exception e) {
            throw new BusinessException(BusinessError.BU_5000);
        }
    }
}
