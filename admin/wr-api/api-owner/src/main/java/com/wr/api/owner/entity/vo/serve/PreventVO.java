package com.wr.api.owner.entity.vo.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: SJiang
 * @Date: 2022-11-15 10:19:26
 * @Desc: 防控信息VO
 */
@Data
public class PreventVO implements Serializable {
    private static final long serialVersionUID = 3943011659658229522L;

    /**
     * 主键
     */
    private Long preventId;
    /**
     * 标题
     */
    private String preventTitle;
    /**
     * 图片
     */
    private String imageUrl;
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
