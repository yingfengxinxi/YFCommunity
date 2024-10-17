package com.wr.estate.service.property.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.common.core.utils.sign.Base64;
import com.wr.common.core.utils.uuid.UUID;
import com.wr.common.customize.util.QrCodeUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.property.BusPropertyMapper;
import com.wr.estate.mapper.property.BusPropertyProcurementRecordMapper;
import com.wr.estate.service.property.BusPropertyProcurementRecordService;
import com.wr.estate.service.property.BusPropertyService;
import com.wr.remote.estate.property.Property;
import com.wr.remote.estate.property.PropertyProcurementRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/1/17 11:08
 */
@Service
public class BusPropertyServiceImpl extends ServiceImpl<BusPropertyMapper, Property> implements BusPropertyService {

    @Autowired
    private BusPropertyProcurementRecordMapper propertyProcurementRecordMapper;

    /**
     * @param property
     */
    @Override
    public void add(Property property) {
        String id = UUID.randomUUID().toString().replace("-", "");
        property.setId(id);
        baseMapper.insert(property);

        if (property.getNumber() != null && property.getNumber() >= 1) {
            PropertyProcurementRecord propertyProcurementRecord = new PropertyProcurementRecord();
            propertyProcurementRecord.setCreateTime(new Date());
            propertyProcurementRecord.setCreateBy(SecurityUtils.getUsername());
            propertyProcurementRecord.setDelFlag("0");
            propertyProcurementRecord.setPropertyId(property.getId());
            propertyProcurementRecord.setNumber(property.getNumber());
            propertyProcurementRecord.setManufacturer(property.getManufacturer());
            propertyProcurementRecord.setPrice(property.getPrice());
            propertyProcurementRecordMapper.insert(propertyProcurementRecord);
        }
    }

    private String getBase64(Property property) {

        String pageUrl = property.getId();
        //物品名称
        String name = property.getName();

        return QrCodeUtils.getQRCodeImage(pageUrl, name,"png");
    }

    /**
     * @param property
     */
    @Override
    public void update(Property property) {

        baseMapper.updateById(property);
    }
}
