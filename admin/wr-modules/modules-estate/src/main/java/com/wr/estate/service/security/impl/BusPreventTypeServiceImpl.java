package com.wr.estate.service.security.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.remote.estate.prevent.BusPreventType;
import com.wr.estate.mapper.security.BusPreventTypeMapper;
import com.wr.estate.service.security.BusPreventTypeService;
import org.springframework.stereotype.Service;

/**
 * @Author lvzy
 * @Date 2024/1/15 17:30
 */
@Service
public class BusPreventTypeServiceImpl extends ServiceImpl<BusPreventTypeMapper, BusPreventType> implements BusPreventTypeService {
}
