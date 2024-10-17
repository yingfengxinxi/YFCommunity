package com.wr.api.estate.service.impl.application.serve;

import com.wr.api.estate.entity.vo.application.serve.BulletinVO;
import com.wr.api.estate.mapper.application.serve.BulletinMapper;
import com.wr.api.estate.service.application.serve.BulletinService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 13:10:00
 * @Desc:
 */
@Service
public class BulletinServiceImpl implements BulletinService {

    @Resource
    private BulletinMapper bulletinMapper;
    @Override
    public List<BulletinVO> searchByCommunityId(Long communityId) {
        return bulletinMapper.searchByCommunityId(communityId);
    }

    @Override
    public BulletinVO detailByBulletinId(Long bulletinId) {
        return bulletinMapper.detailByBulletinId(bulletinId);
    }
}
