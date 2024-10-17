package com.wr.estate.service.customer.inspection;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wr.remote.domain.Inspection;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/1 21:43
 */
public interface InspectionService extends IService<Inspection> {

    /**
     *
     * @param id
     * @return
     */
    Inspection get(String id);

    List<Inspection> getList(LambdaQueryWrapper<Inspection> queryWrapper);
}
