package com.wr.api.estate.service.application.security.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.remote.estate.prevent.BusPreventType;
import com.wr.api.estate.mapper.application.security.PreventTypeMapper;
import com.wr.api.estate.service.application.security.PreventTypeService;
import org.springframework.stereotype.Service;

/**
 * @Author lvzy
 * @Date 2024/1/15 17:30
 */
@Service
public class PreventTypeServiceImpl extends ServiceImpl<PreventTypeMapper, BusPreventType> implements PreventTypeService {
}
