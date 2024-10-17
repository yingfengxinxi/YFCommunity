package com.wr.api.estate.entity.vo.application.serve;

import com.wr.api.estate.entity.application.serve.Rescue;
import com.wr.api.estate.entity.application.serve.RescueUser;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 13:55:09
 * @Desc: 社会救助视图类
 */
@Data
public class RescueVO extends Rescue {

    private static final long serialVersionUID = 6294895180656585876L;

    /**
     * 志愿者集合
     */
    private List<RescueUser> rescueUserList;

    /**
     * 性别
     */
    private String gender;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 发布人姓名
     */
    private String publisherName;

    /**
     * 发布人手机号
     */
    private String publisherPhone;

    /**
     * 发布人年龄
     */
    private String publisherAge;

    /**
     * 发布人身份证
     */
    private String publisherIdCard;

    /**
     * 活动状态 0进行中 1未开始 2已结束
     */
    private String rescueStatus;
}
