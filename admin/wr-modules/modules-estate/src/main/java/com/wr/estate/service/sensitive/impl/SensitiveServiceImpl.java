package com.wr.estate.service.sensitive.impl;

import com.wr.estate.mapper.sensitive.SensitiveMapper;
import com.wr.estate.service.sensitive.SensitiveService;
import com.wr.remote.domain.Sensitive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/19 10:15
 */
@Service
public class SensitiveServiceImpl implements SensitiveService {

    @Autowired
    private SensitiveMapper sensitiveMapper;

    @Override
    public List<Sensitive> getListBusSensitive() {
        return sensitiveMapper.getListBusSensitive();
    }
}
