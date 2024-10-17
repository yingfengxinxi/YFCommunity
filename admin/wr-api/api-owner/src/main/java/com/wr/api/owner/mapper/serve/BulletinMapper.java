package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.serve.Clue;
import com.wr.api.owner.entity.vo.serve.BulletinVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-15 14:38:33
 * @Desc: 服务-警民互动
 */
public interface BulletinMapper {

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
    BulletinVO getBulletinById(@Param("bulletinId") Long bulletinId);

    /**
     * 检查重复
     * @param bulletinId 通报id
     * @param accountId 账号id
     * @return: java.lang.Integer
     */
   Integer checkAccountIdUnique(@Param("bulletinId") Long bulletinId, @Param("accountId") Long accountId);

    /**
     * 提供线索
     * @param clue
     * @return: java.lang.Integer
     */
    Integer addClue(Clue clue);
}
