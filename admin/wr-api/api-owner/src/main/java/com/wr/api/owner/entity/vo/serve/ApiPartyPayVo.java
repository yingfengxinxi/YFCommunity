package com.wr.api.owner.entity.vo.serve;

import com.wr.api.owner.entity.serve.ApiPartyPay;
import lombok.Data;

import java.io.Serializable;

/**
 * 党费缴费记扩展
 * @Author: lvzy
 * @Date: 2022-11-17 13:07:16
 * @Desc:党费缴费记扩展
 */
@Data
public class ApiPartyPayVo extends ApiPartyPay implements Serializable {

    private static final long serialVersionUID = -270660287927594365L;

    /**
     * 党组织名称
     */
    private String partyName;
}
