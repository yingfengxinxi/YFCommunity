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
@ApiModel("阿里云短信配置")
@TableName(value = "xccj_community.bus_send_setting")
public class SendSetting extends BaseEntity {
    //	主键
    private String id;
    //物业id
    private Long estateId;
    //阿里云短信key
    private String accessKeyId;
    //	阿里云短信密钥
    private String accessKeySecret;
    //阿里云短信签名
    private String signName;
    //地域
    private String region;
    //是否停用0=否1=是
    private String status;
    //删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;

}
