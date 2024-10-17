package com.wr.estate.service.impl.center.carManage;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.vo.CarportImportVo;
import com.wr.estate.entity.vo.RoomImportVo;
import com.wr.estate.entity.vo.UserIdVehicleNoVo;
import com.wr.estate.mapper.center.carManage.BusVehicleMapper;
import com.wr.estate.mapper.center.carManage.CarportNoMapper;
import com.wr.estate.mapper.center.useraccountmanage.AccountManageMapper;
import com.wr.estate.service.center.useraccountmanage.AccountManageService;
import com.wr.remote.domain.CarportNo;
import com.wr.remote.domain.vo.CarportVo;
import com.wr.remote.estate.center.*;
import com.wr.remote.estate.center.vo.BusCarportLogVo;
import com.wr.remote.estate.center.vo.BusCarportVo;
import com.wr.estate.mapper.center.carManage.BusCarportMapper;
import com.wr.estate.service.center.CarportService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.center.vo.BusVehicleVo;
import com.wr.remote.estate.center.vo.SpaceVehicleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 车位管理
 *
 * @Author: lvzy
 * @Date: 2022-10-14 13:55:53
 * @Desc: 车位管理
 */
@Service
public class CarportServiceImpl implements CarportService {

    @Autowired
    private BusCarportMapper carportMapper;

    @Autowired
    private CarportNoMapper carportNoMapper;


    @Override
    public List<BusCarportVo> listPage(BusCarportVo carportVo) {
        return carportMapper.listPage(carportVo);
    }

    @Override
    public BusCarportVo detail(Long carportId) {
        return carportMapper.detail(carportId);
    }

    /**
     * @param carportId
     * @return
     */
    @Override
    public Long getByCarportIdAccountId(Long carportId) {
        return carportMapper.getByCarportIdAccountId(carportId);
    }

    @Override
    public List<BusCarportLogVo> detailList(Long carportId) {
        return carportMapper.detailList(carportId);
    }

    /**
     * @param communityId
     * @return
     */
    @Override
    public List<CarportVo> getCommunityCarportList(Long communityId) {
        return carportMapper.getCommunityCarportList(communityId);
    }

    @Override
    public Integer removes(Long id) {
        Long carportNo = carportMapper.getByCarportIdNo(id);
        carportNoMapper.delete(carportNo);
        carportMapper.removes(id);
        return 1;
    }

