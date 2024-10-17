package com.wr.api.estate.service.application.serve;

import com.wr.api.estate.entity.vo.application.serve.OlderDetailVO;
import com.wr.api.estate.entity.vo.application.serve.OlderVO;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-15 13:23:26
 * @Desc: 老人档案接口
 */
public interface OlderService {

    /**
     * 查询老人档案列表
     * @param olderVO
     * @return
     */
    List<OlderVO> selectOlderList(OlderVO olderVO);

    /**
     * 查询老人详情
     * @param olderId
     * @return
     */
    OlderDetailVO getOlderDetail(Long olderId);

    /**
     * 养老审核
     * @param olderVO
     * @return
     */
    Integer olderAudit(OlderVO olderVO);
}
