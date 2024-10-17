package com.wr.api.owner;

import com.wr.common.security.annotation.EnableCustomConfig;
import com.wr.common.security.annotation.EnableRyFeignClients;
import com.wr.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 业主H5接口模块
 *
 * @author wr
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class ApiOwnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiOwnerApplication.class, args);
		System.out.println("(♥◠‿◠)ﾉﾞ  业主H5接口模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
	}

}
