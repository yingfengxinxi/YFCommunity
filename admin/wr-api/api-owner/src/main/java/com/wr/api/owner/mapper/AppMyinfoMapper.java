package com.wr.api.owner.mapper;

import com.wr.api.owner.entity.dto.RentFileDTO;
import com.wr.api.owner.entity.myinfo.*;
import com.wr.api.owner.entity.vo.MyinfoDTO;
import com.wr.api.owner.entity.vo.MyinfoVo;
import com.wr.api.owner.entity.vo.TreeSelectVo;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.domain.Carportlog;
import com.wr.remote.domain.CommunityWechat;
import com.wr.remote.estate.manage.contract.Account;
import com.wr.remote.estate.manage.contract.Owner;
import com.wr.remote.govern.party.Party;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: wen chou
 * @Date: 2022-08-22 11:11:50
 * @Desc:
 */
public interface AppMyinfoMapper {
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
     * 查询资产数量信息
     *
     * @param accountId
     * @return
     */
    Integer roomCount(@Param("accountId") Long accountId, @Param("communityId") Long communityId);
    Integer carCount(@Param("accountId") Long accountId, @Param("communityId") Long communityId);
    Integer parkCount(@Param("accountId") Long accountId, @Param("communityId") Long communityId);

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
    Myinfo defaultRoom(Long accountId);

    /**
     * 查询租客房产信息
     *
     * @param accountId 账号id
     * @return com.wr.api.owner.entity.myinfo.Myinfo
     * @author yx
     * @date 2023/1/13 15:32
     */
    Myinfo defaultTenantRoom(Long accountId);

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
    List<TreeSelectVo> getBuildingTree(@Param("communityId") Long communityId, @Param("ownerId") Long ownerId);

    /**
     * 获取单元树
     *
     * @param buildingId
     * @return
     */
    List<TreeSelectVo> getUnitTree(@Param("buildingId") Long buildingId, @Param("ownerId") Long ownerId);

    /**
     * 获取房间树
     *
     * @param unitId
     * @return
     */
    List<TreeSelectVo> getRoomTree(@Param("unitId") Long unitId, @Param("ownerId") Long ownerId);

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
    int insertOwner(Myinfo myinfo);

    /**
     * 修改业主审核
     *
     * @param ownerId
     * @return
     */
    Integer upOwner(@Param("ownerId") Long ownerId);

    /**
     * 新租客信息
     *
     * @param rentFileDTO
     * @return
     */
    int insertRentFile(RentFileDTO rentFileDTO);

    /**
     * 新合同信息
     *
     * @param rentFileDTO
     * @return
     */
    int insertRentFileDetile(RentFileDTO rentFileDTO);

    List<Myinfo> selectCardType();

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
     * @param ownerId
     * @return
     */
    List<RentFile> selectRentFile(RentFile rentFile);

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
     * 合同信息
     *
     * @param accountId
     * @return
     */
    List<Liaison> selectPassCard(Long accountId);

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
     * @param ownerId 业主id
     * @return
     */
    Owner getOwnerDetail(Long ownerId);

    /**
     * 根据ownerId获取业主详情
     *
     * @param ownerId 业主id
     * @return
     */
    Myinfo getOwnerDetailByOwnerId(@Param("ownerId") Long ownerId);

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
     * 获取业主住址
     *
     * @param accountId
     * @return
     */
    Owner getOwnerAddress(Long accountId);

    /**
     * 获取社会救助数量统计
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
     * @param accountId 账号id
     * @param roomId    房间id
     * @return
     */
    Integer updateOwnerRoom(@Param("accountId") Long accountId, @Param("roomId") Long roomId);

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
     * 修改党员信息
     *
     * @param member
     * @return
     */
    Integer updatePolitical(Member member);


    /**
     * 获取楼栋集合
     *
     * @param communityId
     * @return
     */
    List<Map<String, Object>> selectBuildingList(Long communityId);

    /**
     * 获取单元集合
     *
     * @param communityId
     * @return
     */
    List<Map<String, Object>> selectUnitList(@Param("communityId") Long communityId, @Param("buildingId") Long buildingId);

    /**
     * 获取房间集合
     *
     * @param communityId
     * @return
     */
    List<Map<String, Object>> selectRoomList(@Param("communityId") Long communityId, @Param("unitId") Long unitId);

    /**
     * 修改房产信息
     *
     * @param myinfo
     * @return
     */
    Integer updateHouse(Myinfo myinfo);

    /**
     * 获取证件类型集合
     *
     * @return
     */
    List<Map<String, Object>> getCardType();

    /**
     * 查询业主是否存在
     *
     * @param accountId
     * @return
     */
    Integer checkOwner(Long accountId);

