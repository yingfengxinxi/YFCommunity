package com.wr.api.owner.service.estateapi.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wr.api.owner.entity.estateapi.CarportImportVo;
import com.wr.api.owner.mapper.estateapi.BusCarportMapper;
import com.wr.api.owner.mapper.estateapi.BusVehicleMapper;
import com.wr.api.owner.mapper.estateapi.CarportNoMapper;
import com.wr.api.owner.service.estateapi.CarportService;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.CarportNo;
import com.wr.remote.domain.SysUser;
import com.wr.remote.domain.vo.CarportVo;
import com.wr.remote.estate.center.BusCarport;
import com.wr.remote.estate.center.vo.BusCarportLogVo;
import com.wr.remote.estate.center.vo.BusCarportVo;
import com.wr.remote.estate.center.vo.BusVehicleVo;
import com.wr.remote.estate.center.vo.SpaceVehicleVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 车位管理
 *
 * @Author: lvzy
 * @Date: 2022-10-14 13:55:53
 * @Desc: 车位管理
 */
@Service
public class CarportServiceImpl extends ServiceImpl<BusCarportMapper, BusCarport> implements CarportService {

    @Resource
    private BusCarportMapper carportMapper;

    @Resource
    private CarportNoMapper carportNoMapper;

    @Resource
    private BusVehicleMapper vehicleMapper;


    @Override
    public List<BusCarportVo> listPage(BusCarportVo carportVo) {
        return carportMapper.listPage( carportVo );
    }

    @Override
    public BusCarportVo detail(Long carportId) {
        return carportMapper.detail( carportId );
    }

    @Override
    public List<BusCarportLogVo> detailList(Long carportId) {
        return carportMapper.detailList( carportId );
    }

    /**
     * @param communityId
     * @return
     */
    @Override
    public List<CarportVo> getCommunityCarportList(Long communityId) {
        return carportMapper.getCommunityCarportList( communityId );
    }

    @Override
    public Integer removes(Long[] ids) {
        for (Long id : ids) {
            carportMapper.removes( id );
        }
        return 1;
    }

