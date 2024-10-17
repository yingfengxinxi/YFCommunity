package com.wr.api.owner.service.serve;

import com.wr.remote.domain.ApiMessage;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * 党建资讯
 * @Author: lvzy
 * @Date: 2022-11-15 16:41:55
 * @Desc:党建资讯
 */
public interface MessageService {

    /**
     * 分页
     * @param communityId
     * @return
     */
    List<ApiMessage> listPage(Long communityId);

    /**
     * 详情
     * @param messageId
     * @return
     */
    AjaxResult detail(Long messageId);
}
