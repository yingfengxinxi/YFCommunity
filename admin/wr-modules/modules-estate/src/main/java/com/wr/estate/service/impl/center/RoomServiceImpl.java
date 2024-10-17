package com.wr.estate.service.impl.center;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.DesensitizationUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.BusHouse;
import com.wr.estate.entity.BusOwner;
import com.wr.estate.entity.vo.BusVehicleImportVo;
import com.wr.estate.entity.vo.RoomImportVo;
import com.wr.estate.mapper.center.BuildingMapper;
import com.wr.estate.mapper.center.RoomMapper;
import com.wr.estate.mapper.center.UnitMapper;
import com.wr.estate.mapper.center.audit.BusHouseMapper;
import com.wr.estate.mapper.center.audit.BusOwnerMapper;
import com.wr.estate.mapper.center.carManage.BusCarportMapper;
import com.wr.estate.mapper.center.carManage.BusVehicleMapper;
import com.wr.estate.mapper.center.personnelfilemanage.OwnerFileMapper;
import com.wr.estate.mapper.center.useraccountmanage.AccountManageMapper;
import com.wr.estate.service.center.RoomService;
import com.wr.estate.service.center.useraccountmanage.AccountManageService;
import com.wr.remote.estate.center.Building;
import com.wr.remote.estate.center.BusCarport;
import com.wr.remote.estate.center.Room;
import com.wr.remote.estate.center.Unit;
import com.wr.remote.estate.center.vo.*;
import com.wr.remote.estate.manage.contract.Account;
import com.wr.remote.estate.manage.contract.Owner;
import com.wr.remote.estate.vote.BusVoteScope;
import io.jsonwebtoken.lang.Collections;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @Author: RainCity
 * @Date: 2022-10-11 10:13:08
 * @Desc:
 */
