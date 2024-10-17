package com.wr.estate.service.message;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.domain.ApiMessage;
import com.wr.remote.domain.vo.ApiMessageVo;

import java.util.List;

/**
 * 党建资讯
 *
 * @Author: lvzy
 * @Date: 2022-11-15 16:41:55
 * @Desc:党建资讯
 */
public interface MessageService {

    /**
     * 分页
     *
     * @param communityId
     * @return
     */
    List<ApiMessage> listPage(ApiMessage apiMessage);

    void insertSelective(ApiMessage apiMessage);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(ApiMessage record);

    /**
     * 删除
     *
     * @param messageId
     * @return
     */
    int deleteByPrimaryKey(Long messageId);

    /**
     * 详情
     *
     * @param messageId
     * @return
     */
    ApiMessageVo detail(Long messageId);
}
