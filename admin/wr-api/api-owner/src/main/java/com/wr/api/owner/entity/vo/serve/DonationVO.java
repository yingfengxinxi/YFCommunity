package com.wr.api.owner.entity.vo.serve;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: SJiang
 * @Date: 2022-11-14 21:14:26
 * @Desc: 业主捐赠 VO
 */
@Data
public class DonationVO implements Serializable {

    /**
     * 主键
     */
    private Long donationId;
    /**
     * 标题
     */
    private String donationTitle;
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
}
