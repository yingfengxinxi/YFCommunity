package com.wr.remote.domain.vo;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/3/18 13:37
 */
@Data
public class StaffEvaluationVo extends BaseEntity {

    //业主名称
    private String ownerName;
    //业主手机号
    private String ownerPhone;
    //业主评论
    private String satisfactionLevel;
    //业主评论内容
    private String comments;
    //
    private Long staffId;
}
