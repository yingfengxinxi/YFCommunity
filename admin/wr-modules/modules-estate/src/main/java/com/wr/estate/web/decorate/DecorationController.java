package com.wr.estate.web.decorate;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.redis.service.RedisService;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.DictUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.center.RoomService;
import com.wr.estate.service.customer.decorate.DecorateViolationService;
import com.wr.estate.service.customer.decorate.DecorationService;
import com.wr.remote.domain.SysDictData;
import com.wr.remote.estate.customer.decorate.BusDecorate;
import com.wr.remote.estate.customer.decorate.DecorateViolation;
import com.wr.remote.estate.customer.vo.decorate.DecorateVo;
import com.wr.remote.estate.manage.meter.RecordVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 晁盖
 * @Date: 2022-10-11 13:34:36
 * @Desc: 装修列表
 */
@RestController
@RequestMapping("/decoration")
@Api(tags = "物管平台-装修工单")
public class DecorationController extends BaseController {

    @Autowired
    private DecorationService decorationService;

    @Autowired
    private DecorateViolationService decorateViolationService;

    @Autowired
    private RoomService roomService;

    /**
     * 办理装修提交
     *
     * @param busDecorate
     * @return
     */
    @ApiOperation("办理装修提交")
    @PostMapping("add")
    public AjaxResult add(@RequestBody BusDecorate busDecorate) {
        busDecorate.setCreateBy(SecurityUtils.getUsername());
        busDecorate.setUpdateBy(SecurityUtils.getUsername());
        busDecorate.setCreateTime(new Date());
        busDecorate.setUpdateTime(new Date());
        busDecorate.setRenovationAuditStatus("0");
        busDecorate.setNo("ZX" + IdWorker.getIdStr());
        busDecorate.setDelFlag("0");
        projectItem(busDecorate);
        decorationService.save(busDecorate);
        //修改房屋状态为装修[3]
        roomService.updateByRoomIdRoomStatus(busDecorate.getRoomId(), "3");
        //向节点记录表中增加记录
        DecorateViolation decorateViolation = new DecorateViolation();
        decorateViolation.setDecorateId(busDecorate.getId());
        decorateViolation.setRenovationAuditStatus(busDecorate.getRenovationAuditStatus());
        decorateViolation.setContent(busDecorate.getContent());
        decorateViolation.setImage(busDecorate.getImage());
        decorateViolation.setCreateBy(SecurityUtils.getUsername());
        decorateViolation.setCreateTime(busDecorate.getUpdateTime());
        decorateViolationService.save(decorateViolation);
        return AjaxResult.success();
    }

    /**
     * @param busDecorate
     * @return
     */
    @ApiOperation("办理装修修改")
    @PostMapping("update")
    public AjaxResult update(@RequestBody BusDecorate busDecorate) {
        busDecorate.setUpdateBy(SecurityUtils.getUsername());
        busDecorate.setUpdateTime(new Date());
        projectItem(busDecorate);
        decorationService.updateById(busDecorate);
        return AjaxResult.success();
    }

    public void projectItem(BusDecorate busDecorate) {
        StringBuilder sb = new StringBuilder();
        List<SysDictData> dictDataList = DictUtils.getDictCache("project_item");
        if (StringUtils.isNotEmpty(busDecorate.getProject())) {
            String[] projects = busDecorate.getProject().split(",");
            for (String project : projects) {
                String dictLabel = dictDataList.stream().filter(SysDictData -> SysDictData.getDictValue().equals(project)).collect(Collectors.toList()).get(0).getDictLabel();
                sb.append(dictLabel).append(",");
            }
        }
        String projectItem = sb.toString();
        if (StringUtils.isNotEmpty(projectItem)) {
            projectItem = projectItem.substring(0, projectItem.length() - 1);
            busDecorate.setProjectItem(projectItem);
        }

    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @ApiOperation("办理装修详情")
    @GetMapping("get/{id}")
    public AjaxResult get(@PathVariable("id") String id) {

        return AjaxResult.success(decorationService.getById(id));
    }

    /**
     * 查询装修列表
     *
     * @param decorateVo
     * @return
     */
    @ApiOperation("装修工单列表")
    @GetMapping("/list")
    public TableDataInfo list(DecorateVo decorateVo) {
        startPage();
        List<DecorateVo> list = decorationService.getDecorateVoList(decorateVo);
        return getDataTable(list);
    }

    /**
     * 导出
     *
     * @param response
     * @param decorateVo
     * @throws IOException
     */
    @ApiOperation("装修工单导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, DecorateVo decorateVo) throws IOException {
        List<DecorateVo> list = decorationService.getDecorateVoList(decorateVo);
        ExcelUtil<DecorateVo> util = new ExcelUtil<DecorateVo>(DecorateVo.class);
        util.exportExcel(response, list, "装修评定");
    }

    /**
     * 查询装修详情
     *
     * @param id
     * @return
     */
    @ApiOperation("装修工单详情")
    @GetMapping(value = "/{id}")
    public AjaxResult getDecorationDetail(@PathVariable String id) {

        return decorationService.getDecorationDetail(id);
    }
}
