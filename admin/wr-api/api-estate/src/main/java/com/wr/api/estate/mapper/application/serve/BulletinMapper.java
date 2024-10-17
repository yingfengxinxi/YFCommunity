package com.wr.api.estate.mapper.application.serve;

import com.wr.api.estate.entity.vo.application.serve.BulletinVO;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 11:56:10
 * @Desc:
 */
public interface BulletinMapper {
    /**
     * 物业通报
     * @param communityId
     * @return
     */
    List<BulletinVO> searchByCommunityId(Long communityId);

    /**
     * 详情
     * @param id
     * @return
     */
    BulletinVO detailByBulletinId(Long id);
}
