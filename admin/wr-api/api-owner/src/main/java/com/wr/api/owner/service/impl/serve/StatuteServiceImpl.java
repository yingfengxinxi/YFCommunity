package com.wr.api.owner.service.impl.serve;

import com.wr.api.owner.entity.vo.serve.StatuteVO;
import com.wr.api.owner.mapper.serve.StatuteMapper;
import com.wr.api.owner.service.serve.StatuteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-15 13:28:20
 * @Desc: 服务-自治规约 业务实现
 */
@Service
@Slf4j
public class StatuteServiceImpl implements StatuteService {
    @Autowired
    private StatuteMapper statuteMapper;

    @Override
    public List<StatuteVO> getStatuteAll(Long communityId) {
        return statuteMapper.getStatuteAll(communityId);
    }

    @Override
    public StatuteVO getStatuteById(Long statuteId) {
        return statuteMapper.getStatuteById(statuteId);
    }
}
