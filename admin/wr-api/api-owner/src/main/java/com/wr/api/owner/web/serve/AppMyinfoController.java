package com.wr.api.owner.web.serve;

import cn.hutool.core.lang.Assert;
import com.wr.api.owner.entity.dto.RentFileDTO;
import com.wr.api.owner.entity.myinfo.*;
import com.wr.api.owner.entity.vo.MyinfoDTO;
import com.wr.api.owner.entity.vo.TreeSelectVo;
import com.wr.api.owner.service.AppMyinfoService;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.domain.Carportlog;
import com.wr.remote.estate.manage.contract.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Wen Chou
 * @Date: 2022-11-11 10:38:47
 * @Desc:
 */
@RestController
@RequestMapping(value = AppMyinfoController.MODEL_NAME)
public class AppMyinfoController extends BaseController {

    protected static final String MODEL_NAME = "/v2/myinfo";


    @Autowired
    private AppMyinfoService appMyinfoService;

    /**
     * 查询业主详情
     *
     * @param ownerId 业主id
     * @return
     */
    @GetMapping("/ownerDetail")
    public AjaxResult ownerDetail(Long ownerId) {
        return AjaxResult.success(appMyinfoService.getOwnerDetail(ownerId));
    }

    /**
     * 查询业主装修工
     *
     * @param ownerId 业主id
     * @return
     */
    @GetMapping("/workList/{ownerId}")
    public AjaxResult getWorkList(@PathVariable Long ownerId) {
        List<WorkVo> workVoList = appMyinfoService.getWorkList(ownerId);
        return AjaxResult.success(workVoList);
    }

    /**
     * 修改个人信息
     *
     * @param myinfo 实体
     * @return 修改结果
     */
    @PostMapping("/update")
    public AjaxResult update(@RequestBody MyinfoDTO myinfo) {
        int editMyinfo = appMyinfoService.editMyinfo( myinfo );
        return AjaxResult.success(editMyinfo>0?"修改成功":"修改失败，请重新填写修改内容");
    }

    /**
     * 修改照片
     *
     * @param myinfo 实体
     * @return 修改结果
     */
    @PostMapping("/editPic")
    public AjaxResult editPic(@RequestBody Myinfo myinfo) {
        appMyinfoService.editPic(myinfo);
        return AjaxResult.success("修改成功");
    }

    /**
     * 查询资产数量
     *
     * @param accountId 实体
     * @return 修改结果
     */
    @GetMapping("/assertCount")
    public AjaxResult assertCount(Long accountId, Long communityId) {
        return AjaxResult.success(appMyinfoService.assertCount(accountId, communityId));
    }

    ;

    /**
     * 根据 ownerId 查询业主详情
     *
     * @param ownerId 业主id
     * @return
     */
    @GetMapping("/ownerDetailByAccountId")
    public AjaxResult ownerDetailByAccountId(Long ownerId) {
        return AjaxResult.success(appMyinfoService.getOwnerDetailByOwnerId(ownerId));
    }

    /**
     * 成为志愿者
     *
     * @param volunteer 实体
     * @return 修改结果
     */
    @GetMapping("/beVolunteer")
    public AjaxResult beVolunteer(Long accountId, String volunteer) {
        appMyinfoService.beVolunteer(accountId, volunteer);
        return AjaxResult.success("提交成功");
    }


    /**
     * 查询家人信息
     *
     * @param ownerId 实体
     * @return 修改结果
     */
    @GetMapping("/selectRelation")
    public AjaxResult selectRelation(Long ownerId) {
        return AjaxResult.success(appMyinfoService.selectRelation(ownerId));
    }

    ;

    /**
     * 查询房屋详细信息
     *
     * @param myinfo 实体
     * @return 修改结果
     */
    @GetMapping("/roomDetile")
    public AjaxResult roomDetile(Myinfo myinfo) {
        return AjaxResult.success(appMyinfoService.roomDetile(myinfo));
    }

    ;

    /**
     * 查询默认房产信息
     *
     * @param accountId 账号id
     * @return com.wr.common.core.web.domain.AjaxResult
     * @author yx
     * @date 2023/1/13 15:07
     */
    @GetMapping("/defaultRoom")
    public AjaxResult defaultRoom(Long accountId, String accountType) {
        return AjaxResult.success(appMyinfoService.defaultRoom(accountId, accountType));
    }

