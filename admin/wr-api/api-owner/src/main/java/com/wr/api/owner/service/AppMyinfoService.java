package com.wr.api.owner.service;

import com.wr.api.owner.entity.dto.RentFileDTO;
import com.wr.api.owner.entity.myinfo.*;
import com.wr.api.owner.entity.vo.MyinfoDTO;
import com.wr.api.owner.entity.vo.MyinfoVo;
import com.wr.api.owner.entity.vo.TreeSelectVo;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.domain.Carportlog;
import com.wr.remote.domain.CommunityWechat;
import com.wr.remote.estate.manage.contract.Owner;
import com.wr.remote.govern.party.Party;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: wen chou
 * @Date: 2022-08-22 11:39:49
 * @Desc:
 */
public interface AppMyinfoService {
    /**
     * 修改
     *
     * @param myinfo
     * @return
     */
    int editMyinfo(MyinfoDTO myinfo);

    /**
     * 修改照片
     *
     * @param myinfo
     * @return
     */
    int editPic(Myinfo myinfo);

    /**
     * 根据户主查询家人信息
     *
     * @param ownerId
     * @return
     */
    List<Myinfo> selectRelation(Long ownerId);

    /**
     * 查询资产数量信息
     *
     * @param accountId
     * @return
     */
    Map<String,Integer> assertCount(Long accountId, Long communityId);

    /**
     * 查询房产信息详情
     *
     * @param myinfo
     * @return
     */
    List<Myinfo> roomDetile(Myinfo myinfo);

    /**
     * 查询默认房产信息
     *
     * @param accountId 账户id
     * @return com.wr.api.owner.entity.myinfo.Myinfo
     * @author yx
     * @date 2023/1/13 15:05
     */
    Myinfo defaultRoom(Long accountId, String accountType);


    /**
     * 获取楼栋树
     *
     * @param
     * @return
     */
    List<TreeSelectVo> getCommunityTree(Long communityId);

    /**
     * 获取楼栋树
     *
     * @param communityId
     * @return
     */
    List<TreeSelectVo> getBuildingTree(Long communityId, Long ownerId);

    /**
     * 获取单元树
     *
     * @param buildingId
     * @return
     */
    List<TreeSelectVo> getUnitTree(Long buildingId, Long ownerId);

    /**
     * 获取房间树
     *
     * @param unitId
     * @return
     */
    List<TreeSelectVo> getRoomTree(Long unitId, Long ownerId);

    /**
     * 新增房屋审核
     *
     * @param myinfo
     * @return
     */
    int insertJudge(Myinfo myinfo);

    /**
     * 新业主审核
     *
     * @param myinfo
     * @return
     */
    AjaxResult insertOwner(Myinfo myinfo);

    /**
     * 租房信息
     *
     * @param rent
     * @return
     */
    List<Rent> selectRent(Rent rent);

    /**
     * 出租合同
     *
     * @param
     * @return
     */
    List<RentFile> selectRentFile(RentFile rentFile);

    /**
     * @param rentFileDTO
     * @desc: 添加租房合同
     * @return: com.wr.common.core.web.domain.AjaxResult
     * @date: 2023/1/4 14:58
     * @author: SJiang
     **/
    AjaxResult insertRentFile(RentFileDTO rentFileDTO);

    List<Myinfo> selectCardType();

    /**
     * 出租合同
     *
     * @param ownerId
     * @return
     */
    RentFile selectRentFile2(RentFile rentFile);

    /**
     * 业主信息
     *
     * @param ownerId
     * @return
     */
    Myinfo selectOwnerInfo(Long ownerId);

    /**
     * 租户信息
     *
     * @param tenantId
     * @return
     */
    Tenant selectRenterInfo(Long tenantId);

    /**
     * 合同信息
     *
     * @param contractId
     * @return
     */
    RentFile selectFileDetile(RentFile rentFile);

    /**
     * 通行证
     *
     * @param accountId
     * @return
     */
    List<Liaison> selectPassCard(Long accountId);

    /**
     * 根据房号跟名字查询业主是否存在
     *
     * @param roomId
     * @return
     */
    Myinfo selectInfoByRoomId(@Param("roomId") Long roomId, @Param("ownerName") String ownerName);

    /**
     * 新增通行证
     *
     * @param liaison
     * @return
     */
    int insertPassCard(Liaison liaison);

    /**
     * 新增党员
     *
     * @param member
     * @return
     */
    int insertPolitical(Member member);

    /**
     * 查询党员信息
     *
     * @param accountId
     * @return
     */
    Member selectPolitical(Member member);

    /**
     * 查询车辆信息
     *
     * @param vehicle
     * @return
     */
    List<Vehicle> selectCar(Vehicle vehicle);

