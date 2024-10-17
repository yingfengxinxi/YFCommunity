package com.wr.estate.entity.vo;

import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/3/1 13:02
 */
@Data
public class SendSmsRecordVo extends BaseEntity {

    //id
    private String id;
    //物业id
    private Long estateId;
    //小区名称
    private String communityName;
    //小区id
    private String communityId;
    //楼栋名称
    private String buildingName;
    //单元楼名称
    private String unitName;
    //房间号
    private String roomName;
    //节假日名称
    private String festivalName;
    //接口人姓名
    private String name;
    //短信类型0=节日1=生日
    private String templateType;
    //0=待发送1=成功2=失败
    private String status;
    //失败信息
    private String errorMsg;
    //发送开始时间
    private String startTime;
    //发送结束时间
    private String endTime;
}
