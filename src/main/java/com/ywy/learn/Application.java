package com.ywy.learn;

import com.ywy.learn.query.repository.UserEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ve
 * @date 2019/3/28 17:38
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    UserEntryRepository userEntryRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... args) throws Exception {
//        NettyServer nettyServer = new NettyServer();
//        nettyServer.start();

    }
}
