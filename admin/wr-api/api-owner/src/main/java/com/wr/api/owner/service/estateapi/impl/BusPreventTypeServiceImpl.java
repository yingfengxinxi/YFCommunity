package com.wr.api.owner.service.estateapi.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.owner.mapper.estateapi.BusPreventTypeMapper;
import com.wr.api.owner.service.estateapi.BusPreventTypeService;
import com.wr.remote.estate.prevent.BusPreventType;
import org.springframework.stereotype.Service;

/**
 * @Author lvzy
 * @Date 2024/1/15 17:30
 */
@Service
public class BusPreventTypeServiceImpl extends ServiceImpl<BusPreventTypeMapper, BusPreventType> implements BusPreventTypeService {
}
