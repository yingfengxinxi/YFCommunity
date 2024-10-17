package com.wr.api.estate.service.impl.application;

import com.wr.api.estate.entity.vo.application.MessageVO;
import com.wr.api.estate.mapper.application.MessageMapper;
import com.wr.api.estate.service.application.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 19:22:37
 * @Desc:
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageMapper messageMapper;

    @Override
    public List<MessageVO> searchByCommunityId(Long communityId) {
        return messageMapper.searchByCommunityId(communityId);
    }

    @Override
    public MessageVO detailByMessageId(Long messageId) {
        //增加浏览次数
        messageMapper.updateLookNumber(messageId);
        return messageMapper.detailByMessageId(messageId);
    }
}