    /**
     * 转让 -> 1.新增车位记录表数据 2.改产权人和车辆
     *
     * @param carportVo
     * @return
     */
    @Override
    public Integer cedeSubmit(BusCarportLogVo carportVo) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        carportVo.setRentalStart( dateFormat.format( date ) );
        // 根据车辆ID查 车牌号 plateNo
        String plateNo = carportMapper.selectPlateNoById( carportVo.getOwnerCarValueId() );
        carportVo.setPlateNo( plateNo );
        // 1. 新增车位记录表数据
        carportMapper.cedeSubmit( carportVo );
        // 根据业主ID查 产权人id，表bus_account
        Long accountId = carportMapper.selectCarportAccount( carportVo.getOwnerId() );
        carportVo.setAccountId( accountId );
        // 2.改产权人和车辆
        carportMapper.updateCede( carportVo );
        return 1;
    }

    @Override
    public AjaxResult saveOrUpdate(BusCarportVo carportVo) {
        String carportNo = carportVo.getCarportNo();
        Long communityId = carportVo.getCommunityId();
        // 查找车位是否存在
        Long carportNoId = carportNoMapper.getCarPoreNoCount( communityId, carportNo );
        if (carportNoId != null) {
            return AjaxResult.error( "该车位号已存在, 不可重复新增或修改!" );
        }
        //新增车位表信息
        CarportNo cn = new CarportNo();
        cn.setCarportNo( carportNo );
        cn.setCommunityId( communityId );
        cn.setCreateBy( SecurityUtils.getUsername() );
        carportNoMapper.insert( cn );
        carportNoId = carportNoMapper.getCarPoreNoCount( communityId, carportNo );
        carportVo.setCarportNo( String.valueOf( carportNoId ) );


        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        Long userId = sysUser.getUserId();
        Date date = new Date();
        if (carportVo.getCarportId() == null) {
            // 1. 新增
            carportVo.setCreateBy( userId.toString() );
            carportMapper.save( carportVo );
        } else {
            // 2. 修改
            carportVo.setUpdateBy( userId.toString() );
            carportVo.setUpdateTime( date );
            carportMapper.update( carportVo );
        }
        return AjaxResult.success();
    }

    @Override
    public BusCarportVo updateDetail(BusCarportVo carportVo) {
        return carportMapper.selectUpdateDetail( carportVo );
    }

    @Override
    public Integer changeCarportStatus(BusCarportVo carportVo) {
        return carportMapper.changeCarportStatus( carportVo );
    }

    @Override
    public String importData(List<CarportImportVo> dataList, Long communityId) {
        if (CollectionUtils.isEmpty( dataList )) {
            throw new ServiceException( "导入车位信息不能为空！" );
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (CarportImportVo carportImportVo : dataList) {

            try {
                String carportNo = carportImportVo.getCarportNo();
                if (StringUtils.isNotEmpty( carportNo )) {
                    Long carportNoId = carportNoMapper.getCarportNoId( carportNo, communityId );
                    if (carportNoId != null) {
                        BusCarport carportInfo = carportMapper.getByCarportNoCarportInfo( String.valueOf( carportNoId ), communityId );
                        if (carportInfo == null) {
                            if (StringUtils.isNotEmpty( carportImportVo.getCarportNature() )) {
                                if (StringUtils.isNotEmpty( carportImportVo.getRentalType() )) {
                                    if (carportImportVo.getCarportFee() != null) {

                                        BusCarportVo carportVo = new BusCarportVo();
                                        carportVo.setCreateBy( SecurityUtils.getUsername() );
                                        carportVo.setCommunityId( communityId );
                                        carportVo.setCarportNo( String.valueOf( carportNoId ) );
                                        carportVo.setCarportNature( carportImportVo.getCarportNature() );
                                        carportVo.setRentalType( carportImportVo.getRentalType() );
                                        carportVo.setCarportFee( carportImportVo.getCarportFee() );
                                        carportMapper.save( carportVo );

                                        successNum++;
                                        successMsg.append( "<br/>" + successNum + "、车位编号 " + carportImportVo.getCarportNo() + " 导入成功" );
                                    } else {
                                        failureNum++;
                                        failureMsg.append( "<br/>" + failureNum + " 、车位编号" + carportImportVo.getCarportNo() + "请输入金额" );
                                    }
                                } else {
                                    failureNum++;
                                    failureMsg.append( "<br/>" + failureNum + " 、车位编号" + carportImportVo.getCarportNo() + "请选择租售类型" );
                                }
                            } else {
                                failureNum++;
                                failureMsg.append( "<br/>" + failureNum + " 、车位编号" + carportImportVo.getCarportNo() + "请选择车位属性" );
                            }

                        } else {
                            failureNum++;
                            failureMsg.append( "<br/>" + failureNum + " 、车位编号" + carportImportVo.getCarportNo() + "已存在" );
                        }
                    } else {
                        failureNum++;
                        failureMsg.append( "<br/>" + failureNum + " 、车位编号" + carportImportVo.getCarportNo() + "不存在" );
                    }

                } else {
                    failureNum++;
                    failureMsg.append( "<br/>" + failureNum + " 、请输入正确的车位编号信息" );
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、车位编号 " + carportImportVo.getCarportNo() + " 导入失败：";
                failureMsg.append( msg + e.getMessage() );
            }
        }
        if (failureNum > 0) {
            failureMsg.insert( 0, "很抱歉，导入失败！共 " + failureNum + " 条数据不正确，错误如下：" );
            //throw new ServiceException(failureMsg.toString());
            return failureMsg.toString();
        } else {
            successMsg.insert( 0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：" );
            return successMsg.toString();
        }
    }

    @Override
    @Transactional
    public AjaxResult saveCar(BusVehicleVo vehicleVo) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        // 查查询车辆管理表 bus_vehicle 查看车辆是否存在
//        SpaceVehicleVo plateNoInfo = vehicleMapper.getByPlateNoInfo( vehicleVo.getCommunityId(), vehicleVo.getPlateNo() );
//        if (plateNoInfo == null || plateNoInfo.getId() == null) {
//            throw new ServiceException( "未添加" + vehicleVo.getPlateNo() + "车牌，请进行添加后在进行车位绑定" );
//        }

        //判断当前车辆是否已绑定
//        SpaceVehicleVo vehicleIdCarport = carportMapper.getVehicleIdCarport( vehicleVo.getAccountId(), vehicleVo.getCommunityId(), plateNoInfo.getVehicleId() );
//        if (vehicleIdCarport != null) {
//            if (!StringUtils.equals( vehicleVo.getCarportNo(), vehicleIdCarport.getCarportNo() )) {
//                CarportNo carportNo = carportNoMapper.get( Long.valueOf( vehicleVo.getCarportNo() ) );
//                return AjaxResult.error( "当前车牌号【" + vehicleVo.getPlateNo() + "】已绑定车位,车位编号为【" + carportNo.getCarportNo() + "】" );
//            }
//        }

        //修改车辆信息
        vehicleVo.setUpdateBy( vehicleVo.getCreateBy() );
        vehicleVo.setVehicleId( null );


        BusCarportVo carportVo = new BusCarportVo();
        carportVo.setCarportId( vehicleVo.getCarportId() );
        carportVo.setAccountId( vehicleVo.getAccountId() );
        carportVo.setVehicleId( null );
        carportVo.setUpdateBy( vehicleVo.getCreateBy() );
        carportVo.setCarportFee( vehicleVo.getCarportFee() );
        carportVo.setRentalEnd( vehicleVo.getRentalEnd() );
        carportVo.setRoomId( vehicleVo.getRoomId() );
        carportVo.setRentalType( vehicleVo.getRentalType() );
        if (StringUtils.isEmpty( vehicleVo.getRentalStart() )) {
            carportVo.setRentalStart( dateFormat.format( date ) );
        }

        //修改 bus_carport 车位管理表
        Integer update = carportMapper.update( carportVo );

//        BusCarportVo selectedUpdateDetail = carportMapper.selectUpdateDetail( carportVo );
//        if (selectedUpdateDetail != null) {
////            carportMapper.updateCarportId( carportVo.getCarportId(), vehicleVo.getCommunityId(), vehicleVo.getBuildingId(), vehicleVo.getUnitId(), vehicleVo.getRoomId() );
//            if (selectedUpdateDetail.getVehicleId() == null || selectedUpdateDetail.getVehicleId() == 0) {
//
//            }
//        }

        return AjaxResult.success(update>0?"绑定车位成功":"绑定车位失败，请重新绑定");
    }

    @Override
    public List<BusCarportVo> selectBusCarportNoRental(BusCarportVo carportVo) {

        return carportMapper.selectBusCarportNoRental( carportVo );
    }

    @Override
    public void updateCarportId(Long carportId,Long communityId, Long roomId) {
         carportMapper.updateCarportId(carportId,communityId,roomId);
    }

    @Override
    public AjaxResult renewalBusCarport(BusCarportVo carport) {
        Integer update = carportMapper.update( carport );
        return AjaxResult.success(update>0?"续费单据成功提交，请支付":"续费单据提交失败，请重试");
    }

    @Override
    public Integer updateByBusCarport(BusCarportVo carport) {
        return carportMapper.updateByBusCarport( carport );
    }

    @Override
    public BusCarportVo detailBusCarport(Long carportId) {
        return carportMapper.detailBusCarport( carportId );
    }

    @Override
    public void clearBusCarport(String carportId) {
         carportMapper.clearBusCarport( carportId );
    }
}
