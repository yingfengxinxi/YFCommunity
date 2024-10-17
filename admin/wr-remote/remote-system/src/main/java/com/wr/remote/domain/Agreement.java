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
 * @Date 2024/2/7 20:37
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("移动端登录页面协议")
@TableName(value = "xccj_community.bus_agreement")
public class Agreement extends BaseEntity {

    private String id;
    //物业移动端隐私政策
    private String estatePrivacyPolicy;

    //物业移动端用户协议
    private String estateThumbnail;

    //业主移动端隐私政策
    private String ownerPrivacyPolicy;

    //业主移动端用户协议
    private String ownerThumbnail;

    @TableLogic
    private String delFlag;

}
