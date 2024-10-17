package com.wr.api.owner.entity.vo.serve;

import com.wr.api.owner.entity.serve.MemberAct;
import lombok.Data;

import java.io.Serializable;

/**
 * 党员活动扩展
 * @Author: lvzy
 * @Date: 2022-11-15 15:26:31
 * @Desc:党员活动扩展
 */
@Data
public class MemberActVo extends MemberAct implements Serializable {

    private static final long serialVersionUID = 3322743882721998460L;

    /**
     * 页数
     */
    private Integer pageNum;

    /**
     * 每页条数
     */
    private Integer pageSize;

    /**
     * 党员ID
     */
    private Long memberId;

    /**
     * 显示隐藏
     */
    private String hideText;

    /**
     * 参加人姓名
     */
    private String joinName;
    /**
     * 参加人手机号
     */
    private String joinPhone;
}
