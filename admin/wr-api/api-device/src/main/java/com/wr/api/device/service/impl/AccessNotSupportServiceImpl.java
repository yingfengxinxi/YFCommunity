package com.wr.api.device.service.impl;

import com.wr.api.device.service.AccessService;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.HealthyConstants;
import com.wr.common.customize.model.Identify;
import com.wr.common.customize.util.IdentifyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author: RainCity
 * @Date: 2021-07-20 17:49:45
 * @Desc: 不支持的识别模式
 */
@Service
public class AccessNotSupportServiceImpl implements AccessService {
    private static final Logger log = LoggerFactory.getLogger(AccessNotSupportServiceImpl.class);

    @Override
    public Boolean support(Integer authType,String authMsg) {
        return !HealthyConstants.MODE_HEALTHY.equals(authType)
                && !HealthyConstants.MODE_FACE.equals(authType);
    }

    @Override
    public AjaxResult execute(Identify identify) {
        log.info("********** 人证比对或其他==> *************");
        return IdentifyUtils.notSupportMode(identify);
    }
}
