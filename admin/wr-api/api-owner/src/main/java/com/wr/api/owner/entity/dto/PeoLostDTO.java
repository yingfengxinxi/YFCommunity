package com.wr.api.owner.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: xuShu
 * @Date: 2022-11-29 14:22:51
 * @Desc:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeoLostDTO {
    /** 主键*/
    private Long lostId;

    /** 小区id，表bus_community*/
    private Long communityId;

    /** 发布人账号id，表bus_account*/
    private Long accountId;

    /** 姓名*/
    private String lostName;

    /** 性别：0=男，1=女*/
    private String gender;

    /** 年龄*/
    private Integer lostAge;

    /** 详情*/
    private String lostInfo;

    /** 联系人电话*/
    private String contactPhone;

    /** 发布时间*/
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date publishTime;

    /** 缩略图*/
    private String thumbnail;
}
