package com.wr.api.owner.service.impl.serve;

import com.wr.api.owner.entity.vo.serve.MajordomoVO;
import com.wr.api.owner.mapper.serve.MajordomoMapper;
import com.wr.api.owner.service.serve.MajordomoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: SJiang
 * @Date: 2022-11-11 16:49:19
 * @Desc: 服务-专属管家 业务实现
 */
@Service
@Slf4j
public class MajordomoServiceImpl implements MajordomoService {
    @Autowired
    private MajordomoMapper majordomoMapper;

    @Override
    public MajordomoVO getMajordomo(Long communityId, Long buildingId) {
        return majordomoMapper.getMajordomo(communityId, buildingId);
    }
}
