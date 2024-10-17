package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.vo.serve.MajordomoVO;

/**
 * @Author: SJiang
 * @Date: 2022-11-11 16:48:25
 * @Desc: 服务-专属管家 业务
 */
public interface MajordomoService {

    /**
     * 获取楼栋专属管家
     * @param communityId 小区id
     * @param buildingId 楼栋id
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.MajordomoVO>
     */
    MajordomoVO getMajordomo(Long communityId, Long buildingId);
}
