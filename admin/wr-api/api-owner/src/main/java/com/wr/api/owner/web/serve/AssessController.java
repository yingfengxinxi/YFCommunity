package com.wr.api.owner.web.serve;

import com.alibaba.fastjson2.JSONObject;
import com.wr.api.owner.entity.serve.VoteRecord;
import com.wr.api.owner.service.CommunityService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.constant.HttpStatus;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.CommunityConstants;
import com.wr.common.customize.util.HttpClientUtil;
import com.wr.common.customize.util.JsonUtil;
import com.wr.remote.domain.Estate;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: SJiang
 * @Date: 2022-11-16 14:47:17
 * @Desc: 服务-投票中心 控制层
 */
@RestController
@RequestMapping("/v2/assess")
public class AssessController extends BaseController {

    @Autowired
    private CommunityService communityService;

    /**
     * 获取考核数据
     *
     * @param estateId
     * @return: com.wr.common.core.web.page.TableDataInfo
     */
    @AuthRequired
    @GetMapping("/list")
    public AjaxResult list(Long estateId, String userType, Long userId) {
        //物业名称
        if (StringUtils.isNull(estateId)) {
            return AjaxResult.error("参数不能为空");
        }
        try {
            String url = CommunityConstants.OFFICE_SERVER_URL + "/assessTwo/list";
            Map<String, Object> params = new HashMap<>();
            //物业id
            params.put("estateId", estateId);
            params.put("userType", userType);
            params.put("userId", userId);
            String result = HttpClientUtil.get(url, params);
            JSONObject jsonObject = JsonUtil.parseObject(result);
            return AjaxResult.jsonArray(jsonObject);
        } catch (Exception e) {
            logger.error("获取投票考核信息失败：{}", e.getMessage());
            return AjaxResult.error();
        }
    }

    /**
     * 获取投票标准数据
     *
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @AuthRequired
    @GetMapping("/voteList")
    public AjaxResult voteList() {
        try {
            String url = CommunityConstants.OFFICE_SERVER_URL + "/assessTwo/voteList";
            String result = HttpClientUtil.get(url);
            JSONObject jsonObject = JsonUtil.parseObject(result);
            return AjaxResult.jsonArray(jsonObject);
        } catch (Exception e) {
            logger.error("获取投票标准信息失败：{}", e.getMessage());
            return AjaxResult.error();
        }
    }

    /**
     * 投票
     *
     * @return: com.wr.common.core.web.domain.AjaxResult
     */
    @PostMapping("/add")
    public AjaxResult add(@RequestBody VoteRecord voteRecord) {
        // 获取物业信息
        Estate estate = communityService.getEstate(voteRecord.getEstateId());
        if (ObjectUtils.isEmpty(estate)){
            return AjaxResult.error("系统错误请联系管理员，未获取到物业信息");
        }
        try {
            String url = CommunityConstants.OFFICE_SERVER_URL + "/assessTwo/add";
            Map<String, Object> params = new HashMap<>();
            // 物业id
            params.put("estateId", voteRecord.getEstateId());
            // 物业名称
            params.put("estateName", estate.getEstateName());
            // 用户类型
            params.put("userType", voteRecord.getUserType());
            // 用户id
            params.put("userId", voteRecord.getUserId());
            // 用户姓名
            params.put("userName", voteRecord.getUserName());
            // 用户手机号
            params.put("userPhone", voteRecord.getUserPhone());
            // 考核id
            params.put("assessId", voteRecord.getAssessId());
            // 得分params = {HashMap@16876}  size = 8
            params.put("assessScore", voteRecord.getAssessScore());
            String result = HttpClientUtil.post(url, JSONObject.toJSONString(params),"application/json");
            JSONObject jsonObject = JsonUtil.parseObject(result);
            return AjaxResult.jsonObject(jsonObject);
        } catch (Exception e) {
            logger.error("添加投票失败：{}", e.getMessage());
            return AjaxResult.error();
        }
    }

    /**
     * 获取已投票分数
     * @param userType 用户类型
     * @param userId 用户id
     * @param assessId 考核id
     * @return: java.math.BigDecimal
     */
    @AuthRequired
    @GetMapping("/getScore")
    public AjaxResult getScore(String userType, Long userId, Long assessId) {
        if (StringUtils.isBlank(userType) || StringUtils.isNull(userId) || StringUtils.isNull(assessId)){
            return AjaxResult.error("参数不能为空");
        }
        try {
            String url = CommunityConstants.OFFICE_SERVER_URL + "/assessTwo/getScore";
            Map<String, Object> params = new HashMap<>();
            params.put("userType", userType);
            params.put("userId", userId);
            params.put("assessId", assessId);
            String result = HttpClientUtil.get(url,params);
            JSONObject jsonObject = JsonUtil.parseObject(result);
            if(null == result){
                return AjaxResult.error("未查询到相关数据");
            }
            int code = jsonObject.getInteger("code");
            if(code != HttpStatus.SUCCESS){
                return AjaxResult.error(jsonObject.getString("msg"));
            }
            return AjaxResult.success(jsonObject.get("data"));
        } catch (Exception e) {
            logger.error("获取考核分数失败：{}", e.getMessage());
            return AjaxResult.error();
        }
    }
}
