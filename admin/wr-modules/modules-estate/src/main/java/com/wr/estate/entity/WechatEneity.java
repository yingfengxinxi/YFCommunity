package com.wr.estate.entity;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @author: bajie
 * @create: 2022/11/1
 * @Description:
 * @FileName: WechatEneity
 * @History:
 */
@Data
public class WechatEneity extends BaseEntity {

    private Long wechatId;
    private Long communityId;
    private String appId;
    private String appSecret;
    private String mchId;
    private String serialNo;
    private String privateUrl;
    private String apiKey;
    private String refundUrl;
    private String wechatStatus;

    private String wechatName;

    private String communityName;

}
