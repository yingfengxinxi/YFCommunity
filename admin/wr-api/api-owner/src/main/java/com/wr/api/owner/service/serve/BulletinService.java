package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.serve.Clue;
import com.wr.api.owner.entity.vo.serve.BulletinVO;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-15 14:54:22
 * @Desc: 服务-警民互动 业务
 */
public interface BulletinService {

    /**
     * 获取人员通报数据
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.BulletinVO>
     */
    List<BulletinVO> getBulletinAll();

    /**
     * 获取详情
     * @param bulletinId 主键id
     * @return: com.wr.api.owner.entity.vo.serve.BulletinVO
     */
    BulletinVO getBulletinById(Long bulletinId);

    /**
     * 提供线索
     * @param clue
     * @return: java.lang.Integer
     */
    AjaxResult addClue(Clue clue);
}
