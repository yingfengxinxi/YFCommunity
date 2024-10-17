package com.wr.estate.service.impl.center.carManage;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.DesensitizationUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.vo.CarportImportVo;
import com.wr.estate.mapper.center.carManage.BusCarportMapper;
import com.wr.estate.mapper.center.carManage.BusVehicleMapper;
import com.wr.estate.mapper.center.carManage.CarportNoMapper;
import com.wr.estate.mapper.center.useraccountmanage.AccountManageMapper;
import com.wr.estate.service.center.CarVehicleService;
import com.wr.estate.service.manage.contract.OwnerService;
import com.wr.remote.domain.CarportNo;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.center.BusCarport;
import com.wr.remote.estate.center.VehiclePlateNoUtil;
import com.wr.remote.estate.center.vo.BusCarportVo;
import com.wr.remote.estate.center.vo.BusVehicleVo;
import com.wr.remote.estate.center.vo.SelectVehicleVo;
import com.wr.remote.estate.center.vo.SpaceVehicleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 车辆管理
 *
 * @Author: lvzy
 * @Date: 2022-10-12 10:00:48
 * @Desc: 车辆管理
 */
@Service
public class CarVehicleServiceImpl implements CarVehicleService {

    @Autowired
    private BusVehicleMapper vehicleMapper;

    @Autowired
    private AccountManageMapper accountManageMapper;

    @Autowired
    private BusCarportMapper carportMapper;

    @Autowired
    private CarportNoMapper carportNoMapper;

    @Autowired
    private OwnerService ownerService;

    @Override
    public List<BusVehicleVo> listPage(BusVehicleVo vehicleVo) {
        List<BusVehicleVo> vehicleVoList = vehicleMapper.listPage(vehicleVo);
        if(CollectionUtils.isNotEmpty(vehicleVoList)){
            vehicleVoList.stream().forEach(vehicleVo1 -> {
                vehicleVo1.setAccountName(DesensitizationUtils.name(vehicleVo1.getAccountName()));
                vehicleVo1.setLoginPhone(DesensitizationUtils.phone(vehicleVo1.getLoginPhone()));

            });
        }
        return vehicleVoList;
    }

    @Override
    public BusVehicleVo detail(Long vehicleId) {
        // 查询车辆是业主的还是租客的
        Integer accountType = vehicleMapper.accountTypeId(vehicleId);
        if (accountType == 0) {
            return vehicleMapper.detailOwner(vehicleId);
        } else if (accountType == 1) {
            return vehicleMapper.detailTenant(vehicleId);
        } else if (accountType == 2) {
            return vehicleMapper.detailVistor(vehicleId);
        } else if (accountType == 3) {
            return vehicleMapper.detailWorker(vehicleId);
        }
        return null;
    }

    @Override
    public Integer remove(Long vehicleId) {
        return vehicleMapper.remove(vehicleId);
    }

    @Override
    public BusVehicleVo selectOwnerName(Long ownerId) {
        return vehicleMapper.selectOwnerName(ownerId);
    }

    @Override
    public List<BusVehicleVo> selectOwnerCar(Long ownerId) {
        List<BusVehicleVo> busVehicleVos = vehicleMapper.selectOwnerCar(ownerId);
        return busVehicleVos;
    }