@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private UnitMapper unitMapper;

    @Autowired
    private BuildingMapper buildingMapper;

    @Autowired
    private BusOwnerMapper busOwnerMapper;

    @Autowired
    private AccountManageService accountManageService;

    @Autowired
    private OwnerFileMapper ownerFileMapper;

    @Autowired
    private BusHouseMapper busHouseMapper;

    @Autowired
    private BusCarportMapper busCarportMapper;

    @Autowired
    private BusVehicleMapper busVehicleMapper;

    /**
     * 根据当前登录人所在物业查询小区
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.remote.domain.Community>
     */
    @Override
    public List<RoomVO> listRooms(RoomQueryVO query) {

        List<RoomVO> roomVOList = roomMapper.listRooms(query);



        return roomVOList;
    }

    @Override
    public List<Room> getByBuildingIdRoomList(Long buildingId) {
        return roomMapper.getByBuildingIdRoomList(buildingId);
    }

    @Override
    public List<Room> getByBuildingIdNullRoomList(Long buildingId) {
        return roomMapper.getByBuildingIdNullRoomList(buildingId);
    }

    /**
     * @param query
     * @return
     */
    @Override
    public List<BusVoteScope> getVoteListRooms(RoomQueryVO query) {
        return roomMapper.getVoteListRooms(query);
    }

    /**
     * 批量新增单元和房间
     *
     * @param insertVO 新增参数
     * @author yx
     * @date 2022/10/12 16:07
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUnitAndRoomBatch(UnitAndRoomInsertVO insertVO) {
        List<Integer> excludeUnitNums = insertVO.getExcludeUnitNums();
        List<Unit> units = new ArrayList<>();
        Building building = buildingMapper.getDetailByBuildingId(insertVO.getBuildingId());

        // 查询楼栋所有单元
        List<Unit> unitList = unitMapper.listByBuildingId(insertVO.getBuildingId());
        List<String> unitNameList = unitList.stream().map(Unit::getUnitName).collect(Collectors.toList());
        // 组装单元批量数据
        // 生成批量数据
        for (int i = insertVO.getStartUnitNum(); i <= insertVO.getEndUnitNum(); i++) {
            // 排除楼宇
            if (!CollectionUtils.isEmpty(excludeUnitNums) && excludeUnitNums.contains(i)) {
                continue;
            }
            String unitName = i + "单元";
            if (!CollectionUtils.isEmpty(unitNameList) && unitNameList.contains(unitName)) {
                continue;
            }
            Unit unit = new Unit();
            unit.setCommunityId(building.getCommunityId());
            unit.setBuildingId(insertVO.getBuildingId());
            unit.setUnitName(unitName);
            unit.setCreateBy(SecurityUtils.getUsername());
            unit.setUpdateBy(SecurityUtils.getUsername());
            units.add(unit);
        }
        // 批量新增单元
        if (!units.isEmpty()) {
            unitMapper.insertBatch(units);
        }
        /// 组装房屋批量数据
        // 有楼层，并且每层房间数>0,并且输入楼层大于当前楼栋最高楼层才添加房间
        if ((insertVO.getFloorsNum() != null && insertVO.getFloorsNum() > 0) && (insertVO.getRoomNum() != null && insertVO.getRoomNum() > 0)) {
            List<Room> rooms = new ArrayList<>();
            for (int i = 1; i <= insertVO.getFloorsNum(); i++) {
                for (Unit unit : units) {
                    Integer unitNumber = Integer.valueOf(unit.getUnitName().substring(0, unit.getUnitName().length() - 2));
                    int roomStartNumber = unitNumber * insertVO.getRoomNum();

                    for (int j = roomStartNumber - insertVO.getRoomNum() + 1; j <= roomStartNumber; j++) {
                        String roomNumber = i + String.format("%02d", j);
                        Room room = new Room();
                        room.setCommunityId(building.getCommunityId());
                        room.setBuildingId(insertVO.getBuildingId());
                        room.setUnitId(unit.getUnitId());
                        room.setRoomName(roomNumber);
                        String roomName = room.getRoomName();
                        String floor = roomName.substring(0, roomName.length() - 2);
                        room.setFloor(floor);
                        room.setCreateBy(SecurityUtils.getUsername());
                        room.setUpdateBy(SecurityUtils.getUsername());
                        rooms.add(room);
                    }
                }
            }
            if (!rooms.isEmpty()) {
                roomMapper.insertBatch(rooms);
            }
        }
    }

    /**
     * 批量新增房间
     *
     * @param insertVO 房间批量参数
     * @author yx
     * @date 2022/10/13 11:56
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertRoomBatch(RoomInsertVO insertVO) {
        Unit unit = unitMapper.getDetailById(insertVO.getUnitId());
        // 获取单元所有房间
        List<Room> unitRooms = roomMapper.listByUnitId(insertVO.getUnitId());
        if (!Collections.isEmpty(unitRooms)) {
            throw new ServiceException("单元下存在房屋，禁止批量添加！");
        }

        List<Room> rooms = new ArrayList<>();

        Integer unitNumber = Integer.valueOf(unit.getUnitName().substring(0, unit.getUnitName().length() - 2));
        int roomStartNumber = unitNumber * insertVO.getRoomNum();

        for (int i = 1; i <= insertVO.getFloorsNum(); i++) {
            for (int j = roomStartNumber - insertVO.getRoomNum() + 1; j <= roomStartNumber; j++) {
                String roomNumber = i + String.format("%02d", j);
                Room room = new Room();
                room.setCommunityId(unit.getCommunityId());
                room.setBuildingId(unit.getBuildingId());
                room.setUnitId(insertVO.getUnitId());
                room.setRoomName(roomNumber);
                String roomName = room.getRoomName();
                String floor = roomName.substring(0, roomName.length() - 2);
                room.setFloor(floor);
                room.setCreateBy(SecurityUtils.getUsername());
                room.setUpdateBy(SecurityUtils.getUsername());
                rooms.add(room);
            }
        }
        if (!rooms.isEmpty()) {
            roomMapper.insertBatch(rooms);
        }
    }

    /**
     * 根据id修改单元信息
     *
     * @param updateVO 修改信息
     * @author yx
     * @date 2022/10/13 14:02
     */
    @Override
    public void updateUnitById(UnitUpdateVO updateVO) {
        updateVO.setUpdateBy(SecurityUtils.getUsername());
        unitMapper.updateUnitById(updateVO);
    }

    /**
     * 删除单元
     *
     * @param unitId 单元id
     * @author yx
     * @date 2022/10/13 14:40
     */
    @Override
    public void deleteUnit(Long unitId) {
        List<Room> rooms = roomMapper.listByUnitId(unitId);
        if (!Collections.isEmpty(rooms)) {
            throw new ServiceException("删除失败，单元下存在房屋！");
        }
        unitMapper.deleteByUnitId(unitId);
    }

    /**
     * 根据条件查询所有房号
     *
     * @param query 查询条件
     * @return java.util.List<java.lang.String>
     * @author yx
     * @date 2022/10/13 19:46
     */
    @Override
    public List<String> listRoomNamesByCondition(RoomQueryVO query) {
        return roomMapper.listRoomNamesByCondition(query);
    }

    /**
     * 根据小区id查询单元列表
     *
     * @param communityId 小区id
     * @return java.util.List<com.wr.estate.entity.vo.UnitVO>
     * @author yx
     * @date 2022/10/13 20:33
     */
    @Override
    public List<UnitVO> listUnitsByCommunityId(Long communityId) {
        return unitMapper.listByCommunityId(communityId);
    }

    /**
     * 批量更新房屋
     *
     * @param updateVO 批量数据
     * @author yx
     * @date 2022/10/13 21:11
     */
    @Override
    public void updateRoomBatch(RoomUpdateBatchVO updateVO) {
        updateVO.setUpdateBy(SecurityUtils.getUsername());
        roomMapper.updateRoomBatch(updateVO);
    }

    @Override
    public void updateByRoomIdRoomStatus(Long roomId, String roomStatus) {
        roomMapper.updateByRoomIdRoomStatus(roomId, roomStatus);
    }

    /**
     * 修改房屋
     *
     * @param updateVO 修改数据
     * @author yx
     * @date 2022/10/14 10:25
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateRoom(RoomUpdateVO updateVO) {
        //修改房屋信息
        updateVO.setUpdateBy(SecurityUtils.getUsername());
        roomMapper.updateRoom(updateVO);
        AtomicReference<String> carportAccountId = new AtomicReference<>("");
        //业主信息
        List<OwnerVo> ownerVoList = updateVO.getOwnerList();
        if (CollectionUtils.isNotEmpty(ownerVoList)) {
            ownerVoList = ownerVoList.stream().sorted(Comparator.comparing(ownerVo -> ownerVo.getOwnerRelation())).collect(Collectors.toList());
            ownerVoList.stream().forEach(ownerVo -> {
                if (ownerVo != null) {
                    if (StringUtils.isNotEmpty(updateVO.getLicenseUrl())) {
                        ownerVo.setLicenseUrl(updateVO.getLicenseUrl());
                    }

                    String ownerName = ownerVo.getOwnerName();
                    if (StringUtils.isEmpty(ownerName)) {
                        throw new ServiceException("业主名称不能为空");
                    }
                    String ownerPhone = ownerVo.getOwnerPhone();
                    if (StringUtils.isEmpty(ownerPhone)) {
                        throw new ServiceException("业主手机号不能为空");
                    }
                    String cardNo = ownerVo.getCardNo();
                    if (StringUtils.isEmpty(cardNo)) {
                        throw new ServiceException("身份证号不能为空");
                    }
                    //根据身份证号和手机号查找业主账号
                    Long accountId = accountManageService.getAccountIdByLoginName(ownerPhone);
                    if (accountId == null) {
                        //注册账号
                        accountManageService.registerAccount(updateVO.getCommunityId(), ownerPhone, ownerName);
                    } else {
                        Owner owner = new Owner();
                        owner.setOwnerPhone(ownerVo.getOwnerPhone());
                        owner.setCardNo(ownerVo.getCardNo());
                        owner.setOwnerName(ownerVo.getOwnerName());
                        Owner ownerInfo = ownerFileMapper.getOwnerInfo(owner);
                        if (ownerInfo != null) {
                            if (!StringUtils.equals(ownerInfo.getOwnerId() + "", updateVO.getOwnerId() + "")) {
                                throw new ServiceException("手机号" + ownerVo.getOwnerPhone() + "已存在");
                            }
                        }
                        accountManageService.updateAccountName("", ownerName, ownerName, accountId);
                    }
                    //添加业主信息
                    RoomDetailVO detailById = roomMapper.getByRoomIdBuildingUnit(updateVO.getRoomId());
                    saveOwner(ownerVo, updateVO.getReligion(), updateVO.getCommunityId(), detailById.getBuildingId(), detailById.getUnitId(), detailById.getRoomId());
                }
            });
        }

        //车位/车辆信息
        List<SpaceVehicleVo> spaceVehicleVoList = updateVO.getSpaceVehicleList();
        if (CollectionUtils.isNotEmpty(spaceVehicleVoList)) {
            spaceVehicleVoList.stream().forEach(spaceVehicleVo -> {
                if (spaceVehicleVo != null) {
                    //车辆信息
                    RoomDetailVO detailById = roomMapper.getByRoomIdBuildingUnit(updateVO.getRoomId());
                    String ownerId = detailById.getOwnerId();
                    Owner owner = new Owner();
                    owner.setOwnerId(Long.valueOf(ownerId));
                    Owner ownerInfo = ownerFileMapper.getOwnerInfo(owner);
                    Long vehicleId = getaVehicle(updateVO.getCommunityId(), spaceVehicleVo, ownerInfo.getAccountId());
                    if (vehicleId != null) {
                        //车位信息
                        getCarport(carportAccountId, vehicleId, updateVO.getCommunityId(), spaceVehicleVo);
                    }


                }
            });
        }
    }

    private Long getaVehicle(Long communityId, SpaceVehicleVo spaceVehicleVo, Long accountId) {
        //车辆信息
        busVehicleMapper.deleteByAccountId(accountId, communityId);
        if (StringUtils.isNotEmpty(spaceVehicleVo.getPlateNo())) {
            BusVehicleVo busVehicleVo = new BusVehicleVo();
            busVehicleVo.setCommunityId(communityId);
            busVehicleVo.setAuditStatus("1");
            busVehicleVo.setAccountId(accountId);
            busVehicleVo.setPlateNo(spaceVehicleVo.getPlateNo());
            busVehicleVo.setPlateColor(Integer.valueOf(spaceVehicleVo.getPlateColor()));
            busVehicleVo.setVehicleBrand(spaceVehicleVo.getVehicleBrand());
            busVehicleVo.setAuditTime(DateUtils.getTime());
            busVehicleVo.setVehicleType(spaceVehicleVo.getVehicleType());
            busVehicleVo.setVehicleStatus("0");
            busVehicleVo.setVehicleColor(spaceVehicleVo.getPlateColor());
            busVehicleVo.setCreateBy(SecurityUtils.getUsername());
            busVehicleMapper.saveCar(busVehicleVo);
            Long vehicleId = busVehicleMapper.getVehicleId(busVehicleVo.getAccountId(), busVehicleVo.getCommunityId(), busVehicleVo.getPlateNo());
            return vehicleId;
        }
        return null;
    }

    /**
     * 新增/保存车位信息
     *
     * @param communityId
     * @param spaceVehicleVo
     */
    private void getCarport(AtomicReference<String> carportAccountId, Long vehicleId, Long communityId, SpaceVehicleVo spaceVehicleVo) {
        if (StringUtils.isNotEmpty(spaceVehicleVo.getCarportNo())) {
            BusCarportVo carportVo = new BusCarportVo();
            carportVo.setCommunityId(communityId);
            carportVo.setCarportNo(spaceVehicleVo.getCarportNo());
            carportVo.setCarportNature(spaceVehicleVo.getCarportNature());
            carportVo.setRentalType(spaceVehicleVo.getRentalType());
            carportVo.setCarportFee(spaceVehicleVo.getCarportFee());
            carportVo.setRentalStart(spaceVehicleVo.getRentalStart());
            carportVo.setRentalEnd(spaceVehicleVo.getRentalStart());

            BusCarport carportInfo = busCarportMapper.getByCarportNoCarportInfo(spaceVehicleVo.getCarportNo(), communityId);

            if (carportInfo != null) {
                //修改
                carportVo.setCarportId(carportInfo.getCarportId());
                carportVo.setUpdateBy(SecurityUtils.getUsername());
                busCarportMapper.update(carportVo);
            } else {
                // 新增
                carportVo.setCreateBy(SecurityUtils.getUsername());
                busCarportMapper.save(carportVo);
            }

            busCarportMapper.updateByCarportNoAccount(Long.valueOf(carportAccountId.get()), vehicleId, carportVo.getCarportNo(), carportVo.getCommunityId());
        }
    }

    /**
     * 增加修改业主信息
     *
     * @param ownerVo
     * @param religion
     * @param communityId
     * @param buildingId
     * @param unitId
     * @param roomId
     */
    private String saveOwner(OwnerVo ownerVo, String religion, Long communityId, Long buildingId, Long unitId, Long roomId) {
        String carportAccountId = "";
        Map<String, Object> map = new HashMap<>();
        Owner owner = new Owner();
        // owner.setCommunityId(communityId);
        owner.setOwnerPhone(ownerVo.getOwnerPhone());
        //owner.setCardNo(ownerVo.getCardNo());
        //owner.setOwnerName(ownerVo.getOwnerName());
        Owner ownerInfo = ownerFileMapper.getOwnerInfo(owner);
        if (ownerInfo != null) {
            String cardNo = ownerInfo.getCardNo();
            if (!StringUtils.equals(cardNo, ownerVo.getCardNo())) {

                throw new RuntimeException("当前手机号已存在另一个业主名下,请勿重复添加");
            }
        }

        Long accountId = accountManageService.getAccountIdByLoginName(ownerVo.getOwnerPhone());
        map.put("communityId", communityId);
        map.put("buildingId", buildingId);
        map.put("unitId", unitId);
        map.put("roomId", roomId);
        map.put("licenseUrl", ownerVo.getLicenseUrl());
        map.put("ownerRelation", ownerVo.getOwnerRelation());
        map.put("ownerName", ownerVo.getOwnerName());
        map.put("ownerPhone", ownerVo.getOwnerPhone());
        map.put("gender", ownerVo.getGender());
        map.put("cardType", ownerVo.getCardType());
        map.put("cardNo", ownerVo.getCardNo());
        map.put("cardFont", ownerVo.getCardFont());
        map.put("cardBack", ownerVo.getCardBack());
        map.put("accountId", accountId);
        map.put("dataSource", "0");
        map.put("religion", religion);
        map.put("emergencyContactPhone", ownerVo.getEmergencyContactPhone());
        map.put("emergencyContact", ownerVo.getEmergencyContact());
        //本人
        if ("0".equals(ownerVo.getOwnerRelation())) {
            map.put("parentId", 0);
        } else {
            Long ownerId = busHouseMapper.getByRoomIdOwnerId(roomId);
            map.put("parentId", ownerId);
        }

        map.put("politicsStatus", ownerVo.getPoliticsStatus());
        map.put("selfIntro", ownerVo.getSelfIntro());
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
            map.put("religion", religion);
            map.put("by", SecurityUtils.getUsername());
            ownerFileMapper.update(map);
        }
        if ("0".equals(ownerVo.getOwnerRelation())) {
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
     * 根据id批量删除房屋
     *
     * @param ids 房屋ids
     * @author yx
     * @date 2022/10/13 21:40
     */
    @Override
    public void deleteRoomBatch(List<Long> ids) {
        if (!CollectionUtils.isEmpty(ids)) {
            Integer count = roomMapper.hasLicense(ids);
            if (count > 0) {
                throw new ServiceException("删除失败，存在产权人房屋，请先解绑后再删除！");
            }

            roomMapper.deleteRoomBatch(ids);
        }
    }

    /**
     * 根据id查询房屋详情
     *
     * @param id id
     * @return com.wr.estate.entity.vo.RoomDetailVO
     * @author yx
     * @date 2022/10/14 10:11
     */
    @Override
    public RoomDetailVO getDetailById(Long id, Long communityId) {
        RoomDetailVO roomDetail = roomMapper.getDetailById(id, null);
        Integer maxFloor = roomMapper.maxFloorByBuildingId(roomDetail.getBuildingId());
        roomDetail.setTotalFloor(maxFloor);
        List<OwnerVo> ownerList = ownerFileMapper.getByRoomIdOwnerInfo(id);
        OwnerVo ov = new OwnerVo();
        for (OwnerVo ownerVo : ownerList) {
            if (ownerVo != null) {
                if (StringUtils.equals("0", ownerVo.getOwnerRelation())) {
                    ov = ownerVo;
                    String licenseUrl = ownerList.get(0).getLicenseUrl();
                    if (StringUtils.isNotEmpty(licenseUrl)) {
                        roomDetail.setLicenseUrl(licenseUrl);
                    }

                }
            }
        }
        roomDetail.setOwnerList(ownerList);
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

    /**
     * 房屋管理图形数据查询返回
     *
     * @param query 查询条件
     * @return java.util.List<com.wr.estate.entity.vo.RoomDetailVO>
     * @author yx
     * @date 2022/10/14 14:44
     */
    @Override
    public GraphVO roomGraphData(RoomQueryVO query) {
        // 查询所有房号
        List<String> roomNumbers = roomMapper.listRoomNamesByBuildId(query);
        GraphVO graphVO = new GraphVO();
        // 查询所有房间
        List<RoomDetailVO> roomDetailList = roomMapper.roomOtherDetailByBuildingId(query);
        Map<Integer, List<RoomDetailVO>> floorMapList = roomDetailList.stream().collect(Collectors.groupingBy(RoomDetailVO::getCurrentFloor));

        List<Integer> floors = roomMapper.listFloorsByBuildingId(query);

        List<BuildingGraphVO> graphVOList = new ArrayList<>();
        for (Integer floor : floors) {
            BuildingGraphVO buildingGraphVO = new BuildingGraphVO();

            buildingGraphVO.setFloor(floor);
            // 获取楼层所有房间
            List<RoomDetailVO> roomDetailVOS = floorMapList.get(floor);
            Map<String, RoomDetailVO> detailVOMap = roomDetailVOS.stream().collect(Collectors.toMap(RoomDetailVO::getRoomNumber, t -> t, (v1, v2) -> v1));
            List<RoomDetailVO> detailVOList = new ArrayList<>();
            for (String roomNumber : roomNumbers) {
                boolean contains = detailVOMap.containsKey(roomNumber);
                if (contains) {
                    detailVOList.add(detailVOMap.get(roomNumber));
                    continue;
                }
                detailVOList.add(null);
            }
            buildingGraphVO.setRoomDetailList(detailVOList);
            graphVOList.add(buildingGraphVO);
        }

        // 统计入住
        Map<Integer, Long> roomStatusTotal = roomDetailList.stream().collect(Collectors.groupingBy(RoomDetailVO::getRoomStatus, Collectors.counting()));

        // 房屋状态统计
        graphVO.setRoomNumbers(roomNumbers);
        graphVOList = graphVOList.stream().sorted(Comparator.comparing(BuildingGraphVO::getFloor).reversed()).collect(Collectors.toList());
        graphVO.setBuildingGraphList(graphVOList);
        graphVO.setRoomStatusStatistics(roomStatusTotal);
        return graphVO;
    }

    /**
     * 过户查询业主信息
     *
     * @param record 查询条件
     * @return java.util.List<com.wr.estate.entity.vo.RoomSearchOwnerVO>
     * @author yx
     * @date 2022/10/15 13:49
     */
    @Override
    public List<RoomSearchOwnerVO> roomSearchListPage(BusOwner record) {
        return busOwnerMapper.roomSearchListPage(record);
    }

    /**
     * 过户
     *
     * @param roomId  房间id
     * @param ownerId 新业主id
     * @author yx
     * @date 2022/10/17 9:40
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transferOwnership(Long roomId, Long ownerId, String licenseUrl) {
        roomMapper.transferOwnership(roomId, ownerId, licenseUrl);
        int count = roomMapper.transferHouse(roomId, ownerId, licenseUrl);
/*
        if (count == 0) {
            throw new ServiceException("房产信息异常，过户失败！");
        }
*/
    }

    /**
     * 查询业主信息以及家庭成员
     *
     * @param roomId 房屋id
     * @return java.util.List<com.wr.estate.entity.vo.RoomSearchOwnerVO>
     * @author yx
     * @date 2022/10/17 17:23
     */
    @Override
    public List<RoomSearchOwnerVO> roomFamilyList(Long roomId) {
        return roomMapper.roomFamilyList(roomId);
    }

    /**
     * 收房
     *
     * @param roomId
     */
    @Override
    public void confirmRoom(Long roomId) {
        RoomUpdateVO updateVO = new RoomUpdateVO();
        updateVO.setUpdateBy(SecurityUtils.getUsername());
        updateVO.setRoomId(roomId);
        updateVO.setRoomStatus("4");
        roomMapper.updateRoom(updateVO);
    }

    @Override
    public Unit getUnitDetailById(Long unitId) {
        return unitMapper.getDetailById(unitId);
    }

    /**
     * @param unitId
     * @return
     */
    @Override
    public UnitInfoVo getByUnitIdDetail(Long unitId) {
        return unitMapper.getByUnitIdDetail(unitId);
    }

    @Override
    public String importRoom(List<RoomImportVo> userList, Long communityId) {
        if (CollectionUtils.isEmpty(userList)) {
            throw new ServiceException("导入房屋数据不能为空！");
        }
        String username = SecurityUtils.getUsername();
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (RoomImportVo roomImportVo : userList) {

            try {
                String buildingName = roomImportVo.getBuildingName();
                if (StringUtils.isNotEmpty(buildingName)) {
                    Long buildingId = buildingMapper.getBuildingNameId(buildingName, communityId);
                    if (buildingId == null) {
                        //新增
                        Building building = new Building();
                        building.setCommunityId(communityId);
                        building.setBuildingName(buildingName);
                        building.setCreateBy(SecurityUtils.getUsername());
                        building.setUpdateBy(SecurityUtils.getUsername());
                        buildingMapper.insert(building);
                        buildingId = buildingMapper.getBuildingNameId(buildingName, communityId);
                    }
                    String unitName = roomImportVo.getUnitName();
                    if (StringUtils.isNotEmpty(unitName)) {
                        Long unitId = unitMapper.getUnitNameId(buildingId, unitName, communityId);
                        if (unitId == null) {
                            Unit unit = new Unit();
                            unit.setCommunityId(communityId);
                            unit.setBuildingId(buildingId);
                            unit.setUnitName(unitName);
                            unit.setCreateBy(SecurityUtils.getUsername());
                            unit.setUpdateBy(SecurityUtils.getUsername());
                            unitMapper.insert(unit);
                            unitId = unitMapper.getUnitNameId(buildingId, unitName, communityId);
                        }
                        String roomName = roomImportVo.getRoomName();
                        if (StringUtils.isNotEmpty(roomName)) {
                            Double totalArea = roomImportVo.getTotalArea();
                            if (totalArea != null && totalArea > 0) {
                                Long roomId = roomMapper.getRoomNameId(communityId, buildingId, unitId, roomName);
                                Room room = new Room();
                                room.setCommunityId(communityId);
                                room.setBuildingId(buildingId);
                                room.setUnitId(unitId);
                                room.setRoomType(roomImportVo.getRoomType());
                                room.setRoomName(roomName);
                                room.setHouseAddress(roomImportVo.getHouseAddress());
                                room.setTotalArea(roomImportVo.getTotalArea());
                                String floor = roomName.substring(0, roomName.length() - 2);
                                room.setFloor(floor);
                                room.setUpdateBy(SecurityUtils.getUsername());
                                if (roomId == null) {
                                    room.setCreateBy(SecurityUtils.getUsername());
                                    roomMapper.insert(room);
                                } else {
                                    room.setRoomId(roomId);
                                    roomMapper.update(room);
                                }
                                successNum++;
                                successMsg.append("<br/>" + successNum + "、房号 " + room.getRoomName() + " 导入成功");
                            } else {
                                failureNum++;
                                failureMsg.append("<br/>" + failureNum + "、" + roomName + "房屋面积不能为空");
                            }

                        } else {
                            failureNum++;
                            failureMsg.append("<br/>" + failureNum + "、房间号不能为空，请输入正确的房间号信息");
                        }
                    } else {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、单元楼不能为空，请输入正确的单元楼信息");
                    }
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、楼栋不能为空，请输入正确的楼栋信息");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、房屋信息 " + roomImportVo.getRoomName() + " 导入失败：";
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
    public BigDecimal getByRoomIdArea(Long roomId) {
        return roomMapper.getByRoomIdArea(roomId);
    }

    /**
     * @param roomId
     * @return
     */
    @Override
    public String getByRoomIdRoomStatus(Long roomId) {
        return roomMapper.getByRoomIdRoomStatus(roomId);
    }

    @Override
    public Long getRoomIdOwnerId(Long roomId) {
        return roomMapper.getRoomIdOwnerId(roomId);
    }

    @Override
    public String getByRoomIdDeliveryTime(Long roomId) {
        return roomMapper.getByRoomIdDeliveryTime(roomId);
    }

    /**
     *
     * @param ownerId
     * @return
     */
    @Override
    public Long getByOwnerIdRoomId(Long ownerId) {
        return roomMapper.getByOwnerIdRoomId(ownerId);
    }

}
