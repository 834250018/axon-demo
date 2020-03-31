package api.security;

import com.ywy.learn.common.api.security.AsymmetricEncryptionKit;
import com.ywy.learn.common.api.security.CRLKit;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.x509.CRLReason;
import org.junit.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;

/**
 * CRL颁发
 *
 * @author ve
 * @date 2020/2/19 10:56
 */
@Slf4j
public class CRLKitTest {
    @Test
    public void test() throws IOException {
        String dirName = new sun.security.x509.X500Name("aa", "bb", "cc", "dd", "ee", "ff").getName();
        CRLKit.genV2CRL(
                new BigInteger[]{BigInteger.valueOf(1994359749)},
                new Date[]{new Date()},
                new int[]{CRLReason.affiliationChanged},
                dirName, AsymmetricEncryptionKit.generateKeyPair().getPrivate(),
                "d://result.crl");
    }
}
