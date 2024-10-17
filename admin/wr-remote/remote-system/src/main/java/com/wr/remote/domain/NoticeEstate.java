package com.wr.remote.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Author lvzy
 * @Date 2024/3/5 18:17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("通知物业需要处理数据公告表")
@TableName(value = "xccj_community.bus_notice_estate")
public class NoticeEstate extends BaseEntity {

    //主键
    private String id;
    //物业id，表xccj_estate_sys.bus_estate
    private Long estateId;
    //小区id
    private Long communityId;
    //	头条标题
    private String noticeTitle;
    //状态：0=未读，1=已读
    private String readStatus;
    //类型0=生日，节假日1=物业费缴费
    private String flag;
    //删除标记：0=未删除，1=已删除
    private String delFlag;

}
