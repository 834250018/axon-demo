package com.ywy.learn.common.api.security;

/**
 * @author ve
 * @date 2020/2/25 22:38
 */
public class SecurityConsts {
    public final static String SHA256_WITH_RSA = "SHA256withRSA";
    public final static String SHA256 = "SHA256";
    public final static String RSA = "RSA";
    public final static String AES = "AES";
    // 演示项目,随便弄个密码
    public final static String DEFAULT_CODE = "suibiandemima";
    // 自签名KeyStore存放路径
    public final static String SELF_SIGN_CERT_PATH = "src/main/resources/cert/axon.pfx";
    public final static String CERT_PATH = "src/main/resources/cert";
    public final static String SELF_SIGN_CERT_ALIAS = "AXON";
}
