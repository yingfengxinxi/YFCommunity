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
 * @Date 2024/3/5 17:50
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("节假日公告表")
@TableName(value = "xccj_community.bus_notice_festival")
public class NoticeFestival extends BaseEntity {

    //主键
    private String id;
    //小区id
    private Long communityId;
    //短信发送记录表id
    private String sendSmsRecordId;
    //标题
    private String noticeTitle;
    //内容
    private String content;
    //用户id物业类型sys_user表userId,业主类型account_id
    private Long userId;
    //账号类型0=物业人员手机号1=业主手机号
    private String userType;
    //	删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;

}
