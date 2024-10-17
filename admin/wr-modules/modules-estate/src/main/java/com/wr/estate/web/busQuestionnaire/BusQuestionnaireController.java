package com.wr.estate.web.busQuestionnaire;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.redis.service.RedisService;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.BusQuestionnaire;
import com.wr.estate.entity.BusQuestionnaireAccount;
import com.wr.estate.entity.BusQuestionnaireScope;
import com.wr.estate.service.busQuestionnaire.BusQuestionnaireAccountService;
import com.wr.estate.service.busQuestionnaire.BusQuestionnaireScopeService;
import com.wr.estate.service.busQuestionnaire.BusQuestionnaireService;
import com.wr.remote.domain.SysUser;
import com.wr.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/1/10 14:23
 */
@RestController
@RequestMapping("/busQuestionnaire")
@Api(tags = "物管平台-问卷调查")
public class BusQuestionnaireController extends BaseController {

    @Autowired
    private BusQuestionnaireService questionnaireService;

    @Autowired
    private UserService userService;

    @Autowired
    private BusQuestionnaireScopeService questionnaireScopeService;

    @Autowired
    private BusQuestionnaireAccountService questionnaireAccountService;

    @Autowired
    private RedisService redisService;


    /**
     * 分页
     *
     * @param busQuestionnaire 查询对象
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("问卷调查列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "title", value = "问题名称", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "类型0=未发布1=进行中2=已提交2=结束", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo list(BusQuestionnaire busQuestionnaire) {
        LambdaQueryWrapper<BusQuestionnaire> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(busQuestionnaire.getTitle())) {
            queryWrapper.like(BusQuestionnaire::getTitle, busQuestionnaire.getTitle());
        }
        if (StringUtils.isNotEmpty(busQuestionnaire.getStatus())) {
            queryWrapper.eq(BusQuestionnaire::getStatus, busQuestionnaire.getStatus());
        }

        if (busQuestionnaire.getCommunityId() != null) {
            queryWrapper.eq(BusQuestionnaire::getCommunityId, busQuestionnaire.getCommunityId());
        }
        queryWrapper.select(BusQuestionnaire.class, r -> !r.getColumn().equals("content"));
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        queryWrapper.eq(BusQuestionnaire::getEstateId, estateId);
        queryWrapper.orderByDesc(BusQuestionnaire::getCreateTime);
        startPage();
        List<BusQuestionnaire> rescueVOList = questionnaireService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(rescueVOList)) {
            rescueVOList.forEach(questionnaire -> {
                LambdaQueryWrapper<BusQuestionnaireAccount> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                int num = questionnaireAccountService.getList(questionnaire.getId()).size();
                questionnaire.setNum(num);
                //发布者姓名
                String releaseBy = questionnaire.getReleaseBy();
                if (StringUtils.isNotEmpty(releaseBy)) {
                    SysUser sysUser = userService.selectUserById(Long.valueOf(releaseBy));
                    questionnaire.setReleaseBy(sysUser.getNickName());
                }


            });
        }
        return getDataTable(rescueVOList);
    }

    /**
     * 详情
     *
     * @param id 查询id
     * @return
     */
    @ApiOperation("问卷调查详情")
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable String id) {
        BusQuestionnaire questionnaire = questionnaireService.getById(id);
        LambdaQueryWrapper<BusQuestionnaireScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusQuestionnaireScope::getQuestionnaireId, id);
        List<BusQuestionnaireScope> list = questionnaireScopeService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            List<Long> buildingIds = list.stream().map(BusQuestionnaireScope::getBuildingId).distinct().collect(Collectors.toList());
            questionnaire.setBuildingIds(buildingIds);
        }
        return AjaxResult.success(questionnaire);
    }

    /**
     * @param busQuestionnaire
     * @return
     */
    @ApiOperation("问卷调查增加")
    @PostMapping("insert")
    public AjaxResult insert(@RequestBody BusQuestionnaire busQuestionnaire) {

        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        String userId = String.valueOf(sysUser.getUserId());
        busQuestionnaire.setCreateBy(userId);
        busQuestionnaire.setCreateTime(new Date());
        String releaseStatus = busQuestionnaire.getReleaseStatus();
        if (releaseStatus.equals("1")) {
            busQuestionnaire.setReleaseTime(DateUtils.getTime());
            busQuestionnaire.setReleaseBy(userId);
            busQuestionnaire.setStatus("1");
        }
        String id = UUID.randomUUID().toString().replace("-", "");
        busQuestionnaire.setId(id);
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        busQuestionnaire.setEstateId(estateId);
        busQuestionnaire.setStatus("0");
        //设置redis修改状态时间
        extracted(busQuestionnaire);
        questionnaireService.save(busQuestionnaire);

        BusQuestionnaireScope questionnaireScope = new BusQuestionnaireScope();
        questionnaireScope.setQuestionnaireId(busQuestionnaire.getId());
        questionnaireScope.setCommunityId(busQuestionnaire.getCommunityId());
        questionnaireScope.setBuildingIds(busQuestionnaire.getBuildingIds());
        questionnaireScopeService.add(questionnaireScope);
        return AjaxResult.success();

    }

    /**
     * @param busQuestionnaire
     * @return
     */
    @ApiOperation("问卷调查修改")
    @PutMapping("update")
    public AjaxResult update(@RequestBody BusQuestionnaire busQuestionnaire) {
        String status = questionnaireService.getById(busQuestionnaire.getId()).getStatus();
        if (!status.equals("0")) {
            return AjaxResult.error("当前问卷状态不支持修改,请刷新当前列表");
        }
        String userId = String.valueOf(SecurityUtils.getUserId());
        busQuestionnaire.setUpdateBy(userId);
        busQuestionnaire.setUpdateTime(new Date());
        busQuestionnaire.setStatus("0");
        //设置redis修改状态时间
        extracted(busQuestionnaire);
        questionnaireService.updateById(busQuestionnaire);
        BusQuestionnaireScope questionnaireScope = new BusQuestionnaireScope();
        questionnaireScope.setQuestionnaireId(busQuestionnaire.getId());
        questionnaireScope.setCommunityId(busQuestionnaire.getCommunityId());
        questionnaireScope.setBuildingIds(busQuestionnaire.getBuildingIds());
        questionnaireScopeService.add(questionnaireScope);
        return AjaxResult.success();
    }

    /**
     * 设置修改调查问卷时间
     *
     * @param busQuestionnaire
     */
    private void extracted(BusQuestionnaire busQuestionnaire) {
        String redisKey = CacheConstants.QUEST_IONNAIRE + busQuestionnaire.getId();
        redisService.deleteObject(redisKey);
        String redisKeyExp = CacheConstants.QUEST_IONNAIRE_EXP + busQuestionnaire.getId();
        redisService.deleteObject(redisKeyExp);
        Long startTime = busQuestionnaire.getStartTime().getTime();
        Long now = new Date().getTime();
        if (startTime <= now) {
            busQuestionnaire.setStatus("1");
        } else {
            //计算到期时间
            long seconds = DateUtils.getSeconds(new Date(), busQuestionnaire.getStartTime());
            redisService.setCacheObject(redisKey, busQuestionnaire, seconds, TimeUnit.SECONDS);
        }
        //调查问卷结束时间
        //计算到期时间
        long deadlineDeconds = DateUtils.getSeconds(new Date(), busQuestionnaire.getDeadline());
        redisService.setCacheObject(redisKeyExp, busQuestionnaire, deadlineDeconds, TimeUnit.SECONDS);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @ApiOperation("问卷调查删除")
    @DeleteMapping("delete")
    public AjaxResult delete(@RequestParam("id") String id) {
        questionnaireService.removeById(id);
        LambdaQueryWrapper<BusQuestionnaireScope> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BusQuestionnaireScope::getQuestionnaireId, id);
        questionnaireScopeService.remove(queryWrapper);
        return AjaxResult.success();
    }

    /**
     * 发布
     *
     * @param releaseStatus
     * @param id
     * @return
     */
    @ApiOperation("问卷调查发布")
    @GetMapping("updateByIdStatus")
    public AjaxResult updateByIdStatus(@RequestParam("releaseStatus") String releaseStatus, @RequestParam("id") String id) {

        String userId = String.valueOf(SecurityUtils.getUserId());
        BusQuestionnaire questionnaire = questionnaireService.getById(id);
        questionnaire.setReleaseStatus(releaseStatus);
        if (releaseStatus.equals("1")) {
            questionnaire.setReleaseTime(DateUtils.getTime());
            questionnaire.setReleaseBy(userId);
            questionnaire.setStatus("1");
        } else {
            questionnaire.setReleaseTime("");
            questionnaire.setReleaseBy("");
            questionnaire.setStatus("0");
        }
        questionnaireService.updateById(questionnaire);

        return AjaxResult.success();
    }
}
