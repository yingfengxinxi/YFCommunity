package com.wr.estate.service.impl;

import com.wr.estate.mapper.BusIllegalMapper;
import com.wr.estate.service.IllegalService;
import com.wr.remote.estate.complaint.report.BusIllegal;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IllegalServiceImpl implements IllegalService {

    @Resource
    private BusIllegalMapper busIllegalMapper;

    @Override
    public List<BusIllegal> pageList(BusIllegal busIllegal) {
        return busIllegalMapper.pageList(busIllegal);
    }

    @Override
    public BusIllegal load(int illegalId) {
        return busIllegalMapper.load(illegalId);
    }
}