    /**
     * 获取楼栋 单元 房间 集合
     *
     * @param myinfo
     * @return
     */
    @GetMapping("/addressList")
    public AjaxResult addressList(Myinfo myinfo) {
        return AjaxResult.success(appMyinfoService.selectAddressList(myinfo));
    }

    /**
     * 获取车辆颜色
     *
     * @param
     * @return 修改结果
     */
    @GetMapping("/getCarColor")
    public AjaxResult getCarColor() {
        return AjaxResult.success(appMyinfoService.getCarColor());
    }

    ;

    /**
     * 查询证件类型
     *
     * @param
     * @return 修改结果
     */
    @GetMapping("/selectCardType")
    public AjaxResult selectCardType() {
        return AjaxResult.success(appMyinfoService.selectCardType());
    }

    ;

    /**
     * 查询车牌颜色
     *
     * @param
     * @return 修改结果
     */
    @GetMapping("/getPassColor")
    public AjaxResult getPassColor() {
        return AjaxResult.success(appMyinfoService.getPassColor());
    }

    /**
     * 修改车辆申请
     *
     * @param vehicle 实体
     * @return 修改结果
     */
    @PostMapping("/editCarApply")
    public AjaxResult editCarApply(@RequestBody Vehicle vehicle) {
        return AjaxResult.success(appMyinfoService.editCarApply(vehicle));
    }

    /**
     * @param rentFileDTO
     * @desc: 添加租房合同
     * @return: com.wr.common.core.web.domain.AjaxResult
     * @date: 2023/1/4 15:24
     * @author: SJiang
     **/
    @PostMapping("/insertRentFile")
    public AjaxResult insertRentFile(@RequestBody RentFileDTO rentFileDTO) {
        return appMyinfoService.insertRentFile(rentFileDTO);
    }

    /**
     * 更改业主默认房产
     *
     * @param accountId 账号id
     * @param roomId    房间id
     * @return
     */
    @GetMapping("/updateOwnerRoom")
    public AjaxResult updateOwnerRoom(Long accountId, Long roomId) {
        Boolean result = appMyinfoService.checkRoomAuditStatus(accountId, roomId);
        if (!result) {
            return AjaxResult.error("房产审核未通过!");
        }
        return toAjax(appMyinfoService.updateOwnerRoom(accountId, roomId));
    }

    /**
     * 修改房产信息
     *
     * @param myinfo
     * @return
     */
    @PostMapping("/updateHouse")
    public AjaxResult updateHouse(@RequestBody Myinfo myinfo) {
        return toAjax(appMyinfoService.updateHouse(myinfo));
    }

    /**
     * 小区树
     *
     * @param
     * @return
     */
    @GetMapping("/tree")
    public AjaxResult getTreeSelects(Long communityId, Long ownerId) {
        //小区
        List<TreeSelectVo> communityTree = appMyinfoService.getCommunityTree(communityId);
        communityTree.forEach(c -> {
            c.setFlag("0");
            //楼栋
            List<TreeSelectVo> buildingTree = appMyinfoService.getBuildingTree(c.getId(), ownerId);
            c.setChildren(buildingTree);
            buildingTree.forEach(b -> {
                b.setFlag("1");
                //单元
                List<TreeSelectVo> unitTree = appMyinfoService.getUnitTree(b.getId(), ownerId);
                b.setChildren(unitTree);
                unitTree.forEach(u -> {
                    u.setFlag("2");
                    List<TreeSelectVo> roomTree = appMyinfoService.getRoomTree(u.getId(), ownerId);
                    u.setChildren(roomTree);
                    roomTree.forEach(r -> {
                        r.setFlag("3");
                    });
                });
            });
        });
        return AjaxResult.success(communityTree);
    }

    /**
     * 新增房屋审核
     *
     * @param myinfo 实体
     * @return 修改结果
     */
    @PostMapping("/insertJudge")
    public AjaxResult addjudge(@RequestBody Myinfo myinfo) {
        Assert.notNull(myinfo.getRoomId(), "房间号不能为空");
        Boolean result = appMyinfoService.checkRoomExist(myinfo.getRoomId());
        if (result) {
            return AjaxResult.error("该房间已被注册!");
        }
        return toAjax(appMyinfoService.insertJudge(myinfo));
    }


    /**
     * 获取业主审核状态
     *
     * @param accountId
     * @return
     */
    @GetMapping("/getOwnerAuditStatus")
    public AjaxResult getOwnerAuditStatus(Long accountId) {
        Map<String, Object> resultMap = appMyinfoService.getOwnerAuditStatus(accountId);
        return AjaxResult.success(resultMap);
    }


