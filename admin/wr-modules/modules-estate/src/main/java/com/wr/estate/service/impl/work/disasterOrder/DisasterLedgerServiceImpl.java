package com.wr.estate.service.impl.work.disasterOrder;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.work.disasterOrder.DisasterLedgerMapper;
import com.wr.estate.service.work.disasterOrder.DisasterLedgerService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.base.BusDisaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-10-27 18:31:51
 * @Desc:
 */
@Service
public class DisasterLedgerServiceImpl implements DisasterLedgerService {
    @Autowired
    private DisasterLedgerMapper disasterLedgerMapper;

    /**
     * 分页查询灾害报事信息
     * @param busDisaster
     * @return
     */
    @Override
    public List<BusDisaster> listDisasterLedger(BusDisaster busDisaster) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        busDisaster.setEstateId(sysUser.getEstateId());
        return disasterLedgerMapper.listDisasterLedger(busDisaster);
    }

    /**
     * 添加灾害报事信息
     *
     * @param busDisaster
     * @return
     */
    @Override
    public AjaxResult insertBusDisaster(BusDisaster busDisaster) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        busDisaster.setEstateId(sysUser.getEstateId());
        busDisaster.setCreateBy(sysUser.getUserName());
        int count = disasterLedgerMapper.getDisasterName(sysUser.getEstateId(), busDisaster.getDisasterName());
        if (count>0){
          return   AjaxResult.error("灾害类型已经存在");
        }
        return AjaxResult.success(disasterLedgerMapper.insertBusDisaster(busDisaster));
    }

    /**
     * 删除灾害报事信息
     * @param disasterId
     * @return
     */
    @Override
    public Integer deleteDisasterId(Long disasterId) {
        return disasterLedgerMapper.deleteById(disasterId);
    }

    /**
     * 修改灾害报事信息
     *
     * @param busDisaster
     * @return 修改结果
     */
    @Override
    public AjaxResult updateBusDisaster(BusDisaster busDisaster) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        busDisaster.setEstateId(sysUser.getEstateId());
        busDisaster.setUpdateBy(sysUser.getUserName());
        int count = disasterLedgerMapper.getUpdateDisasterName(busDisaster);
        if (count>0){
           return AjaxResult.error("灾害类型已经存在");
        }
        return AjaxResult.success(disasterLedgerMapper.updateById(busDisaster));
    }

    @Override
    public List<BusDisaster> getOrderType(Long estateId) {
        return disasterLedgerMapper.getType(estateId);
    }

    /**
     * 批量导入灾害报事信息
     * @param dataList
     * @param updateSupport
     * @param operName
     * @return
     */
//    @Override
//    public String importData(List<BusDisaster> dataList, boolean updateSupport, String operName) {
//        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
//        Long userId = sysUser.getUserId();
//        Date date = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        if (StringUtils.isNull(dataList) || dataList.size() == 0) {
//            throw new ServiceException("导入用户数据不能为空！");
//        }
//        for (BusDisaster data : dataList) {
//            disasterLedgerMapper.insertBusDisaster(data);
//        }
//        return null;
//    }
}
