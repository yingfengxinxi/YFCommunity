package com.wr.remote.aiot;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @Author: RainCity
 * @Date: 2022-10-25 20:59:18
 * @Desc: 道闸
 */
@Data
public class Barrier extends BaseDevice {
    private static final long serialVersionUID = 1686659511762599365L;

    /**主键*/
    private Long barrierId;

    /**车道名称*/
    private String barrierName;

    /**开闸输出口 2：C1-N1 3：C2-N2 4：C3-N3 5：C4-N4*/
    private String openOutput;

    /**关闸输出口 2：C1-N1 3：C2-N2 4：C3-N3 5：C4-N4*/
    private String closeOutput;

    /**组件标志*/
    private String comId;

    private String connectStyle;

    /** 设备二维码 */
    private String deviceQr;

    private String parkIndex;
    private String parkName;

    private String entranceIndex;
    private String entranceName;

    private String roadwayIndex;
    private String roadwayName;

    /**
     * 所属区域路径，由唯一标示组成
     */
    private String regionPath;
    /**
     * 设备系列
     */
    private String deviceType;
    /**
     * 接入协议
     */
    private String treatyType;
    /**
     * 资源类型
     */
    private String resourceType;
    /**
     * 所属区域indexCode
     */
    private String regionIndexCode;
    /**
     * 端口
     */
    private String port;
    /**
     * 设备ip
     */
    private String ip;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("barrierId", barrierId)
                .append("communityId", getCommunityId())
                .append("indexCode", getIndexCode())
                .append("barrierName", barrierName)
                .append("openOutput", openOutput)
                .append("closeOutput", closeOutput)
                .append("comId", comId)
                .append("connectStyle", connectStyle)
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("manufacturer", getManufacturer())
                .append("renewalBy", getRenewalBy())
                .append("renewalTime", getRenewalTime())
                .append("updateBy", getUpdateBy())
                .append("operateTime", getOperateTime())
                .append("operateTime", getAddress())
                .append("deviceQr", deviceQr)
                .toString();
    }
}
