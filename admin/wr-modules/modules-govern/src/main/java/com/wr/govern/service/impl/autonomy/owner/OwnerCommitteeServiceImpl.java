package com.wr.govern.service.impl.autonomy.owner;

import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.mapper.autonomy.owner.OwnerCommitteeCommonMapper;
import com.wr.govern.mapper.autonomy.owner.OwnerCommitteeMapper;
import com.wr.govern.service.autonomy.owner.OwnerCommitteeService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.govern.autonomy.owner.OwnerCommittee;
import com.wr.remote.govern.autonomy.owner.OwnerCommitteeCommonOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理 服务实现
 * @author liubei
 * @createDate 2022-10-24
 */
@Service
public class OwnerCommitteeServiceImpl implements OwnerCommitteeService {
    @Autowired
    private OwnerCommitteeMapper ownerCommitteeMapper;
    @Autowired
    private OwnerCommitteeCommonMapper ownerCommitteeCommonMapper;


    @Override
    public List<OwnerCommittee> selectBusCommitteeList(OwnerCommittee ownerCommittee) {
        return ownerCommitteeMapper.selectBusCommitteeList(ownerCommittee);
    }

    @Override
    public OwnerCommittee selectBusCommitteeById(Long committeeId) {
        return ownerCommitteeMapper.selectBusCommitteeById(committeeId);
    }

    @Override
    public int insertBusCommittee(OwnerCommittee ownerCommittee) {
        //通过判断当前小区是否绑定业主委员会
        OwnerCommittee ownerCommittee1=ownerCommitteeMapper.selectBusCommitteeByCommunityId(ownerCommittee.getCommunityId());
        if(ownerCommittee1!=null) {
            if (ownerCommittee1.getCommunityId().equals(ownerCommittee.getCommunityId())) {
                return 0;
            }
        }
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        sysUser.getEstateId();
        ownerCommitteeMapper.insertBusCommittee(ownerCommittee);
        return 1;

    }

    @Override
    public int updateBusCommittee(OwnerCommittee ownerCommittee) {
        return ownerCommitteeMapper.updateBusCommittee(ownerCommittee);

    }

    @Override
    public int deleteBusCommitteeById(Long[] committeeId) {
        return ownerCommitteeMapper.deleteBusCommitteeById(committeeId);

    }

    @Override
    public int bind(OwnerCommittee ownerCommittee) {
        //查找是否此业主委员会是否已经绑定了业主，如果已经绑定则不需要，如果未绑定则绑定此业
       for (int i = 0; i < ownerCommittee.getOwnerIds().length; i++) {
           OwnerCommitteeCommonOwner ownerCommitteeCommonOwner= ownerCommitteeCommonMapper.selectCommitteeByOwnerId(ownerCommittee.getOwnerIds()[i]);
           if(ownerCommitteeCommonOwner != null){
               return 0;
           }
            ownerCommittee.setOwnerId(ownerCommittee.getOwnerIds()[i]);
            ownerCommittee.setCommitteeId(ownerCommittee.getCommitteeId());
            ownerCommitteeCommonMapper.insertBusCommitteeOwner(ownerCommittee);
        }
        return 1;
    }

    @Override
    public List<LinkedHashMap<String, Object>> queryByCommunityId(Map<String, Object> params) {
        List<LinkedHashMap<String, Object>> list = ownerCommitteeMapper.queryByCommunityId(params);
        return list;
    }

    @Override
    public List<LinkedHashMap<String, Object>> queryByRoomId(Map<String, Object> params) {
        return ownerCommitteeMapper.queryByRoomId(params);
    }

    @Override
    public List<LinkedHashMap<String, Object>> queryRoomByOwnerId(Long id) {
        return ownerCommitteeMapper.queryRoomByOwnerId(id);
    }

    @Override
    public List<LinkedHashMap<String, Object>> queryByCommunityIds(Map<String, Object> params) {
        List<LinkedHashMap<String, Object>> list = ownerCommitteeMapper.queryByCommunityIds(params);
        return list;
    }

    @Override
    public int deleteOwner(Long[] ownerId) {
        return ownerCommitteeCommonMapper.deleteOwner(ownerId);
    }

    @Override
    public OwnerCommittee selectBusCommittee() {
        return ownerCommitteeMapper.selectBusCommittee();
    }

    @Override
    public int deleteOwners(Long ownerId) {
        return ownerCommitteeCommonMapper.deleteOwners(ownerId);
    }

    @Override
    public List<LinkedHashMap<String, Object>> getDictData() {
        return ownerCommitteeMapper.getDictData();
    }

}
