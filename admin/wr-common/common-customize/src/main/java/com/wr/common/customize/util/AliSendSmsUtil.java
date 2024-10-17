package com.wr.common.customize.util;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @Author: RainCity
 * @Date: 2021-11-30 10:19:08
 * @Desc: 阿里短信发送
 */
public class AliSendSmsUtil {
    private static final Logger log = LoggerFactory.getLogger(AliSendSmsUtil.class);

    /**AccessKey ID*/
    private static final String ACCESS_KEY_ID = "LTAI5tCMEm2C4xrgWd7rDje7";
    /**AccessKey Secret*/
    private static final String ACCESS_KEY_SECRET = "owOMFHc77mgMEAjmb9iDfWBNj7lX4t";
    /**短信签名名称*/
    private static final String SIGN_NAME = "宣城城建智能科技有限公司";
    /**验证码短信模板ID*/
    public static final String TEMPLATE_CODE ="SMS_228851715";
    /**验证码失效时间(毫秒)*/
    public static final long EXPIRATION =120000;

    /**
     * 短信发送验证码
     * @param phoneNumber 手机号
     * @param code 验证码
     * @return
     */
    public static String sendSms(String phoneNumber, String code){

        try {
            //初始化
            Config config = new Config()
                    // AccessKey ID
                    .setAccessKeyId(ACCESS_KEY_ID)
                    //AccessKey Secret
                    .setAccessKeySecret(ACCESS_KEY_SECRET);
            // 访问的域名
            config.endpoint = "dysmsapi.aliyuncs.com";
            Client client = new Client(config);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            //待发送手机号。
            request.setPhoneNumbers(phoneNumber);
            //必填:短信签名-可在短信控制台中找到
            request.setSignName(SIGN_NAME);
            //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
            request.setTemplateCode(TEMPLATE_CODE);
            //短信模板变量对应的实际值，JSON格式
            request.setTemplateParam("{\"code\":\"" + code + "\"}");
            //发送请求
            SendSmsResponse response = client.sendSms(request);
            SendSmsResponseBody body = response.getBody();
            if(StringUtils.isEmpty(body.getCode())){
                log.error("向手机：{}，短信发送失败",phoneNumber);
                return null;
            }
            if(!"OK".equals(body.getCode())){
                log.error("向手机：{}，短信发送失败：{}",phoneNumber,body.getMessage());
                return null;
            }
            log.info("向手机：{}，短信发送成功：{}",phoneNumber,body.getRequestId());
            return code;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("向手机：{}，短信发送失败,初始化客户端失败：{}",phoneNumber,e.getMessage());
            return null;
        }

    }

    /**
     * 生成随机的6为验证码
     * @return
     */
    public static String getMsgCode() {
        int n = 6;
        StringBuilder code = new StringBuilder();
        Random ran = new Random();
        for (int i = 0; i < n; i++) {
            code.append(ran.nextInt(10));
        }
        return code.toString();
    }

}
