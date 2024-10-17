package com.wr.remote.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author lvzy
 * @Date 2024/2/29 21:00
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("短信模板配置")
@TableName(value = "xccj_community.bus_send_sms")
public class SendSms extends BaseEntity {


    //	主键
    private String id;
    //设置表id
    private String sendSettingId;
    //小区id，表xccj_community.bus_community
    private Long communityId;
    //短信类型0=节假日1=生日
    private String templateType;
    //阿里云短信模板
    private String templateCode;
    //短信描述
    private String description;
    //是否停用0=否1=是
    private String status;
    //删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;


}
