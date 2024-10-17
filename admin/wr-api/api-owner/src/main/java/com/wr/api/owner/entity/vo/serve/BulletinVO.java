package com.wr.api.owner.entity.vo.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: SJiang
 * @Date: 2022-11-15 14:36:42
 * @Desc: 人员通报VO
 */
@Data
public class BulletinVO implements Serializable {
    private static final long serialVersionUID = -6892205565367351497L;

    /**
     * 主键
     */
    private Long bulletinId;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别：0=男，1=女，2=未知
     */
    private String gender;
    /**
     * 年龄
     */
    private Integer userAge;
    /**
     * 犯罪级别：A=A级，B=B级
     */
    private String crimeLevel;
    /**
     * 通报原因
     */
    private String crimeReason;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 证件类型
     **/
    private String cardType;
    /**
     * 证件号码
     **/
    private String cardNo;
}
