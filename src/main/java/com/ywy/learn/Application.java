package com.ywy.learn;

import com.ywy.learn.command.admin.api.command.AdminCreateCommand;
import com.ywy.learn.infrastructure.gateway.MetaDataGateway;
import com.ywy.learn.query.entry.AdminEntry;
import com.ywy.learn.query.entry.QAdminEntry;
import com.ywy.learn.query.repository.AdminEntryRepository;
import com.ywy.learn.query.repository.UserEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.MetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ve
 * @date 2019/3/28 17:38
 */
@SpringBootApplication
@Slf4j
public class Application implements CommandLineRunner {

    @Autowired
    UserEntryRepository userEntryRepository;

    @Autowired
    AdminEntryRepository adminEntryRepository;

    @Autowired
    MetaDataGateway metaDataGateway;

    public static final String EMAIL = "834250018@qq.com";

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... args) throws Exception {
//        NettyServer nettyServer = new NettyServer();
//        nettyServer.start();
        initAdmin();
    }

    public void initAdmin() {
        // todo
        AdminEntry adminEntry = adminEntryRepository.findOne(QAdminEntry.adminEntry.username.eq(EMAIL));
        if (adminEntry == null) {
            try {
                metaDataGateway.sendAndWait(
                        new AdminCreateCommand(null, EMAIL, "admin"),
                        MetaData.emptyInstance());
            } catch (InterruptedException e) {
                log.error("初始化admin失败: " + e.getMessage());
            }
        }
    }
}
