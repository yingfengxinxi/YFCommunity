package com.wr.api.estate.service.application.serve;

import com.wr.api.estate.entity.vo.application.serve.BulletinVO;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 13:09:26
 * @Desc:
 */
public interface BulletinService {
    /**
     * 物业通报
     * @param communityId
     * @return
     */
    List<BulletinVO> searchByCommunityId(Long communityId);

    /**
     * 详情
     * @param bulletinId
     * @return
     */
    BulletinVO detailByBulletinId(Long bulletinId);
}
