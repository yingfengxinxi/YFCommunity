package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.serve.Rescue;
import com.wr.api.owner.entity.vo.serve.RescueVO;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-11 15:37:29
 * @Desc: 服务-社区救助 业务
 */
public interface RescueService {

    /**
     * 获取社区救助列表
     * @param communityId
     * @param releaseId
     * @param releaseType
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.RescueVO>
     */
    List<RescueVO> getRescueAll(Long communityId,String releaseType, Long releaseId);

    /**
     * 获取社区救助详情
     * @param rescueId
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.RescueVO>
     */
    RescueVO getRescueById(Long rescueId);

    /**
     * 添加救助
     * @param rescue
     * @return: java.lang.Integer
     */
    Integer addRescue(Rescue rescue);
}
