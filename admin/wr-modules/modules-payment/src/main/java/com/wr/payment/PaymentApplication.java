package com.wr.payment;


import com.wr.common.security.annotation.EnableCustomConfig;
import com.wr.common.security.annotation.EnableRyFeignClients;
import com.wr.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 支付服务
 *
 * @author wr
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class PaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
		System.out.println("(♥◠‿◠)ﾉﾞ  支付服务模块启动成功   ლ(´ڡ`ლ)ﾞ  ");
	}

}
