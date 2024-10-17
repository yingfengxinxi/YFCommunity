package com.wr.remote.govern.party;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

/**
 * @Author: SJiang
 * @Date: 2022-10-24 17:10:31
 * @Desc: 党建资讯实体
 */
@Data
@TableName("xccj_govern.bus_message")
public class Message extends BaseEntity {
    private static final long serialVersionUID = 2906725699297613163L;
    /**
     * 主键
     */
    private Long messageId;
    /**
     * 小区id，表xccj_community.bus_community
     */
    private Long communityId;
    /**
     * 咨询标题
     */
    private String msgTitle;
    /**
     * 跳转路径
     */
    private String skipUrl;
    /**
     * 内容
     */
    private String content;
    /**
     * 阅读数
     */
    private Long lookNumber;
    /**
     * 排序序号
     */
    private Long sortNumber;
    private String delFlag;
    private String top;
    @Override
    public String toString() {
        return "BusMessage{" +
                "messageId=" + messageId +
                ", communityId=" + communityId +
                ", msgTitle='" + msgTitle + '\'' +
                ", skipUrl='" + skipUrl + '\'' +
                ", content='" + content + '\'' +
                ", lookNumber=" + lookNumber +
                ", sortNumber=" + sortNumber +
                '}';
    }
}

