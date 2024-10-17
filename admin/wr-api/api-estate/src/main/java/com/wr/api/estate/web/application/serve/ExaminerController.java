package com.wr.api.estate.web.application.serve;

import com.wr.api.estate.entity.vo.application.serve.ExaminerVO;
import com.wr.api.estate.entity.vo.application.serve.MedicalVO;
import com.wr.api.estate.service.application.serve.ExaminerService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-16 10:34:25
 * @Desc: 控制层
 */
@RestController
@RequestMapping("/v2/examiner")
public class ExaminerController extends BaseController {
    @Autowired
    private ExaminerService examinerService;

    /**
     * 获取业主体检报告
     * @param communityId 小区id
     * @param userPhone 手机号
     * @return:
     */
    @AuthRequired
    @GetMapping("/list")
    public AjaxResult list(Long communityId, String userPhone) {
        List<ExaminerVO> examinerAll = examinerService.getExaminerAll(communityId, "");
        return AjaxResult.success(examinerAll);
    }

    /**
     * 医疗检测通告列表
     *
     * @param communityId 社区id
     * @return {@code AjaxResult}
     */
    @AuthRequired
    @GetMapping("/listMedical")
    public AjaxResult listMedical(Long communityId) {
        List<MedicalVO> medicalVO = examinerService.listMedical(communityId);
        return AjaxResult.success(medicalVO);
    }

    /**
     * 检查列表
     *
     * @param medicalId 医疗标识
     * @return {@code AjaxResult}
     */
    @AuthRequired
    @GetMapping("/listExaminer")
    public AjaxResult listExaminer(Long medicalId) {
        List<ExaminerVO> examinerVOList = examinerService.listExaminerByMedicalId(medicalId);
        return AjaxResult.success(examinerVOList);
    }



}
