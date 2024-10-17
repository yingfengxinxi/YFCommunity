package com.wr.api.estate.mapper.application.property;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.estate.property.StaffProperty;

/**
 * @Author lvzy
 * @Date 2024/1/19 20:00
 */
public interface StaffPropertyMapper extends BaseMapper<StaffProperty> {


    void updateByIdStaffProperty(StaffProperty staffProperty);
}
