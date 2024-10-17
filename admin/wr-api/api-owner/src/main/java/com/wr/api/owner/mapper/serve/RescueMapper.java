package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.serve.Rescue;
import com.wr.api.owner.entity.serve.RescueUser;
import com.wr.api.owner.entity.vo.serve.RescueVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Author: SJiang
 * @Date: 2022-11-11 14:40:22
 * @Desc: 服务-社区救助 mapper
 */
public interface RescueMapper {

    /**
     * 获取社区救助列表（业主）
     * @param communityId 小区id
     * @param releaseId 发布人id
     * @param releaseType 人员类型
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.RescueVO>
     */
    List<RescueVO> getOwnerRescueAll(@Param("communityId") Long communityId,@Param("releaseType") String releaseType, @Param("releaseId") Long releaseId);

    /**
     * 获取社区救助列表（租客）
     * @param communityId 小区id
     * @param releaseId 发布人id
     * @param releaseType 人员类型
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.RescueVO>
     */
    List<RescueVO> getTenantRescueAll(@Param("communityId") Long communityId,@Param("releaseType") String releaseType, @Param("releaseId") Long releaseId);

    /**
     * 获取社区救助详情
     * @param rescueId
     * @return: java.util.List<com.wr.api.owner.entity.vo.serve.RescueVO>
     */
    RescueVO getRescueById(@Param("rescueId") Long rescueId);

    /**
     * 获取志愿者信息
     * @param rescueId
     * @return: java.util.List<com.wr.api.owner.entity.serve.RescueUser>
     */
    List<RescueUser> getRescueUsers(@Param("rescueId") Long rescueId);

    /**
     * 添加救助
     * @param rescue
     * @return: java.lang.Integer
     */
    Integer addRescue(Rescue rescue);
}
