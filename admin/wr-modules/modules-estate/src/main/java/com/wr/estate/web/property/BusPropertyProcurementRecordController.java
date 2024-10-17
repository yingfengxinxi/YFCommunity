package com.wr.estate.web.property;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.property.BusPropertyProcurementRecordService;
import com.wr.estate.service.property.BusPropertyService;
import com.wr.remote.estate.property.Property;
import com.wr.remote.estate.property.PropertyProcurementRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/21 18:20
 */
@RestController
@RequestMapping("propertyProcurementRecord")
public class BusPropertyProcurementRecordController {

    @Autowired
    private BusPropertyProcurementRecordService propertyProcurementRecordService;

    @Autowired
    private BusPropertyService propertyService;

    /**
     * @param propertyProcurementRecord
     * @return
     */
    @PostMapping("add")
    public AjaxResult add(@RequestBody PropertyProcurementRecord propertyProcurementRecord) {

        propertyProcurementRecord.setCreateTime(new Date());
        propertyProcurementRecord.setCreateBy(SecurityUtils.getUsername());
        propertyProcurementRecord.setDelFlag("0");
        propertyProcurementRecordService.save(propertyProcurementRecord);
        //更新主表数据
        Property property = propertyService.getById(propertyProcurementRecord.getPropertyId());
        property.setUpdateTime(new Date());
        property.setUpdateBy(SecurityUtils.getUsername());
        property.setNumber(property.getNumber() + propertyProcurementRecord.getNumber());
        property.setManufacturer(propertyProcurementRecord.getManufacturer());
        property.setPrice(propertyProcurementRecord.getPrice());
        property.setPurchaser(propertyProcurementRecord.getPurchaser());
        propertyService.updateById(property);
        return AjaxResult.success();
    }
}
