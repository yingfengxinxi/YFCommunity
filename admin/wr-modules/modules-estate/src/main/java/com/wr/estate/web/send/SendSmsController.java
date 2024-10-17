package com.wr.estate.web.send;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.send.SendSmsService;
import com.wr.remote.domain.SendSetting;
import com.wr.remote.domain.SendSms;
import com.wr.remote.domain.SysUser;
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
@RequestMapping("sendSms")
@Api(tags = "发送短信模板配置")
public class SendSmsController extends BaseController {

    @Autowired
    private SendSmsService sendSmsService;

    /**
     * @param sendSms
     * @return
     */
    @ApiOperation("列表")
    @PostMapping("list")
    public TableDataInfo list(@RequestBody SendSms sendSms) {

        LambdaQueryWrapper<SendSms> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SendSms::getSendSettingId, sendSms.getSendSettingId());
        if (sendSms.getCommunityId() != null) {
            queryWrapper.eq(SendSms::getCommunityId, sendSms.getCommunityId());
        }
        if (StringUtils.isNotEmpty(sendSms.getTemplateType())) {
            queryWrapper.eq(SendSms::getTemplateType, sendSms.getTemplateType());
        }
        queryWrapper.orderByDesc(SendSms::getCreateTime);
        PageUtils.startPage(sendSms.getPageNum(), sendSms.getPageSize());
        List<SendSms> list = sendSmsService.list(queryWrapper);
        return getDataTable(list);
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation("详情")
    @GetMapping("{id}")
    public AjaxResult get(@PathVariable("id") String id) {

        return AjaxResult.success(sendSmsService.getById(id));
    }

    /**
     * @param sendSms
     * @return
     */
    @ApiOperation("新增")
    @PostMapping("add")
    public AjaxResult add(@RequestBody SendSms sendSms) {
        LambdaQueryWrapper<SendSms> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SendSms::getSendSettingId, sendSms.getSendSettingId());
        queryWrapper.eq(SendSms::getCommunityId, sendSms.getCommunityId());
        queryWrapper.eq(SendSms::getTemplateType, sendSms.getTemplateType());
        int size = sendSmsService.list(queryWrapper).size();
        if (size >= 1) {
            return AjaxResult.error("当前短信类型已存在重复添加");
        }
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        sendSms.setCreateTime(new Date());
        sendSms.setCreateBy(sysUser.getUserName());
        sendSms.setDelFlag("0");
        sendSms.setStatus("1");
        sendSmsService.save(sendSms);
        return AjaxResult.success();
    }

    /**
     * @param sendSms
     * @return
     */
    @ApiOperation("修改")
    @PutMapping("update")
    public AjaxResult update(@RequestBody SendSms sendSms) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        sendSms.setUpdateTime(new Date());
        sendSms.setUpdateBy(sysUser.getUserName());
        sendSmsService.updateById(sendSms);
        return AjaxResult.success();
    }


}
