package api.security;

import com.ywy.learn.common.api.security.AsymmetricEncryptionKit;
import com.ywy.learn.common.api.security.SignatureKit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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
public class SignatureKitTest {

    @Test
    public static void main(String[] args) throws Exception {
        String str = "hello world!";

        KeyPair keyPair = AsymmetricEncryptionKit.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] signature = SignatureKit.sign(str.getBytes(), privateKey);

        boolean bool = SignatureKit.checkSign(str.getBytes(), signature, publicKey);
        log.info("", bool);
    }
}
