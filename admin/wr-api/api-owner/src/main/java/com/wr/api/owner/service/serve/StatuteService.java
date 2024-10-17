package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.vo.serve.StatuteVO;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-15 13:26:35
 * @Desc: 服务-自治规约 业务
 */
public interface StatuteService {

    /**
     * 获取本小区的自治规约
     * @param communityId
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.StatuteVO>
     */
    List<StatuteVO> getStatuteAll(Long communityId);

    /**
     * 查看详情
     * @param statuteId
     * @return: com.wr.api.owner.entity.vo.serve.StatuteVO
     */
    StatuteVO getStatuteById(Long statuteId);
}
