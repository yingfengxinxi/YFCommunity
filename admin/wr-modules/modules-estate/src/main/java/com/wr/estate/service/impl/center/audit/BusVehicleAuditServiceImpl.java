package com.wr.estate.service.impl.center.audit;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.DesensitizationUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.bean.BeanValidators;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.vo.BusVehicleAuditvo;
import com.wr.estate.entity.vo.BusVehicleImportVo;
import com.wr.estate.mapper.center.audit.BusVehicleAuditMapper;
import com.wr.estate.mapper.center.useraccountmanage.AccountManageMapper;
import com.wr.estate.service.center.audit.BusVehicleAuditService;
import com.wr.remote.estate.center.BusVehicle;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-18 16:27:49
 * @Desc:
 */
@Service
public class BusVehicleAuditServiceImpl implements BusVehicleAuditService {

    @Resource
    private BusVehicleAuditMapper vehicleAuditMapper;

    @Autowired
    private AccountManageMapper accountManageMapper;

    @Override
    public List<BusVehicleAuditvo> listOwnerAuditQure(BusVehicleAuditvo busVehicleAuditvo) {
        List<BusVehicleAuditvo> busVehicleAuditvos = vehicleAuditMapper.selectListVehicleAudit(busVehicleAuditvo);
        if(CollectionUtils.isNotEmpty(busVehicleAuditvos)){
            busVehicleAuditvos.stream().forEach(busVehicleAuditvo1 -> {
                busVehicleAuditvo1.setAccountName(DesensitizationUtils.name(busVehicleAuditvo1.getAccountName()));
                busVehicleAuditvo1.setLoginName(DesensitizationUtils.phone(busVehicleAuditvo1.getLoginName()));
            });
        }
        return busVehicleAuditvos;
    }

    @Override
    public void deleteConfigByIds(Long vehicleId) {
        vehicleAuditMapper.deleteByPrimaryKeys(vehicleId);
    }

    @Override
    public BusVehicleAuditvo selectOneOwneAuditVo(Long vehicleId) {
        Long userId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        String accountType = vehicleAuditMapper.getVehicleIdAccountType(vehicleId);
        BusVehicleAuditvo busVehicleAuditvo = vehicleAuditMapper.selectoneOwneAuditVo(vehicleId, userId, accountType);
        if(busVehicleAuditvo!=null){
            busVehicleAuditvo.setAccountName(DesensitizationUtils.name(busVehicleAuditvo.getAccountName()));
            busVehicleAuditvo.setLoginName(DesensitizationUtils.phone(busVehicleAuditvo.getLoginName()));
        }
        return busVehicleAuditvo;
    }

    @Override
    public int passAndRejection(Long vehicleId, String auditStatus, String rejectReason) {
        return vehicleAuditMapper.updataByownerIdAndAuditStatus(vehicleId, auditStatus, rejectReason, SecurityUtils.getUsername());
    }

    /**
     * 导入
     *
     * @param vehicleList
     * @param estateId
     * @return
     */
    @Override
    public String importVehicle(List<BusVehicleImportVo> vehicleList, int estateId) {
        if (StringUtils.isNull(vehicleList) || vehicleList.size() == 0) {
            throw new ServiceException("导入车辆数据不能为空！");
        }
        String username = SecurityUtils.getUsername();
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (BusVehicleImportVo vehicleImportVo : vehicleList) {

            try {
                if (StringUtils.isEmpty(vehicleImportVo.getVehicleStatus())) {
                    vehicleImportVo.setVehicleStatus("0");
                }

                vehicleImportVo.setEstateId(estateId);
                vehicleImportVo.setCreateBy(username);
                //查询用户id
                Long accountId = accountManageMapper.getAccountIdByLoginName(vehicleImportVo.getAccountId());
                if (accountId != null) {
                    vehicleImportVo.setAccountId(String.valueOf(accountId));
                    vehicleAuditMapper.insertvehicle(vehicleImportVo);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、车牌号 " + vehicleImportVo.getPlateNo() + " 导入成功");

                } else {
                    failureNum++;
                    String msg = "<br/>" + failureNum + "、车牌号 " + vehicleImportVo.getPlateNo() + " 导入失败：";
                    failureMsg.append(msg + "手机号不存在，请核对手机号是否正确");
                }


            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、车牌号 " + vehicleImportVo.getPlateNo() + " 导入失败：";
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
