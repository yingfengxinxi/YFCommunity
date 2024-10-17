package com.wr.govern.mapper.autonomy.owner;


import com.wr.remote.govern.autonomy.owner.OwnerCommittee;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 社区委员会业主信息
 *
 * @author liubei
 * @CreateDate 2022-10-26
 */
public interface OwnerCommitteeMapper {
    /**
     * 删除业主委员会
     *
     * @param committeeId
     * @return
     */
    int deleteBusCommitteeById(Long[] committeeId);

    /**
     * 查询业主委员会
     *
     * @param ownerCommittee
     * @return
     */
    List<OwnerCommittee> selectBusCommitteeList(OwnerCommittee ownerCommittee);

    /**
     * 根据ID查询业主委员会
     *
     * @param committeeId
     * @return
     */
    OwnerCommittee selectBusCommitteeById(Long committeeId);

    /**
     * 新增业主委员会
     *
     * @param ownerCommittee
     * @return
     */
    int insertBusCommittee(OwnerCommittee ownerCommittee);

    /**
     * 修改业主委员会
     *
     * @param ownerCommittee
     * @return
     */
    int updateBusCommittee(OwnerCommittee ownerCommittee);

    /**
     * 查询社区
     * @param params
     * @return
     */
    List<LinkedHashMap<String, Object>> queryByCommunityId(Map<String, Object> params);

    /**
     * 查询房间下的人员信息
     * @param params
     * @return
     */
    List<LinkedHashMap<String, Object>> queryByRoomId(Map<String, Object> params);

    /**
     * 查询业主房产信息
     * @param id
     * @return
     */
    List<LinkedHashMap<String, Object>> queryRoomByOwnerId(Long id);

    /**
     * 查询社区
     * @param params
     * @return
     */
    List<LinkedHashMap<String, Object>> queryByCommunityIds(Map<String, Object> params);

    /**
     * 查询社区委员会信息
     * @return
     */
    OwnerCommittee selectBusCommittee();

    /**
     * 查询字典方法
     * @return
     */
    List<LinkedHashMap<String, Object>> getDictData();

    OwnerCommittee selectBusCommitteeByCommunityId(Long communityId);
}
