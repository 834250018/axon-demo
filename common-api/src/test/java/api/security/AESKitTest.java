package api.security;

import com.ywy.learn.common.api.security.AESKit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.crypto.Cipher;

/**
 * @author ve
 * @date 2020/2/20 22:36
 */
@Slf4j
public class AESKitTest {

    @Test
    public void testCryption() {

        String str = "Hello World!";
        String secretKey = "sdfkjelklngnjvj55f4df4sa";
        String padding = "zxvcbnmasdfghjkl";
        byte[] bytes = AESKit.cryption(Cipher.ENCRYPT_MODE, "ecb", str.getBytes(), secretKey, null);
        byte[] bytes1 = AESKit.cryption(Cipher.DECRYPT_MODE, "ecb", bytes, secretKey, null);
        assert str.equals(new String(bytes1));

        byte[] bytes2 = AESKit.cryption(Cipher.ENCRYPT_MODE, "cbc", str.getBytes(), secretKey, padding);
        byte[] bytes3 = AESKit.cryption(Cipher.DECRYPT_MODE, "cbc", bytes2, secretKey, padding);
        assert str.equals(new String(bytes3));

        byte[] bytes4 = AESKit.cryption(Cipher.ENCRYPT_MODE, "ctr", str.getBytes(), secretKey, padding);
        byte[] bytes5 = AESKit.cryption(Cipher.DECRYPT_MODE, "ctr", bytes4, secretKey, padding);
        assert str.equals(new String(bytes5));

        byte[] bytes6 = AESKit.cryption(Cipher.ENCRYPT_MODE, "cfb", str.getBytes(), secretKey, padding);
        byte[] bytes7 = AESKit.cryption(Cipher.DECRYPT_MODE, "cfb", bytes6, secretKey, padding);
        assert str.equals(new String(bytes7));

        byte[] bytes8 = AESKit.cryption(Cipher.ENCRYPT_MODE, "ofb", str.getBytes(), secretKey, padding);
        byte[] bytes9 = AESKit.cryption(Cipher.DECRYPT_MODE, "ofb", bytes8, secretKey, padding);
        assert str.equals(new String(bytes9));
    }


}