    /**
     * 查询车位信息
     *
     * @param carport
     * @return
     */
    List<Carport> selectCarport(Carport carport);

    /**
     * 新增车辆信息
     *
     * @param vehicle
     * @return
     */
    int insertvehicle(Vehicle vehicle);

    /**
     * 根据条件获取车位列表信息
     *
     * @param carport
     * @return
     */
    List<Carport> selectCarportByS(Carport carport);

    /**
     * 根据条件获取业主车辆列表信息
     *
     * @param vehicle
     * @return
     */
    List<Vehicle> selectCarByAccount(Vehicle vehicle);

    /**
     * 插入购车记录
     *
     * @param carportlog
     * @return
     */
    int insertCartrentLog(Carportlog carportlog);

    /**
     * 查询微信配置
     *
     * @param wechatId 主键id
     * @return com.wr.common.payment.domain.CommunityWechat
     */
    CommunityWechat selectWechatById(Long wechatId);

    /**
     * 修改车位信息表
     *
     * @param carport
     * @return
     */
    int editCarport(Carport carport);

    /**
     * 获取业主详情
     *
     * @param ownerId
     * @return
     */
    Owner getOwnerDetail(Long ownerId);

    /**
     * 获取accountId获取业主详情
     *
     * @param ownerId 业主id
     * @return
     */
    Myinfo getOwnerDetailByOwnerId(Long ownerId);

    /**
     * 成为志愿者
     *
     * @param accountId 业主id
     * @return
     */
    int beVolunteer(@Param("accountId") Long accountId, @Param("volunteer") String volunteer);

    /**
     * 查询党组织信息
     *
     * @param communityId
     * @return
     */
    List<Party> getPartyInfo(Long communityId);

    /**
     * 获取业主
     *
     * @param accountId
     * @return
     */
    Owner getOwnerAddress(Long accountId);

    /**
     * 获取社会救助数量
     *
     * @param communityId
     * @return
     */
    List<String> getRescueCount(Long communityId);

    /**
     * 添加租房浏览数
     *
     * @param rentId
     * @return
     */
    Integer addRentNum(Long rentId);

    /**
     * 修改业主默认房屋
     *
     * @param accountId
     * @param roomId
     * @return
     */
    Integer updateOwnerRoom(Long accountId, Long roomId);


    int editCarApply(Vehicle vehicle);

    /**
     * 查询车牌颜色
     *
     * @return
     */
    List<Vehicle> getPassColor();

    /**
     * 获取车辆颜色
     *
     * @return
     */
    List<Vehicle> getCarColor();

    /**
     * 修改党员
     *
     * @param member
     * @return
     */
    Integer updatePolitical(Member member);

    /**
     * 获取楼栋 单元 房间 集合
     *
     * @param myinfo
     * @return
     */
    Map<String, List<Map<String, Object>>> selectAddressList(Myinfo myinfo);

    /**
     * 修改房产信息
     *
     * @param myinfo
     * @return
     */
    Integer updateHouse(Myinfo myinfo);

    /**
     * 获取身份证类型
     *
     * @return
     */
    List<Map<String, Object>> getCardType();

    /**
     * 获取业主状态
     *
     * @param accountId
     * @return
     */
    Map<String, Object> getOwnerAuditStatus(Long accountId);

    /**
     * 检查房产是否审核通过
     *
     * @param accountId
     * @param roomId
     * @return
     */
    Boolean checkRoomAuditStatus(Long accountId, Long roomId);

    /**
     * 查询该房间房产是否已经存在
     *
     * @param roomId
     * @return
     */
    Boolean checkRoomExist(Long roomId);

    List<Map<String, Object>> getCarType();

    /**
     * 人脸登记照片修改
     *
     * @param myinfo
     * @return
     */
    int updateFaceUrl(Myinfo myinfo);

    /**
     * 查询业主装修工
     *
     * @param ownerId
     * @return
     */
    List<WorkVo> getWorkList(Long ownerId);

    /**
    * @Author SUNk
    * @Description 注册业主用户
    * @Date 17:27 2024/1/31
    * @Param [dto]
    * @return boolean
    **/
    AjaxResult registeredUser(MyinfoDTO dto);

    /**
    * @Author SUNk
    * @Description 根据账号ID修改个人信息
    * @Date 9:34 2024/2/27
    * @Param [owner]
    * @return int
    **/
    int updateUserMessageByAccountId(Owner owner);

    /**
    * @Author SUNk
    * @Description 切换小区
    * @Date 20:28 2024/3/13
    * @Param [owner]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    AjaxResult updateOwnTable(Owner owner);
}
