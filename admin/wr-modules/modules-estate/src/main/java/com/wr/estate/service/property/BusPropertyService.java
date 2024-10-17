package com.wr.estate.service.property;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.estate.property.Property;

/**
 * @Author lvzy
 * @Date 2024/1/17 11:06
 */
public interface BusPropertyService extends IService<Property> {

    /**
     *
     * @param property
     */
    public void add(Property property);

    /**
     *
     * @param property
     */
    public void update(Property property);
}
