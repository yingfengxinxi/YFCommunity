package com.wr.aiot.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wr.aiot.mapper.BarrierMapper;
import com.wr.aiot.mapper.CommunityMapper;
import com.wr.aiot.service.BarrierService;
import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.util.CommunityHik;
import com.wr.common.customize.util.HaiKUtils;
import com.wr.common.customize.util.QrCodeUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.aiot.Barrier;
import com.wr.remote.aiot.Roadway;
import com.wr.remote.domain.SysFile;
import com.wr.remote.system.RemoteFileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: RainCity
 * @Date: 2022-10-27 10:47:26
 * @Desc: 道闸数据业务处理
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class BarrierServiceImpl implements BarrierService {
    private static final Logger log = LoggerFactory.getLogger(BarrierServiceImpl.class);

    private final CommunityMapper communityMapper;
    private final BarrierMapper barrierMapper;
    private final RemoteFileService remoteFileService;

    @Override
    public List<Barrier> selectBarrierList(Barrier barrier) {

        return barrierMapper.selectBarrierList(barrier);
    }

    @Override
    @Transactional(readOnly = false)
    public AjaxResult syncBarrierData(Long communityId) {
        if (null == communityId || 0L == communityId) {
            return AjaxResult.error("请选择小区！");
        }
        CommunityHik hik = communityMapper.selectHikByCommunityId(communityId);
        if (null == hik) {
            return AjaxResult.error("请检查小区海康配置后重试！");
        }
        String renewalBy = SecurityUtils.getUsername();
        // HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
        int pageNo = 1;
        int pageSize = 1000;
        int num = 0;
        String resourceType="snapAio";
        Integer total = HaiKUtils.deviceResourceTotal(hik,resourceType);
        if (0 == total) {
            return AjaxResult.error("未查询到道闸信息");
        }
        Map<String, Roadway> indexCodeMap = barrierMapper.getIndexCode();
        pageNo = (int) Math.ceil((double) total / pageSize);
        for (int i = 0; i < pageNo; i++) {
            JSONArray dataList = HaiKUtils.deviceResource(hik, (i + 1), pageSize,resourceType);
            for (int j = 0; j < dataList.size(); j++) {
                String indexCode = String.valueOf(dataList.getJSONObject(j).get("indexCode"));
                String updateTime = String.valueOf(dataList.getJSONObject(j).get("updateTime"));
                String createTime = String.valueOf(dataList.getJSONObject(j).get("createTime"));
                updateTime = updateTime.replace("T", " ").replace("+08:00", "");
                createTime = createTime.replace("T", " ").replace("+08:00", "");
                dataList.getJSONObject(j).put("updateTime",updateTime);
                dataList.getJSONObject(j).put("createTime",createTime);
                if (null != indexCodeMap.get(indexCode)) {
                    barrierMapper.updateByIndexCode(dataList.getJSONObject(j), "hikvision", renewalBy, communityId);
                } else {
                    barrierMapper.insert(dataList.getJSONObject(j), "hikvision", renewalBy, communityId);
                }
            }
            log.info("道闸数据同步：第{}页，共：{}条数据", (i + 1), dataList.size());
            // barrierMapper.insertOrUpdate(dataList, "hikvision", renewalBy, communityId);
            num += dataList.size();
        }
        //删除本次未更新的数据（海康平台已删除的数据）
        barrierMapper.delRenewalTimeNotToday();
        return num > 0 ? AjaxResult.success("已同步" + num + "条道闸数据") : AjaxResult.error("数据同步失败");
    }

    @Override
    public Barrier selectBarrierById(Long barrierId) {

        return barrierMapper.selectBarrierById(barrierId);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer updateBarrier(Barrier barrier) {

        return barrierMapper.updateBarrier(barrier);
    }

    @Override
    @Transactional(readOnly = false)
    public Integer batchUpdateDeviceQr(Barrier barrier) {
        List<Barrier> barriers;
        if (barrier.getIds().length == 0) {
            // 查询未设置设备二维码的设备id集
            barriers = barrierMapper.selectIdsOfNotQrCode();
        } else {
            barriers = barrierMapper.selectBarrierByIds(Arrays.asList(barrier.getIds()));
        }
        return barrierMapper.batchUpdateDeviceQr(packageBarrier(barriers));
    }

    @Override
    public List<Roadway> selectUnbidRoadway(Barrier barrier) {

        return barrierMapper.selectUnbidRoadway(barrier);
    }

    /**
     * 封装二维码数据
     *
     * @param barriers 道闸设备信息
     * @return java.util.List<com.wr.remote.aiot.Barrier>
     */
    private List<Barrier> packageBarrier(List<Barrier> barriers) {
        barriers.forEach(barrier -> {
            JSONObject json = JSONObject.of();
            json.put("id", barrier.getIndexCode());
            json.put("deviceType", "barrier");
            json.put("type", "device");
            try {
                MultipartFile file = QrCodeUtils.createQrCode(json.toJSONString(), null, barrier.getBarrierName());
                R<SysFile> fileResult = remoteFileService.upload(file);
                if (StringUtils.isNotNull(fileResult) && StringUtils.isNotNull(fileResult.getData())) {
                    String url = fileResult.getData().getUrl();
                    barrier.setDeviceQr(url);
                }
            } catch (Exception e) {
                log.error("二维码生成失败：{}", e.getMessage());
            }
        });

        return barriers;
    }
}
