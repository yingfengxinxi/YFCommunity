package com.wr.estate.service.impl.center.personnelfilemanage;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.DesensitizationUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.model.PersonInfo;
import com.wr.common.customize.util.HikUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.BusHouse;
import com.wr.estate.entity.dto.AuthDto;
import com.wr.estate.entity.dto.TenantFileDto;
import com.wr.estate.entity.vo.OwnerFileImportVo;
import com.wr.estate.mapper.CommunityMapper;
import com.wr.estate.mapper.center.BuildingMapper;
import com.wr.estate.mapper.center.RoomMapper;
import com.wr.estate.mapper.center.UnitMapper;
import com.wr.estate.mapper.center.audit.BusHouseMapper;
import com.wr.estate.mapper.center.carManage.BusCarportMapper;
import com.wr.estate.mapper.center.carManage.BusVehicleMapper;
import com.wr.estate.mapper.center.personnelfilemanage.OwnerFileMapper;
import com.wr.estate.mapper.center.personnelfilemanage.TenantFileMapper;
import com.wr.estate.mapper.center.useraccountmanage.AccountManageMapper;
import com.wr.estate.service.center.personnelfilemanage.OwnerFileService;
import com.wr.estate.service.center.personnelfilemanage.TenantFileService;
import com.wr.estate.service.center.useraccountmanage.AccountManageService;
import com.wr.remote.aiot.Access;
import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.center.Room;
import com.wr.remote.estate.center.vo.*;
import com.wr.remote.estate.manage.contract.Owner;
import com.wr.system.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Author: lvzy
 * @Date: 2022-10-12 17:20:41
 * @Desc: 业主档案
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class OwnerFileServiceImpl implements OwnerFileService {
    private static final Logger log = LoggerFactory.getLogger(OwnerFileServiceImpl.class);

    private final CommunityMapper communityMapper;
    private final OwnerFileMapper ownerFileMapper;
    private final AccountManageMapper accountManageMapper;
    private final AccountManageService accountManageService;
    private final RoomMapper roomMapper;
    private final BusVehicleMapper busVehicleMapper;
    private final BusCarportMapper busCarportMapper;
    private final BuildingMapper buildingMapper;
    private final UnitMapper unitMapper;
    private final BusHouseMapper busHouseMapper;
    private final TenantFileMapper tenantFileMapper;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = false)
    public int insert(Map<String, Object> params) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        params.put("password", SecurityUtils.encryptPassword("1234abc"));
        params.put("accountType", "0");
        params.put("by", sysUser.getUserName());
        params.put("phone", params.get("ownerPhone"));
        params.put("name", params.get("ownerName"));
        // 查询账号是否存在，存在则修改成业主类型
        Long accountId = accountManageMapper.getAccountIdByLoginName(String.valueOf(params.get("ownerPhone")));
        if (ObjectUtil.isEmpty(accountId)) {
            //新增账号
            accountManageMapper.insert(params);
        } else {
            params.put("accountId", accountId);
            // 修改账号
            accountManageMapper.updateOwnerById(accountId);
        }
        params.put("auditStatus", "1");
        String ownerRelation = String.valueOf(params.get("ownerRelation"));
        //本人
        if ("0".equals(ownerRelation)) {
            params.put("parentId", 0);
        } else {
            params.put("parentId", ownerFileMapper.getOwnerId(Long.valueOf(params.get("roomId") + "")));
        }

        //新增业主
        ownerFileMapper.insert(params);
        ownerFileMapper.insertHouse(params);
        params.put("roomStatus", "1");
        //本人修改
        if ("0".equals(String.valueOf(params.get("ownerRelation")))) {
            //修改房间信息
            ownerFileMapper.updateRoom(params);
        }
        //房间人数 +1
        ownerFileMapper.increaseLive(Long.valueOf(String.valueOf(params.get("roomId"))));
        return 1;
    }

    @Override
    @Transactional(readOnly = false)
    public int update(Map<String, Object> params) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        params.put("by", sysUser.getUserName());
        params.put("roomStatus", "2");
        params.put("name", params.get("ownerName"));
        //本人修改
        if ("0".equals(String.valueOf(params.get("ownerRelation")))) {
            //修改房间信息
            ownerFileMapper.updateRoom(params);
        }
        if (!(String.valueOf(params.get("oldRoomId")).equals(String.valueOf(params.get("roomId"))))) {
            //房间人数 +1
            ownerFileMapper.increaseLive(Long.valueOf(String.valueOf(params.get("roomId"))));
            //房间人数 -1
            ownerFileMapper.decreaseLive(Long.valueOf(String.valueOf(params.get("oldRoomId"))));
            ownerFileMapper.updateRoomState(params);
        }
        String faceUrl = String.valueOf(params.get("faceUrl"));
        String accountName = String.valueOf(params.get("name"));
        String accountNick = String.valueOf(params.get("name"));
        Long accountId = Long.valueOf(String.valueOf(params.get("accountId")));
        accountManageMapper.updateAccountName(faceUrl, accountName, accountNick, accountId);
        return ownerFileMapper.update(params);
    }

    @Override
    @Transactional(readOnly = false)
    public int deleteds(Long[] id) {
        Map<String, Object> params = new HashMap<>();
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        ArrayList<Long> roomId = new ArrayList<>();
        ArrayList<Long> accountIds = new ArrayList<>();
        params.put("by", sysUser.getUserName());
        params.put("roomStatus", "4");
        params.put("ownerId", 0);
        params.put("licenseUrl", "");
        params.put("deliveryTime", null);
        params.put("roomId", roomId);
        List<Long> roomAllIds = Lists.newArrayList();
        ownerFileMapper.getRoomIdAndAccountId(id).forEach(e -> {
            accountIds.add((Long) e.get("accountId"));
            roomAllIds.add((Long) e.get("roomId"));
            //本人修改
            if ("0".equals(String.valueOf(e.get("ownerRelation")))) {
                roomId.add((Long) e.get("roomId"));
            }
        });
        if (roomId.size() > 0) {
            //更新房屋
            ownerFileMapper.updateRooms(params);
        }
        //房间人数 -1
        if (!roomAllIds.isEmpty()) {
            ownerFileMapper.reduceLiveNumOne(roomAllIds);
        }
        //删除账号
        accountManageMapper.deleteds(accountIds);
        // 删除党员
        accountIds.forEach(accountManageMapper::delMember);
        return ownerFileMapper.deleteds(id);
    }

    @Override
    @Transactional(readOnly = false)
    public int deleted(Long id) {
        Map<String, Object> params = new HashMap<>();
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();

        params.put("by", sysUser.getUserName());
        params.put("roomStatus", "4");
        params.put("ownerId", 0);
        params.put("licenseUrl", "");
        params.put("deliveryTime", null);

        ownerFileMapper.getRoomIdAndAccountId(new Long[]{id}).forEach(e -> {
            if ("0".equals(String.valueOf(e.get("ownerRelation")))) {
                params.put("roomId", e.get("roomId"));
                //更新房屋
                ownerFileMapper.updateRoom(params);
            } else {
                //房间人数 -1
                ownerFileMapper.decreaseLive(Long.valueOf(String.valueOf(e.get("roomId"))));
            }
            //删除账号
            Long accountId = (Long) e.get("accountId");
            //删除账号
            List<Long> accountIds = Lists.newArrayList();
            accountIds.add(accountId);
            accountManageMapper.deleteds(accountIds);
            // 删除党员
            accountManageMapper.delMember(accountId);
        });
        //删除业主绑定的房产信息
        busHouseMapper.deleteByOwnerIdHouse(id);
        return ownerFileMapper.deleted(id);
    }

    @Override
    public List<OwnerFileVo> queryByCommunityId(Map<String, Object> params) {
        List<OwnerFileVo> ownerFileVos = ownerFileMapper.queryByCommunityId(params);
        if (CollectionUtils.isNotEmpty(ownerFileVos)) {
            ownerFileVos.stream().forEach(ownerFileVo -> {
                ownerFileVo.setOwnerName(DesensitizationUtils.name(ownerFileVo.getOwnerName()));
                ownerFileVo.setOwnerPhone(DesensitizationUtils.name(ownerFileVo.getOwnerPhone()));
                if (StringUtils.equals(ownerFileVo.getCardType(), "I")) {
                    ownerFileVo.setCardNo(DesensitizationUtils.cardNo(ownerFileVo.getCardNo()));
                }

                Integer space = ownerFileVo.getSpace();
                Integer roomSpace = ownerFileVo.getRoomSpace();
                ownerFileVo.setIsShowDel(0);
                if ((space + roomSpace) > 0) {
                    ownerFileVo.setIsShowDel(1);
                }

                String updateBy = ownerFileVo.getUpdateBy();
                if (StringUtils.isNotEmpty(updateBy)) {
                    SysUser user = userMapper.selectUserByUserName(updateBy);
                    if(user!=null){
                        ownerFileVo.setUpdateBy(user.getNickName());
                    }
                }
            });
        }
        return ownerFileVos;
    }

    /**
     * @param roomId
     * @param ownerId
     * @return
     */
    @Override
    public Integer getByRoomIdOwnerRelationCount(Long roomId, Long ownerId) {
        return ownerFileMapper.getByRoomIdOwnerRelationCount(roomId, ownerId);
    }


    @Override
    public List<LinkedHashMap<String, Object>> queryByRoomId(Map<String, Object> params) {
        List<LinkedHashMap<String, Object>> list = ownerFileMapper.queryByRoomId(params);
        list.forEach(e -> {
            String[] car = ownerFileMapper.queryCarByAccountId((Long) e.get("accountId"));
            if (car.length == 0) {
                e.put("carNum", "");
            } else {
                e.put("carNum", Arrays.toString(car));
            }
        });
        return list;
    }

    @Override
    public List<LinkedHashMap<String, Object>> queryRoomByOwnerId(Long id) {
        return ownerFileMapper.queryRoomByOwnerId(id);
    }

    /**
     * 根据业主id查询
     *
     * @param id
     * @param communityId
     * @return
     */
    @Override
    public RoomDetailVO getDetailByOwnerId(Long id, Long communityId) {
        RoomDetailVO roomDetail = new RoomDetailVO();
        List<OwnerVo> ownerList = ownerFileMapper.getByOwnerIdOwnerInfo(id, communityId);

        OwnerVo ov = new OwnerVo();
        for (OwnerVo ownerVo : ownerList) {
            if (ownerVo != null) {

//                ownerVo.setOwnerName(DesensitizationUtils.name(ownerVo.getOwnerName()));
//                ownerVo.setOwnerPhone(DesensitizationUtils.phone(ownerVo.getOwnerPhone()));
//                if(StringUtils.equals(ownerVo.getCardType(),"I")){
//                    ownerVo.setCardNo(DesensitizationUtils.cardNo(ownerVo.getCardNo()));
//                }


                roomDetail = roomMapper.getDetailById(ownerVo.getRoomId(), id);
                Integer maxFloor = roomMapper.maxFloorByBuildingId(roomDetail.getBuildingId());
                roomDetail.setTotalFloor(maxFloor);
                ov = ownerVo;
                String licenseUrl = ownerList.get(0).getLicenseUrl();
                if (StringUtils.isNotEmpty(licenseUrl)) {
                    roomDetail.setLicenseUrl(licenseUrl);
                }
                roomDetail.setOwnerList(ownerList);
            }
        }

        //车位信息
        Long accountId = ov.getAccountId();
        List<SpaceVehicleVo> spaceVehicleVoList = busVehicleMapper.getAccountIdVehicleInfo(accountId, communityId);
        if (CollectionUtils.isNotEmpty(spaceVehicleVoList)) {
            spaceVehicleVoList.stream().forEach(spaceVehicleVo -> {
                SpaceVehicleVo vehicleIdCarport = busCarportMapper.getVehicleIdCarport(accountId, communityId, spaceVehicleVo.getVehicleId());
                if (vehicleIdCarport != null) {
                    if (StringUtils.isNotEmpty(vehicleIdCarport.getCarportNo())) {
                        spaceVehicleVo.setCarportNo(vehicleIdCarport.getCarportNo());
                    }
                    if (StringUtils.isNotEmpty(vehicleIdCarport.getCarportNature())) {
                        spaceVehicleVo.setCarportNature(vehicleIdCarport.getCarportNature());
                    }
                    if (StringUtils.isNotEmpty(vehicleIdCarport.getRentalType())) {
                        spaceVehicleVo.setRentalType(vehicleIdCarport.getRentalType());
                    }
                    if (vehicleIdCarport.getCarportFee() != null) {
                        spaceVehicleVo.setCarportFee(vehicleIdCarport.getCarportFee());
                    }
                    if (StringUtils.isNotEmpty(vehicleIdCarport.getRentalStart())) {
                        spaceVehicleVo.setRentalStart(vehicleIdCarport.getRentalStart());
                    }
                    if (StringUtils.isNotEmpty(vehicleIdCarport.getRentalEnd())) {
                        spaceVehicleVo.setRentalEnd(vehicleIdCarport.getRentalEnd());
                    }
                }
            });
            roomDetail.setSpaceVehicleList(spaceVehicleVoList);
        }
        return roomDetail;
    }

    @Override
    public List<LinkedHashMap<String, Object>> queryCarByOwnerId(Long id) {
        return ownerFileMapper.queryCarByOwnerId(id);
    }

    @Override
    public List<LinkedHashMap<String, Object>> queryTenantByOwnerId(Long id) {
        List<LinkedHashMap<String, Object>> list = ownerFileMapper.queryTenantByOwnerId(id);
        list.forEach(e -> {

            String[] car = ownerFileMapper.queryCarByAccountId((Long) e.get("accountId"));
            if (car.length == 0) {
                e.put("carNum", "");
            } else {
                e.put("carNum", Arrays.toString(car));
            }

        });
        return list;
    }

    @Override
    public List<LinkedHashMap<String, Object>> queryCarPortByOwnerId(Long id) {

        return ownerFileMapper.queryCarPortByOwnerId(id);
    }

    @Override
    public int getPhoneNum(String no) {
        return ownerFileMapper.getPhoneNum(no);
    }

    @Override
    public int getCardNoNum(String no) {
        return ownerFileMapper.getCardNoNum(no);
    }

    @Override
    public List<LinkedHashMap<String, Object>> getDictData() {
        return ownerFileMapper.getDictData();
    }

    @Override
    public AjaxResult selectDeviceList(Long communityId) {
        AjaxResult ajaxResult = AjaxResult.success();
        Owner owner = new Owner();
        owner.setCommunityId(communityId);
        List<Owner> ownerList = communityMapper.selectOwnerList(owner);
        ajaxResult.put("ownerList", ownerList);
        List<Access> deviceList = ownerFileMapper.selectAccessList(communityId);
        ajaxResult.put("deviceList", deviceList);
        return ajaxResult;
    }

    @Override
    public AjaxResult authOwner(AuthDto authDto) {
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
            Owner owner = ownerFileMapper.selectOwnerById(authDto.getIds()[i]);
            if (null != owner && StringUtils.isNotEmpty(owner.getFaceUrl())) {
                PersonInfo personInfo = new PersonInfo();
                personInfo.setPersonId(String.valueOf(owner.getOwnerId()));
                personInfo.setCertificateNo(owner.getCardNo());
                personInfo.setPersonName(owner.getOwnerName());
                personInfo.setFaceUrl(owner.getFaceUrl());
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
        log.info("taskId:{}", taskId);
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
            return AjaxResult.success("授权中...");
        }
        return AjaxResult.success("已授权");
    }

    /**
     * 导入
     *
     * @param dataList
     * @param communityId
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public String importData(List<OwnerFileImportVo> dataList, Long communityId) {
        if (CollectionUtils.isEmpty(dataList)) {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (OwnerFileImportVo ownerFileImportVo : dataList) {
            try {
                String buildingName = ownerFileImportVo.getBuildingName();
                if (StringUtils.isNotEmpty(buildingName)) {
                    Long buildingId = buildingMapper.getBuildingNameId(buildingName, communityId);
                    if (buildingId != null) {
                        String unitName = ownerFileImportVo.getUnitName();
                        if (StringUtils.isNotEmpty(unitName)) {
                            Long unitId = unitMapper.getUnitNameId(buildingId, unitName, communityId);
                            if (unitId != null) {
                                String roomName = ownerFileImportVo.getRoomName();
                                if (StringUtils.isNotEmpty(roomName)) {
                                    Long roomId = roomMapper.getRoomNameId(communityId, buildingId, unitId, roomName);
                                    if (roomId != null) {
                                        if (StringUtils.isNotEmpty(ownerFileImportVo.getOwnerName())) {
                                            if (StringUtils.isNotEmpty(ownerFileImportVo.getOwnerPhone())) {
                                                if (StringUtils.isNotEmpty(ownerFileImportVo.getCardNo())) {
                                                    if (StringUtils.isNotEmpty(ownerFileImportVo.getCardNo())) {
                                                        if (StringUtils.isNotEmpty(ownerFileImportVo.getOwnerRelation())) {
                                                            if (StringUtils.equals(ownerFileImportVo.getOwnerRelation(), "0")) {
                                                                Owner owner = new Owner();
                                                                owner.setCommunityId(communityId);
                                                                owner.setOwnerPhone(ownerFileImportVo.getOwnerPhone());
                                                                owner.setCardNo(ownerFileImportVo.getCardNo());
                                                                owner.setOwnerName(ownerFileImportVo.getOwnerName());
                                                                Owner ownerInfo = ownerFileMapper.getOwnerInfo(owner);
                                                                if (ownerInfo != null) {
                                                                    Integer relationCount = ownerFileMapper.getByRoomIdOwnerRelationCount(roomId, ownerInfo.getOwnerId());
                                                                    if (relationCount >= 1) {
                                                                        failureNum++;
                                                                        failureMsg.append(failureNum + "、业主" + ownerFileImportVo.getOwnerName() + "与业主关系已存在");
                                                                    }
                                                                }

                                                                //修改房屋信息
                                                                updateRoom(communityId, ownerFileImportVo, buildingId, unitId, roomName, roomId, ownerFileImportVo.getDeliveryTime());


                                                                //根据身份证号和手机号查找业主账号
                                                                Long accountId = accountManageService.getAccountIdByLoginName(ownerFileImportVo.getOwnerPhone());
                                                                if (accountId == null) {
                                                                    //注册账号
                                                                    accountManageService.registerAccount(communityId, ownerFileImportVo.getOwnerPhone(), ownerFileImportVo.getOwnerName());
                                                                }
                                                                //添加业主信息
                                                                this.saveOwner(ownerFileImportVo, communityId, buildingId, unitId, roomId);
                                                                successNum++;
                                                                successMsg.append("<br/>" + successNum + "、业主信息 " + ownerFileImportVo.getOwnerName() + " 导入成功");


                                                            }
                                                        } else {
                                                            failureNum++;
                                                            failureMsg.append(failureNum + "、业主" + ownerFileImportVo.getOwnerName() + "与户主关系不能为空");
                                                        }


                                                    } else {
                                                        failureNum++;
                                                        failureMsg.append(failureNum + "、业主" + ownerFileImportVo.getOwnerName() + "证件号码不能为空");
                                                    }
                                                } else {
                                                    failureNum++;
                                                    failureMsg.append(failureNum + "、业主" + ownerFileImportVo.getOwnerName() + "证件类型不能为空");
                                                }


                                            } else {
                                                failureNum++;
                                                failureMsg.append(failureNum + "、业主" + ownerFileImportVo.getOwnerName() + "手机号不能为空");
                                            }
                                        } else {
                                            failureNum++;
                                            failureMsg.append(failureNum + "、房间号" + roomName + "业主姓名不能为空");
                                        }

                                    } else {
                                        failureNum++;
                                        failureMsg.append(failureNum + "、房间号" + roomName + "不存在，请输入正确的房间号信息");
                                    }
                                } else {
                                    failureNum++;
                                    failureMsg.append(failureNum + "、房间号不能为空，请输入正确的房间号信息");
                                }
                            } else {
                                failureNum++;
                                failureMsg.append(failureNum + "、单元楼" + unitName + "不存在，请输入正确的单元楼信息");
                            }
                        } else {
                            failureNum++;
                            failureMsg.append(failureNum + "、单元楼不能为空，请输入正确的单元楼信息");
                        }
                    } else {
                        failureNum++;
                        failureMsg.append(failureNum + "、楼栋" + buildingName + "不存在，请输入正确的楼栋信息");
                    }
                } else {
                    failureNum++;
                    failureMsg.append(failureNum + "、楼栋不能为空，请输入正确的楼栋信息");
                }
            } catch (Exception e) {
                e.printStackTrace();
                failureNum++;
                String msg = "<br/>" + failureNum + "、业主信息 " + ownerFileImportVo.getOwnerName() + " 导入失败：";
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
     * @param voteScopeId
     * @return
     */
    @Override
    public List<OwnerFileVo> getAllOwnerList(Long communityId, String voteScopeId) {
        return ownerFileMapper.getAllOwnerList(communityId, voteScopeId);
    }


    /**
     * 增加修改业主信息
     *
     * @param ownerFileImportVo
     * @param communityId
     * @param buildingId
     * @param unitId
     * @param roomId
     */
    private String saveOwner(OwnerFileImportVo ownerFileImportVo, Long communityId, Long buildingId, Long unitId, Long roomId) {
        String carportAccountId = "";
        Map<String, Object> map = new HashMap<>();
        Owner owner = new Owner();
        owner.setCommunityId(communityId);
        owner.setOwnerPhone(ownerFileImportVo.getOwnerPhone());
        owner.setCardNo(ownerFileImportVo.getCardNo());
        owner.setOwnerName(ownerFileImportVo.getOwnerName());
        Owner ownerInfo = ownerFileMapper.getOwnerInfo(owner);
        Long accountId = accountManageService.getAccountIdByLoginName(ownerFileImportVo.getOwnerPhone());
        map.put("communityId", communityId);
        map.put("buildingId", buildingId);
        map.put("unitId", unitId);
        map.put("roomId", roomId);
        map.put("ownerRelation", ownerFileImportVo.getOwnerRelation());
        map.put("ownerName", ownerFileImportVo.getOwnerName());
        map.put("ownerPhone", ownerFileImportVo.getOwnerPhone());
        if (StringUtils.isEmpty(ownerFileImportVo.getGender())) {
            ownerFileImportVo.setGender("2");
        }
        map.put("gender", ownerFileImportVo.getGender());
        map.put("cardType", ownerFileImportVo.getCardType());
        map.put("cardNo", ownerFileImportVo.getCardNo());
        map.put("accountId", accountId);
        map.put("dataSource", "0");
        map.put("emergencyContactPhone", ownerFileImportVo.getEmergencyContactPhone());
        map.put("emergencyContact", ownerFileImportVo.getEmergencyContact());
        //本人
        if ("0".equals(ownerFileImportVo.getOwnerRelation())) {
            map.put("parentId", 0);
        } else {
            Long ownerId = busHouseMapper.getByRoomIdOwnerId(roomId);
            map.put("parentId", ownerId);
        }

        map.put("politicsStatus", ownerFileImportVo.getPoliticsStatus());

        if (ownerInfo == null) {
            //新增业主信息
            map.put("auditStatus", "1");
            map.put("auditTime", new Date());
            map.put("by", SecurityUtils.getUsername());
            map.put("delFlag", "0");
            ownerFileMapper.insert(map);
            ownerInfo = ownerFileMapper.getOwnerInfo(owner);
            map.put("ownerId", ownerInfo.getOwnerId());
        } else {
            //更新业主信息
            map.put("ownerId", ownerInfo.getOwnerId());
            map.put("faceUrl", ownerInfo.getFaceUrl());
            map.put("domicileAddress", ownerInfo.getDomicileAddress());
            map.put("nation", ownerInfo.getNation());
            map.put("nativePlace", ownerInfo.getNativePlace());
            map.put("religion", ownerInfo.getReligion());
            map.put("eduLevel", ownerInfo.getEduLevel());
            map.put("military", ownerInfo.getMilitary());
            map.put("marriageStatus", ownerInfo.getMarriageStatus());
            map.put("nationality", ownerInfo.getNationality());
            map.put("population", ownerInfo.getPopulation());
            map.put("career", ownerInfo.getCareer());
            map.put("qqNumber", ownerInfo.getQqNumber());
            map.put("email", ownerInfo.getEmail());
            map.put("ownerId", ownerInfo.getOwnerId());
            map.put("by", SecurityUtils.getUsername());
            ownerFileMapper.update(map);
        }
        if ("0".equals(ownerFileImportVo.getOwnerRelation())) {
            carportAccountId = String.valueOf(accountId);
            //修改房间信息
            ownerFileMapper.updateByRoomIdOwerId(ownerInfo.getOwnerId(), SecurityUtils.getUsername(), roomId);
        }
        BusHouse busHouse = new BusHouse();
        busHouse.setOwnerId(ownerInfo.getOwnerId());
        busHouse.setCommunityId(ownerInfo.getCommunityId());
        busHouse.setBuildingId(buildingId);
        busHouse.setUnitId(unitId);
        busHouse.setRoomId(roomId);
        busHouseMapper.deleteHouse(busHouse);
        ownerFileMapper.insertHouse(map);
        Integer roomIdCount = busHouseMapper.getRoomIdCount(roomId);
        //修改房间人数
        ownerFileMapper.updateByRoomIdLiveNum(roomIdCount, roomId);

        return carportAccountId;
    }

    /**
     * 修改房屋信息
     *
     * @param communityId
     * @param ownerFileImportVo
     * @param buildingId
     * @param unitId
     * @param roomName
     * @param roomId
     */
    private void updateRoom(Long communityId, OwnerFileImportVo ownerFileImportVo, Long buildingId, Long unitId, String roomName, Long roomId, Date deliveryTime) {
        Room room = new Room();
        room.setCommunityId(communityId);
        room.setBuildingId(buildingId);
        room.setUnitId(unitId);
        if (StringUtils.isNotEmpty(ownerFileImportVo.getRoomType())) {
            room.setRoomType(ownerFileImportVo.getRoomType());
        }
        room.setRoomName(roomName);
        if (StringUtils.isNotEmpty(ownerFileImportVo.getHouseAddress())) {
            room.setHouseAddress(ownerFileImportVo.getHouseAddress());
        }
        String floor = roomName.substring(0, roomName.length() - 2);
        room.setFloor(floor);
        room.setUpdateBy(SecurityUtils.getUsername());
        room.setRoomId(roomId);
        if (deliveryTime != null) {
            room.setDeliveryTime(deliveryTime);
        }

        roomMapper.update(room);
    }

    /**
     * @param ownerPhone
     * @return
     */
    @Override
    public OwnerFileVo getOwnerPhoneInfo(String ownerPhone) {
        return ownerFileMapper.getOwnerPhoneInfo(ownerPhone);
    }

}
