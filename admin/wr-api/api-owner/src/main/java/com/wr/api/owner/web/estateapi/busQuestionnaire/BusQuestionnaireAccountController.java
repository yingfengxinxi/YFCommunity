package com.wr.api.owner.web.estateapi.busQuestionnaire;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wr.api.owner.entity.estateapi.BusQuestionnaire;
import com.wr.api.owner.entity.estateapi.BusQuestionnaireAccount;
import com.wr.api.owner.service.estateapi.busQuestionnaire.BusQuestionnaireAccountService;
import com.wr.api.owner.service.estateapi.busQuestionnaire.BusQuestionnaireService;
import com.wr.api.owner.service.estateapi.useraccountmanage.AccountManageService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.estate.manage.contract.Account;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/10 14:23
 */
@RestController
@RequestMapping("/estateapi/busQuestionnaireAccount")
@Api(tags = "业主端-问卷调查人员表")
public class BusQuestionnaireAccountController extends BaseController {

    @Resource
    private BusQuestionnaireAccountService questionnaireAccountService;
    @Resource
    private AccountManageService accountManageService;
    @Resource
    private BusQuestionnaireService questionnaireService;


    /**
     * 分页
     *
     * @param busQuestionnaireAccount 查询对象
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(BusQuestionnaireAccount busQuestionnaireAccount) {
        LambdaQueryWrapper<BusQuestionnaireAccount> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq( BusQuestionnaireAccount::getQuestionnaireInvestigationId, busQuestionnaireAccount.getQuestionnaireInvestigationId() );
        queryWrapper.select( BusQuestionnaireAccount.class, r -> !r.getColumn().equals( "content" ) );
        queryWrapper.orderByDesc( BusQuestionnaireAccount::getCreateTime );
        startPage();
        List<BusQuestionnaireAccount> rescueVOList = questionnaireAccountService.list( queryWrapper );
        if (CollectionUtils.isNotEmpty( rescueVOList )) {
            rescueVOList.forEach( questionnaireAccount -> {
                Account account = accountManageService.getByAccountId( questionnaireAccount.getAccountId() );
                questionnaireAccount.setUserName( account.getAccountName() );
                questionnaireAccount.setUserPhone( account.getLoginName() );
            } );
        }
        return getDataTable( rescueVOList );
    }

    /**
     * 详情
     *
     * @param id 查询id
     * @return
     */
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable String id) {

        return AjaxResult.success( questionnaireAccountService.getById( id ) );
    }

    /**
     * 问卷调查答题保存
     *
     * @param busQuestionnaireAccount 答题内容
     * @return
     */
    @PostMapping("/savequestionnaireAccount")
    public AjaxResult savequestionnaireAccount(@RequestBody BusQuestionnaireAccount busQuestionnaireAccount) {
        busQuestionnaireAccount.setUpdateTime( new Date() );
        boolean save = questionnaireAccountService.save( busQuestionnaireAccount );
        if(save){
            BusQuestionnaire questionnaire = new BusQuestionnaire();
            questionnaire.setId( busQuestionnaireAccount.getQuestionnaireInvestigationId() );
            questionnaire.setStatus( "2" );
            questionnaire.setUpdateTime( new Date() );
            questionnaireService.updateById(questionnaire);
        }
        return AjaxResult.success( save );
    }

    /**
     * 获取问卷答题详情接口
     *
     * @param busQuestionnaireAccount 查询id
     * @return
     */
    @PostMapping("/findOneAccount")
    public AjaxResult findOneAccount(@RequestBody BusQuestionnaireAccount busQuestionnaireAccount) {
        LambdaQueryWrapper<BusQuestionnaireAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq( BusQuestionnaireAccount::getAccountId,busQuestionnaireAccount.getAccountId() )
                .eq( BusQuestionnaireAccount::getCommunityId,busQuestionnaireAccount.getCommunityId() )
                .eq( BusQuestionnaireAccount::getQuestionnaireInvestigationId,busQuestionnaireAccount.getQuestionnaireInvestigationId() );

        BusQuestionnaireAccount accountServiceOne = questionnaireAccountService.getOne( wrapper );
        return AjaxResult.success( accountServiceOne );
    }

    /**
     * 问卷调查更新接口
     *
     * @param busQuestionnaireAccount 查询id
     * @return
     */
    @PostMapping("/updatequestionnaireAccount")
    public AjaxResult updatequestionnaireAccount(@RequestBody BusQuestionnaireAccount busQuestionnaireAccount) {
        boolean update = questionnaireAccountService.updateById( busQuestionnaireAccount );
        return AjaxResult.success( update?"更新成功":"更新失败,请刷新重新提交" );
    }
}
