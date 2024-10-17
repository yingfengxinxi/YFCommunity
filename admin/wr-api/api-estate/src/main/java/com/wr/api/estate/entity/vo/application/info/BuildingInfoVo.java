package com.wr.api.estate.entity.vo.application.info;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @Author lvzy
 * @Date 2024/1/23 20:35
 */
@Data
public class BuildingInfoVo {

    //姓名
    private String name;
    //手机号
    private String phone;
    //证件类型
    private String cardType;
    //证件号
    private String cardNo;
    //性别
    private String sex;
    //政治面貌
    private String politicsStatus;
    //个人介绍
    private String selfIntro;
    //租客信息/业主信息
    private String personType;
    //业主关系
    private String ownerRelation;
    //交房时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String deliveryTime;
}
