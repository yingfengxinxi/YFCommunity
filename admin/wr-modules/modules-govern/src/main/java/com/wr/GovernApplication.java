package com.wr;

import com.wr.common.security.annotation.EnableCustomConfig;
import com.wr.common.security.annotation.EnableRyFeignClients;
import com.wr.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 社区治理模块
 *
 * @author wr
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class GovernApplication {

    public static void main(String[] args) {
        SpringApplication.run(GovernApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  社区治理模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }

}
