package com.wr.estate.web.manage.assess;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.constant.CommunityConstants;
import com.wr.common.customize.util.HttpClientUtil;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.estate.assess.vo.AssessVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-15 16:27:25
 * @Desc: 考核管理控制层
 */
@RestController
@RequestMapping("/assess")
public class AssessController extends BaseController {


    /**
     * 查询考核管理列表
     *
     * @param
     * @return
     */
    @RequiresPermissions("estate:assess:list")
    @GetMapping("/list")
    public TableDataInfo list(AssessVO assessVO,
                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                              @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        TableDataInfo tableData = new TableDataInfo();
        String url = CommunityConstants.OFFICE_SERVER_URL + "/assess/list";
        List<AssessVO> dataList = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("pageSize", pageSize);
            map.put("pageNum", pageNum);
            map.put("estateId", SecurityUtils.getLoginUser().getSysUser().getEstateId());
            if (assessVO.getParams().size() > 0) {
                map.put("startTime", assessVO.getParams().get("beginTime"));
                map.put("endTime", assessVO.getParams().get("endTime"));
            }
            if (assessVO.getAssessName() != null) {
                map.put("assessName", assessVO.getAssessName());
            }

            String result = HttpClientUtil.get(url, map);
            String data = JSONObject.parseObject(result).getString("rows");
            dataList = JSON.parseArray(data, AssessVO.class);
            tableData.setTotal(Long.parseLong(JSONObject.parseObject(result).getString("total")));
            tableData.setRows(dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableData;
    }

    @RequiresPermissions("estate:assess:list")
    @GetMapping("/detail/{assessId}")
    public AjaxResult getAssessRecordDetail(@PathVariable Long assessId) {
        AjaxResult ajaxResult = AjaxResult.success();
        String url = CommunityConstants.OFFICE_SERVER_URL + "/assess/detail/" + assessId;
        AssessVO resultData = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("estateId", SecurityUtils.getLoginUser().getSysUser().getEstateId());
            String result = HttpClientUtil.get(url, map);

            String data = JSONObject.parseObject(result).getString("data");
            resultData = JSON.parseObject(data, AssessVO.class);

            String assessItemResult = JSONObject.parseObject(result).getString("resultMap");
            Map<String, Object> resultMap = JSON.parseObject(assessItemResult, Map.class);

            String sumResult = JSONObject.parseObject(result).getString("sumCount");
            Map<String, Object> sumCount = JSON.parseObject(sumResult, Map.class);

            String assessStatus = null;
            if (resultData != null && resultData.getStartTime() != null && resultData.getEndTime() != null) {
                long now = System.currentTimeMillis();
                if (now < resultData.getStartTime().getTime()) {
                    assessStatus = "未开始";
                    resultMap.replaceAll((k, v) -> "/");
                    sumCount.replaceAll((k, v) -> "/");
                } else if (now > resultData.getEndTime().getTime()) {
                    assessStatus = "已结束";
                } else {
                    assessStatus = "进行中";
                }
            }
            ajaxResult.put(AjaxResult.DATA_TAG, resultData);
            ajaxResult.put("resultMap", resultMap);
            ajaxResult.put("sumCount", sumCount);
            ajaxResult.put("assessStatus", assessStatus);
            return ajaxResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error();
    }
}

