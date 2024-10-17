package com.wr.common.payment.pay.domain;

import com.alibaba.fastjson2.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName WechatPayResourceIn
 * @Author lvzy
 * @Date 2023/6/14 0014 10:56
 **/
@Data
public class WechatPayResourceIn {

    @JSONField(name = "algorithm")
    @ApiModelProperty("加密算法类型")
    private String algorithm;

    @JSONField(name = "ciphertext")
    @ApiModelProperty("数据密文")
    private String ciphertext;

    @JSONField(name = "associated_data")
    @ApiModelProperty("附加数据")
    private String associated_data;

    @JSONField(name = "nonce")
    @ApiModelProperty("随机串")
    private String nonce;
}
