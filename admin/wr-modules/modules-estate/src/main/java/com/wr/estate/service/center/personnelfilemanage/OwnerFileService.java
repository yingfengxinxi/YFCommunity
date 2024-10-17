package com.wr.estate.service.center.personnelfilemanage;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.estate.entity.dto.AuthDto;
import com.wr.estate.entity.vo.OwnerFileImportVo;
import com.wr.remote.estate.center.vo.OwnerFileVo;
import com.wr.remote.estate.center.vo.RoomDetailVO;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-12 17:20:41
 * @Desc: 业主档案
 */
public interface OwnerFileService {

    /**
     * 新增 业主
     *
     * @param params
     * @return
     */
    int insert(Map<String, Object> params);

    /**
     * 修改 业主
     *
     * @param params
     * @return
     */
    int update(Map<String, Object> params);

    /**
     * 删除 业主
     *
     * @param id
     * @return
     */
    int deleteds(Long[] id);

    /**
     * 删除 业主单个
     *
     * @param id
     * @return
     */
    int deleted(Long id);

    /**
     * 小区下的业主
     *
     * @param params
     * @return
     */
    List<OwnerFileVo> queryByCommunityId(Map<String, Object> params);


    /**
     *
     * @param roomId
     * @param ownerId
     * @return
     */
    Integer getByRoomIdOwnerRelationCount(Long roomId,Long ownerId);

    /**
     * 查询 房间下的人员
     *
     * @param
     * @return
     */
    List<LinkedHashMap<String, Object>> queryByRoomId(Map<String, Object> params);

    /**
     * 业主房产
     *
     * @param
     * @return
     */
    List<LinkedHashMap<String, Object>> queryRoomByOwnerId(Long id);

    /**
     *
     * @param id
     * @param communityId
     * @return
     */
    public RoomDetailVO getDetailByOwnerId(Long id,Long communityId);

    /**
     * 业主车辆
     *
     * @param id
     * @return
     */
    List<LinkedHashMap<String, Object>> queryCarByOwnerId(Long id);

    /**
     * 业主的租客
     *
     * @param id
     * @return
     */
    List<LinkedHashMap<String, Object>> queryTenantByOwnerId(Long id);

    /**
     * 业主的车位
     *
     * @param id
     * @return
     */
    List<LinkedHashMap<String, Object>> queryCarPortByOwnerId(Long id);

    /**
     * 存在相同手机号
     *
     * @param no
     * @return
     */
    int getPhoneNum(String no);

    /**
     * 存在相同身份证号
     *
     * @param no
     * @return
     */
    int getCardNoNum(String no);

    List<LinkedHashMap<String, Object>> getDictData();

    /**
     * 人员授权查询数据
     *
     * @param communityId 小区id
     * @return {@link AjaxResult}
     * @ver v1.0.0
     */
    AjaxResult selectDeviceList(Long communityId);

    /**
     * 人员授权
     *
     * @param authDto
     * @return {@link AjaxResult}
     * @ver v1.0.0
     */
    AjaxResult authOwner(AuthDto authDto);

    String importData(List<OwnerFileImportVo> dataList, Long communityId);

    /**
     *
     * @param communityId
     * @param voteScopeId
     * @return
     */
    List<OwnerFileVo> getAllOwnerList(Long communityId,String voteScopeId);


    /**
     *
     * @param ownerPhone
     * @return
     */
    OwnerFileVo getOwnerPhoneInfo(String ownerPhone);
}