    /**
     * 获取业主状态
     *
     * @param accountId
     * @return
     */
    Map<String, Object> getOwnerAuditStatus(Long accountId);

    /**
     * 获取小区
     *
     * @param communityId
     * @return
     */
    List<Map<String, Object>> getCommunityName(Long communityId);

    /**
     * 检查房产是否审核通过
     *
     * @param accountId
     * @param roomId
     * @return
     */
    Integer checkRoomAuditStatus(@Param("accountId") Long accountId, @Param("roomId") Long roomId);

    /**
     * 查询该房间房产是否已经存在
     *
     * @param roomId
     * @return
     */
    Integer checkRoomExist(Long roomId);

    /**
     * 获取车辆类型
     *
     * @return
     */
    List<Map<String, Object>> getCarType();

    /**
     * 添加账号
     *
     * @param account
     * @return: java.lang.Integer
     * @date: 2023/1/4 17:54
     * @author: SJiang
     **/
    Integer inAccount(Account account);

    /**
     * 修改账号类型
     * @param accountId
     * @return: java.lang.Integer
     * @date: 2023/4/25 13:58
     * @author: SJiang
     **/
    Integer upAccountType(@Param("accountId") Long accountId, @Param("updateBy") String updateBy,@Param("accountType") String accountType);

    /**
     * 查看当前房间是否绑定业主
     *
     * @param roomId
     * @return: java.lang.Long
     * @date: 2023/4/12 9:36
     * @author: SJiang
     **/
    Long getOwnerId(@Param("roomId") Long roomId);

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
     * 查看账号是否唯一
     *
     * @param phone
     * @return: com.wr.remote.estate.manage.contract.Account
     * @date: 2023/4/25 13:45
     * @author: SJiang
     **/
    Account checkPhoneUnique(@Param("phone") String phone);

    /**
     * 根据账号id查询业主信息
     *
     * @param accountId 账号id
     * @return com.wr.remote.estate.manage.contract.Owner
     */
    Owner selectOwnerByAccountId(Long accountId);

    /**
     * 根据房间id查询房产
     * @param roomId 房间id
     * @return {@link Integer}
     * @ver v1.0.0
     */
    Integer getHouseByRoomId(Long roomId);

    /**
    * @Author SUNk
    * @Description 更新房屋信息
    * @Date 17:05 2024/2/22
    * @Param [myinfo]
    * @return java.lang.Integer
    **/
    Integer updateRoomById(Myinfo myinfo);

    /**
     * 更新业主相关信息
     *
     * @param myinfo
     * @return
     */
    int updateOwnerById(Myinfo myinfo);
    int updateOwnerOverruledById(Myinfo myinfo);

    /**
    * @Author SUNk
    * @Description 根据业主ID更新房屋信息
    * @Date 19:51 2024/2/26
    * @Param [myinfo]
    * @return int
    **/
    int updateHouseByOwnerId(Myinfo myinfo);

    /**
    * @Author SUNk
    * @Description 根据账号ID修改业主信息
    * @Date 9:34 2024/2/27
    * @Param [owner]
    * @return int
    **/
    int updateOwnerByAccountId(Owner owner);

    /**
    * @Author SUNk
    * @Description 根据账号ID修改账号信息
    * @Date 9:54 2024/2/27
    * @Param [owner]
    * @return int
    **/
    int updateAccountByAccountId(Owner owner);

    /**
    * @Author SUNk
    * @Description 根据账号ID修改游客信息
    * @Date 9:54 2024/2/27
    * @Param [owner]
    * @return int
    **/
    int updateVisitorByAccountId(Owner owner);

    /**
     * @Author SUNk
     * @Description 切换小区
     * @Date 20:28 2024/3/13
     * @Param [owner]
     * @return com.wr.common.core.web.domain.AjaxResult
     **/
    int updateOwnTable(Owner owner);

    /**
    * @Author SUNk
    * @Description 更新accound表
    * @Date 18:13 2024/3/23
    * @Param [myinfo]
    * @return int
    **/
    int updateAccountByMyInfo(Myinfo myinfo);

    /**
    * @Author SUNk
    * @Description 切换小区更新accound表
    * @Date 18:18 2024/3/23
    * @Param [myinfo]
    * @return int
    **/
    int updateAccountByOwner(Owner myinfo);

    /**
     * 根据车牌号查询是否已存在
     *
     * @param vehicle
     * @return
     */
    Integer selectCarByPlateNo(Vehicle vehicle);

    /**
     * 查看是否存在审核中的房产
     *
     * @param roomId
     * @return: java.lang.Long
     * @date: 2023/4/12 9:36
     * @author: SJiang
     **/
    Integer houseExistSubmit(@Param("roomId") Long roomId);
}
