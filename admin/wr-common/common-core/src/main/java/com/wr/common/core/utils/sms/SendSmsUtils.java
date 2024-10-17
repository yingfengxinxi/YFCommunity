package com.wr.common.core.utils.sms;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;

import java.util.concurrent.CompletableFuture;

public class SendSmsUtils {


//    @Autowired
//    private AliSendConfig aliSendConfig;


    /**
     * 发送短信
     *
     * @param sendMsgRequest
     * @return
     * @throws Exception
     */
    public static String sendMsg(SendMsgRequest sendMsgRequest) throws Exception {
        System.out.println("======================START正在给" + sendMsgRequest.getPhoneNumber() + "手机号发送短信======================");
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId(sendMsgRequest.getAccessKeyId())
                .accessKeySecret(sendMsgRequest.getAccessKeySecret())
                .build());
        AsyncClient client = AsyncClient.builder()
                .region(sendMsgRequest.getRegion()) // Region ID
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")

                )
                .build();

        // Parameter settings for API request
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .signName(sendMsgRequest.getSignName())
                .templateCode(sendMsgRequest.getTemplateCode())
                .phoneNumbers(sendMsgRequest.getPhoneNumber())
                .templateParam(sendMsgRequest.getTemplateParam())
                .build();

        CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);

        SendSmsResponse resp = response.get();
        String json = new Gson().toJson(resp);

        client.close();
        JSONObject jsonObject = JSON.parseObject(json);
        String body = jsonObject.get("body").toString();
        System.out.println("短信响应内容:" + body);
        System.out.println("======================END正在给" + sendMsgRequest.getPhoneNumber() + "手机号发送短信======================");
        return body;
    }

}