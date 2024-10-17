package com.wr.api.owner.web.home.advice;

import com.wr.api.owner.entity.home.advice.Complaint;
import com.wr.api.owner.service.home.advice.ComplaintService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;


@RestController
@RequestMapping("/v2/complain")
public class ComplaintController extends BaseController {

    @Autowired
    private ComplaintService complainService;

    /**
     * 查询投诉表扬列表
     *
     * @param complaint
     * @return
     * @throws ParseException
     */
    @AuthRequired
    @GetMapping("/list")
    public TableDataInfo list(Complaint complaint) throws ParseException {
        if (ObjectUtils.allNull(complaint.getAccountId())){
            return new TableDataInfo();
        }
        startPage();
        List<Complaint> list = complainService.selectComplaintList(complaint);
        return getDataTable(list);
    }

    /**
     * 根据id查投诉表扬详情
     *
     * @param complaintId
     * @return
     */
    @AuthRequired
    @GetMapping(value = "/complainOne")
    public AjaxResult getHeadlineById(Long complaintId) {
        if (ObjectUtils.isEmpty(complaintId)) {
            return AjaxResult.error("参数不能为空");
        }
        Complaint complaint = complainService.getComplaintById(complaintId);
        return AjaxResult.success(complaint);
    }

    /**
     * 新增投诉表扬数据
     *
     * @param complaint
     * @return
     */

    @ApiLog(title="投诉表扬-新增", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(@RequestBody Complaint complaint) {
        Integer row = complainService.insertComplaint(complaint);
        return row > 0 ? AjaxResult.success() : AjaxResult.error();
    }


}
