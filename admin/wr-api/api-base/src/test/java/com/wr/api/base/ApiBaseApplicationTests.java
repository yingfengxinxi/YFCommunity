package com.wr.api.base;

import com.wr.common.security.utils.SecurityUtils;
import org.junit.Test;

public class ApiBaseApplicationTests {

	@Test
	public void contextLoads() {
		System.out.println(SecurityUtils.encryptPassword("wrkj@2023"));
	}

}
