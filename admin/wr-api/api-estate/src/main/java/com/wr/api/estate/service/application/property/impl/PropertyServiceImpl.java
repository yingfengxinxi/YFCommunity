package com.wr.api.estate.service.application.property.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.estate.mapper.application.property.PropertyMapper;
import com.wr.api.estate.service.application.property.PropertyService;
import com.wr.common.core.utils.sign.Base64;
import com.wr.common.core.utils.uuid.UUID;
import com.wr.common.customize.util.QrCodeUtils;
import com.wr.remote.estate.property.Property;
import org.jetbrains.annotations.NotNull;
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
public class PropertyServiceImpl extends ServiceImpl<PropertyMapper, Property> implements PropertyService {


}
