package com.wr.remote.govern.party.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.remote.govern.party.Party;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: SJiang
 * @Date: 2022-10-28 11:29:21
 * @Desc: 党建资讯 VO
 */
@Data
public class MessageVO implements Serializable {
    private static final long serialVersionUID = 6235582131733269411L;

    /**
     * 主键
     */
    private Long messageId;
    /**
     * 咨询标题
     */
    private String msgTitle;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建人
     */
    private String updateBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 内容
     */
    private String content;
    /**
     * 阅读数
     */
    private Long lookNumber;
    /**
     * 组织id
     */
    private Long [] partyIds;
    /**
     * 组织
     */
    private List<Party> parties;
    /**
     * 发布组织
     */
    private String partyName;
    /**
     * 物业id
     */
    private Long estateId;
    /**
     * 请求参数
     */
    private Map<String, Object> params;
    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;
    /**
     * 小区
     */
    private String communityName;
    /**
     * 跳转路径
     */
    private String skipUrl;

    /**
    * 置顶0=否1=是
    */
    private String top;
    @Override
    public String toString() {
        return "MessageVO{" +
                "messageId=" + messageId +
                ", msgTitle='" + msgTitle + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                '}';
    }
}
