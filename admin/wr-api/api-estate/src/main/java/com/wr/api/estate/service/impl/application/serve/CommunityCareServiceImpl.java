package com.wr.api.estate.service.impl.application.serve;

import com.wr.api.estate.entity.application.serve.CommunityCareTypeVo;
import com.wr.api.estate.entity.application.serve.CommunityCareVo;
import com.wr.api.estate.mapper.application.CommunityCareMapper;
import com.wr.api.estate.service.application.serve.CommunityCareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/9 16:00
 */
@Service
public class CommunityCareServiceImpl implements CommunityCareService {

    @Autowired
    private CommunityCareMapper communityCareMapper;

    @Override
    public List<CommunityCareVo> getList(CommunityCareVo communityCareVo) {
        return communityCareMapper.getList(communityCareVo);
    }

    @Override
    public List<CommunityCareTypeVo> getTypeList() {
        return communityCareMapper.getTypeList();
    }

    @Override
    public CommunityCareVo getById(Long id) {
        return communityCareMapper.getById(id);
    }
}
