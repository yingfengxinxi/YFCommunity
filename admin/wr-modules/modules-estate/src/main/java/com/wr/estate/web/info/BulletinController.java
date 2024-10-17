package com.wr.estate.web.info;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.constant.CommunityConstants;
import com.wr.common.customize.util.HttpClientUtil;
import com.wr.common.security.annotation.RequiresPermissions;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.estate.info.Bulletin;
import com.wr.remote.estate.info.vo.BulletinVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-10 15:06:32
 * @Desc: 物业通告控制层
 */
@RestController
@RequestMapping("/bulletin")
public class BulletinController extends BaseController {

    /**
     * 获取物业通告列表
     *
     * @param bulletin
     * @return
     */
    @RequiresPermissions("estate:bulletin:list")
    @GetMapping("/list")
    public TableDataInfo list(Bulletin bulletin,
                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                              @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum) {
        TableDataInfo tableData = new TableDataInfo();
        String url = CommunityConstants.OFFICE_SERVER_URL + "/bulletin/list";
        List<Bulletin> dataList = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("pageSize", pageSize);
            map.put("pageNum", pageNum);
            if (bulletin.getBulletinTitle() != null) {
                map.put("bulletinTitle", bulletin.getBulletinTitle());
            }
            map.put("estateId", SecurityUtils.getLoginUser().getSysUser().getEstateId());
            String result = HttpClientUtil.get(url, map);
            String data = JSONObject.parseObject(result).getString("rows").toString();
            dataList = JSON.parseArray(data, Bulletin.class);
            tableData.setTotal(Long.parseLong(JSONObject.parseObject(result).getString("total")));
            tableData.setRows(dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableData;
    }

    /**
     * 获取物业通告详情
     *
     * @param
     * @return
     */
    @RequiresPermissions("estate:bulletin:list")
    @GetMapping("/detail")
    public AjaxResult list(String bulletinId) {
        String url = CommunityConstants.OFFICE_SERVER_URL + "/bulletin/detail";
        BulletinVO bulletinVO = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("bulletinId", bulletinId);
            String result = HttpClientUtil.get(url, map);
            String data = JSONObject.parseObject(result).getString("data").toString();
            bulletinVO = JSON.parseObject(data, BulletinVO.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success(bulletinVO);
    }
}
