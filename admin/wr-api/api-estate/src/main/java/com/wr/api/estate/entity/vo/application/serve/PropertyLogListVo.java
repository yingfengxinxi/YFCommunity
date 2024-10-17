package com.wr.api.estate.entity.vo.application.serve;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/11 19:53
 */
@Data
public class PropertyLogListVo extends BaseEntity {


    private Long logId;
    //楼栋号码
    private String buildingName;
    //单元楼
    private String unitName;
    //门牌号
    private String roomName;
    //房屋面积
    private String roomArea;
    //房屋性质：1=安置房，2=商品房，3=公租房
    private String roomType;
    //收费类型：0=按住宅面积收费，1=统一收费
    private String feeType;
    //缴费金额
    private BigDecimal payAmount;
    //支付时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;
    //业主姓名
    private String ownerName;
    //性别：0=男，1=女，2=未知
    private String gender;
    //业主手机号
    private String ownerPhone;


    private Long communityId;
}
