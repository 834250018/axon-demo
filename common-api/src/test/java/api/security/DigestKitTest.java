package api.security;

import com.ywy.learn.common.api.security.DigestKit;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 消息摘要
 *
 * @author ve
 * @date 2020/2/17 13:56
 */
@Slf4j
public class DigestKitTest {
    @Before
    public void beforeTest() {
        log.info("begin..");
    }
    @After
    public void afterTest() {
        log.info("end..");
    }

    @Test
    public void test() throws NoSuchAlgorithmException {

        MessageDigest md5 = MessageDigest.getInstance("md5");

        String str = "hello world!";
        log.info("md5已被破解: " + Hex.toHexString(md5.digest(str.getBytes())));
        log.info("sha1已被破解: " + Hex.toHexString(DigestKit.sha(1, str.getBytes())));
        log.info("sha224: " + Hex.toHexString(DigestKit.sha(224, str.getBytes())));
        log.info("sha256: " + Hex.toHexString(DigestKit.sha(256, str.getBytes())));
        log.info("sha384: " + Hex.toHexString(DigestKit.sha(384, str.getBytes())));
        log.info("sha512: " + Hex.toHexString(DigestKit.sha(512, str.getBytes())));

        log.info("sha3-224: " + Hex.toHexString(DigestKit.sha3(224, str.getBytes())));
        log.info("sha3-256: " + Hex.toHexString(DigestKit.sha3(256, str.getBytes())));
        log.info("sha3-384: " + Hex.toHexString(DigestKit.sha3(384, str.getBytes())));
        log.info("sha3-512: " + Hex.toHexString(DigestKit.sha3(512, str.getBytes())));

        log.info("sm3: " + Hex.toHexString(DigestKit.sm3(str.getBytes())));
    }
}
