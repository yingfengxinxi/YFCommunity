package com.wr.api.device;

import com.wr.common.security.annotation.EnableCustomConfig;
import com.wr.common.security.annotation.EnableRyFeignClients;
import com.wr.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 硬件设备接口模块
 *
 * @author wr
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class ApiDeviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiDeviceApplication.class, args);
		System.out.println("(♥◠‿◠)ﾉﾞ  硬件设备接口模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
	}

}
