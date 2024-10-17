package com.wr.estate.web.send;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.send.SendSettingService;
import com.wr.remote.domain.SendSetting;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.property.Property;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/29 21:00
 */
@RestController
@RequestMapping("sendSetting")
@Api(tags = "发送短信参数配置")
public class SendSettingController extends BaseController {

    @Autowired
    private SendSettingService sendSettingService;


    /**
     * @param sendSetting
     * @return
     */
    @ApiOperation("列表")
    @PostMapping("list")
    public TableDataInfo list(@RequestBody SendSetting sendSetting) {

        LambdaQueryWrapper<SendSetting> queryWrapper = new LambdaQueryWrapper<>();
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        queryWrapper.eq(SendSetting::getEstateId, estateId);

        queryWrapper.orderByDesc(SendSetting::getCreateTime);
        PageUtils.startPage(sendSetting.getPageNum(), sendSetting.getPageSize());
        List<SendSetting> list = sendSettingService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation("详情")
    @GetMapping("{id}")
    public AjaxResult get(@PathVariable("id") String id) {

        return AjaxResult.success(sendSettingService.getById(id));
    }

    /**
     * @param sendSetting
     * @return
     */
    @ApiOperation("新增")
    @PostMapping("add")
    public AjaxResult add(@RequestBody SendSetting sendSetting) {

        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        sendSetting.setEstateId(sysUser.getEstateId());
        sendSetting.setCreateTime(new Date());
        sendSetting.setCreateBy(sysUser.getUserName());
        sendSetting.setStatus("1");
        sendSetting.setDelFlag("0");
        sendSettingService.save(sendSetting);
        return AjaxResult.success();
    }

    /**
     * @param sendSetting
     * @return
     */
    @ApiOperation("修改")
    @PutMapping("update")
    public AjaxResult update(@RequestBody SendSetting sendSetting) {

        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        sendSetting.setEstateId(sysUser.getEstateId());
        sendSetting.setUpdateTime(new Date());
        sendSetting.setUpdateBy(sysUser.getUserName());
        sendSettingService.updateById(sendSetting);
        return AjaxResult.success();
    }


}
