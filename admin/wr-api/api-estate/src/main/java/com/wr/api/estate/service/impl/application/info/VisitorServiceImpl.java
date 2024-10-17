package com.wr.api.estate.service.impl.application.info;

import com.wr.api.estate.mapper.application.info.VisitorMapper;
import com.wr.api.estate.service.application.info.VisitorService;
import com.wr.remote.domain.vo.VisitorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/21 10:13
 */
@Service
public class VisitorServiceImpl implements VisitorService {

    @Autowired
    private VisitorMapper visitorMapper;

    @Override
    public List<VisitorVo> getList(VisitorVo visitorVo) {
        return visitorMapper.getList(visitorVo);
    }

    @Override
    public VisitorVo getVisitorById(Long visitorId) {
        return visitorMapper.getVisitorById(visitorId);
    }

    @Override
    public Integer getVisitCount(Long communityId) {
        return visitorMapper.getVisitCount(communityId);
    }
}
