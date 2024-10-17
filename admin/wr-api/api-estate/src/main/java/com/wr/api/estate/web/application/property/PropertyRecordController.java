package com.wr.api.estate.web.application.property;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.api.estate.service.application.property.PropertyRecordService;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.estate.property.PropertyRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author lvzy
 * @Date 2024/1/17 17:25
 */
@RestController
@RequestMapping("v2/propertyRecord")
public class PropertyRecordController extends BaseController {

    @Autowired
    private PropertyRecordService propertyRecordService;

    /**
     * @param propertyRecord
     * @return
     */
    @GetMapping("list")
    public TableDataInfo list(PropertyRecord propertyRecord) {
        LambdaQueryWrapper<PropertyRecord> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(propertyRecord.getName())) {
            queryWrapper.like(PropertyRecord::getName, propertyRecord.getName());
        }
        if (StringUtils.isNotEmpty(propertyRecord.getPhone())) {
            queryWrapper.like(PropertyRecord::getPhone, propertyRecord.getPhone());
        }
        queryWrapper.eq(PropertyRecord::getPropertyId,propertyRecord.getPropertyId());
        queryWrapper.orderByDesc(PropertyRecord::getCreateTime);

        return getDataTable(propertyRecordService.list(queryWrapper));
    }
}
