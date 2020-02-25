package com.ywy.learn.infrastructure.security;

import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.*;

import java.io.File;
import java.security.*;
import java.security.cert.X509Certificate;
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

    /**
     * 签发签名证书
     *
     * @param publicKey    用户公钥
     * @param x500Name     用户dn
     * @param validityTime 有效期/秒
     * @param outputPath   输出路径
     * @return
     * @throws Exception
     */
    private static String genSignedCertificate(PublicKey publicKey, X500Name x500Name, long validityTime, String outputPath) throws Exception {
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

        long certId = System.currentTimeMillis();
        // 存储证书
        CertificateKit.saveCRT(x509Cert, SecurityConsts.CERT_PATH + File.separator + certId + ".cer");
        return SecurityConsts.CERT_PATH + File.separator + certId + ".cer";
    }
}
