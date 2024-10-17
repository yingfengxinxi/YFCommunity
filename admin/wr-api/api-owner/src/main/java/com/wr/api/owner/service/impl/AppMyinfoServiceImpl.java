package com.wr.api.owner.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.wr.api.owner.entity.dto.RentFileDTO;
import com.wr.api.owner.entity.myinfo.*;
import com.wr.api.owner.entity.vo.MyinfoDTO;
import com.wr.api.owner.entity.vo.MyinfoVo;
import com.wr.api.owner.entity.vo.TreeSelectVo;
import com.wr.api.owner.mapper.AppMyinfoMapper;
import com.wr.api.owner.mapper.CommunitysMapper;
import com.wr.api.owner.service.AppMyinfoService;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.ContractUtil;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.util.HttpClientUtil;
import com.wr.common.customize.util.JsonUtil;
import com.wr.common.payment.constants.PayConstants;
import com.wr.common.payment.util.SignUtil;
import com.wr.common.payment.util.WechatPayUtil;
import com.wr.common.redis.service.RedisService;
import com.wr.common.security.utils.DictUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.Carportlog;
import com.wr.remote.domain.Community;
import com.wr.remote.domain.CommunityWechat;
import com.wr.remote.estate.manage.contract.Account;
import com.wr.remote.estate.manage.contract.Owner;
import com.wr.remote.govern.party.Party;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;


/**
 * @Author: wen chou
 * @Date: 2022-08-22 11:40:31
 * @Desc:
 */
