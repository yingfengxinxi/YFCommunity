package com.wr.estate.service.impl.center.personnelfilemanage;

import com.alibaba.fastjson2.JSONObject;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.model.PersonInfo;
import com.wr.common.customize.util.HikUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.dto.AuthDto;
import com.wr.estate.entity.dto.TenantFileDto;
import com.wr.estate.mapper.CommunityMapper;
import com.wr.estate.mapper.center.RoomMapper;
import com.wr.estate.mapper.center.audit.BusHouseMapper;
import com.wr.estate.mapper.center.personnelfilemanage.OwnerFileMapper;
import com.wr.estate.mapper.center.personnelfilemanage.TenantFileMapper;
import com.wr.estate.mapper.center.personnelfilemanage.VisitorFileMapper;
import com.wr.estate.mapper.center.useraccountmanage.AccountManageMapper;
import com.wr.estate.mapper.customer.rent.RentMapper;
import com.wr.estate.service.center.personnelfilemanage.TenantFileService;
import com.wr.remote.aiot.Access;
import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.domain.SysUser;
import com.wr.remote.domain.vo.VisitorVo;
import com.wr.remote.estate.manage.contract.Account;
import com.wr.remote.estate.manage.contract.Tenant;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: lvzy
 * @Date: 2022-10-17 16:12:35
 * @Desc:
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class TenantFileServiceImpl implements TenantFileService {
    private static final Logger log = LoggerFactory.getLogger(TenantFileServiceImpl.class);

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private OwnerFileMapper ownerFileMapper;

    @Autowired
    private TenantFileMapper tenantFileMapper;

    @Autowired
    private VisitorFileMapper visitorFileMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private AccountManageMapper accountManageMapper;

    @Autowired
    private BusHouseMapper busHouseMapper;

    @Autowired
    private RentMapper rentMapper;


    @Override
    public List<TenantFileDto> queryByCommunityId(TenantFileDto tenantFileDto) {

        List<TenantFileDto> list = tenantFileMapper.queryByCommunityId(tenantFileDto);

        String state = (String) tenantFileDto.getState();
        if (StringUtils.isNotEmpty(state)) {
            return Integer.valueOf(state) == 0 ? list.stream().filter(e -> e.getState().equals("进行中")).collect(Collectors.toList()) :
                    list.stream().filter(e -> e.getState().equals("已结束")).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public List<LinkedHashMap<String, Object>> queryContract(Long id) {
        return tenantFileMapper.queryContract(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int update(TenantFileDto tenantFileDto) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        tenantFileDto.setBy(sysUser.getUserName());
        return tenantFileMapper.update(tenantFileDto);
    }

    @Override
    @Transactional(readOnly = false)
    public int save(TenantFileDto tenantFileDto) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        tenantFileDto.setBy(sysUser.getUserName());
        Integer roomStatusCount = roomMapper.getByRoomIdRoomStatusCount(tenantFileDto.getRoomId());
        if (roomStatusCount >= 1) {
            throw new ServiceException("当前房屋已出租");
        }
        // 校验租客账号是否存在（查看租客是否是访客）
        Account unique = accountManageMapper.checkPhoneUnique(tenantFileDto.getTenantPhone());
        Account account = new Account();
        if (ObjectUtils.allNull(unique)) {
            account.setCommunityId(tenantFileDto.getCommunityId());
            account.setLoginName(tenantFileDto.getTenantPhone());
            account.setPassword(SecurityUtils.encryptPassword("1234abc"));
            // 账号类型（租客）
            account.setAccountType("1");
            account.setAccountName(tenantFileDto.getTenantName());
            account.setAccountNick(tenantFileDto.getTenantName());
            // 数据来源（手机注册）
            account.setDataSource("1");
            // 是否是志愿者（不是）
            account.setVolunteer("0");
            account.setAvatar("");
            account.setCreateBy(tenantFileDto.getTenantPhone());
            // 添加账号
            accountManageMapper.inAccount(account);
            // 账号id
            tenantFileDto.setAccountId(account.getAccountId());
        } else {
            Long accountId = unique.getAccountId();
            // 账号id
            tenantFileDto.setAccountId(accountId);
            // 修改账号类型
            accountManageMapper.upAccountType(accountId, SecurityUtils.getUsername(), "1");
        }
        //修改房屋状态为出租
        roomMapper.updateByRoomIdRoomStatus(tenantFileDto.getRoomId(), "5");
        //修改出租表状态为已出租
        rentMapper.updateByRoomIdStatus("1", tenantFileDto.getRoomId());
        // 房间人数+1
        //修改房间人数
        ownerFileMapper.increaseLive(tenantFileDto.getRoomId());

        return tenantFileMapper.save(tenantFileDto);
    }

    @Override
    @Transactional(readOnly = false)
    public int deleted(Long[] ids) {
        Map<String, Object> params = new HashMap<>();
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        params.put("by", sysUser.getUserName());
        List<TenantFileDto> tenantFileDto = tenantFileMapper.queryById(ids);
        for (TenantFileDto fileDto : tenantFileDto) {
            VisitorVo visitorVo = new VisitorVo();
            visitorVo.setCommunityId(fileDto.getCommunityId());
            visitorVo.setAccountId(fileDto.getAccountId());
            visitorVo.setVisitorName(fileDto.getTenantName());
            visitorVo.setVisitorPhone(fileDto.getTenantPhone());
            visitorVo.setWithNum(1);
            visitorVo.setBeginTime(new Date());
            visitorVo.setEndTime(new Date());
            visitorVo.setRoomId(fileDto.getRoomId());
            visitorVo.setOwnerId(Long.valueOf(fileDto.getOwnerId()));
            visitorVo.setCreateBy(SecurityUtils.getUsername());
            //添加访客
            visitorFileMapper.insertVisitor(visitorVo);
            rentMapper.updateByRoomIdStatus("0", fileDto.getRoomId());
            //修改房屋状态为入住
            roomMapper.updateByRoomIdRoomStatus(fileDto.getRoomId(), "2");
        }

        //账号type
        tenantFileMapper.updateAccountState(ids);
        // 房间人数-1
        tenantFileMapper.reduceLiveNumOne(ids);

        return tenantFileMapper.deleted(ids);
    }

    /**
     * 续约租客
     *
     * @param contractStart
     * @param contractEnd
     * @param ids
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public int updateContract(Date contractStart,
                              Date contractEnd,
                              String ids) {

        List<String> idList = Arrays.asList(ids.split(","));
        int i = tenantFileMapper.updateContract(contractStart, contractEnd, SecurityUtils.getUsername(), idList);
        for (String id : idList) {
            Tenant tenant = tenantFileMapper.selectTenantByTenantId(Long.valueOf(id));
            roomMapper.updateByRoomIdRoomStatus(tenant.getRoomId(), "5");
        }
        return i;
    }

    @Override
    public AjaxResult selectDeviceList(Long communityId) {
        AjaxResult ajaxResult = AjaxResult.success();
        Tenant tenant = new Tenant();
        tenant.setCommunityId(communityId);
        List<Tenant> tenantList = communityMapper.selectTenantList(tenant);
        ajaxResult.put("tenantList", tenantList);
        List<Access> deviceList = ownerFileMapper.selectAccessList(communityId);
        ajaxResult.put("deviceList", deviceList);
        return ajaxResult;
    }

    @Override
    public AjaxResult authTenant(AuthDto authDto) {
        log.info("小区id：{}", authDto.getCommunityId());
        log.info("人员：{}", authDto.getIds());
        log.info("所选设备：{}", authDto.getIndexCodes());
        //查询小区对应的海康配置
        CommunityHik hik = communityMapper.selectHikByCommunityId(authDto.getCommunityId());
        if (null == hik) {
            return AjaxResult.error("请检查小区海康配置后重试");
        }
        //处理设备编码
        List<String> resourceIndexCode = new ArrayList<>(Arrays.asList(authDto.getIndexCodes()));
        List<PersonInfo> listPeronInfo = new ArrayList<>();
        for (int i = 0; i < authDto.getIds().length; i++) {
            Tenant tenant = tenantFileMapper.selectTenantByTenantId(authDto.getIds()[i]);
            if (null != tenant && org.apache.commons.lang3.StringUtils.isEmpty(tenant.getFaceUrl())) {
                PersonInfo personInfo = new PersonInfo();
                personInfo.setPersonId(String.valueOf(tenant.getTenantId()));
                personInfo.setCertificateNo(tenant.getCardNo());
                personInfo.setPersonName(tenant.getTenantName());
                personInfo.setFaceUrl(tenant.getFaceUrl());
                listPeronInfo.add(personInfo);
            }
        }
        if (listPeronInfo.isEmpty()) {
            return AjaxResult.error("上传人员信息无人脸信息");
        }
        //初始化海康
        HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
        log.info("开始创建任务====>");
        String taskId = HikUtils.createTask(5);
        if (null == taskId) {
            return AjaxResult.error("授权失败，创建任务失败");
        }
        //添加数据到任务中
        log.info("添加数据到任务中====>");
        boolean bolTwo = HikUtils.addData(taskId, resourceIndexCode, listPeronInfo);
        if (!bolTwo) {
            return AjaxResult.error("授权失败");
        }
        //开始任务
        log.info("开始任务====>");
        boolean bolThree = HikUtils.startTask(taskId);
        if (!bolThree) {
            return AjaxResult.error("授权失败");
        }
        //任务处理
        log.info("任务处理====>");
        JSONObject jsonObject = HikUtils.progressTask(taskId);
        if (null == jsonObject) {
            return AjaxResult.error("授权失败");
        }
        boolean finished = jsonObject.getBoolean("isDownloadFinished");
        if (!finished) {
            return AjaxResult.success("设备授权中...");
        }
        return AjaxResult.success("已授权");
    }

    @Override
    public List<Long> getContractEndRoomIds() {
        return tenantFileMapper.getContractEndRoomIds();
    }

    @Override
    public TenantFileDto getById(Long tenantId) {
        return tenantFileMapper.getById(tenantId);
    }

}