    /**
     * 业主认证
     *
     * @param myinfo 实体
     * @return 修改结果
     */
    @PostMapping("/insertOwner")
    public AjaxResult insertOwner(@RequestBody Myinfo myinfo) {
        Assert.notNull(myinfo, "业主信息必须填写");
        Assert.notNull(myinfo.getAccountId(), "账号id不可为空");
        return appMyinfoService.insertOwner(myinfo);
    }

    /**
     * 租房信息
     *
     * @param
     * @return
     */
    @GetMapping("/selectRent")
    public AjaxResult selectRent(Rent rent) {
        return AjaxResult.success(appMyinfoService.selectRent(rent));
    }

    /**
     * 添加租房浏览数
     *
     * @param rentId 租房id
     * @return
     */
    @GetMapping("/addRentNum")
    public AjaxResult addRentNum(Long rentId) {
        return AjaxResult.success(appMyinfoService.addRentNum(rentId));
    }

    /**
     * 出租合同
     *
     * @param
     * @return
     */
    @GetMapping("/selectRentFile")
    public AjaxResult selectRentFile(RentFile rentFile) {
        return AjaxResult.success(appMyinfoService.selectRentFile(rentFile));
    }

    /**
     * 租房合同
     *
     * @param
     * @return
     */
    @GetMapping("/selectRentFile2")
    public AjaxResult selectRentFile2(RentFile rentFile) {
        return AjaxResult.success(appMyinfoService.selectRentFile2(rentFile));
    }

    /**
     * 业主信息
     *
     * @param
     * @return
     */
    @GetMapping("/selectOwnerInfo")
    public AjaxResult selectOwnerInfo(Long ownerId) {
        return AjaxResult.success(appMyinfoService.selectOwnerInfo(ownerId));
    }

    /**
     * 租户信息
     *
     * @param
     * @return
     */
    @GetMapping("/selectRenterInfo")
    public AjaxResult selectRenterInfo(Long tenantId) {
        return AjaxResult.success(appMyinfoService.selectRenterInfo(tenantId));
    }

    /**
     * 合同信息
     *
     * @param
     * @return
     */
    @GetMapping("/selectFileDetile")
    public AjaxResult selectFileDetile(RentFile rentFile) {
        return AjaxResult.success(appMyinfoService.selectFileDetile(rentFile));
    }

    /**
     * 查询通行证
     *
     * @param
     * @return
     */
    @GetMapping("/selectPassCard")
    public TableDataInfo selectPassCard(Long accountId) {
        startPage();
        return getDataTable(appMyinfoService.selectPassCard(accountId));
    }


    /**
     * 根据房号跟名字查询业主是否存在
     *
     * @param
     * @return
     */
    @GetMapping("/selectInfoByRoomId")
    public AjaxResult selectInfoByRoomId(Long roomId, String ownerName) {
        return AjaxResult.success(appMyinfoService.selectInfoByRoomId(roomId, ownerName));
    }

    /**
     * 新增通行证
     *
     * @param liaison 实体
     * @return 修改结果
     */
    @PostMapping("/insertPassCard")
    public AjaxResult insertPassCard(@RequestBody Liaison liaison) {
        appMyinfoService.insertPassCard(liaison);
        return AjaxResult.success("提交成功");
    }


    /**
     * 新增党员
     *
     * @param member 实体
     * @return 修改结果
     */
    @PostMapping("/insertPolitical")
    public AjaxResult insertPolitical(@RequestBody Member member) {
        appMyinfoService.insertPolitical(member);
        return AjaxResult.success("提交成功");
    }


    /**
     * 修改党员
     *
     * @param member 实体
     * @return 修改结果
     */
    @PostMapping("/updatePolitical")
    public AjaxResult updatePolitical(@RequestBody Member member) {
        return toAjax(appMyinfoService.updatePolitical(member));
    }

    /**
     * 获取业主住址
     *
     * @param accountId 账号id
     * @return
     */
    @GetMapping("/getOwnerAddress")
    public AjaxResult getOwnerAddress(Long accountId) {
        return AjaxResult.success(appMyinfoService.getOwnerAddress(accountId));
    }

    /**
     * 查询党组织信息
     *
     * @param communityId 小区id
     * @return
     */
    @GetMapping("/getPartyInfo")
    public AjaxResult getPartyInfo(Long communityId) {
        return AjaxResult.success(appMyinfoService.getPartyInfo(communityId));
    }

