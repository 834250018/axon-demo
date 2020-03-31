package api.security;

import com.ywy.learn.common.api.security.AsymmetricEncryptionKit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.security.KeyPair;

/**
 * RSA非对称加解密
 *
 * @author ve
 * @date 2020/2/17 14:44
 */
@Slf4j
public class AsymmetricEncryptionKitTest {

    @Test
    public void test() {
        KeyPair keyPair = AsymmetricEncryptionKit.generateKeyPair();
        byte[] bytes = AsymmetricEncryptionKit.encrypt("Hello world".getBytes(), keyPair.getPublic());
        byte[] result = AsymmetricEncryptionKit.decrypt(bytes, keyPair.getPrivate());
        assert "Hello world".equals(new String(result));

    }

}
