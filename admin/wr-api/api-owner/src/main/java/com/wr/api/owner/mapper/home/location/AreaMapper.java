package com.wr.api.owner.mapper.home.location;


import com.wr.api.owner.entity.home.location.ApiCamera;
import com.wr.api.owner.entity.home.location.Area;
import com.wr.api.owner.entity.home.location.AreaExample;
import com.wr.api.owner.entity.vo.location.AreaAndcommunityVo;
import com.wr.api.owner.entity.vo.location.CameraVO;
import com.wr.api.owner.entity.vo.location.DoorVo;
import com.wr.common.customize.util.CommunityHik;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AreaMapper {
    int countByExample(AreaExample example);

    int deleteByExample(AreaExample example);

    int deleteByPrimaryKey(Long areaId);

    int insert(Area record);

    int insertSelective(Area record);

    List<Area> selectByExample(AreaExample example);

    Area selectByPrimaryKey(Long areaId);

    int updateByExampleSelective(@Param("record") Area record, @Param("example") AreaExample example);

    int updateByExample(@Param("record") Area record, @Param("example") AreaExample example);

    int updateByPrimaryKeySelective(Area record);

    int updateByPrimaryKey(Area record);

    List<AreaAndcommunityVo> selectListArea();

    //查询门禁设备
    List<DoorVo> selectDoorEquipment(@Param("accountId")Long accountId,@Param("communityId")Long communityId);

    /**
     * 根据条件查询监控点集合
     * @param apiCamera
     * @return {@link List< CameraVO>}
     * @ver v1.0.0
     */
    List<CameraVO> selectCameraList(ApiCamera apiCamera);

    /**
     * 根据小区id查询海康配置
     * @param communityId 小区id
     * @return com.wr.common.customize.util.CommunityHik
     */
    CommunityHik selectHikByCommunityId(Long communityId);


}