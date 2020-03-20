package com.ywy.learn;

import com.ywy.learn.command.admin.api.command.AdminCreateCommand;
import com.ywy.learn.common.api.exception.BusinessError;
import com.ywy.learn.common.api.exception.BusinessException;
import com.ywy.learn.common.api.gateway.MetaDataGateway;
import com.ywy.learn.common.api.security.SecurityKit;
import com.ywy.learn.query.entry.AdminEntry;
import com.ywy.learn.query.entry.QAdminEntry;
import com.ywy.learn.query.repository.AdminEntryRepository;
import com.ywy.learn.query.repository.UserEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.MetaData;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sun.security.x509.X500Name;

import java.security.Security;

/**
 * @author ve
 * @date 2019/3/28 17:38
 */
@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

    public static final String ADMIN = "admin";
    @Autowired
    UserEntryRepository userEntryRepository;
    @Autowired
    AdminEntryRepository adminEntryRepository;
    @Autowired
    MetaDataGateway metaDataGateway;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... args) throws Exception {
        Security.addProvider(new BouncyCastlePQCProvider());
        initAdmin();
        initRootCert();
    }

    /**
     * 初始化根证书
     */
    public void initRootCert() {
        try {
            SecurityKit.genSelfSignedCertificate(new X500Name("ve", "ve", "ve", "ve", "ve", "ve"), 50 * 365 * 24 * 60 * 60L);
        } catch (Exception e) {
            log.error("根证书创建失败: " + e.getMessage(), e);
        }
    }

    /**
     * 初始化管理员
     */
    public void initAdmin() {
        AdminEntry adminEntry = adminEntryRepository.findOne(QAdminEntry.adminEntry.username.eq(ADMIN));
        if (adminEntry == null) {
            try {
                metaDataGateway.sendAndWait(
                        new AdminCreateCommand(null, ADMIN, ADMIN + "123"),
                        MetaData.emptyInstance());
            } catch (InterruptedException e) {
                log.error("初始化admin失败: " + e.getMessage());
                Thread.currentThread().interrupt();
                throw new BusinessException(BusinessError.BU_5001);
            }
        }
    }
}
