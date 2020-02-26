package com.ywy.learn.infrastructure.security;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.bouncycastle.util.encoders.Base64;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Random;

/**
 * @author ve
 * @date 2020/2/25 22:47
 */
public class SecurityKit {

    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(SecurityConsts.RSA);
        keyPairGenerator.initialize(1024, new SecureRandom());
        return keyPairGenerator.genKeyPair();
    }

    /**
     * 生成新密钥对,生成根证书
     *
     * @param x500Name
     * @param validityTime 有效期,单位是秒
     * @throws Exception
     */
    public static void genSelfSignedCertificate(X500Name x500Name, long validityTime) throws Exception {
        File cert = new File(SecurityConsts.SELF_SIGN_CERT_PATH);
        if (cert.exists()) {
            // 已存在根证书则不生成
            return;
        }
        cert.getParentFile().mkdirs();
        CertAndKeyGen cak = new CertAndKeyGen(SecurityConsts.RSA, SecurityConsts.SHA256_WITH_RSA, null);
        cak.setRandom(new SecureRandom());
        cak.generate(1024);
        X509Certificate x509Certificate = cak.getSelfCertificate(x500Name, validityTime);
        X509Certificate[] certs = {x509Certificate};
        CertificateKit.saveKeyStore("pkcs12", SecurityConsts.SELF_SIGN_CERT_ALIAS, cak.getPrivateKey(), SecurityConsts.PWD, SecurityConsts.PWD, certs, SecurityConsts.SELF_SIGN_CERT_PATH);
    }

    public static void main(String[] args) throws  Exception {
        String m = "YTRjMTkxZWEtZmVhNy00MDZmLTk3N2QtNjRmM2JhODdkZjM1";
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJaVyYSwHm+FtXFDkaatqTkrOhUHHZ0d0/u+cAxJ55ueYVl2yCeqKVAYZKOeXEQHD47fRLz4VOzm4acKNKLoagUPd/KWFv5vlxhOMTyWE1SJapfL+JTJBmjywLJntyyl5XBkjOcAbNwei0cXfRLk/X6sVHutqaw075WiCavmiZ3vAgMBAAECgYEAg5SrHAiQ774UR75VHSIOBmhhPlKTa+DOhOFpIzPI79A7HGOQjihAJT7LYbVUxhStYZhyhfUwsDkm4EEosVkImb0p9jorMWZjsR4DgbwTK4wwdAh2T4B3HuOMnPKB4f1soUiH6nRcfnDeZFTEaXSg+/R07cm6dl6bVROEU1fOXTECQQDgkDNgEmByM+I00gJG9EnfUbZeV47oo4UvNWP+Wq19azHwTDbxpJN32nazysT4GANwPqnaC6VId9+f8OLjyOL7AkEAq6pjryQHInpOtI/7kuWbqwk02+ubUUBWLzFcPOU1DS2+b32ou+pYKokzgpnuU4+VzNTtPGkWqd6TVQ02SKcenQJAcTVhIQrConAzlqtUyI54+2NHS/JaMj/VccI5wc3W7oZu65SQkmuEiISTGPqdJ/F7Mkf0+t6qGYmNLCWVTH5GpwJBAIZNiym17SMLP1JCp1bY7j4UuNcp8FzUEdlgK8K0rBnAZRIC5KUIBaxZpQQTfALB/je/ik9OBF4n9WqAKK0Umn0CQHQboVQWJSgW82Ft91TVyxj8vff703V24xMIcVcuSNPVrhqg76XkLJ2eva4j29U7eqrUUtUB6uUVx0hHaW/N9+4=";
        PrivateKey p = getPrivateKey(privateKey);
        String sec = Base64.toBase64String(AsymmetricEncryptionKit.encrypt(Base64.decode(m), p));
        System.out.println(sec);
    }

    public static PublicKey getPublicKey(String publicKey) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKey));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec (Base64.decode(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

    /**
     * 签发签名证书
     *
     * @param publicKey    用户公钥
     * @param x500Name     用户dn
     * @param validityTime 有效期/秒
     * @return
     * @throws Exception
     */
    public static String genSignedCertificate(PublicKey publicKey, X500Name x500Name, long validityTime) throws Exception {
        // 获取ca的私钥跟证书
        KeyStore keyStore = CertificateKit.getKeyStore("pkcs12", SecurityConsts.PWD, SecurityConsts.SELF_SIGN_CERT_PATH);
        PrivateKey caPrivateKey = CertificateKit.getCertPrivateKeyByFirstAlias(keyStore, SecurityConsts.PWD);
        X509CertImpl caCert = (X509CertImpl) keyStore.getCertificate(SecurityConsts.SELF_SIGN_CERT_ALIAS);

        // 设置有效期
        CertificateValidity certificateValidity = new CertificateValidity(new Date(), new Date(System.currentTimeMillis() + validityTime * 1000L));
        // 设置证书信息
        X509CertInfo x509CertInfo = new X509CertInfo();
        x509CertInfo.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));
        x509CertInfo.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber((new Random()).nextInt() & 2147483647));
        AlgorithmId algorithmId = AlgorithmId.get(SecurityConsts.SHA256_WITH_RSA);
        x509CertInfo.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(algorithmId));
        x509CertInfo.set(X509CertInfo.SUBJECT, x500Name);
        x509CertInfo.set(X509CertInfo.KEY, new CertificateX509Key(publicKey));
        x509CertInfo.set(X509CertInfo.VALIDITY, certificateValidity);
        x509CertInfo.set(X509CertInfo.ISSUER, caCert.getSubjectDN());

        X509CertImpl x509Cert = new X509CertImpl(x509CertInfo);
        // 使用ca私钥对证书信息进行签名(包含摘要跟加密)
        x509Cert.sign(caPrivateKey, SecurityConsts.SHA256_WITH_RSA);

        String certId = String.valueOf(System.currentTimeMillis());
        // 存储证书
        CertificateKit.saveCRT(x509Cert, SecurityConsts.CERT_PATH + File.separator + certId + ".cer");
        return certId;
    }

    public static X509Certificate getCert(String certId) throws Exception {
        FileInputStream fis = new FileInputStream(SecurityConsts.CERT_PATH + File.separator + certId + ".cer");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        IOUtils.copy(fis, bos);
        byte[] bytes = bos.toByteArray();

        fis.close();
        bos.close();
        X509Certificate x509Certificate = new X509CertImpl(bytes);
        return x509Certificate;
    }
}
