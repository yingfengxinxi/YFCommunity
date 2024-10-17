package com.wr.estate.web.busQuestionnaire;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.BusQuestionnaire;
import com.wr.estate.entity.BusQuestionnaireAccount;
import com.wr.estate.service.busQuestionnaire.BusQuestionnaireAccountService;
import com.wr.estate.service.busQuestionnaire.BusQuestionnaireService;
import com.wr.estate.service.center.useraccountmanage.AccountManageService;
import com.wr.estate.service.financial.AccountManagementService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.estate.manage.contract.Account;
import com.wr.system.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/10 14:23
 */
@RestController
@RequestMapping("/busQuestionnaireAccount")
@Api(tags = "物管平台-问卷调查人员表")
public class BusQuestionnaireAccountController extends BaseController {

    @Autowired
    private BusQuestionnaireAccountService questionnaireAccountService;


    /**
     * 分页
     *
     * @param busQuestionnaireAccount 查询对象
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo list(BusQuestionnaireAccount busQuestionnaireAccount) {
        startPage();
        List<BusQuestionnaireAccount> rescueVOList = questionnaireAccountService.getList(busQuestionnaireAccount.getQuestionnaireInvestigationId());
        return getDataTable(rescueVOList);
    }

    /**
     * 详情
     *
     * @param id 查询id
     * @return
     */
    @GetMapping("/{id}")
    public AjaxResult getById(@PathVariable String id) {

        return AjaxResult.success(questionnaireAccountService.getById(id));
    }
}
