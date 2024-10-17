package com.wr.govern.mapper.autonomy.owner;

import com.wr.remote.govern.autonomy.owner.OwnerCommittee;
import com.wr.remote.govern.autonomy.owner.OwnerCommitteeCommonOwner;

/**
 * 社区委员会业主绑定关联管理
 *
 * @author liubei
 * @CreateDate 2022-10-26
 */
public interface OwnerCommitteeCommonMapper {

    /**
     * 新增人員社区关联表信息
     * @param ownerCommittee
     * @return
     */
    public int insertBusCommitteeOwner(OwnerCommittee ownerCommittee);

    /**
     * 修改人员社区关联表信息
     * @param ownerCommittee
     * @return
     */
    public  int updateBusCommitteeOwner(OwnerCommittee ownerCommittee);

    /**
     * 查询人员社区关联表信息
     * @param ownerCommitteeCommonOwner
     * @return
     */

    public  OwnerCommitteeCommonOwner selectCommitteeById(OwnerCommitteeCommonOwner ownerCommitteeCommonOwner);

    /**
     * 单个删除业主委员会绑定
     * @param ownerId
     * @return
     */
    int deleteOwner(Long[] ownerId);

    /**
     * 批删除业主委员会绑定
     * @param ownerId
     * @return
     */
    int deleteOwners(Long ownerId);

    /**
     * 判断地业主委员会是否绑定此业主
     * @param ownerId
     * @return
     */
    OwnerCommitteeCommonOwner selectCommitteeByOwnerId(Long  ownerId);
}
