package com.wr.api.owner.service.impl.serve;

import com.wr.remote.domain.ApiMessage;
import com.wr.api.owner.mapper.serve.MessageMapper;
import com.wr.api.owner.service.serve.MessageService;
import com.wr.common.core.web.domain.AjaxResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 党建资讯
 * @Author: lvzy
 * @Date: 2022-11-15 16:42:12
 * @Desc:党建资讯
 */
@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;

    @Override
    public List<ApiMessage> listPage(Long communityId) {
        return messageMapper.listPage(communityId);
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult detail(Long messageId) {
        // 添加一次浏览次数
        messageMapper.addReadNum(messageId);
        return AjaxResult.success(messageMapper.detail(messageId));
    }
}
