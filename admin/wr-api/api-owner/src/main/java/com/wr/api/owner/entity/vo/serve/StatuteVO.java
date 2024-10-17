package com.wr.api.owner.entity.vo.serve;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: SJiang
 * @Date: 2022-11-15 13:10:39
 * @Desc: 自治规约VO
 */
@Data
public class StatuteVO implements Serializable {
    private static final long serialVersionUID = -4497874588223765273L;

    /**
     * 主键
     */
    private Long statuteId;
    /**
     * 标题
     */
    private String statuteTitle;
    /**
     * 封面
     */
    private String coverUrl;
    /**
     * 内容
     */
    private String content;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 创建人*/
    private String createBy;

}
