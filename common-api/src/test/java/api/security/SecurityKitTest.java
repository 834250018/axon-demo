package api.security;

import com.ywy.learn.common.api.security.AsymmetricEncryptionKit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author ve
 * @date 2020/2/25 22:47
 */
@Slf4j
public class SecurityKitTest {

    @Test
    public void testGenerateKeyPair() {
        AsymmetricEncryptionKit.generateKeyPair();
    }
}
