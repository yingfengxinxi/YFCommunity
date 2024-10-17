package com.wr.api.base;

import com.wr.common.security.annotation.EnableCustomConfig;
import com.wr.common.security.annotation.EnableRyFeignClients;
import com.wr.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 基础接口模块
 *
 * @author wr
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class ApiBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiBaseApplication.class, args);
		System.out.println("(♥◠‿◠)ﾉﾞ  基础接口模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
	}

}
