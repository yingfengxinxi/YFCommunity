package com.wr.api.device.service;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.model.Identify;

/**
 * @Author: RainCity
 * @Date: 2021-07-20 17:07:28
 * @Desc: 设备识别
 */
public interface AccessService {

    /**
     * 识别支持
     * @param authType 认证方式
     * @param authMsg 设备人证比对后读出的身份证号码
     *                or识别到的二维码字符串
     *                or单独刷脸后从设备本地取出的人员身份证号（需提前下发到卡号字段）
     * @return
     */
    Boolean support(Integer authType,String authMsg);

    /**
     * 识别执行
     * @param identify 参数
     * @return
     */
    AjaxResult execute(Identify identify);

}
