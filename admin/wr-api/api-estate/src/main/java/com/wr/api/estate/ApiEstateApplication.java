package com.wr.api.estate;

import com.wr.common.security.annotation.EnableCustomConfig;
import com.wr.common.security.annotation.EnableRyFeignClients;
import com.wr.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 物业小程序接口模块
 *
 * @author wr
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class ApiEstateApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiEstateApplication.class, args);
		System.out.println("(♥◠‿◠)ﾉﾞ  物业小程序接口模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
	}

}
