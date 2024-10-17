package com.wr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.wr.common.security.annotation.EnableCustomConfig;
import com.wr.common.security.annotation.EnableRyFeignClients;
import com.wr.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 物业管理模块
 *
 * @author wr
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
@EnableScheduling
//重启机制
@EnableRetry
public class EstateApplication
{
    public static void main(String[] args)
    {

        SpringApplication.run(EstateApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  物业端模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
    }
}
