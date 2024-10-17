package com.wr.api.owner.web.estateapi.busQuestionnaire;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.api.owner.entity.estateapi.BusQuestionnaire;
import com.wr.api.owner.entity.estateapi.BusQuestionnaireAccount;
import com.wr.api.owner.entity.estateapi.BusQuestionnaireScope;
import com.wr.api.owner.service.estateapi.UserService;
import com.wr.api.owner.service.estateapi.busQuestionnaire.BusQuestionnaireAccountService;
import com.wr.api.owner.service.estateapi.busQuestionnaire.BusQuestionnaireScopeService;
import com.wr.api.owner.service.estateapi.busQuestionnaire.BusQuestionnaireService;
import com.wr.common.core.constant.CacheConstants;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.redis.service.RedisService;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.domain.SysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/1/10 14:23
 */
@RestController
@RequestMapping("/estateapi/busQuestionnaire")
@Api(tags = "业主端-问卷调查")
public class BusQuestionnaireController extends BaseController {

    @Resource
    private BusQuestionnaireService questionnaireService;


    @Autowired
    private RedisService redisService;

    @Resource
    private UserService userService;

    @Resource
    private BusQuestionnaireScopeService questionnaireScopeService;

    @Resource
    private BusQuestionnaireAccountService questionnaireAccountService;


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
            @ApiImplicitParam(name = "type", value = "类型0=未发布1=进行中2=已提交2=结束", dataType = "String", required = false, paramType = "query"),
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
        Long estateId = busQuestionnaire.getEstateId();
        queryWrapper.eq(BusQuestionnaire::getEstateId, estateId);
        queryWrapper.orderByDesc(BusQuestionnaire::getCreateTime);

        List<BusQuestionnaire> rescueVOList = questionnaireService.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(rescueVOList)) {
            Iterator<BusQuestionnaire> iterator = rescueVOList.iterator();
            if (iterator.hasNext()) {
                BusQuestionnaire next = iterator.next();

                //查询控制范围表是否有数据，如果有则查看用户是否再范围表中，如果没有则统计下一步
                if (StringUtils.equals(next.getActRang(), "1")) {
                    LambdaQueryWrapper<BusQuestionnaireScope> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(BusQuestionnaireScope::getQuestionnaireId, next.getId())
                            .in(BusQuestionnaireScope::getBuildingId, busQuestionnaire.getBuildingIds())
                            .eq(BusQuestionnaireScope::getDelFlag, "0");
                    List<BusQuestionnaireScope> list = questionnaireScopeService.list(wrapper);
                    if (CollUtil.isEmpty(list)) {
                        iterator.remove();
                    }
                }

                LambdaQueryWrapper<BusQuestionnaireAccount> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(BusQuestionnaireAccount::getQuestionnaireInvestigationId, next.getId());
                int num = questionnaireAccountService.list(lambdaQueryWrapper).size();
                next.setNum(num);

                //发布者姓名
                String releaseBy = next.getReleaseBy();
                if (StringUtils.isNotEmpty(releaseBy)) {
                    SysUser sysUser = userService.selectUserById(Long.valueOf(releaseBy));
                    next.setReleaseBy(sysUser.getNickName());
                }
            }
        }
        startPage();
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
        busQuestionnaire.setStatus("0");
        //设置redis修改状态时间
        extracted(busQuestionnaire);


        String id = UUID.randomUUID().toString().replace("-", "");
        busQuestionnaire.setId(id);
        Long estateId = SecurityUtils.getLoginUser().getSysUser().getEstateId();
        busQuestionnaire.setEstateId(estateId);
        questionnaireService.save(busQuestionnaire);


        BusQuestionnaireScope questionnaireScope = new BusQuestionnaireScope();
        questionnaireScope.setQuestionnaireId(busQuestionnaire.getId());
        questionnaireScope.setCommunityId(busQuestionnaire.getCommunityId());
        questionnaireScope.setBuildingIds(busQuestionnaire.getBuildingIds());
        questionnaireScopeService.add(questionnaireScope);
        return AjaxResult.success();

    }

    private void extracted(BusQuestionnaire busQuestionnaire) {
        Long startTime = busQuestionnaire.getStartTime().getTime();
        Long now = new Date().getTime();
        if (startTime <= now) {
            busQuestionnaire.setStatus("1");
        } else {
            //计算到期时间
            long seconds = DateUtils.getSeconds(new Date(), busQuestionnaire.getStartTime());
            redisService.setCacheObject(CacheConstants.QUEST_IONNAIRE, busQuestionnaire.getId(), seconds, TimeUnit.SECONDS);
        }
        //调查问卷结束时间
        //计算到期时间
        long deadlineDeconds = DateUtils.getSeconds(new Date(), busQuestionnaire.getDeadline());
        redisService.setCacheObject(CacheConstants.QUEST_IONNAIRE_EXP, busQuestionnaire.getId(), deadlineDeconds, TimeUnit.SECONDS);
    }

    /**
     * @param busQuestionnaire
     * @return
     */
    @ApiOperation("问卷调查修改")
    @PostMapping("update")
    public AjaxResult update(@RequestBody BusQuestionnaire busQuestionnaire) {
        String userId = String.valueOf(SecurityUtils.getUserId());
        busQuestionnaire.setUpdateBy(userId);
        busQuestionnaire.setUpdateTime(new Date());
        String releaseStatus = busQuestionnaire.getReleaseStatus();
        if (releaseStatus.equals("1")) {
            busQuestionnaire.setReleaseTime(DateUtils.getTime());
            busQuestionnaire.setReleaseBy(userId);
            busQuestionnaire.setStatus("1");
        } else {
            busQuestionnaire.setReleaseTime("");
            busQuestionnaire.setReleaseBy("");
            busQuestionnaire.setStatus("0");
        }
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

    @GetMapping("/findListStatusBusQuestionnaire")
    @ApiOperation("问卷调查分页查询")
    public TableDataInfo findListStatusBusQuestionnaire(BusQuestionnaire busQuestionnaire) {
        startPage();
        List<BusQuestionnaire> list = questionnaireService.findListStatusBusQuestionnaire( busQuestionnaire );
        for (BusQuestionnaire questionnaire : list) {
            LambdaQueryWrapper<BusQuestionnaireAccount> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(BusQuestionnaireAccount::getQuestionnaireInvestigationId, questionnaire.getId());
            int num = questionnaireAccountService.list(lambdaQueryWrapper).size();
            questionnaire.setNum(num);

            //发布者姓名
            String releaseBy = questionnaire.getReleaseBy();
            if (StringUtils.isNotEmpty(releaseBy)) {
                SysUser sysUser = userService.selectUserById(Long.valueOf(releaseBy));
                questionnaire.setReleaseBy(sysUser.getNickName());
            }
        }

        return getDataTable(list);
    }
}