    /**
     * 查询党员信息
     *
     * @param
     * @return
     */
    @GetMapping("/selectPolitical")
    public AjaxResult selectPolitical(Member member) {
        return AjaxResult.success(appMyinfoService.selectPolitical(member));
    }

    /**
     * 分页查询车辆信息
     *
     * @param
     * @return
     */
    @GetMapping("/selectCar")
    public TableDataInfo selectCar(Vehicle vehicle) {
        startPage();
        return getDataTable( appMyinfoService.selectCar(vehicle));
    }

    /**
     * 查询车辆信息列表
     *
     * @param
     * @return
     */
    @GetMapping("/selectCarList")
    public AjaxResult selectCarList(Vehicle vehicle) {
        return AjaxResult.success( appMyinfoService.selectCar(vehicle));
    }

    /**
     * 查询车辆类型
     *
     * @param
     * @return
     */
    @GetMapping("/getCarType")
    public AjaxResult getCarType() {
        return AjaxResult.success(appMyinfoService.getCarType());
    }


    /**
     * 查询车位信息
     *
     * @param
     * @return
     */
    @GetMapping("/selectCarport")
    public AjaxResult selectCarport(Carport carport) {
        return AjaxResult.success(appMyinfoService.selectCarport(carport));
    }

    /**
     * 新增车辆信息
     *
     * @param vehicle 实体
     * @return 修改结果
     */
    @PostMapping("/insertvehicle")
    public AjaxResult insertvehicle(@RequestBody Vehicle vehicle) {
        appMyinfoService.insertvehicle(vehicle);
        return AjaxResult.success("提交成功");
    }

    /**
     * 查询车位信息
     *
     * @param
     * @return
     */
    @GetMapping("/selectCarportByfS")
    public AjaxResult selectCarportByS(Carport carport) {
        return AjaxResult.success(appMyinfoService.selectCarportByS(carport));
    }

    /**
     * 查询车两信息
     *
     * @param
     * @return
     */
    @GetMapping("/selectCarByAccount")
    public AjaxResult selectCarByAccount(Vehicle vehicle) {
        return AjaxResult.success(appMyinfoService.selectCarByAccount(vehicle));
    }

    /**
     * 新增车辆信息
     *
     * @param carportlog 实体
     * @return 修改结果
     */
    @PostMapping("/insertCartrentLog")
    public AjaxResult insertCartrentLog(@RequestBody Carportlog carportlog) {
        appMyinfoService.insertCartrentLog(carportlog);
        return AjaxResult.success("提交成功");
    }

    /**
     * 获取社会救助数量
     *
     * @param communityId 小区id
     * @return
     */
    @GetMapping("/getRescueCount")
    public AjaxResult getRescueCount(Long communityId) {
        List<String> rescueCount = appMyinfoService.getRescueCount(communityId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("everRescue", rescueCount.get(0));
        resultMap.put("currentRescue", rescueCount.get(1));
        return AjaxResult.success(resultMap);
    }


    /**
     * 获取身份证数量
     *
     * @param
     * @return
     */
    @GetMapping("/getCardType")
    public AjaxResult getCardType() {
        return AjaxResult.success(appMyinfoService.getCardType());
    }

    /**
     * 人脸登记照片修改
     *
     * @param myinfo
     * @return
     */
    @PostMapping("/updateFaceUrl")
    public AjaxResult updateFaceUrl(@RequestBody Myinfo myinfo) {
        return toAjax(appMyinfoService.updateFaceUrl(myinfo));
    }

    /**
     * 业主端注册用户
     *
     * @param dto 实体
     * @return 注册结果
     */
    @PostMapping("/registeredUser")
    public AjaxResult registeredUser(@RequestBody MyinfoDTO dto) {
        return appMyinfoService.registeredUser(dto);
    }

    /**
    * @Author SUNk
    * @Description 根据账号ID修改个人信息
    * @Date 9:36 2024/2/27
    * @Param [owner]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    @PostMapping("/updateUserMessageByAccountId")
    public AjaxResult updateUserMessageByAccountId(@RequestBody Owner owner) {
        if(owner == null || owner.getAccountId() == null){
            throw new ServiceException("获取更新信息失败，请稍后重试");
        }
        return toAjax( appMyinfoService.updateUserMessageByAccountId( owner ) );
    }

    /**
     * 切换小区
     *
     * @param dto 实体
     * @return 注册结果
     */
    @PostMapping("/updateOwnTable")
    public AjaxResult updateOwnTable(@RequestBody Owner dto) {
        return appMyinfoService.updateOwnTable(dto);
    }
}

