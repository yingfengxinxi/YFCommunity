package com.wr.common.payment.domain.dto;

import com.wr.common.payment.domain.Wechat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Author: RainCity
 * @Date: 2022-11-11 14:57:21
 * @Desc: 获取open_id
 */
@Data
public class OpenDTO extends Wechat {
    private static final long serialVersionUID = -3230986510925008157L;

    /** 前端获取的code值 */
    private String code;

    private Long communityId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("code", code)
                .toString() + super.toString();
    }
}
