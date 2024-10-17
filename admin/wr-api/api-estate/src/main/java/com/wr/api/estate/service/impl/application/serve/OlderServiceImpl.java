package com.wr.api.estate.service.impl.application.serve;

import com.wr.api.estate.entity.vo.application.serve.OlderDetailVO;
import com.wr.api.estate.entity.vo.application.serve.OlderVO;
import com.wr.api.estate.mapper.application.serve.OlderMapper;
import com.wr.api.estate.service.application.serve.OlderService;
import com.wr.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-15 13:23:54
 * @Desc: 老人档案业务层
 */
@Service
public class OlderServiceImpl implements OlderService {

    @Autowired
    private OlderMapper olderMapper;

    @Override
    public List<OlderVO> selectOlderList(OlderVO olderVO) {
        return olderMapper.selectOlderList(olderVO);
    }

    @Override
    public OlderDetailVO getOlderDetail(Long olderId) {
        return olderMapper.getOlderDetail(olderId);
    }

    @Override
    public Integer olderAudit(OlderVO olderAudit) {
        return olderMapper.olderAudit(olderAudit);
    }
}
