package com.wr.remote.domain;

import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel("业主生日通知公告表")
@TableName(value = "xccj_community.bus_notice_birthday")
public class NoticeBirthday extends BaseEntity {

    //主键
    private String id;
    //小区id
    private Long communityId;
    //短信发送记录表id
    private String sendSmsRecordId;
    //标题
    private String noticeTitle;
    //	内容
    private String content;
    //	业主账号account_id
    private Long accountId;
    //删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;

}
