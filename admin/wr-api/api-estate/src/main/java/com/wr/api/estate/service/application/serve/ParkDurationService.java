package com.wr.api.estate.service.application.serve;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.CommunityWechat;
import com.wr.remote.estate.financial.ParkDurationEntity;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * @Author lvzy
 * @Date 2024/1/20 17:30
 */
public interface ParkDurationService extends IService<ParkDurationEntity> {

    /**
     *
     * @param jsonObject
     */
    void updateWechatPay(JSONObject jsonObject) throws ParseException;
}
