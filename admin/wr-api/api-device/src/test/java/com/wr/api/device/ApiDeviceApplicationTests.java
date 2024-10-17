//package com.wr.api.device;
//
//import com.alibaba.fastjson2.JSONArray;
//import com.wr.common.customize.util.HikUtils;
//import org.junit.Test;
//
//public class ApiDeviceApplicationTests {
//
//	@Test
//	public void deviceResource() {
//		//HikUtils.initConfigTest();
//		JSONArray list = HikUtils.deviceResource(1,1, "barrierGate");
//		System.out.println(list);
//	}
//
//	@Test
//	public void deviceOnline() {
//		// 364aaf8987944cb0bcd75831ca0b4ba7	barrierGate
//		HikUtils.initConfigTest();
//        /*String url = HikUtils.camerasPreviewURLs("86a9fdb53d27450c996863df8428b929", "ws");
//		System.out.println(url);*/
//		Integer online = HikUtils.cameraOnline("86a9fdb53d27450c996863df8428b929");
//		System.out.println(online);
//	}
//
//	@Test
//	public void getImageUrl() {
//		String picUri = "https://:6113/pic?2d58=f608i4c-=o3c18p31bf9733-df039a1b2*476s=**613===pt8*6417543247l8*6177=0o3*71a-=5*3e11od11dl43-a052d4";
//		HikUtils.initConfigTest();
//		String pic = HikUtils.acsEventPictures("7f2fd82b-090b-418a-82dd-1523f7f15d6c", picUri);
//	}
//
//}
