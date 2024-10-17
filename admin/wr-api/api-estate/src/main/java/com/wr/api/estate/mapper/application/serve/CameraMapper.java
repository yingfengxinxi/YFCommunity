package com.wr.api.estate.mapper.application.serve;

import com.wr.api.estate.entity.vo.application.serve.CameraVO;
import com.wr.common.customize.util.CommunityHik;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 20:10:16
 * @Desc:
 */
public interface CameraMapper {

    /**
     * 在线 监控点
     *
     * @param communityId,cameraName
     * @return
     */
    List<CameraVO> searchByCommunityId(@Param("communityId") Long communityId, @Param("cameraName") String cameraName);

    /**
     * 根据小区id查询海康配置
     *
     * @param communityId
     * @return
     */
    CommunityHik selectHikByCommunityId(Long communityId);
}
