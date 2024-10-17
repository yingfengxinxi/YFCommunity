package com.wr.api.estate.web.application.serve;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wr.api.estate.entity.vo.application.serve.AssessDetailVo;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.CommunityConstants;
import com.wr.common.customize.util.HttpClientUtil;
import com.wr.common.customize.util.JsonUtil;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: lvzy
 * @Date: 2022-11-16 16:45:04
 * @Desc: 考核管理
 */
@RestController
@RequestMapping("/v2/assess")
public class AssessController {


    @GetMapping("/search")
    @AuthRequired
    public JSONObject search(@RequestParam Map<String,Object> params) throws Exception {
            String url= CommunityConstants.OFFICE_SERVER_URL+"/assess/manage/search";
        String result = HttpClientUtil.get(url, params);
        JSONObject jsonObject = JsonUtil.parseObject(result);

        return jsonObject;
    }
    @GetMapping("/searchRecord")
    @AuthRequired
    public AjaxResult searchRecord() throws Exception {

        String url=CommunityConstants.OFFICE_SERVER_URL+"/assess/manage/searchRecord";
        String result = HttpClientUtil.get(url);
        JSONObject jsonObject = JsonUtil.parseObject(result);

        return  AjaxResult.jsonArray(jsonObject);
    }
    @GetMapping("/score")
    @AuthRequired
    public AjaxResult score(@RequestParam("assessId") Long assessId,@RequestParam("estateId") Long estateId) throws Exception {

        String url=CommunityConstants.OFFICE_SERVER_URL+"/assess/manage/score";
        String result = HttpClientUtil.get(url,new HashMap<String, Object>(2){{
            put("assessId",assessId);
            put("estateId",estateId);
        }});
        JSONObject jsonObject = JsonUtil.parseObject(result);

        return  AjaxResult.jsonObject(jsonObject);
    }
    @ApiLog(title = "物业端-员工考核打分", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    @AuthRequired
    public JSONObject save(@RequestBody Map<String,Object> params) throws Exception {
        String url=CommunityConstants.OFFICE_SERVER_URL+"/assess/manage/save";
        String result = HttpClientUtil.post(url, params);
        JSONObject jsonObject = JsonUtil.parseObject(result);

        return jsonObject;
    }
    @GetMapping("/ranking")
    @AuthRequired
    public JSONObject ranking(Long assessId) throws Exception {

        String url=CommunityConstants.OFFICE_SERVER_URL+"/assess/manage/ranking";
        String result = HttpClientUtil.get(url,new HashMap<String, Object>(1){{
            put("assessId",assessId);
        }});
        JSONObject jsonObject = JsonUtil.parseObject(result);

        return  jsonObject;
    }
    @GetMapping("/count")
    @AuthRequired
    public AjaxResult countNum(Long assessId,Long userId) throws Exception {

        String url=CommunityConstants.OFFICE_SERVER_URL+"/assess/manage/count";
        String result = HttpClientUtil.get(url,new HashMap<String, Object>(2){{
            put("assessId",assessId);
            put("userId",userId);
        }});
        JSONObject jsonObject = JsonUtil.parseObject(result);

        return  AjaxResult.jsonObject(jsonObject);
    }
    @GetMapping("/detail")
    @AuthRequired
    public AjaxResult detail(@RequestParam("assessId") Long assessId,@RequestParam("estateId") Long estateId) throws Exception {

        String url=CommunityConstants.OFFICE_SERVER_URL+"/assess/manage/detail";
        String result = HttpClientUtil.get(url,new HashMap<String, Object>(1){{
            put("assessId",assessId);
        }});
        JSONObject jsonObject = JSONObject.parseObject(result);
        List<AssessDetailVo> list = jsonObject.getList("assessDetailVoList", AssessDetailVo.class);

        AssessDetailVo vo=new AssessDetailVo();
        if (StringUtils.isNotNull(list)){
            list.stream().filter(e -> e.getEstateId().equals(estateId)).collect(Collectors.toList()).forEach(e->{
                vo.setNightOrder(e.getNightOrder());
                vo.setAssessment(e.getAssessment());
                vo.setInspectionOrder(e.getInspectionOrder());
                vo.setPatrolOrder(e.getPatrolOrder());
                vo.setScore(e.getScore());
            });
        }
        Map<String,Object> data=new HashMap<>(3);
        data.put("score",vo);
        data.put("assess",jsonObject.get("data"));
        data.put("ranking",list);
        return AjaxResult.success(data);
    }
}
