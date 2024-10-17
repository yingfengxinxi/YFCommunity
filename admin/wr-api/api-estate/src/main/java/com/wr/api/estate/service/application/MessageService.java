package com.wr.api.estate.service.application;

import com.wr.api.estate.entity.vo.application.MessageVO;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 19:22:05
 * @Desc:
 */
public interface MessageService {

    /**
     * 小区 的 党建咨询
     * @param communityId
     * @return
     */
    List<MessageVO> searchByCommunityId(Long communityId);

    /**
     * 详情
     * @param messageId
     * @return
     */
    MessageVO detailByMessageId(Long messageId);
}
