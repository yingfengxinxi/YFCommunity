package com.wr.aiot.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wr.aiot.mapper.CommunityMapper;
import com.wr.aiot.mapper.VisualMapper;
import com.wr.aiot.service.VisualService;
import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.CommunityConstants;
import com.wr.common.customize.constant.HikConstants;
import com.wr.common.customize.util.HaiKUtils;
import com.wr.common.customize.util.HikUtils;
import com.wr.common.customize.util.QrCodeUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.Visual;
import com.wr.common.customize.util.CommunityHik;
import com.wr.remote.domain.SysFile;
import com.wr.remote.system.RemoteFileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @Author: RainCity
 * @Date: 2022-10-26 11:19:25
 * @Desc: 可视对讲业务处理
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(rollbackFor = Exception.class)
public class VisualServiceImpl implements VisualService {
    private static final Logger log = LoggerFactory.getLogger(VisualServiceImpl.class);

    private final VisualMapper visualMapper;
    private final CommunityMapper communityMapper;
    private final RemoteFileService remoteFileService;

    @Override
    @Transactional(readOnly = false)
    public List<Visual> selectVisualList(Visual visual) {
        if (null == visual.getCommunityId() || 0L == visual.getCommunityId()) {
            return Lists.newArrayList();
        }
        return visualMapper.selectVisualList(visual);
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult syncVisualData(Long communityId) throws Exception {
        if (null == communityId || 0L == communityId) {
            return AjaxResult.error("请选择小区！");
        }
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if (null == hik) {
            return AjaxResult.error("请检查小区海康配置后重试！");
        }
        String renewalBy = SecurityUtils.getUsername();
        int pageNo = 1;
        int pageSize = 1000;
        Integer total = HaiKUtils.visDeviceSearchTotal(hik);
        pageNo = (int) Math.ceil((double) total / pageSize);
        int num = 0;
        for (int i = 0; i < pageNo; i++) {
            JSONArray dataList = HaiKUtils.visDeviceSearch(hik, (i + 1), pageSize);
            num = num + dataList.size();
            log.info("可视对讲设备数据同步：第{}页，共：{}条数据", (i + 1), dataList.size());

            for (int j = 0; j < dataList.size(); j++) {
                JSONObject jsonObject = dataList.getJSONObject(j);
                if (jsonObject != null) {
                    extracted(jsonObject);
                    Visual visual = JSONObject.parseObject(jsonObject.toJSONString(), Visual.class);
                    System.out.println(visual);
                    visual.setRenewalTime(new Date());
                    visual.setRenewalBy(renewalBy);
                    Integer status = HaiKUtils.visDeviceOnline(hik, visual.getIndexCode());
                    int online = Optional.ofNullable(status).orElse(-1);
                    visual.setOnline(online);
                    visual.setCommunityId(hik.getCommunityId());
                    Visual visual1 = visualMapper.getByIndexCodeInfo(visual.getIndexCode());
                    if (visual1 != null) {
                        visual.setVisualId(visual1.getVisualId());
                        visualMapper.update(visual);
                    } else {
                        visualMapper.insert(visual);
                    }
                }
            }
        }
        visualMapper.delete();
        return num > 0 ? AjaxResult.success("已同步" + num + "条可视对讲数据") : AjaxResult.error("数据同步失败");
    }


    private static void extracted(JSONObject jsonObject) {

        String createTime = String.valueOf(jsonObject.get("createTime"));
        createTime = createTime.replace("T", " ").replace("+08:00", "");
        createTime = createTime.substring(0, 19);
        jsonObject.put("createTime", createTime);
        String updateTime = String.valueOf(jsonObject.get("updateTime"));
        updateTime = updateTime.replace("T", " ").replace("+08:00", "");
        updateTime = updateTime.substring(0, 19);
        jsonObject.put("updateTime", updateTime);

    }


    @Override
    @Transactional(readOnly = false)
    public AjaxResult checkOnline(Visual visual) throws Exception {
        if (null == visual.getCommunityId() || 0L == visual.getCommunityId()) {
            return AjaxResult.error("请选择小区！");
        }
        CommunityHik hik = communityMapper.selectHikByCommunityId(visual.getCommunityId());
        if (null == hik) {
            return AjaxResult.error("请检查小区海康配置后重试！");
        }

        List<Visual> visualList = Lists.newArrayList();
        for (String indexCode : visual.getIndexCodeList()) {
            Integer status = HaiKUtils.visDeviceOnline(hik, indexCode);
            int online = Optional.ofNullable(status).orElse(-1);
            Visual temp = new Visual();
            temp.setIndexCode(indexCode);
            temp.setOnline(online);
            visualList.add(temp);
        }
        if (!visualList.isEmpty()) {
            //修改可视对讲状态
            visualMapper.updateVisualOnline(visualList);
        }
        return AjaxResult.success();
    }

    @Override
    public Visual selectVisualById(Long visualId) {

        return visualMapper.selectVisualById(visualId);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateVisual(Visual visual) {

        return visualMapper.updateVisual(visual);
    }

    @Override
    public Integer batchUpdateDeviceQr(Visual visual) {
        List<Visual> visualList;
        if (visual.getIds().length == 0) {
            // 查询未设置设备二维码的设备id集
            visualList = visualMapper.selectIdsOfNotQrCode();
        } else {
            visualList = visualMapper.selectVisualByIds(Arrays.asList(visual.getIds()));
        }
        return visualMapper.batchUpdateDeviceQr(packageVisual(visualList));
    }

    @Override
    public AjaxResult subscribeLock(Long communityId) {
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if (null == hik) {
            return AjaxResult.error("请检查小区海康配置后重试！");
        }
        HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
        String eventDest = CommunityConstants.GATEWAY_URL + "/api/device/visOpen";
        boolean result = HikUtils.eventSubscription(HikConstants.EVENT_VIS_INOUT, eventDest, 0);
        return result ? AjaxResult.success("订阅成功！") : AjaxResult.error("订阅失败！");
    }

    @Override
    public AjaxResult unsubscribeLock(Long communityId) {
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if (null == hik) {
            return AjaxResult.error("请检查小区海康配置后重试！");
        }
        HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
        boolean result = HikUtils.eventUnSubscription(HikConstants.EVENT_VIS_INOUT);
        return result ? AjaxResult.success("退订成功！") : AjaxResult.error("退订失败！");
    }

    /**
     * 封装二维码数据
     *
     * @param visuals 设备信息
     * @return java.util.List<com.wr.remote.aiot.Visual>
     */
    private List<Visual> packageVisual(List<Visual> visuals) {
        visuals.forEach(visual -> {
            JSONObject json = JSONObject.of();
            json.put("id", visual.getIndexCode());
            json.put("deviceType", "visDevice");
            json.put("type", "device");
            try {
                MultipartFile file = QrCodeUtils.createQrCode(json.toJSONString(), null, visual.getVisualName());
                R<SysFile> fileResult = remoteFileService.upload(file);
                if (StringUtils.isNotNull(fileResult) && StringUtils.isNotNull(fileResult.getData())) {
                    String url = fileResult.getData().getUrl();
                    visual.setDeviceQr(url);
                }
            } catch (Exception e) {
                log.error("二维码生成失败：{}", e.getMessage());
            }
        });

        return visuals;
    }
}
