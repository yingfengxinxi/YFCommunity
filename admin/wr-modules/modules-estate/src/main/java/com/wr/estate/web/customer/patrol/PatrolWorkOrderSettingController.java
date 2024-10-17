package com.wr.estate.web.customer.patrol;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.DictUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.customer.patrol.PatrolWorkOrderSettingService;
import com.wr.remote.domain.PatrolWorkOrderSetting;
import com.wr.remote.domain.SysDictData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/3/2 9:56
 */
@RestController
@RequestMapping("patrolWorkOrderSetting")
public class PatrolWorkOrderSettingController extends BaseController {

    @Autowired
    private PatrolWorkOrderSettingService patrolWorkOrderSettingService;


    @ApiOperation("巡检工单设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    @PostMapping("list")
    public AjaxResult list(@RequestBody PatrolWorkOrderSetting patrolWorkOrderSetting) {
        List<SysDictData> dictCache = DictUtils.getDictCache("patrol_content");
        List<PatrolWorkOrderSetting> patrolSettingList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(dictCache)) {
            dictCache.stream().forEach(sysDictData -> {
                PatrolWorkOrderSetting pwosl = new PatrolWorkOrderSetting();
                LambdaQueryWrapper<PatrolWorkOrderSetting> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(PatrolWorkOrderSetting::getEstateId, SecurityUtils.getLoginUser().getSysUser().getEstateId());
                queryWrapper.eq(PatrolWorkOrderSetting::getCommunityId, patrolWorkOrderSetting.getCommunityId());
                queryWrapper.orderByDesc(PatrolWorkOrderSetting::getCreateTime);
                queryWrapper.eq(PatrolWorkOrderSetting::getFacilitieEquipmentType, sysDictData.getDictValue());
                PatrolWorkOrderSetting patrolWorkOrderSetting1 = patrolWorkOrderSettingService.getOne(queryWrapper);
                pwosl.setFacilitieEquipmentType(sysDictData.getDictValue());
                if (patrolWorkOrderSetting1 != null) {
                    pwosl.setId(patrolWorkOrderSetting1.getId());
                    pwosl.setEstateId(patrolWorkOrderSetting1.getEstateId());
                    pwosl.setCommunityId(patrolWorkOrderSetting1.getCommunityId());
                    pwosl.setFrequency(patrolWorkOrderSetting1.getFrequency());
                    pwosl.setPatrolStatus(patrolWorkOrderSetting1.getPatrolStatus());
                    pwosl.setFrequency(patrolWorkOrderSetting1.getFrequency());
                    pwosl.setOrderExpirationTime(patrolWorkOrderSetting1.getOrderExpirationTime());
                }
                patrolSettingList.add(pwosl);


            });
        }

        return AjaxResult.success(patrolSettingList);
    }

    /**
     * @param patrolWorkOrderSettingList
     * @return
     */
    @ApiOperation("工单设置增加")
    @PostMapping("save")
    public AjaxResult save(@RequestBody List<PatrolWorkOrderSetting> patrolWorkOrderSettingList) {

        for (PatrolWorkOrderSetting patrolWorkOrderSetting : patrolWorkOrderSettingList) {
            try {
                patrolWorkOrderSetting.setIsOrder("0");
                patrolWorkOrderSetting.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
                if (StringUtils.isNotEmpty(patrolWorkOrderSetting.getId())) {
                    patrolWorkOrderSetting.setUpdateBy(SecurityUtils.getUsername());
                    patrolWorkOrderSetting.setUpdateTime(new Date());
                    PatrolWorkOrderSetting orderSetting = patrolWorkOrderSettingService.getById(patrolWorkOrderSetting.getId());
                    if (!StringUtils.equals(patrolWorkOrderSetting.getFrequency(), orderSetting.getFrequency())) {
                        patrolWorkOrderSettingService.saveRedisData(patrolWorkOrderSetting, "0");

                    }
                    //工单状态发生改变
                    if (!StringUtils.equals(patrolWorkOrderSetting.getPatrolStatus(), orderSetting.getPatrolStatus())) {
                        //关闭工单生成
                        if (StringUtils.equals(patrolWorkOrderSetting.getPatrolStatus(), "0")) {
                            patrolWorkOrderSettingService.saveRedisData(patrolWorkOrderSetting, "1");
                            patrolWorkOrderSetting.setOrderExpirationTime(null);
                        }
                        //打开工单生成
                        if (StringUtils.equals(patrolWorkOrderSetting.getPatrolStatus(), "1")) {
                            patrolWorkOrderSettingService.saveRedisData(patrolWorkOrderSetting, "0");
                        }
                    }

                    //修改
                    patrolWorkOrderSettingService.updateById(patrolWorkOrderSetting);
                } else {
                    patrolWorkOrderSetting.setCreateBy(SecurityUtils.getUsername());
                    patrolWorkOrderSetting.setCreateTime(new Date());
                    patrolWorkOrderSetting.setDelFlag("0");
                    //新增
                    patrolWorkOrderSettingService.save(patrolWorkOrderSetting);
                    patrolWorkOrderSettingService.saveRedisData(patrolWorkOrderSetting, "0");
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }


        return AjaxResult.success();
    }
}
