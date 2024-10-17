package com.wr.api.owner.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.stringtemplate.v4.ST;

import java.util.Date;

/**
 * @Author: xuShu
 * @Date: 2022-12-01 10:47:27
 * @Desc: 我得寻人寻物
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyProItemLostDTO {

    private String comId;

    /** 主键*/
    private String lostId;

    /** 缩略图*/
    private String thumbnail;

    /** 姓名*/
    private String lostName;

    /** 发布时间*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishTime;

    /** 状态：0=寻找中，1=已找回，2=已撤销*/
    private String lostStatus;
}
