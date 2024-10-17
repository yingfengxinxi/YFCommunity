package com.wr.govern.service.impl.voluntary;

import com.wr.common.core.utils.StringUtils;
import com.wr.govern.mapper.voluntary.RescueMapper;
import com.wr.govern.service.voluntary.RescueService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: lvzy
 * @Date: 2022-10-26 14:53:37
 * @Desc:
 */
@Service
public class RescueServiceImpl implements RescueService {

    @Resource
    private RescueMapper rescueMapper;

    @Override
    public List<LinkedHashMap<String, Object>> searchList(Map<String, Object> params) {


        return rescueMapper.searchList(params);
    }
}
