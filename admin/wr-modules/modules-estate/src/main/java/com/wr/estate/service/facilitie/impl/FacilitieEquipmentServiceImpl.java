package com.wr.estate.service.facilitie.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.customize.util.QrCodeUtils;
import com.wr.common.redis.service.RedisService;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.facilitie.FacilitieEquipmentMapper;
import com.wr.estate.service.facilitie.FacilitieEquipmentService;
import com.wr.remote.domain.FacilitieEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author lvzy
 * @Date 2024/2/26 21:17
 */
@Service
public class FacilitieEquipmentServiceImpl extends ServiceImpl<FacilitieEquipmentMapper, FacilitieEquipment> implements FacilitieEquipmentService {

    @Autowired
    private RedisService redisService;

    /**
     * @param facilitieEquipment
     * @return
     */
    @Override
    public String getQrCode(FacilitieEquipment facilitieEquipment) {
        String FACILITIE_QR_CODE = redisService.getCacheObject(CacheConstants.SYS_CONFIG_KEY + "FACILITIE_QR_CODE");
        //生成二维码
        String url = FACILITIE_QR_CODE + facilitieEquipment.getId();
        return QrCodeUtils.getQRCodeImage(url, facilitieEquipment.getEquipmentName(), "png");
    }

    /**
     * @param dataList
     * @param communityId
     * @return
     */
    @Override
    public String importData(List<FacilitieEquipment> dataList, Long communityId) {

        if (CollectionUtils.isEmpty(dataList)) {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (FacilitieEquipment facilitieEquipment : dataList) {
            try {
                String equipmentName = facilitieEquipment.getEquipmentName();
                if (StringUtils.isNotEmpty(equipmentName)) {
                    String facilitieEquipmentType = facilitieEquipment.getFacilitieEquipmentType();
                    if (StringUtils.isNotEmpty(facilitieEquipmentType)) {
                        String installationLocation = facilitieEquipment.getInstallationLocation();
                        if (StringUtils.isNotEmpty(installationLocation)) {
                            String productNumber = facilitieEquipment.getProductNumber();
                            if (StringUtils.isNotEmpty(productNumber)) {
                                LambdaQueryWrapper<FacilitieEquipment> queryWrapper = new LambdaQueryWrapper<>();
                                queryWrapper.eq(FacilitieEquipment::getCommunityId, communityId);
                                queryWrapper.eq(FacilitieEquipment::getProductNumber, productNumber);
                                int size = super.list(queryWrapper).size();
                                if (size <= 0) {

                                    facilitieEquipment.setId(UUID.randomUUID().toString().replace("-", ""));
                                    facilitieEquipment.setCreateTime(new Date());
                                    facilitieEquipment.setCreateBy(SecurityUtils.getUsername());
                                    facilitieEquipment.setCommunityId(communityId);
                                    facilitieEquipment.setQrCode(this.getQrCode(facilitieEquipment));
                                    super.save(facilitieEquipment);
                                    successNum++;
                                    successMsg.append("<br/>" + successNum + "、设备名称: " + equipmentName + " 导入成功");


                                } else {
                                    failureNum++;
                                    failureMsg.append(failureNum + "、设备名称:" + equipmentName + " 产品编号:" + productNumber + "已存在");
                                }

                            } else {
                                failureNum++;
                                failureMsg.append(failureNum + "、设备名称:" + equipmentName + " 产品编号不能为空");
                            }

                        } else {
                            failureNum++;
                            failureMsg.append(failureNum + "、设备名称:" + equipmentName + " 安装位置不能为空");
                        }

                    } else {
                        failureNum++;
                        failureMsg.append(failureNum + "、设备名称:" + equipmentName + " 未选择设备类型");
                    }

                } else {
                    failureNum++;
                    failureMsg.append(failureNum + "、设备名称不能为空");
                }
            } catch (Exception e) {

                e.printStackTrace();
                failureNum++;
                String msg = "<br/>" + failureNum + "、设备名称 " + facilitieEquipment.getEquipmentName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }

        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据不正确，错误如下：");
            //throw new ServiceException(failureMsg.toString());
            return failureMsg.toString();
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
            return successMsg.toString();
        }
    }
}
