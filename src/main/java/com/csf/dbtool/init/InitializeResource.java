package com.csf.dbtool.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author fuping
 */
@Component
@Slf4j
public class InitializeResource implements ApplicationRunner {

    @Value("${server.port}")
    private String serverPort;

    @Override
    public void run(ApplicationArguments args) {
        log.info("访问地址: http://localhost:{}", serverPort);
    }

}

