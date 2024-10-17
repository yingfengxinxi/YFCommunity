package com.wr.govern.service.autonomy.owner;


import com.wr.remote.govern.autonomy.owner.OwnerCommittee;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理 服务层
 * 
 * @author wr
 */
public interface OwnerCommitteeService
{
    /**
     * 查询业主委员会数据
     *
     * @param ownerCommittee 业主委员会信息
     * @return 部门信息集合
     */
    public List<OwnerCommittee> selectBusCommitteeList(OwnerCommittee ownerCommittee);
    /**
     * 根据业主委员会ID查询信息
     *
     * @param committeeId 部门ID
     * @return 部门信息
     */
    public OwnerCommittee selectBusCommitteeById(Long committeeId);

    /**
     * 新增业主委员会信息
     *
     * @param ownerCommittee 业主委员会信息
     * @return 结果
     */
    public int insertBusCommittee(OwnerCommittee ownerCommittee);

    /**
     * 修改业主委员会信息
     *
     * @param ownerCommittee 业主委员会信息
     * @return 结果
     */
    public int updateBusCommittee(OwnerCommittee ownerCommittee);

    /**
     * 删除业主委员会信息
     *
     * @param committeeId 业主委员会ID
     * @return 结果
     */
    public int deleteBusCommitteeById(Long[]committeeId);

    int bind(OwnerCommittee ownerCommittee);

    List<LinkedHashMap<String,Object>> queryByCommunityId(Map<String, Object> params);

    List<LinkedHashMap<String,Object>>queryByRoomId(Map<String, Object> params);

    List<LinkedHashMap<String,Object>> queryRoomByOwnerId(Long id);

    List<LinkedHashMap<String,Object>> queryByCommunityIds(Map<String, Object> params);

    int  deleteOwner(Long[] ownerId);
    OwnerCommittee selectBusCommittee();

    int deleteOwners(Long ownerId);

    List<LinkedHashMap<String,Object>> getDictData();
}
