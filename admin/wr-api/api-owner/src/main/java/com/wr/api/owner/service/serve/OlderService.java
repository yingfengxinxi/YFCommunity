package com.wr.api.owner.service.serve;

import com.wr.api.owner.entity.vo.serve.MyRoomsVo;
import com.wr.api.owner.entity.vo.serve.OldBasicInfoVo;
import com.wr.api.owner.entity.vo.serve.OlderFilesVo;
import com.wr.common.core.web.domain.AjaxResult;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-15 16:21:15
 * @Desc:
 */
public interface OlderService {

    /**
     * 查询老人信息
     * @param olderFilesVo
     * @return
     */
    List<OlderFilesVo> getOlderFiles(OlderFilesVo olderFilesVo);

    /**
     * 查询老人详情
     * @param ownerId
     * @return
     */
    OlderFilesVo getOldInfo(Long ownerId);

    /**
     * 添加老人
     * @param olderFilesVo
     * @return
     */
    AjaxResult insertOlderFile(OlderFilesVo olderFilesVo);

    /**
     * 根据老人姓名查看老人信息
     * @param ownerId
     * @return
     */
    OldBasicInfoVo getOlderByName(Long  ownerId,String cardNo);


    /**
     * 查询小区
     * @param communityId
     * @return
     */
    List<MyRoomsVo> getCommunity(Long communityId);

    /**
     * 查询楼栋
     * @param communityId
     * @return
     */
    List<MyRoomsVo> getBuilds(Long communityId);

    /**
     * 查询单元
     * @param buildingId
     * @return
     */
    List<MyRoomsVo> getUnit(Long buildingId);

    /**
     * 查询房间
     * @param unitId
     * @return
     */
    List<MyRoomsVo> getRoom(Long unitId);

    /**
     * 查询老人
     * @param ownerId
     * @return
     */
    List<OldBasicInfoVo> getOldName(Long ownerId);
}
