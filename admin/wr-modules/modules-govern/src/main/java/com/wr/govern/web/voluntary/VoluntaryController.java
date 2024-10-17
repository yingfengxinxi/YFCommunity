package com.wr.govern.web.voluntary;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.poi.ExcelUtil;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.govern.voluntary.vo.VoluntaryUserVo;
import com.wr.govern.service.voluntary.VoluntaryService;
import com.wr.remote.domain.SysUser;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: lvzy
 * @Date: 2022-10-25 10:14:09
 * @Desc:
 */
@RequestMapping("voluntary")
@RestController
public class VoluntaryController extends BaseController {

    @Resource
    private VoluntaryService voluntaryService;

    /**
     * 查询
     * @param params
     * @return
     */
    @GetMapping("searchList")
    public TableDataInfo searchList(@RequestParam Map<String,Object> params){

        if (StringUtils.isNotNull(params.get("dateTime"))){

            String time = String.valueOf(params.get("dateTime"));
            params.put("time",time.split(","));
        }
        startPage();
        return getDataTable(voluntaryService.searchList(params));
    }
    @RequiresPermissions("voluntary:service:update")
    @Log(title = "修改志愿者活动", businessType = BusinessType.UPDATE)
    @PostMapping("updateVoluntary")
    public AjaxResult updateVoluntary(@RequestBody Map<String,Object> params){

        return AjaxResult.success(voluntaryService.updateVoluntary(params));
    }
    @RequiresPermissions("voluntary:service:save")
    @Log(title = "新增志愿者活动", businessType = BusinessType.INSERT)
    @PostMapping("saveVoluntary")
    public AjaxResult saveVoluntary(@RequestBody Map<String,Object> params){

        return AjaxResult.success(voluntaryService.saveVoluntary(params));
    }
    @RequiresPermissions("voluntary:service:remove")
    @Log(title = "删除志愿者活动", businessType = BusinessType.DELETE)
    @PostMapping("removeVoluntary")
    public AjaxResult removeVoluntary(@RequestBody Long id){

        return AjaxResult.success(voluntaryService.removeVoluntary(id));
    }
    /**
     * 查询 活动 下 的人员
     * @param params
     * @return
     */
    @GetMapping("searchListUser")
    public TableDataInfo searchListUser(@RequestParam Map<String,Object> params){
        startPage();
        return getDataTable(voluntaryService.searchListUser(params));
    }
    /**
     * 导出
     */
    @RequiresPermissions("voluntary:service:export")
    @Log(title = "志愿者信息导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response,Long id){

        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        if(null == sysUser){
            return;
        }
        Map<String,Object> params=new HashMap<>();
        params.put("voluntary_id",id);
        List<VoluntaryUserVo> voArrList = new ArrayList<>();
        List<LinkedHashMap<String, Object>> list = voluntaryService.searchListUser(params);
        list.forEach(e->{

            VoluntaryUserVo vo = new VoluntaryUserVo();
            vo.setUserName(String.valueOf(e.get("user_name")));
            vo.setCommunityName(String.valueOf(e.get("community_name")));
            vo.setActTitle(String.valueOf(e.get("act_title")));
            vo.setGender(String.valueOf(e.get("gender")));
            vo.setUserAge(Integer.valueOf(String.valueOf(e.get("user_age"))));
            vo.setCard_no(String.valueOf(e.get("card_no")));
            vo.setDictLabel(String.valueOf(e.get("dict_label")));
            vo.setUserPhone(String.valueOf(e.get("user_phone")));
            vo.setApplyTime(String.valueOf(e.get("apply_time")).replaceAll("T"," "));
            voArrList.add(vo);
        });
        ExcelUtil<VoluntaryUserVo> util = new ExcelUtil(VoluntaryUserVo.class);
        util.exportExcel(response, voArrList, "志愿者信息数据");

    }
}
