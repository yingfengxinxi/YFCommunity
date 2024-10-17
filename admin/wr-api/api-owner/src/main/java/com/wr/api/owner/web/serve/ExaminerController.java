package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.vo.serve.ExaminerVO;
import com.wr.api.owner.service.serve.ExaminerService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.page.TableDataInfo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: SJiang
 * @Date: 2022-11-11 11:32:18
 * @Desc: 服务-体检报告 控制层
 */
@RestController
@RequestMapping("/v2/examiner")
public class ExaminerController extends BaseController {
    @Autowired
    private ExaminerService examinerService;

    /**
     * 获取业主体检报告
     *
     * @param communityId 小区id
     * @param idCard      身份证号
     * @return: com.wr.common.core.web.page.TableDataInfo
     */
    @AuthRequired
    @GetMapping("/list")
    public TableDataInfo list(Long communityId, String idCard) {
        if (StringUtils.isBlank(idCard) || ObjectUtils.isEmpty(communityId)) {
            return new TableDataInfo();
        }
        startPage();
        List<ExaminerVO> examinerAll = examinerService.getExaminerAll(communityId, idCard);
        return getDataTable(examinerAll);
    }
}
