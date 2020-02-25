package com.ywy.learn.infrastructure.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author ve
 * @date 2020/2/20 22:36
 */
public class AESKit {

    public static void main(String[] args) {
        String str = "Hello World!";
        String secretKey = "sdfkjelklngnjvj55f4df4sa";
        String padding = "zxvcbnmasdfghjkl";
        try {
            byte[] bytes = new AESKit().cryption(Cipher.ENCRYPT_MODE, "ecb", str.getBytes(), secretKey, null);
            byte[] bytes1 = new AESKit().cryption(Cipher.DECRYPT_MODE, "ecb", bytes, secretKey, null);
            System.out.println(new String(bytes1));

            byte[] bytes2 = new AESKit().cryption(Cipher.ENCRYPT_MODE, "cbc", str.getBytes(), secretKey, padding);
            byte[] bytes3 = new AESKit().cryption(Cipher.DECRYPT_MODE, "cbc", bytes2, secretKey, padding);
            System.out.println(new String(bytes3));

            byte[] bytes4 = new AESKit().cryption(Cipher.ENCRYPT_MODE, "ctr", str.getBytes(), secretKey, padding);
            byte[] bytes5 = new AESKit().cryption(Cipher.DECRYPT_MODE, "ctr", bytes4, secretKey, padding);
            System.out.println(new String(bytes5));

            byte[] bytes6 = new AESKit().cryption(Cipher.ENCRYPT_MODE, "cfb", str.getBytes(), secretKey, padding);
            byte[] bytes7 = new AESKit().cryption(Cipher.DECRYPT_MODE, "cfb", bytes6, secretKey, padding);
            System.out.println(new String(bytes7));

            byte[] bytes8 = new AESKit().cryption(Cipher.ENCRYPT_MODE, "ofb", str.getBytes(), secretKey, padding);
            byte[] bytes9 = new AESKit().cryption(Cipher.DECRYPT_MODE, "ofb", bytes8, secretKey, padding);
            System.out.println(new String(bytes9));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param cipherMode ENCRYPT_MODE: 1; DECRYPT_MODE:2
     * @param workMode   ECB,CBC,CTR,CFB,OFB五选一
     * @param data       加/解密数据
     * @param secretKey  密钥
     * @param padding    填充,当workMode为ECB时可为空
     * @return
     * @throws Exception
     */
    public byte[] cryption(int cipherMode, String workMode, byte[] data, String secretKey, String padding) throws Exception {
        Cipher cipher = Cipher.getInstance(SecurityConsts.AES+"/" + workMode + "/PKCS5Padding");
        byte[] raw = secretKey.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, SecurityConsts.AES);
        if ("ECB".equalsIgnoreCase(workMode)) {
            cipher.init(cipherMode, secretKeySpec);
        } else {
            IvParameterSpec iv = new IvParameterSpec(padding.getBytes());
            cipher.init(cipherMode, secretKeySpec, iv);
        }
        return cipher.doFinal(data);
    }


}