    /**
     * 转让 ->改产权人和车辆
     *
     * @param carportVo
     * @return
     */
    @Override
    public Integer cedeSubmit(BusCarportLogVo carportVo) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        carportVo.setRentalStart(dateFormat.format(date));
        // 根据车辆ID查 车牌号 plateNo
        String plateNo = carportMapper.selectPlateNoById(carportVo.getOwnerCarValueId());
        carportVo.setPlateNo(plateNo);
        // 根据业主ID查 产权人id，表bus_account
        Long accountId = carportMapper.selectCarportAccount(carportVo.getOwnerId());
        carportVo.setAccountId(accountId);
        // 2.改产权人和车辆
        carportMapper.updateCede(carportVo);
        return 1;
    }

    /**
     * @param carportVo
     * @return
     */

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public AjaxResult saveOrUpdate(BusCarportVo carportVo) {
        try {
            String carportNo = carportVo.getCarportNo();
            Long communityId = carportVo.getCommunityId();
            Long carportId = carportVo.getCarportId();
            if (carportId != null) {
                Long carportNoId = carportMapper.getByCarportIdNo(carportId);
                if (carportNoId != null) {
                    Integer count = carportNoMapper.getCarportIdCarportNo(carportNoId, carportNo, communityId);
                    if (count >= 1) {
                        return AjaxResult.error("该车位号已存在, 不可重复新增或修改!");
                    } else {
                        //更新车位名称
                        CarportNo cn = new CarportNo();
                        cn.setCarportNo(carportNo);
                        cn.setCommunityId(communityId);
                        cn.setUpdateBy(SecurityUtils.getUsername());
                        cn.setUpdateTime(new Date());
                        cn.setCarportNoId(carportNoId);
                        carportNoMapper.update(cn);
                    }
                }

                // 2. 修改
                carportVo.setCarportNo(String.valueOf(carportNoId));
                carportVo.setUpdateBy(SecurityUtils.getUsername());
                carportVo.setUpdateTime(new Date());
                this.update(carportVo);

            } else {
                Integer count = carportNoMapper.getCarportIdCarportNo(null, carportNo, communityId);
                if (count >= 1) {
                    return AjaxResult.error("该车位号已存在, 不可重复添加!");
                }
                //新增车位表信息
                CarportNo cn = new CarportNo();
                cn.setCarportNo(carportNo);
                cn.setCommunityId(communityId);
                cn.setCreateBy(SecurityUtils.getUsername());
                carportNoMapper.insert(cn);
                Long carportNoId = carportNoMapper.getCarPoreNoCount(communityId, carportNo);
                carportVo.setCarportNo(String.valueOf(carportNoId));
                // 1. 新增
                SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
                Long userId = sysUser.getUserId();
                carportVo.setCreateBy(userId.toString());
                carportMapper.save(carportVo);
            }
            return AjaxResult.success();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param carportVo
     */
    @Override
    public void update(BusCarportVo carportVo) {
        carportMapper.update(carportVo);
    }

    @Override
    public BusCarportVo selectUpdateDetail(BusCarportVo carportVo) {
        return carportMapper.selectUpdateDetail(carportVo);
    }

    @Override
    public Integer changeCarportStatus(BusCarportVo carportVo) {
        return carportMapper.changeCarportStatus(carportVo);
    }

    @Override
    public String importData(List<CarportImportVo> dataList, Long communityId) {
        if (CollectionUtils.isEmpty(dataList)) {
            throw new ServiceException("导入车位信息不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (CarportImportVo carportImportVo : dataList) {

            try {
                String carportNo = carportImportVo.getCarportNo();
                if (StringUtils.isNotEmpty(carportNo)) {
                    Long mapperCarportNoId = carportNoMapper.getCarportNoId(carportNo, communityId);
                    if (mapperCarportNoId == null) {
                        if (StringUtils.isNotEmpty(carportImportVo.getCarportNature())) {
                            //if (StringUtils.isNotEmpty(carportImportVo.getRentalType())) {
                             //   if (carportImportVo.getCarportFee() != null) {

                                    //新增车位表信息
                                    CarportNo cn = new CarportNo();
                                    cn.setCarportNo(carportNo);
                                    cn.setCommunityId(communityId);
                                    cn.setCreateBy(SecurityUtils.getUsername());
                                    carportNoMapper.insert(cn);
                                    Long carportNoId = carportNoMapper.getCarPoreNoCount(communityId, carportNo);
                                    BusCarportVo carportVo = new BusCarportVo();
                                    carportVo.setCreateBy(SecurityUtils.getUsername());
                                    carportVo.setCommunityId(communityId);
                                    carportVo.setCarportNo(String.valueOf(carportNoId));
                                    carportVo.setCarportNature(carportImportVo.getCarportNature());
                                    //carportVo.setRentalType(carportImportVo.getRentalType());
                                    //carportVo.setCarportFee(carportImportVo.getCarportFee());
                                    carportMapper.save(carportVo);

                                    successNum++;
                                    successMsg.append("<br/>" + successNum + "、车位编号 " + carportImportVo.getCarportNo() + " 导入成功");
//                                } else {
//                                    failureNum++;
//                                    failureMsg.append("<br/>" + failureNum + " 、车位编号" + carportImportVo.getCarportNo() + "请输入金额");
//                                }
//                            } else {
//                                failureNum++;
//                                failureMsg.append("<br/>" + failureNum + " 、车位编号" + carportImportVo.getCarportNo() + "请选择租售类型");
//                            }
                        } else {
                            failureNum++;
                            failureMsg.append("<br/>" + failureNum + " 、车位编号" + carportImportVo.getCarportNo() + "请选择车位属性");
                        }

                    } else {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + " 、车位编号" + carportImportVo.getCarportNo() + "已存在");
                    }
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + " 、请输入正确的车位编号信息");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、车位编号 " + carportImportVo.getCarportNo() + " 导入失败：";
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

    @Override
    public AjaxResult saveCar(BusVehicleVo vehicleVo) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        String userName = sysUser.getUserName();
        BusCarportVo carportVo = new BusCarportVo();
        carportVo.setCarportId(vehicleVo.getCarportId());
        carportVo.setAccountId(vehicleVo.getAccountId());
        carportVo.setVehicleId(Long.valueOf(vehicleVo.getPlateNo()));
        carportVo.setUpdateBy(userName);
        carportMapper.update(carportVo);


        return AjaxResult.success();
    }

    @Override
    public BusCarportVo getByAccountIdCarportInfo(Long accountId, String carportNo) {

        return carportMapper.getByAccountIdCarportInfo(accountId, carportNo);
    }

    @Override
    public void updateByCarportId(BusCarportVo carportVo) {
        carportMapper.updateByCarportId(carportVo);
    }

    @Override
    public List<CarportVo> getOwnerIdCarportList(Long ownerId, Long communityId) {

        return carportMapper.getOwnerIdCarportList(ownerId, communityId);
    }

    @Override
    public Long getCarportNoCarportId(String carportNo) {
        return carportMapper.getCarportNoCarportId(carportNo);
    }

    /**
     * @param carportId
     * @return
     */
    @Override
    public String getByCarportIdRentalType(Long carportId) {
        return carportMapper.getByCarportIdRentalType(carportId);
    }

    /**
     * @param carportNo
     * @return
     */
    @Override
    public BusCarportVo getCarportNoCarportNature(String carportNo) {
        return carportMapper.getCarportNoCarportNature(carportNo);
    }

}
