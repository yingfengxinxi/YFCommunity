package com.wr.estate.web.financial;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.vo.ParkDurationVo;
import com.wr.remote.estate.financial.ParkDurationEntity;
import com.wr.estate.service.financial.ParkDurationService;
import com.wr.remote.domain.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: bajie
 * @create: 2022/11/2
 * @Description:
 * @FileName: ParkDurationController
 * @History: 车位缴费规则配置
 */
@RestController
@RequestMapping("/parkDuration")
@Api(tags = "物管平台-车位缴费规则配置")
public class ParkDurationController extends BaseController {

    @Autowired
    private ParkDurationService parkDurationService;

    @GetMapping("/list")
    @ApiOperation("车位缴费规则配置列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(ParkDurationEntity parkDurationEntity) {
        // 获取用户所属物业
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if (ObjectUtils.allNull(sysUser)) {
            return getDataTable(Lists.newArrayList());
        }
        LambdaQueryWrapper<ParkDurationEntity> queryWrapper = new LambdaQueryWrapper<>();
        if (parkDurationEntity.getCommunityId() != null) {
            queryWrapper.eq(ParkDurationEntity::getCommunityId, parkDurationEntity.getCommunityId());
        }
        Long estateId = sysUser.getEstateId();
        queryWrapper.eq(ParkDurationEntity::getEstateId, estateId);
        queryWrapper.orderByDesc(ParkDurationEntity::getCreateTime);
        startPage();
        List<ParkDurationEntity> list = parkDurationService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation("车位缴费规则配置详情")
    @GetMapping("{id}")
    public AjaxResult get(@PathVariable("id") String id) {
        return AjaxResult.success(parkDurationService.getById(id));
    }

    /**
     * @param parkDurationEntity
     * @return
     */
    @ApiOperation("车位缴费规则配置新增")
    @PostMapping("/insert")
    public AjaxResult insert(@RequestBody ParkDurationEntity parkDurationEntity) {

        parkDurationEntity.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());

        LambdaQueryWrapper<ParkDurationEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ParkDurationEntity::getEstateId, parkDurationEntity.getEstateId());
        queryWrapper.eq(ParkDurationEntity::getCommunityId, parkDurationEntity.getCommunityId());
        queryWrapper.eq(ParkDurationEntity::getLeasePurchase, parkDurationEntity.getLeasePurchase());
        if (StringUtils.isNotEmpty(parkDurationEntity.getPurchasePackage())) {
            queryWrapper.eq(ParkDurationEntity::getPurchasePackage, parkDurationEntity.getPurchasePackage());
        }
        queryWrapper.eq(ParkDurationEntity::getDelFlag, "0");
        int size = parkDurationService.list(queryWrapper).size();
        if (size >= 1) {
            return AjaxResult.error("当前添加的缴费类型已存在!");
        }


        parkDurationEntity.setCreateTime(new Date());
        parkDurationEntity.setCreateBy(SecurityUtils.getUsername());
        parkDurationService.save(parkDurationEntity);

        return AjaxResult.success();
    }

    /**
     * @param parkDurationEntity
     * @return
     */
    @ApiOperation("车位缴费规则配置修改")
    @PutMapping("/update")
    public AjaxResult update(@RequestBody ParkDurationEntity parkDurationEntity) {

        parkDurationEntity.setUpdateTime(new Date());
        parkDurationEntity.setUpdateBy(SecurityUtils.getUsername());
        parkDurationService.updateById(parkDurationEntity);

        return AjaxResult.success();
    }


    /**
     * @param id
     * @return
     */
    @ApiOperation("车位缴费规则配置删除")
    @DeleteMapping("/delete")
    public AjaxResult delete(@RequestParam("id") String id) {
        parkDurationService.removeById(id);
        return AjaxResult.success();
    }

    /**
     * @param communityId
     * @return
     */
    @GetMapping("getLeasePurchaseList")
    @ApiOperation("租赁购买下拉")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = true, paramType = "query"),
    })
    public AjaxResult getLeasePurchaseList(@RequestParam("communityId") Long communityId, @RequestParam("carportNature") String carportNature) {
        LambdaQueryWrapper<ParkDurationEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ParkDurationEntity::getCommunityId, communityId);
        queryWrapper.eq(ParkDurationEntity::getDelFlag, "0");
        queryWrapper.eq(ParkDurationEntity::getLeasePurchase, carportNature);

        List<ParkDurationEntity> list = parkDurationService.list(queryWrapper);
        List<ParkDurationVo> parkDurationVoList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(list)) {
            List<String> collect = list.stream().map(ParkDurationEntity::getLeasePurchase).distinct().collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect)) {
                collect.stream().forEach(le -> {
                    ParkDurationVo parkDurationVo = new ParkDurationVo();
                    parkDurationVo.setId(le);
                    parkDurationVo.setName(le.equals("0") ? "租" : "买");
                    parkDurationVoList.add(parkDurationVo);
                });
            }
        }
        return AjaxResult.success(parkDurationVoList);
    }


    /**
     * @param communityId
     * @param leasePurchase
     * @return
     */
    @GetMapping("getPurchasePackageList")
    @ApiOperation("选择套餐下拉")
    public AjaxResult getPurchasePackageList(@RequestParam("communityId") Long communityId, @RequestParam("leasePurchase") String leasePurchase) {
        LambdaQueryWrapper<ParkDurationEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ParkDurationEntity::getCommunityId, communityId);
        queryWrapper.eq(ParkDurationEntity::getLeasePurchase, leasePurchase);
        queryWrapper.eq(ParkDurationEntity::getDelFlag, "0");
        List<ParkDurationEntity> list = parkDurationService.list(queryWrapper);
        List<ParkDurationVo> parkDurationVoList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(list)) {
            List<String> collect = list.stream().map(ParkDurationEntity::getPurchasePackage).distinct().collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(collect)) {
                collect.stream().forEach(le -> {
                    ParkDurationVo parkDurationVo = new ParkDurationVo();
                    parkDurationVo.setId(le);
                    parkDurationVo.setName(le.equals("0") ? "包年" : "包月");
                    parkDurationVoList.add(parkDurationVo);
                });
            }
        }
        return AjaxResult.success(parkDurationVoList);
    }

    /**
     * @param communityId
     * @param leasePurchase
     * @return
     */
    @GetMapping("getFee")
    @ApiOperation("查询费用")
    public AjaxResult getFee(
            @RequestParam("communityId") Long communityId,
            @RequestParam("leasePurchase") String leasePurchase,
            @RequestParam(value = "purchasePackage", required = false) String purchasePackage
    ) {
        LambdaQueryWrapper<ParkDurationEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ParkDurationEntity::getCommunityId, communityId);
        queryWrapper.eq(ParkDurationEntity::getLeasePurchase, leasePurchase);
        if (StringUtils.isNotEmpty(purchasePackage)) {
            queryWrapper.eq(ParkDurationEntity::getPurchasePackage, purchasePackage);
        }
        queryWrapper.eq(ParkDurationEntity::getDelFlag, "0");
        List<ParkDurationEntity> list = parkDurationService.list(queryWrapper);

        return AjaxResult.success(list);
    }


    /**
     *
     * @param communityId
     * @return
     */
    @ApiOperation("查询是否配置车位费缴费规则")
    @GetMapping("/getCommunityIdParkDurationCount")
    public AjaxResult getCommunityIdParkDurationCount(@RequestParam("communityId") Long communityId) {
        LambdaQueryWrapper<ParkDurationEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ParkDurationEntity::getCommunityId, communityId);
        return AjaxResult.success(parkDurationService.list(queryWrapper).size());
    }

}
