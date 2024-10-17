package com.wr.estate.service.property.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.vo.BusStaffPropertyVo;
import com.wr.estate.entity.vo.BusVehicleImportVo;
import com.wr.estate.entity.vo.StaffPropertyVo;
import com.wr.estate.entity.vo.UserIdVehicleNoVo;
import com.wr.estate.mapper.center.carManage.BusCarportMapper;
import com.wr.estate.mapper.center.carManage.CarportNoMapper;
import com.wr.estate.mapper.center.useraccountmanage.AccountManageMapper;
import com.wr.estate.mapper.manage.StaffMapper;
import com.wr.estate.mapper.property.BusStaffPropertyMapper;
import com.wr.estate.service.center.CarVehicleService;
import com.wr.estate.service.center.CarportNoService;
import com.wr.estate.service.center.CarportService;
import com.wr.estate.service.property.BusStaffPropertyService;
import com.wr.remote.domain.CarportNo;
import com.wr.remote.domain.vo.CarportVo;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.estate.property.StaffProperty;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/19 20:00
 */
@Service
public class BusStaffPropertyServiceImpl extends ServiceImpl<BusStaffPropertyMapper, StaffProperty> implements BusStaffPropertyService {

    @Autowired
    private StaffMapper staffMapper;

    @Autowired
    private CarportNoMapper carportNoMapper;

    @Autowired
    private BusCarportMapper carportMapper;


    /**
     * @param busStaffPropertyVo
     * @return
     */
    @Override
    public List<BusStaffPropertyVo> list(BusStaffPropertyVo busStaffPropertyVo) {
        return baseMapper.list(busStaffPropertyVo);
    }

    /**
     * @param dataList
     * @return
     */
    @Override
    public String importData(List<StaffPropertyVo> dataList) {
        if (StringUtils.isNull(dataList) || dataList.size() == 0) {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (StaffPropertyVo staffPropertyVo : dataList) {

            try {
                //判断手机号是否为空
                if (StringUtils.isNotEmpty(staffPropertyVo.getStaffPhone())) {
                    //判断车位编号是否为空
                    if (StringUtils.isNotEmpty(staffPropertyVo.getCarportNo())) {
                        //判断车牌号是否为空
                        if (StringUtils.isNotEmpty(staffPropertyVo.getPlateNo())) {
                            Staff staff = staffMapper.getStaffPhoneId(staffPropertyVo.getStaffPhone());
                            if (staff != null) {
                                Long carportNoId = carportNoMapper.getCarportNoId(staffPropertyVo.getCarportNo(), null);
                                if (carportNoId != null) {
                                    Integer carportNoCount = carportMapper.getCarportNoCount(staff.getCommunityId(), String.valueOf(carportNoId));
                                    Integer staffCount = baseMapper.getVehicleNoCount(staff.getCommunityId(), String.valueOf(carportNoId));
                                    if ((carportNoCount + staffCount) <= 0) {
                                        LambdaQueryWrapper<StaffProperty> queryWrapper = new LambdaQueryWrapper<>();
                                        queryWrapper.eq(StaffProperty::getPlateNo, staffPropertyVo.getPlateNo());
                                        StaffProperty staffProperty = baseMapper.selectOne(queryWrapper);
                                        Long userId = staff.getUserId();
                                        if (staffProperty == null) {
                                            staffProperty = new StaffProperty();
                                            staffProperty.setUserId(userId);
                                            staffProperty.setVehicleNo(String.valueOf(carportNoId));
                                            staffProperty.setPlateNo(staffPropertyVo.getPlateNo());
                                            staffProperty.setVehicleColor(staffPropertyVo.getVehicleColor());
                                            staffProperty.setVehicleBrand(staffPropertyVo.getVehicleBrand());
                                            staffProperty.setVehicleType(staffPropertyVo.getVehicleType());
                                            staffProperty.setVehicleStatus("1");
                                            staffProperty.setAuditStatus("1");
                                            staffProperty.setAuditTime(new Date());
                                            staffProperty.setAuditBy(SecurityUtils.getUsername());
                                            staffProperty.setDelFlag("0");
                                            baseMapper.insert(staffProperty);
                                        } else {
                                            staffProperty.setUserId(userId);
                                            staffProperty.setVehicleNo(String.valueOf(carportNoId));
                                            staffProperty.setPlateNo(staffPropertyVo.getPlateNo());
                                            staffProperty.setVehicleColor(staffPropertyVo.getVehicleColor());
                                            staffProperty.setVehicleBrand(staffPropertyVo.getVehicleBrand());
                                            staffProperty.setVehicleType(staffPropertyVo.getVehicleType());
                                            staffProperty.setVehicleStatus("1");
                                            staffProperty.setAuditStatus("1");
                                            staffProperty.setAuditTime(new Date());
                                            staffProperty.setAuditBy(SecurityUtils.getUsername());
                                            staffProperty.setDelFlag("0");
                                            baseMapper.updateById(staffProperty);
                                        }
                                        successNum++;
                                    } else {
                                        failureNum++;
                                        String msg = "<br/>" + failureNum + "、车位编号" + staffPropertyVo.getCarportNo() + "已被占用导入失败";
                                        failureMsg.append(msg);
                                    }
                                } else {
                                    failureNum++;
                                    String msg = "<br/>" + failureNum + "、车牌号" + staffPropertyVo.getPlateNo() + "绑定车位编号" + staffPropertyVo.getCarportNo() + "不存在";
                                    failureMsg.append(msg);
                                }
                            } else {
                                failureNum++;
                                String msg = "<br/>" + failureNum + "、" + staffPropertyVo.getStaffPhone() + "手机号不存在导入失败";
                                failureMsg.append(msg);
                            }
                        } else {
                            failureNum++;
                            String msg = "<br/>" + failureNum + "、车牌号不能为空导入失败";
                            failureMsg.append(msg);
                        }
                    } else {
                        failureNum++;
                        String msg = "<br/>" + failureNum + "、车位编号不能为空导入失败";
                        failureMsg.append(msg);
                    }
                } else {
                    failureNum++;
                    String msg = "<br/>" + failureNum + "、手机号不能为空导入失败";
                    failureMsg.append(msg);
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、车牌号 " + staffPropertyVo.getPlateNo() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                e.printStackTrace();
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

    /**
     * @param userId
     * @return
     */
    @Override
    public List<CarportVo> getUserIdVehicleNo(String userId) {

        return baseMapper.getUserIdVehicleNo(userId);
    }
}
