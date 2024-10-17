package com.wr.api.estate.web.application.serve;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.api.estate.mapper.application.info.RoomMapper;
import com.wr.api.estate.service.application.info.RoomService;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.security.utils.DictUtils;
import com.wr.remote.domain.SysDictData;
import com.wr.remote.estate.customer.decorate.BusDecorate;
import com.wr.remote.estate.customer.decorate.Decorate;
import com.wr.remote.estate.customer.decorate.DecorateViolation;
import com.wr.api.estate.service.application.serve.DecorateViolationService;
import com.wr.api.estate.service.application.serve.DecorationService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.estate.customer.vo.decorate.DecorateInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/1/12 17:39
 */
@RestController
@RequestMapping("/v2/decorateViolation")
@Api(tags = "物业移动端-装修节点")
public class DecorateViolationController extends BaseController {

    @Autowired
    private DecorateViolationService decorateViolationService;

    @Autowired
    private DecorationService decorationService;

    @Autowired
    private RoomService roomService;


    @PostMapping("add")
    @ApiOperation("装修节点记录")
    public AjaxResult add(@RequestBody DecorateViolation decorateViolation) {
        if (StringUtils.isEmpty(decorateViolation.getCreateBy())) {

            return AjaxResult.error("当前登录用户名不能为空");
        }
        decorateViolation.setCreateTime(new Date());
        decorateViolationService.save(decorateViolation);
        //修改装修表信息
        BusDecorate busDecorate = new BusDecorate();
        busDecorate.setUpdateBy(decorateViolation.getCreateBy());
        busDecorate.setUpdateTime(new Date());
        busDecorate.setRenovationAuditStatus(decorateViolation.getRenovationAuditStatus());
        busDecorate.setId(decorateViolation.getDecorateId());
        decorationService.updateByRenovationAuditStatus(busDecorate);
        DecorateInfoVo decorationDetail = decorationService.getDecorationDetail(busDecorate.getId());
        //装修完成修改房屋状态为已入住
        if(busDecorate.getRenovationAuditStatus().equals("5")){
            //修改房屋状态为已入住[2]
            roomService.updateByRoomIdRoomStatus(decorationDetail.getRoomId(), "2");
        }

        return AjaxResult.success();
    }

    /**
     * 节点记录查询
     *
     * @param decorateId
     * @return
     */
    @GetMapping("getViolationList")
    @ApiOperation("节点记录查询")
    public AjaxResult getViolationList(@RequestParam("decorateId") String decorateId) {

        LambdaQueryWrapper<DecorateViolation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DecorateViolation::getDecorateId, decorateId);
        queryWrapper.orderByDesc(DecorateViolation::getCreateTime);
        List<DecorateViolation> list = decorateViolationService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            List<SysDictData> auditStatusList = DictUtils.getDictCache("renovation_audit_status");
            list.stream().forEach(decorateViolation -> {
                String image = decorateViolation.getImage();
                if(StringUtils.isNotEmpty(image)){
                    decorateViolation.setImages(Arrays.asList(image.split(",")));
                }
                String renovationAuditStatus = decorateViolation.getRenovationAuditStatus();
                String dictLabel = auditStatusList.stream().filter(SysDictData -> SysDictData.getDictValue().equals(renovationAuditStatus)).collect(Collectors.toList()).get(0).getDictLabel();
                decorateViolation.setRenovationAuditStatus(dictLabel);
            });
        }
        return AjaxResult.success(list);
    }


    /**
     * 节点状态查询
     *
     * @param decorateId
     * @return
     */
    @ApiOperation("节点状态查询")
    @GetMapping("renovationAuditStatusList/{decorateId}")
    public AjaxResult auditStatusList(@PathVariable("decorateId") String decorateId) {

        LambdaQueryWrapper<DecorateViolation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DecorateViolation::getDecorateId, decorateId);
        List<DecorateViolation> list = decorateViolationService.list(queryWrapper);
        List<String> renovationAuditStatusList = list.stream().map(DecorateViolation::getRenovationAuditStatus).collect(Collectors.toList());
        List<SysDictData> dictDatas = Lists.newArrayList();
        List<SysDictData> auditStatusList = DictUtils.getDictCache("renovation_audit_status");

        for (SysDictData dictData : auditStatusList) {
            List<String> collect = renovationAuditStatusList.stream().filter(renovationAuditStatus -> dictData.getDictValue().equals(renovationAuditStatus)).collect(Collectors.toList());
            if (collect.size() <= 0) {
                dictDatas.add(dictData);
            }
        }

        return AjaxResult.success(dictDatas);
    }
}