    @Override
    public AjaxResult saveOrUpdate(BusVehicleVo vehicleVo) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        Long userId = sysUser.getUserId();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 查找车位是否存在
        Integer integer = vehicleMapper.selectVehicleNo(vehicleVo.getPlateNo(), vehicleVo.getCommunityId(), vehicleVo.getVehicleId());
        if (integer > 0) {
            return AjaxResult.error("该车位号已存在, 不可重复新增或修改!");
        }
        // 车辆ID 为null 新增
        if (vehicleVo.getVehicleId() == null) {
            vehicleVo.setCreateBy(userId.toString());
            vehicleVo.setAuditStatus("1");
            vehicleVo.setAuditTime(dateFormat.format(date));
            vehicleMapper.saveCar(vehicleVo);
            return AjaxResult.success("新增成功");
        } else {
            // 修改
            vehicleVo.setUpdateBy(userId.toString());
            vehicleVo.setUpdateTime(date);
            vehicleMapper.updateCar(vehicleVo);
            return AjaxResult.success("修改成功");
        }
    }

    @Override
    public BusVehicleVo updateDetail(Long vehicleId) {
        BusVehicleVo busVehicleVo = vehicleMapper.selectUpdateDetail(vehicleId);
        if (busVehicleVo != null) {
            BusVehicleVo owner = vehicleMapper.getAccountIdOwner(busVehicleVo.getAccountId());
            if (owner != null) {
                busVehicleVo.setBuildingId(owner.getBuildingId());
                busVehicleVo.setUnitId(owner.getUnitId());
                busVehicleVo.setRoomId(owner.getRoomId());
                busVehicleVo.setOwnerId(owner.getOwnerId());
                busVehicleVo.setOwnerPhone(owner.getOwnerPhone());
            }
        }
        return busVehicleVo;
    }

    @Override
    public AjaxResult saveCar(BusVehicleVo vehicleVo) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        String userName = sysUser.getUserName();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 查找车牌号是否存在
        SpaceVehicleVo plateNoInfo = vehicleMapper.getByPlateNoInfo(vehicleVo.getCommunityId(), vehicleVo.getPlateNo());
        Long accountId = ownerService.getByOwnerPhoneAccountId(vehicleVo.getLoginPhone());
        if (plateNoInfo != null) {
            Long vehicleId = plateNoInfo.getVehicleId();
            //查询车位信息
            SpaceVehicleVo vehicleIdCarport = carportMapper.getVehicleIdCarport(accountId, vehicleVo.getCommunityId(), vehicleId);
            if (vehicleIdCarport != null) {
                //判断当前车牌号是否已绑定过车位
                if (!StringUtils.equals(vehicleVo.getCarportNo(), vehicleIdCarport.getCarportNo())) {
                    CarportNo carportNo = carportNoMapper.get(Long.valueOf(vehicleVo.getCarportNo()));
                    return AjaxResult.error("当前车牌号【" + vehicleVo.getPlateNo() + "】已绑定车位,车位编号为【" + carportNo.getCarportNo() + "】");
                }
            }
            //修改车辆信息
            vehicleVo.setUpdateBy(userName);
            vehicleVo.setVehicleId(plateNoInfo.getVehicleId());
            vehicleMapper.updateCar(vehicleVo);
        } else {
            //新增车辆信息
            vehicleVo.setCreateBy(userName);
            vehicleVo.setAuditStatus("1");
            vehicleVo.setAuditTime(dateFormat.format(date));
            vehicleVo.setAccountId(accountId);
            vehicleMapper.saveCar(vehicleVo);

           // plateNoInfo = vehicleMapper.getByPlateNoInfo(vehicleVo.getCommunityId(), vehicleVo.getPlateNo());
        }
