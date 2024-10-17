package com.wr.common.payment.domain.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2022-11-11 15:08:27
 * @Desc: 获取 code
 */
@Data
public class CodeDTO implements Serializable {
    private static final long serialVersionUID = -6988405213663957125L;

    /**应用ID*/
    private String appId;
    /**redirectUrl*/
    private String redirectUrl;

}
