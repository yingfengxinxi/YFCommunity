package com.wr.remote.domain;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Description 敏感词管理实体类
 * @Date 2022/9/19 10:28
 * @Author DIANWEI
 **/
@Data
public class Sensitive extends BaseEntity {
    /**
     * 主键id
     */
    private Long sensitiveId;
    /**
     * 词内容
     */
    private String phrases;
    /**
     * 排序
     */
    private Long orderNum;
    /**
     * 删除标记0：未删除1已删除
     */
    private String delFlag;
}
