package com.wr.api.owner.entity.vo.location;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-12-01 15:19:11
 * @Desc:
 */
@Data
public class ParkOrderVO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 图片路径
     */
    private String imgUrl;

    /**
     * 工单类型
     */
    private String typeName;

    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date time;


    /**
     * 状态
     */
    private String status;

    /**
     * 评分
     */
    private Integer star;
}
