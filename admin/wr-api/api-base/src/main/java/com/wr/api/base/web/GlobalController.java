package com.wr.api.base.web;

import com.alibaba.fastjson2.JSONObject;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.domain.R;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.ApiConstants;
import com.wr.common.customize.util.SendMsgUtils;
import com.wr.common.redis.service.RedisService;
import com.wr.remote.domain.SysFile;
import com.wr.remote.system.RemoteFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Author: RainCity
 * @Date: 2022-06-01 14:36:15
 * @Desc: 全局控制器
 */
@RestController
@RequestMapping("/v2/global")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class GlobalController extends BaseController {
    private final RedisService redisService;
    private final RemoteFileService remoteFileService;

    /**
     * 获取短信验证码---测试用
     * @param phoneNumber 手机号
     * @return com.wr.common.core.domain.AjaxResult
     */
    @AuthRequired
    @GetMapping("/get_code_test")
    public AjaxResult getCodeTest(String phoneNumber) {
        return getSmsCode(phoneNumber, true);
    }

    /**
     * 发送短信验证码
     * @param phoneNumber 手机号
     * @return com.wr.common.core.domain.AjaxResult
     */
    @AuthRequired
    @GetMapping(value = "/get_code")
    public AjaxResult getCode(String phoneNumber) {
        return getSmsCode(phoneNumber, false);
    }

    private AjaxResult getSmsCode(String phoneNumber, Boolean isTest){
        try {
            String key = ApiConstants.TIMES_KEY + DateUtils.dateTime() + ":" + phoneNumber;
            //获取缓存中的次数
            Integer cacheNum = redisService.getCacheObject(key);
            int num = Optional.ofNullable(cacheNum).orElse(0);
            if(num >= ApiConstants.MAX_SMS_COUNT){
                return AjaxResult.error("对不起，今日验证码获取次数已达上限");
            }
            String code = SendMsgUtils.getMsgCode();
            logger.info("phoneNum:{}的短信验证码是:{}", phoneNumber, code);
            num++;
            // 放入缓存，5分钟后删除
            redisService.setCacheObject(ApiConstants.SMS_KEY + phoneNumber, code, ApiConstants.EXPIRATION, TimeUnit.MINUTES);
            // 存入今日获取验证码的次数，一天后自动删除
            redisService.setCacheObject(key, num,  1L, TimeUnit.DAYS);
            if(isTest){
                AjaxResult ajaxResult = AjaxResult.success("短信发送成功");
                ajaxResult.put("smsCode",code);
                return ajaxResult;
            }
            String sendResult = SendMsgUtils.sendSmsCode(phoneNumber, code);
            if(null == sendResult){
                return AjaxResult.error("短信发送失败");
            }
            return AjaxResult.success("短信发送成功");
        } catch (Exception e) {
            logger.error("短信发送失败：{}", e.getMessage());
            return AjaxResult.error("短信发送失败");
        }
    }

    /**
     * 文件上传
     */
    @AuthRequired
    @PostMapping(value = "/upload")
    public AjaxResult imageUpload(@RequestParam(name = "file") MultipartFile file) {
        logger.info("文件上传======>");
        if (file.isEmpty()) {
            logger.error("请上传文件");
            return AjaxResult.error("请上传文件");
        }
        R<SysFile> fileResult = remoteFileService.upload(file);
        Assert.notNull(fileResult, "文件上传失败");
        Assert.notNull(fileResult.getData(), "文件上传失败");
        String url = fileResult.getData().getUrl();
        JSONObject data = new JSONObject();
        data.put("filePath", url);
        return AjaxResult.success(data);
    }

}
