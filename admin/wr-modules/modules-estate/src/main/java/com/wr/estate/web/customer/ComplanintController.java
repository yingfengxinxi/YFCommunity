package com.wr.estate.web;

import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.estate.service.ComplanintService;
import com.wr.remote.estate.custom.vo.ComplaintDeatilVO;
import com.wr.remote.estate.custom.vo.ComplanintPraiseVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 荀彧
 * @Date: 2022-10-11 15:52:01
 * @Desc: 投诉表扬controller
 */
@RestController
@RequestMapping("/complanint")
public class ComplanintController extends BaseController {

    @Resource
    private ComplanintService complanintService;

    /**
     * 表扬
     * @param complanintPraiseVO 查询参数
     * @return 分页数据
     */
    @GetMapping("/praise")
    @PreAuthorize("@ss.hasPermi('bussiness:praise:list')")
    public TableDataInfo getComplanint(ComplanintPraiseVO complanintPraiseVO){
        startPage();
        List<ComplanintPraiseVO> list = complanintService.getAllComplanint(complanintPraiseVO);
        return getDataTable(list);
    }

    /**
     * 根据主键查询
     * @return  查询的数据
     */
    @GetMapping("/praiseDetail/{complaintId}")
    @PreAuthorize("@ss.hasPermi('bussiness:praise:list')")
    public AjaxResult getComplanintById(@PathVariable("complaintId") Long complaintId){
        if (null == complaintId){
            return AjaxResult.error("系统错误，请联系管理员。");
        }
        return AjaxResult.success(complanintService.getDetailById(complaintId));
    }


    /**
     * 新增
     * @param complaintId
     * @return
     */
    @Log(title = "公示新增", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('bussiness:praise:add')")
    @PostMapping("/add/{complaintId}")
    public AjaxResult add(@PathVariable("complaintId") Long complaintId){
        Integer num = complanintService.upPublicStatus(complaintId);
        return num > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * 根据主键查询已公告
     * @return
     */
    @GetMapping("/publicDetail/{complaintId}")
    @PreAuthorize("@ss.hasPermi('bussiness:praise:list')")
    public AjaxResult publicDetail(@PathVariable("complaintId") Long complaintId){
        if (null == complaintId){
            return AjaxResult.error("系统错误，请联系管理员。");
        }
        return AjaxResult.success(complanintService.getPublicDetails(complaintId));
    }
}
