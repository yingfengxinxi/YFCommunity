package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.vo.serve.StatuteVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-15 13:11:58
 * @Desc: 服务-自治规约
 */
public interface StatuteMapper {

    /**
     * 获取本小区的自治规约
     * @param communityId
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.StatuteVO>
     */
    List<StatuteVO> getStatuteAll(@Param("communityId") Long communityId);

    /**
     * 查看详情
     * @param statuteId
     * @return: com.wr.api.owner.entity.vo.serve.StatuteVO
     */
    StatuteVO getStatuteById(@Param("statuteId") Long statuteId);
}
