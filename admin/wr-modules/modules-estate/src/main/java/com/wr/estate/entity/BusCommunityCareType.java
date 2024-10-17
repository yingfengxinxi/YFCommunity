package com.wr.estate.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/9 14:13
 * 社区关怀类型
 */
@Data
@ApiModel("社区关怀类型")
public class BusCommunityCareType implements Serializable {

    private Long id;

    //类型名称
    private String name;

    //背景色
    private String backgroupColor;
    //颜色1
    private String color1;
    //颜色2
    private String color2;

    //顺序
    private int sort;

    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
    private String delFlag;
}
