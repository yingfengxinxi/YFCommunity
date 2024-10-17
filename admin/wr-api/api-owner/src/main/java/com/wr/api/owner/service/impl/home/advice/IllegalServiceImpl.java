package com.wr.api.owner.service.impl.home.advice;

import com.wr.api.owner.entity.home.advice.Complaint;
import com.wr.api.owner.entity.home.advice.Illegal;
import com.wr.api.owner.mapper.home.advice.ComplaintMapper;
import com.wr.api.owner.mapper.home.advice.IllegalMapper;
import com.wr.api.owner.service.home.advice.ComplaintService;
import com.wr.api.owner.service.home.advice.IllegalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: WuKong
 * @Date: 2022-11-16 15:50:43
 * @Desc: 违法举报
 */
@Service
public class IllegalServiceImpl implements IllegalService {
    @Autowired
    private IllegalMapper illegalMapper;

    @Override
    public List<Illegal> selectIllegalList(Illegal illegal) {
        return illegalMapper.selectIllegalList(illegal);
    }

    @Override
    public Illegal getIllegalById(Long illegalId) {
        return illegalMapper.getIllegalById(illegalId);
    }

    @Override
    public Integer insertIllegal(Illegal illegal) {
        return illegalMapper.insertIllegal(illegal);
    }
}
