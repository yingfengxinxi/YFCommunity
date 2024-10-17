package com.wr.api.owner.service.impl.serve;

import com.wr.api.owner.entity.serve.Clue;
import com.wr.api.owner.entity.vo.serve.BulletinVO;
import com.wr.api.owner.mapper.CommunitysMapper;
import com.wr.api.owner.mapper.serve.BulletinMapper;
import com.wr.api.owner.service.serve.BulletinService;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.domain.Community;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-15 14:55:33
 * @Desc: 服务-警民互动 业务实现
 */
@Service
@Slf4j
public class BulletinServiceImpl implements BulletinService {
    @Autowired
    private BulletinMapper bulletinMapper;
    @Autowired
    private CommunitysMapper communityMapper;

    @Override
    public List<BulletinVO> getBulletinAll() {
        return bulletinMapper.getBulletinAll();
    }

    @Override
    public BulletinVO getBulletinById(Long bulletinId) {
        return bulletinMapper.getBulletinById(bulletinId);
    }

    @Override
    public AjaxResult addClue(Clue clue) {
        // 获取小区数据
        Community communityById = communityMapper.getCommunityById(clue.getCommunityId());
        if (ObjectUtils.isEmpty(communityById)){
            return AjaxResult.error("系统错误，未获取到当前小区");
        }
//        Integer unique = bulletinMapper.checkAccountIdUnique(clue.getBulletinId(), clue.getAccountId());
//        if (unique > 0){
//            return AjaxResult.error("该通报人员您已提供过线索，有情况及时拨打110");
//        }
        clue.setEstateId(communityById.getEstateId());
        Integer num = bulletinMapper.addClue(clue);
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }
}
