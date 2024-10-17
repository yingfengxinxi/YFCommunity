package com.wr.api.device.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2021-09-14 19:43:39
 * @Desc: 门禁
 */
@Data
public class AccessVO implements Serializable {
    private static final long serialVersionUID = 711159007171055029L;

    private String indexCode;

    private String deviceName;

    private String deviceIp;

    private String inoutType;

    private Long communityId;
    private String communityName;

}
