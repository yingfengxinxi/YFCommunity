package com.wr.estate.service.impl.manage.contract;


import com.wr.common.core.utils.ContractUtil;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.center.personnelfilemanage.OwnerFileMapper;
import com.wr.estate.mapper.manage.contract.LeaseMapper;
import com.wr.estate.mapper.manage.contract.TenantMapper;
import com.wr.estate.service.manage.contract.LeaseService;
import com.wr.remote.estate.manage.contract.Account;
import com.wr.remote.estate.manage.contract.Tenant;
import com.wr.remote.estate.manage.vo.contract.LeaseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: lusu
 * @Date: 2022/10/12 14:46
 * @Desc: 租赁合同管理的controller
 */
@Service
public class LeaseServiceImpl implements LeaseService {


    @Autowired
    private LeaseMapper leaseMapper;
    @Autowired
    private TenantMapper tenantMapper;
    @Autowired
    private OwnerFileMapper ownerFileMapper;

    /**
     * 查询列表信息
     *
     * @param leaseVo
     * @return
     */
    @Override
    public List<LeaseVo> getLeaseList(LeaseVo leaseVo) {
        leaseVo.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        return leaseMapper.getLeaseList(leaseVo);
    }

    /**
     * 新增业主租赁合同信息（租客  业主  合同  物业等信息）
     * 新增尚未校验数据唯一性，目前不知那些数据需唯一
     *
     * @param leaseVo
     * @return
     */
    @Override
    @Transactional
    public Integer leaseAdd(LeaseVo leaseVo) {
        //获取当前的登录人信息
        leaseVo.setCreateBy(SecurityUtils.getUsername());
        //添加租客
        Long tenantId = insertTenant(leaseVo);
        //添加合同
        leaseVo.setTenantId(tenantId);
        //生产一个自定义合同编号
        leaseVo.setContractNo(ContractUtil.contractNoCreate(leaseVo.getCommunityName()));
        return leaseMapper.leaseAdd(leaseVo);
    }

    /**
     * 删除租赁合同
     *
     * @param leaseId
     * @return
     */
    @Override
    @Transactional
    public int deleteLeaseByIds(Long[] leaseId) {
        return leaseMapper.deleteLeaseByIds(leaseId);
    }

    /**
     * 根据合同id查询合同记录
     *
     * @param contractId
     * @return
     */
    @Override
    public LeaseVo selectOneLeaseById(Long contractId) {
        return leaseMapper.selectOneLeaseById(contractId);
    }

    @Override
    public boolean checkLeaseExist(String cardNo) {
        return leaseMapper.checkLeaseExist(cardNo) != 0;
    }

    /**
     * 检查手机号是否存在
     *
     * @param tenantPhone
     * @return
     */
    @Override
    public boolean checkLeaseExistByPhone(String tenantPhone) {
        return leaseMapper.checkLeaseExistByPhone(tenantPhone) != 0;
    }

    @Override
    public Integer getCkeckRoom(Long communityId, Long roomId) {
        return leaseMapper.getCkeckRoom(communityId, roomId);
    }


    /**
     * vo中的信息存放到tenant实体类中
     *
     * @param leaseVo
     * @return
     */
    @Transactional(readOnly = false)
    public Long insertTenant(LeaseVo leaseVo) {
        // 房间居住数+1
        ownerFileMapper.increaseLive(leaseVo.getRoomId());
        // 查询租客是否存在
        Tenant tenantTemp = tenantMapper.getByPhoneOrCardNo(leaseVo.getTenantPhone(), leaseVo.getCardNo());
        //存在==>直接返回id
        if (null != tenantTemp) {
            return tenantTemp.getTenantId();
        }
        // 不存在==>新增
        Tenant tenant = new Tenant();
        //完善租客其他信息
        tenant.setTenantName(leaseVo.getTenantName());
        tenant.setTenantPhone(leaseVo.getTenantPhone());
        tenant.setGender(leaseVo.getGender());
        tenant.setCardType(leaseVo.getCardType());
        tenant.setCardNo(leaseVo.getCardNo());
        tenant.setCardFont(leaseVo.getCardFont());
        tenant.setCardBack(leaseVo.getCardBack());
        tenant.setFaceUrl(leaseVo.getFaceUrl());
        tenant.setCreateBy(SecurityUtils.getUsername());
        tenant.setDomicileAddress(leaseVo.getDomicileAddress());
        tenant.setPoliticsStatus(leaseVo.getPoliticsStatus());
        tenant.setReligion(leaseVo.getReligion());
        tenant.setNativePlace(leaseVo.getNativePlace());
        tenant.setNation(leaseVo.getNation());
        tenant.setEduLevel(leaseVo.getEduLevel());
        tenant.setMilitary(leaseVo.getMilitary());
        tenant.setMarriageStatus(leaseVo.getMarriageStatus());
        tenant.setNationality(leaseVo.getNationality());
        tenant.setPopulation(leaseVo.getPopulation());
        tenant.setCareer(leaseVo.getCareer());
        tenant.setQqNumber(leaseVo.getQqNumber());
        tenant.setEmail(leaseVo.getEmail());
        tenant.setSelfIntro(leaseVo.getSelfIntro());
        tenant.setCommunityId(leaseVo.getCommunityId());
        tenant.setBuildingId(leaseVo.getBuildingId());
        tenant.setUnitId(leaseVo.getUnitId());
        tenant.setRoomId(leaseVo.getRoomId());
        tenant.setOwnerId(leaseVo.getOwnerId());
        tenant.setAuditStatus("1");
        //判断三个字段是否为null,政治面貌,婚姻状况,人口类型[当三个字段某个字段用户未填写时,默认给到9]
        if (leaseVo.getPoliticsStatus() == null || "".equals(leaseVo.getPoliticsStatus())) {
            tenant.setPoliticsStatus("");
        }
        if (leaseVo.getMarriageStatus() == null || "".equals(leaseVo.getMarriageStatus())) {
            tenant.setMarriageStatus("");
        }
        if (leaseVo.getPopulation() == null || "".equals(leaseVo.getPopulation())) {
            tenant.setPopulation("");
        }
        //检测租客是否已注册
        Account temp = leaseMapper.checkTenantIsRegisterByPhone(leaseVo.getTenantPhone());
        // 存在修改帐号状态
        if (null != temp) {
            tenant.setAccountId(temp.getAccountId());
            tenantMapper.updateAccountType(tenant.getAccountId());
        } else {
            //Account对象
            Account account = new Account();
            account.setCommunityId(leaseVo.getCommunityId());
            account.setLoginName(leaseVo.getTenantPhone());
            account.setAvatar(leaseVo.getFaceUrl());
            account.setAccountType("1");
            account.setAccountName(leaseVo.getTenantName());
            account.setDataSource("0");
            account.setCreateBy(SecurityUtils.getUsername());
            //租客注册
            leaseMapper.addTenantAccount(account);
            tenant.setAccountId(account.getAccountId());
        }
        // 添加租客信息
        tenantMapper.tenantAdd(tenant);
        return tenant.getTenantId();
    }

}
