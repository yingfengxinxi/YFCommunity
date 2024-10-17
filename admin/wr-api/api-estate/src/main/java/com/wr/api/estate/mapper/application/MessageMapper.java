package com.wr.api.estate.mapper.application;

import com.wr.api.estate.entity.vo.application.MessageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 19:13:55
 * @Desc:
 */
public interface MessageMapper {
    /**
     * 小区 的 党建资讯
     *
     * @param communityId
     * @return
     */
    List<MessageVO> searchByCommunityId(@Param("communityId") Long communityId);

    /**
     * 详情
     *
     * @param messageId
     * @return
     */
    MessageVO detailByMessageId(@Param("messageId") Long messageId);

    /**
     *
     * @param messageId
     */
    void updateLookNumber(@Param("messageId") Long messageId);
}
