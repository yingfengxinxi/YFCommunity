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
@ApiModel("短信发送记录表")
@TableName(value = "xccj_community.bus_send_sms_record")
public class SendSmsRecord extends BaseEntity {

    //	主键
    private String id;
    //	短信模板id
    private String sendSmsId;
    //	用户id物业类型sys_user表userId,业主类型account_id
    private Long userId;
    //发送手机号
    private String phone;
    //	发送端0=物业人员手机号1=业主手机号
    private String userType;
    //节日名称
    private String festivalName;
    //发送状态0=待发送1=成功2=失败
    private String status;
    //短信类型0=站内消息1=短信消息
    private String type;
    //	失败原因
    private String errorMsg;
    //删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;
}
