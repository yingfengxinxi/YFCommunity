package com.wr.api.owner.service.impl.serve;

import com.wr.api.owner.entity.serve.Rescue;
import com.wr.api.owner.entity.serve.RescueUser;
import com.wr.api.owner.entity.vo.serve.RescueVO;
import com.wr.api.owner.mapper.serve.RescueMapper;
import com.wr.api.owner.service.serve.RescueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-11 15:55:39
 * @Desc: 服务-社区救助 业务实现
 */
@Service
@Slf4j
public class RescueServiceImpl implements RescueService {
    @Autowired
    private RescueMapper rescueMapper;

    @Override
    public List<RescueVO> getRescueAll(Long communityId, String releaseType, Long releaseId) {
        if ("0".equals(releaseType)){
            return rescueMapper.getOwnerRescueAll(communityId,releaseType, releaseId);
        }
        return rescueMapper.getTenantRescueAll(communityId,releaseType, releaseId);
    }

    @Override
    public RescueVO getRescueById(Long rescueId) {
        RescueVO rescueById = rescueMapper.getRescueById(rescueId);
        // 获取志愿者信息
        List<RescueUser> rescueUsers = rescueMapper.getRescueUsers(rescueId);
        rescueById.setRescueUsers(rescueUsers);
        return rescueById;
    }

    @Override
    public Integer addRescue(Rescue rescue) {
        return rescueMapper.addRescue(rescue);
    }
}
