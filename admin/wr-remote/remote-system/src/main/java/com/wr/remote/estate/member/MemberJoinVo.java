package com.wr.remote.estate.member;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/14 20:37
 */
@Data
public class MemberJoinVo {

    private Long activityId;

    //报名人头像
    private String avatar;
    //报名人
    private String ownerName;

    //备注
    private String remark;

    //手机号
    private String ownerPhone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String searchValue;
}
