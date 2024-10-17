package com.wr.remote.aiot;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 15:52:15
 * @Desc: 预警事件
 */
@Data
public class WarnEvent implements Serializable {
    private static final long serialVersionUID = -8521576325344369620L;

    /** 主键 */
    private Long warnId;

    /** 事件从接收者（程序处理后）发出的时间,ISO8601 */
    private String sendTime;

    /** 事件类别 */
    private String ability;

    /** 事件唯一标识,同一事件若上报多次，则上报事件的eventId相同 */
    private String eventId;

    /** 事件源编号，物理设备是资源编号 */
    private String srcIndex;

    /** 事件源类型 */
    private String srcType;

    /** 事件源名称 */
    private String srcName;

    /** 事件类型 */
    private Integer eventType;

    /** 事件状态:0-瞬时,1-开始,2-停止,4-事件联动结果更新,5-事件图片异步上传 */
    private Integer status;

    /** 脉冲超时时间,单位：秒 */
    private Integer timeout;

    /** 事件规则名称 */
    private String eventName;

    /** 事件发生时间（设备时间）,ISO8601 */
    private String happenTime;

    /** 事件结束时间 */
    private String stopTime;

    /** 用户自定义注释 */
    private String remark;

    /** 记录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recordTime;

    /** 所属小区id */
    private Long communityId;
    private String communityName;

    /** 删除标记：0=未删除，1=已删除 */
    private String delFlag;

    private String startTime;
    private String endTime;
    private String address;

    /** 门禁事件扩展信息 */
    List<AcsData> acsDataList = Lists.newArrayList();
    /** 可视对讲预警事件详情 */
    List<VisData> visDataList = Lists.newArrayList();
    /** 视频预警事件扩展信息 */
    List<VideoData> videoDataList = Lists.newArrayList();
}
