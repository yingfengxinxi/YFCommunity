package com.wr.api.estate.entity.vo.application.serve;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 13:45:10
 * @Desc: 场馆信息
 */
@Data
public class VenueVO {

    /**
     * 主键
     */
    private Long venueId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 场馆名称
     */
    private String venueName;
    /**
     * 场馆地址
     */
    private String address;
    /**
     * 押金
     */
    private BigDecimal deposit;
    /**
     * 每小时费用
     */
    private BigDecimal hourFee;
    /**
     * 管理员
     */
    private String staffName;

    /**
     * 管理员 联系电话
     */
    private String staffPhone;
    /**
     * 场馆图片
     */
    private String venueImage;
    /**
     * 状态：0=可用，1=关闭
     */
    private String venueStatus;
    /**
     * 场馆预约人 list
     */
    private List<LinkedHashMap<String,Object>> appointList;
}