//        if (StringUtils.isNotEmpty(vehicleVo.getCarportNo())) {
//            BusCarportVo carportVo = new BusCarportVo();
//            //carportVo.setCarportId(vehicleVo.getCarportId());
//            carportVo.setAccountId(accountId);
//            carportVo.setVehicleId(plateNoInfo.getVehicleId());
//            carportVo.setUpdateBy(userName);
//            carportVo.setCarportNo(vehicleVo.getCarportNo());
//            carportVo.setRentalStart(vehicleVo.getRentalStart());
//            carportVo.setRentalEnd(vehicleVo.getRentalEnd());
//            //根据车位编号查询车位信息
//            BusCarportVo selectedUpdateDetail = carportMapper.selectUpdateDetail(carportVo);
//            if (selectedUpdateDetail != null) {
//                if (selectedUpdateDetail.getVehicleId() == null || selectedUpdateDetail.getVehicleId() == 0) {
//                    carportVo.setCarportId(selectedUpdateDetail.getCarportId());
//                    carportMapper.update(carportVo);
//                }
//            } else {
//                //增加车位信息
//                carportMapper.save(carportVo);
//                //selectedUpdateDetail = carportMapper.selectUpdateDetail(carportVo);
//            }
//        }
        return AjaxResult.success();
    }

    @Override
    public Integer removes(Long[] ids) {
        for (Long id : ids) {
            vehicleMapper.remove(id);
        }
        return 1;
    }


    /**
     * @param dataList
     * @param communityId
     * @return
     */
    @Override
    public String importData(List<BusVehicleVo> dataList, Long communityId) {
        if (CollectionUtils.isEmpty(dataList)) {
            throw new ServiceException("导入车辆信息不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (BusVehicleVo vehicleVo : dataList) {

            try {
                String plateNo = vehicleVo.getPlateNo();
                if (StringUtils.isNotEmpty(plateNo)) {
                    VehiclePlateNoUtil.VehiclePlateNoEnum vehiclePlateNo = VehiclePlateNoUtil.getVehiclePlateNo(plateNo);
                    if (vehiclePlateNo != null) {
                        String vehicleBrand = vehicleVo.getVehicleBrand();
                        if (StringUtils.isNotEmpty(vehicleBrand)) {
                            String vehicleType = vehicleVo.getVehicleType();
                            if (StringUtils.isNotEmpty(vehicleType)) {
                                String vehicleColor = vehicleVo.getVehicleColor();
                                if (StringUtils.isNotEmpty(vehicleColor)) {
                                    // 1. 输入的手机号不能为空 并且 格式正确
                                    String loginPhone = vehicleVo.getLoginPhone();
                                    if (StringUtils.isNotEmpty(loginPhone)) {
                                        if (Pattern.matches("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$", loginPhone.toString())) {
                                            //手机号码格式正确
                                            // 2. 根据手机号找 小区ID和车主ID
                                            BusVehicleVo busVehicleVo = vehicleMapper.selectOwnerIdAndCommunityIdByVehicle(loginPhone, communityId);
                                            if (busVehicleVo != null) {
                                                vehicleVo.setCommunityId(busVehicleVo.getCommunityId());
                                                vehicleVo.setAccountId(busVehicleVo.getAccountId());

                                                // 3. 根据车主ID查是否存在重复车牌号
                                                Integer plateNoBoolean = vehicleMapper.selectPlateNoBoolean(vehicleVo.getPlateNo());
                                                if (plateNoBoolean > 0) {
                                                    failureNum++;
                                                    failureMsg.append("<br/>" + "车牌号 " + vehicleVo.getPlateNo() + " 已经存在");
                                                } else {
                                                    vehicleVo.setCreateBy(SecurityUtils.getUsername());
                                                    vehicleVo.setAuditStatus("1");
                                                    vehicleVo.setVehicleStatus("0");
                                                    vehicleVo.setAuditTime(DateUtils.getTime());
                                                    vehicleMapper.saveCar(vehicleVo);
                                                    successNum++;
                                                    successMsg.append("<br/>" + successNum + "、车牌号 " + vehicleVo.getPlateNo() + " 导入成功");
                                                }
                                            } else {
                                                failureNum++;
                                                failureMsg.append("<br/>" + "当前小区手机号 " + vehicleVo.getLoginPhone() + " 未绑定账号, 请检查数据后重新导入!");
                                            }

                                        } else {
                                            failureNum++;
                                            failureMsg.append("<br/>" + "手机号为 " + vehicleVo.getLoginPhone() + " 的格式错误, 请检查数据后重新导入!");
                                        }
                                    } else {
                                        failureNum++;
                                        failureMsg.append("<br/>" + "车牌号为 " + vehicleVo.getPlateNo() + " 的手机号未输入, 请检查数据后重新导入!");

                                    }


                                } else {
                                    failureNum++;
                                    failureMsg.append("<br/>" + "车牌号为 " + vehicleVo.getPlateNo() + " 的车辆颜色未选择, 请检查数据后重新导入!");
                                }

                            } else {
                                failureNum++;
                                failureMsg.append("<br/>" + "车牌号为 " + vehicleVo.getPlateNo() + " 的车辆类型未选择, 请检查数据后重新导入!");
                            }


                        } else {
                            failureNum++;
                            failureMsg.append("<br/>" + "车牌号为 " + vehicleVo.getPlateNo() + " 的车辆品牌未输入, 请检查数据后重新导入!");
                        }

                    } else {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、输入的车牌号 " + vehicleVo.getPlateNo() + " 格式错误, 请检查数据后重新导入!");
                    }


                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + " 、车牌号不能为空");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、车牌号 " + vehicleVo.getPlateNo() + " 导入失败：";
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

    /**
     * @param communityId
     * @param ownerId
     * @return
     */
    @Override
    public List<SelectVehicleVo> getByOwnerIdVehicleList(Long communityId, Long ownerId) {
        return vehicleMapper.getByOwnerIdVehicleList(communityId, ownerId);
    }

    @Override
    public List<SelectVehicleVo> getByAccountIdVehicleList(Long communityId, Long accountId) {
        return vehicleMapper.getByAccountIdVehicleList(communityId, accountId);
    }
}