@Service
@Transactional
public class AppMyinfoServiceImpl implements AppMyinfoService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private AppMyinfoMapper appMyinfoMapper;
    @Resource
    private CommunitysMapper communitysMapper;

    @Autowired
    private RedisService redisService;

    @Override
    @Transactional
    public int editMyinfo(MyinfoDTO myinfo) {
        return appMyinfoMapper.editMyinfo(myinfo);
    }

    @Override
    public int editPic(Myinfo myinfo) {
        return appMyinfoMapper.editPic(myinfo);
    }

    @Override
    public List<Myinfo> selectRelation(Long ownerId) {
        return appMyinfoMapper.selectRelation(ownerId);
    }

    @Override
    public Map<String,Integer> assertCount(Long accountId, Long communityId) {
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put( "roomCount",appMyinfoMapper.roomCount(accountId, communityId) );
        hashMap.put( "carCount",appMyinfoMapper.carCount(accountId, communityId) );
        hashMap.put( "parkCount",appMyinfoMapper.parkCount(accountId, communityId) );
        return hashMap;
    }

    @Override
    public List<Myinfo> roomDetile(Myinfo myinfo) {
        return appMyinfoMapper.roomDetile(myinfo);
    }

    /**
     * 查询默认房产信息
     *
     * @param accountId 账户id
     * @return com.wr.api.owner.entity.myinfo.Myinfo
     * @author yx
     * @date 2023/1/13 15:05
     */
    @Override
    public Myinfo defaultRoom(Long accountId, String accountType) {
        // 租客
        if ("1".equals(accountType)) {
            return appMyinfoMapper.defaultTenantRoom(accountId);
        }
        // 业主
        if ("0".equals(accountType)) {
            return appMyinfoMapper.defaultRoom(accountId);
        }
        return null;
    }

    @Override
    public List<TreeSelectVo> getCommunityTree(Long communityId) {
        return appMyinfoMapper.getCommunityTree(communityId);
    }

    @Override
    public List<TreeSelectVo> getBuildingTree(Long communityId, Long ownerId) {
        return appMyinfoMapper.getBuildingTree(communityId, ownerId);
    }

    @Override
    public List<TreeSelectVo> getUnitTree(Long buildingId, Long ownerId) {
        return appMyinfoMapper.getUnitTree(buildingId, ownerId);
    }

    @Override
    public List<TreeSelectVo> getRoomTree(Long unitId, Long ownerId) {
        return appMyinfoMapper.getRoomTree(unitId, ownerId);
    }


    /*public List<TreeSelectVo> getTreeSelects(Long communityId) {
        //小区
        List<TreeSelectVo> communityTree = appMyinfoMapper.getCommunityTree(communityId);
        communityTree.forEach(c -> {
            c.setFlag("0");
            //楼栋
            List<TreeSelectVo> buildingTree = appMyinfoMapper.getBuildingTree(c.getId());
            c.setChildren(buildingTree);
            buildingTree.forEach(b -> {
                b.setFlag("1");
                //单元
                List<TreeSelectVo> unitTree = appMyinfoMapper.getUnitTree(b.getId());
                b.setChildren(unitTree);
                unitTree.forEach(u -> {
                    u.setFlag("2");
                    List<TreeSelectVo> roomTree = appMyinfoMapper.getRoomTree(u.getId());
                    u.setChildren(roomTree);
                    roomTree.forEach(r -> {
                        r.setFlag("3");
                    });
                });
            });
        });
        return communityTree;
    }*/

    @Override
    public int insertJudge(Myinfo myinfo) {
        myinfo.setAuditStatus("0");
        return appMyinfoMapper.insertJudge(myinfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AjaxResult insertOwner(Myinfo myinfo) {
        if(StringUtils.equals( String.valueOf( myinfo.getAccountId() ),myinfo.getOwnerPhone() )){
            throw new ServiceException("当前登录账号与业主审核手机号不一致，请检查后再进行提交！");
        }

        Long accountId = myinfo.getAccountId();
        //根据账号查询业主信息
        Owner owner = appMyinfoMapper.selectOwnerByAccountId(accountId);

        Long roomId = myinfo.getRoomId();
        int ownerResult = -1;

        // 查看当前房间是否绑定业主（产权人）
        Long ownerId = appMyinfoMapper.getOwnerId(roomId);

        // 判断当前房间是否存在审核中状态，有的话不允许提交
        Integer houseExistSubmit = appMyinfoMapper.houseExistSubmit(roomId);
        if(houseExistSubmit > 0){
            throw new ServiceException("当前房间正在审核中，如需绑定此房间请联系物业处理！");
        }

        //之前有申请过业主信息
        if (null != owner && owner.getOwnerId() != null && "0".equals(owner.getAuditStatus())) {
            return AjaxResult.error("业主信息审核中，请等待审核完成后再进行操作");
        }

        //如果房产是我的 添加到房产
        if ("0".equals(myinfo.getOwnerRelation())) {
            if(owner != null && owner.getOwnerId() != null){
                myinfo.setOwnerRelation( owner.getOwnerRelation() );
            }

            if (null != ownerId && 0 != ownerId) {
                return AjaxResult.error("当前房间已存在产权人！");
            }

            //查询bus_house中已经审核通过的房间
            Integer count = appMyinfoMapper.getHouseByRoomId(roomId);
            if(null != count && count > 0){
                return AjaxResult.error("当前房间已存在产权人！");
            }

            // 添加业主
            if(owner == null || owner.getOwnerId() == null){
                appMyinfoMapper.insertOwner(myinfo);
            }else if("2".equals(owner.getAuditStatus())){
                //如果已驳回状态应该进行业主更新
                myinfo.setAuditStatus( "0" );
                myinfo.setOwnerId( owner.getOwnerId() );
                ownerResult = appMyinfoMapper.updateOwnerOverruledById( myinfo );
                judgmentUpdate(ownerResult,"业主信息添加");
            }

            myinfo.setAuditStatus("0");
            Long roomOwnerId = appMyinfoMapper.getOwnerId(myinfo.getRoomId());
            myinfo.setParentId((ObjectUtils.isEmpty(roomOwnerId) ? 0L : roomOwnerId));
            // 添加房产
            if (owner == null || owner.getOwnerId() == null || !"2".equals(myinfo.getHouseAuditStatus())) {
                if(owner != null && owner.getOwnerId() != null){
                    myinfo.setOwnerId( owner.getOwnerId() );
                }
                ownerResult = appMyinfoMapper.insertJudge(myinfo);
                judgmentUpdate(ownerResult, "房产信息添加");
            } else {
                ownerResult = appMyinfoMapper.updateHouseByOwnerId(myinfo);
                judgmentUpdate(ownerResult, "房产信息更新");
            }

            //更新房屋信息
            ownerResult = appMyinfoMapper.updateRoomById( myinfo );
            judgmentUpdate(ownerResult,"房屋信息更新");

            appMyinfoMapper.updateAccountByMyInfo( myinfo );
            return AjaxResult.success();
        }

        if (null == ownerId || 0 == ownerId) {
            return AjaxResult.error("添加失败，当前房间不存在产权人！");
        }

        if(ownerId.equals( owner.getOwnerId() )){
            return AjaxResult.error("添加失败，不允许选择自己房产！");
        }

        // 绑定户主
        myinfo.setParentId(Long.valueOf( ownerId ));
        // 添加业主
        if(owner == null || owner.getOwnerId() == null){
            appMyinfoMapper.insertOwner(myinfo);
        }else {
            //如果本次认证非业主并且本身已有非业主的认证则不允许再次提交认证
            if(!StringUtils.equals( "0",owner.getOwnerRelation()) && "1".equals(owner.getAuditStatus()) ){
                throw new ServiceException("该业主已绑定相关业主信息，不允许再次绑定！");
            }
            myinfo.setAuditStatus("0");
            ownerResult = appMyinfoMapper.updateOwnerOverruledById( myinfo );
        }

        //更新注册账号信息
        appMyinfoMapper.updateAccountByMyInfo( myinfo );
        return AjaxResult.success();
    }

    private void judgmentUpdate(int flag,String msg){
        if(flag <= 0){
            throw new ServiceException(msg + "失败，请稍后重试");
        }
    }

    @Override
    public List<Rent> selectRent(Rent rent) {
        return appMyinfoMapper.selectRent(rent);
    }

    @Override
    public List<RentFile> selectRentFile(RentFile rentFile) {
        return appMyinfoMapper.selectRentFile(rentFile);
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult insertRentFile(RentFileDTO rentFileDTO) {
        // 获取业主详情
        Myinfo ownerInfo = appMyinfoMapper.selectOwnerInfo(rentFileDTO.getOwnerId());
        if (com.wr.common.core.utils.StringUtils.isNull(ownerInfo)) {
            return AjaxResult.error("添加失败，未获取到业主信息！");
        }
        // 获取小区详情
        Long communityId = rentFileDTO.getCommunityId();
        Community communityById = communitysMapper.getCommunityById(communityId);
        if (com.wr.common.core.utils.StringUtils.isNull(communityById)) {
            return AjaxResult.error("系统错误，请重试！");
        }
        // 业主手机号
        String ownerPhone = ownerInfo.getOwnerPhone();
        // 租客手机号
        String tenantPhone = rentFileDTO.getTenantPhone();
        // 租客姓名
        String tenantName = rentFileDTO.getTenantName();
        // 校验租客账号是否存在（查看租客是否是访客）
        Account unique = appMyinfoMapper.checkPhoneUnique(tenantPhone);
        Account account = new Account();
        rentFileDTO.setCreateBy(ownerPhone);
        if (ObjectUtils.allNull(unique)) {
            account.setCommunityId(communityId);
            account.setLoginName(tenantPhone);
            account.setPassword(SecurityUtils.encryptPassword("1234abc"));
            account.setAvatar(rentFileDTO.getFaceUrl());
            // 账号类型（租客）
            account.setAccountType("1");
            account.setAccountName(tenantName);
            account.setAccountNick(tenantName);
            // 数据来源（手机注册）
            account.setDataSource("1");
            // 是否是志愿者（不是）
            account.setVolunteer("0");
            account.setCreateBy(ownerPhone);
            account.setUpdateBy(ownerPhone);
            // 添加账号
            appMyinfoMapper.inAccount(account);
            // 账号id
            rentFileDTO.setAccountId(account.getAccountId());
        } else {
            Long accountId = unique.getAccountId();
            // 账号id
            rentFileDTO.setAccountId(accountId);
            // 修改账号类型
            appMyinfoMapper.upAccountType(accountId, ownerPhone, "1");
        }
        // 添加租客审核
        appMyinfoMapper.insertRentFile(rentFileDTO);
        // 生成租房合同编号
        rentFileDTO.setContractNo(ContractUtil.contractNoCreate(communityById.getCommunityName()));
        // 添加租房合同
        log.info("rentFileDTO:{}", rentFileDTO);
        int num = appMyinfoMapper.insertRentFileDetile(rentFileDTO);
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    @Override
    public List<Myinfo> selectCardType() {
        return appMyinfoMapper.selectCardType();
    }

    @Override
    public RentFile selectRentFile2(RentFile rentFile) {
        return appMyinfoMapper.selectRentFile2(rentFile);
    }

    @Override
    public Myinfo selectOwnerInfo(Long ownerId) {
        return appMyinfoMapper.selectOwnerInfo(ownerId);
    }

    @Override
    public Tenant selectRenterInfo(Long tenantId) {
        return appMyinfoMapper.selectRenterInfo(tenantId);
    }

    @Override
    public RentFile selectFileDetile(RentFile rentFile) {
        return appMyinfoMapper.selectFileDetile(rentFile);
    }

    @Override
    public List<Liaison> selectPassCard(Long accountId) {
        return appMyinfoMapper.selectPassCard(accountId);
    }

    @Override
    public Myinfo selectInfoByRoomId(Long roomId, String ownerName) {
        return appMyinfoMapper.selectInfoByRoomId(roomId, ownerName);
    }

    @Override
    public int insertPassCard(Liaison liaison) {
        return appMyinfoMapper.insertPassCard(liaison);
    }

    @Override
    public int insertPolitical(Member member) {
        //审核中
        member.setAuditStatus("0");
        return appMyinfoMapper.insertPolitical(member);
    }

    @Override
    public Member selectPolitical(Member member) {
        return appMyinfoMapper.selectPolitical(member);
    }

    @Override
    public List<Vehicle> selectCar(Vehicle vehicle) {
        return appMyinfoMapper.selectCar(vehicle);
    }

    @Override
    public List<Carport> selectCarport(Carport carport) {
        List<Carport> carportList = appMyinfoMapper.selectCarport(carport);
        for (Carport carports : carportList) {
            if ("1".equals(carports.getRentalType())) {
                getRentalTime(carports, "年");
            } else if ("2".equals(carports.getRentalType())) {
                getRentalTime(carports, "月");
            } else {
                carports.setRentalTime("永久");
            }
        }
        return carportList;
    }

    /**
     * 获取租售时长
     *
     * @param carport
     * @param yearOrMonth
     */
    private void getRentalTime(Carport carport, String yearOrMonth) {
        if (carport.getRentalEnd() == null || carport.getRentalStart() == null) {
            carport.setRentalTime("/");
            return;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(carport.getRentalEnd());
        int endYear = cal.get(Calendar.YEAR);
        cal.setTime(carport.getRentalStart());
        int beginYear = cal.get(Calendar.YEAR);
        carport.setRentalTime(endYear - beginYear < 1 ? "未满一" + yearOrMonth : endYear - beginYear + yearOrMonth);
    }

    @Override
    @Transactional
    public int insertvehicle(Vehicle vehicle) {
        //根据车牌号查询，如果已有则不允许新增
        Integer integer = appMyinfoMapper.selectCarByPlateNo( vehicle );
        if(integer > 0){
            throw new ServiceException("车牌号"+vehicle.getPlateNo()+"已存在，不允许重复添加！");
        }
        return appMyinfoMapper.insertvehicle(vehicle);
    }

    @Override
    public List<Carport> selectCarportByS(Carport carport) {
        return appMyinfoMapper.selectCarportByS(carport);
    }

    @Override
    public List<Vehicle> selectCarByAccount(Vehicle vehicle) {
        return appMyinfoMapper.selectCarByAccount(vehicle);
    }

    @Override
    public int insertCartrentLog(Carportlog carportlog) {
        return appMyinfoMapper.insertCartrentLog(carportlog);
    }

    @Override
    public CommunityWechat selectWechatById(Long wechatId) {
        return appMyinfoMapper.selectWechatById(wechatId);
    }

    @Override
    public int editCarport(Carport carport) {
        return appMyinfoMapper.editCarport(carport);
    }

    @Override
    public Owner getOwnerDetail(Long ownerId) {
        return appMyinfoMapper.getOwnerDetail(ownerId);
    }

    @Override
    public Myinfo getOwnerDetailByOwnerId(Long ownerId) {

        return appMyinfoMapper.getOwnerDetailByOwnerId(ownerId);
    }

    @Override
    public int beVolunteer(Long accountId, String volunteer) {
        return appMyinfoMapper.beVolunteer(accountId, volunteer);
    }

    @Override
    public List<Party> getPartyInfo(Long communityId) {
        return appMyinfoMapper.getPartyInfo(communityId);
    }

    @Override
    public Owner getOwnerAddress(Long accountId) {
        return appMyinfoMapper.getOwnerAddress(accountId);
    }

    @Override
    public List<String> getRescueCount(Long communityId) {
        return appMyinfoMapper.getRescueCount(communityId);
    }

    @Override
    public Integer addRentNum(Long rentId) {
        return appMyinfoMapper.addRentNum(rentId);
    }

    @Override
    public Integer updateOwnerRoom(Long accountId, Long roomId) {
        return appMyinfoMapper.updateOwnerRoom(accountId, roomId);
    }

    @Override
    public int editCarApply(Vehicle vehicle) {
        return appMyinfoMapper.editCarApply(vehicle);
    }

    /**
     * 查询车牌颜色
     *
     * @return
     */
    @Override
    public List<Vehicle> getPassColor() {
        return appMyinfoMapper.getPassColor();
    }

    /**
     * 获取车辆颜色
     *
     * @return
     */
    @Override
    public List<Vehicle> getCarColor() {
        return appMyinfoMapper.getCarColor();
    }

    @Override
    public Integer updatePolitical(Member member) {
        member.setAuditStatus("0");
        return appMyinfoMapper.updatePolitical(member);
    }

    @Override
    public Map<String, List<Map<String, Object>>> selectAddressList(Myinfo myinfo) {
        Map<String, List<Map<String, Object>>> result = new HashMap<>();
        result.put("buildingList", appMyinfoMapper.selectBuildingList(myinfo.getCommunityId()));
        if (myinfo.getBuildingId() != null) {
            result.put("unitList", appMyinfoMapper.selectUnitList(myinfo.getCommunityId(), myinfo.getBuildingId()));
        }
        if (myinfo.getUnitId() != null) {
            result.put("roomList", appMyinfoMapper.selectRoomList(myinfo.getCommunityId(), myinfo.getUnitId()));
        }
        result.put("communityName", appMyinfoMapper.getCommunityName(myinfo.getCommunityId()));
        return result;
    }

    @Override
    public Integer updateHouse(Myinfo myinfo) {
        myinfo.setAuditStatus("0");
        return appMyinfoMapper.updateHouse(myinfo);
    }

    @Override
    public List<Map<String, Object>> getCardType() {
        return appMyinfoMapper.getCardType();
    }

    @Override
    public Map<String, Object> getOwnerAuditStatus(Long accountId) {
        Map<String, Object> ownerAuditStatus = appMyinfoMapper.getOwnerAuditStatus(accountId);
        if (ownerAuditStatus == null) {
            ownerAuditStatus = new HashMap<>();
            ownerAuditStatus.put("auditStatus", "-1");
        }
        return ownerAuditStatus;
    }

    @Override
    public Boolean checkRoomAuditStatus(Long accountId, Long roomId) {
        Integer result = appMyinfoMapper.checkRoomAuditStatus(accountId, roomId);
        if (result == null || result == 0) {
            return false;
        }
        return true;
    }


    @Override
    public Boolean checkRoomExist(Long roomId) {
        Integer result = appMyinfoMapper.checkRoomExist(roomId);
        if (result == null || result == 0) {
            return false;
        }
        return true;
    }

    @Override
    public List<Map<String, Object>> getCarType() {
        return appMyinfoMapper.getCarType();
    }

    /**
     * 人脸登记照片修改
     *
     * @param myinfo
     * @return
     */
    @Override
    public int updateFaceUrl(Myinfo myinfo) {
        return appMyinfoMapper.updateFaceUrl(myinfo);
    }

    /**
     * 查询业主装修工
     *
     * @param ownerId
     * @return
     */
    @Override
    public List<WorkVo> getWorkList(Long ownerId) {
        return appMyinfoMapper.getWorkList(ownerId);
    }

    @Transactional(readOnly = false)
    public boolean insertPropertyLog(HttpServletRequest request, JSONObject attach) {
        String payWay = attach.getString(PayConstants.PAY_WAY);
        log.info("异步通知==>支付方式:{}", payWay);
        //微信返回的请求体
        String body = HttpClientUtil.getRequestBody(request);
        log.info("返回的请求体:{}", body);
        if (StringUtils.isEmpty(body)) {
            log.error("微信支付回调请求体为null");
            return false;
        }
        //redis中的附加数据
        Long communityId = attach.getLong(PayConstants.COMMUNITY_ID);
        String userName = attach.getString("userName");
        String userPhone = attach.getString("userPhone");
        String cardNo = attach.getString("cardNo");
        String plantNo = attach.getString("plantNo");
        Date rentalStart = attach.getDate("rentalStart");
        Date rentalEnd = attach.getDate("rentalEnd");
        Long accountId = attach.getLong("accountId");
        Long vehicleId = attach.getLong("vehicleId");
        Long carportId = attach.getLong("carportId");
        Long wechatId = attach.getLong(PayConstants.WECHAT_ID);
        //通过证书序列号查询
        CommunityWechat wechat = communitysMapper.selectWechatById(wechatId);
        if (!SignUtil.verifiedSign(request, body, wechat.getMchId(), wechat.getSerialNumber(), wechat.getPrivateUrl(), wechat.getApiKey())) {
            log.error("验签失败====>");
            log.error("mchId:{},serialNumber:{},ApiV3Key:{}", wechat.getMchId(), wechat.getSerialNumber(), wechat.getApiKey());
            return false;
        }

        JSONObject bodyJo = JsonUtil.parseObject(body);
        if (null == bodyJo) {
            log.error("微信支付回调请求体为空");
            return false;
        }
        String resourceType = bodyJo.getString("resource_type");
        String eventType = bodyJo.getString("event_type");
        if (!PayConstants.RESOURCE_TYPE.equals(resourceType) || !PayConstants.EVENT_TYPE.equals(eventType)) {
            log.error("通知类型：event_type={}，通知数据类型：resource_type：{}", eventType, resourceType);
            return false;
        }
        JSONObject encrypt = bodyJo.getJSONObject("resource");
        JSONObject resource = WechatPayUtil.decodeWechatData(encrypt, wechat.getApiKey());
        if (null == resource) {
            log.warn("微信回调成功，数据未返回，源数据encrypt：{}", encrypt);
            return false;
        }
        String tradeState = resource.getString("trade_state");
        if (!"SUCCESS".equals(tradeState)) {
            log.warn("微信回调成功，交易状态={}，{}", tradeState, resource.getString("trade_state_desc"));
            return false;
        }
        //订单号
        String outTradeNo = resource.getString("out_trade_no");
        //微信支付订单号
        String transactionId = resource.getString("transaction_id");
        //金额
        JSONObject amount = resource.getJSONObject("amount");
        int payerTotal = amount.getInteger("payer_total");
        BigDecimal payAmount = new BigDecimal(payerTotal).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
        // 封装数据
        Carportlog cl = new Carportlog();
        cl.setCarportId(carportId);
        cl.setUserName(userName);
        cl.setUserPhone(userPhone);
        cl.setCardNo(cardNo);
        cl.setPlantNo(plantNo);
        cl.setRentalStart(rentalStart);
        cl.setRentalEnd(rentalEnd);
        Carport carport = new Carport();
        carport.setCommunityId(communityId);
        carport.setRentalStart(rentalStart);
        carport.setRentalEnd(rentalEnd);
        carport.setAccountId(accountId);
        carport.setVehicleId(vehicleId);
        // 记录车位费缴费记录
        int result = appMyinfoMapper.insertCartrentLog(cl);
        if (result > 0) {
            appMyinfoMapper.editCarport(carport);
        }
        return result > 0;
    }

    @Override
    @Transactional
    public AjaxResult registeredUser(MyinfoDTO dto) {
        //保存用户信息
        Account unique = appMyinfoMapper.checkPhoneUnique(dto.getOwnerPhone());
        if(ObjectUtils.allNotNull(unique)){
            return AjaxResult.error("用户已注册，请进行登录");
        }

        Account account = new Account();
        account.setCommunityId(dto.getCommunityId());
        account.setLoginName(dto.getOwnerPhone());
        account.setPassword(SecurityUtils.encryptPassword("1234abc"));
        account.setAvatar(dto.getAvatar());
        // 账号类型（租客）
        account.setAccountType("1");
        account.setAccountName(dto.getOwnerName());
        account.setAccountNick(dto.getOwnerName());
        // 数据来源（手机注册）
        account.setDataSource("1");
        // 是否是志愿者（不是）
        account.setVolunteer("0");
        account.setCreateBy(dto.getOwnerPhone());
        account.setUpdateBy(dto.getOwnerPhone());
        String avatar = redisService.getCacheObject(CacheConstants.SYS_CONFIG_KEY + "ownerAvatarMain");
        account.setAvatar(avatar);
        Integer integer = appMyinfoMapper.inAccount( account );

        //保存业主信息
        Myinfo myinfo = new Myinfo();
        myinfo.setCommunityId( dto.getCommunityId() );
        myinfo.setAccountId( account.getAccountId() );
        myinfo.setOwnerRelation( "0" );
        myinfo.setOwnerName( dto.getOwnerName() );
        myinfo.setOwnerPhone( dto.getOwnerPhone() );
        myinfo.setCardType( dto.getCardType() );
        myinfo.setCardNo( dto.getCardNo() );
        myinfo.setCreateBy( dto.getOwnerPhone() );
        // 添加业主
        integer = appMyinfoMapper.insertOwner(myinfo);
        return AjaxResult.success(integer>0?"注册成功":"注册失败");
    }

    @Override
    @Transactional
    public int updateUserMessageByAccountId(Owner owner) {
        appMyinfoMapper.updateOwnerByAccountId( owner );
        int account = appMyinfoMapper.updateAccountByAccountId( owner );
        if(account < 1){
            throw new ServiceException("修改账号信息失败，请稍后重试！");
        }
        account = appMyinfoMapper.updateVisitorByAccountId( owner );
        if(account < 1){
            throw new ServiceException("修改游客信息失败，请稍后重试！");
        }
        return account;
    }

    @Override
    public AjaxResult updateOwnTable(Owner owner) {
        int table = appMyinfoMapper.updateOwnTable( owner );
        table = appMyinfoMapper.updateAccountByOwner( owner );
        return AjaxResult.success(table>0?"小区切换成功":"小区切换失败");
    }
}
