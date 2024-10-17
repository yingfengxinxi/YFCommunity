package com.wr.api.owner.service.impl.home.location;

import com.alibaba.fastjson2.JSONObject;
import com.wr.api.owner.entity.home.location.ApiCamera;
import com.wr.api.owner.entity.vo.location.AreaAndcommunityVo;
import com.wr.api.owner.entity.vo.location.CameraVO;
import com.wr.api.owner.entity.vo.location.DoorVo;
import com.wr.api.owner.mapper.home.location.AreaMapper;
import com.wr.api.owner.service.home.location.AreaService;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.util.HikUtils;
import com.wr.common.customize.util.CommunityHik;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 15:15:19
 * @Desc:
 */
@Service
public class AreaServiceImpl implements AreaService {

    //区域
    @Resource
    private AreaMapper areaMapper;

    //所有小区的地址
    @Override
    @Transactional
    public List<AreaAndcommunityVo> AreaAndcommunityVoList() {
        return areaMapper.selectListArea();
    }

    //查询当前业主得楼栋单元门禁设备
    @Override
    @Transactional
    public List<DoorVo> selectDoorControl(Long accountId,Long communityId) {
        return areaMapper.selectDoorEquipment(accountId,communityId);
    }

    //远程打开门禁设备
    @Override
    @Transactional
    public AjaxResult openDoorControl(Long communityId, String indexCode) {
        //查询小区信息
        CommunityHik hik = areaMapper.selectHikByCommunityId(communityId);
        if(null == hik){
            return AjaxResult.error("请检查小区海康配置后重试！");
        }
        HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
        //调开门接口
        JSONObject conRes = HikUtils.doControlOpenDoor(indexCode);
        if(null == conRes){
            return AjaxResult.error("开门失败");
        }
        if(0 != conRes.getInteger("controlResultCode")){
            return AjaxResult.error("开门失败");
        }
        return AjaxResult.success("开门成功！");
    }

    @Override
    public List<CameraVO> selectCameraList(ApiCamera apiCamera) {

        return areaMapper.selectCameraList(apiCamera);
    }

    @Override
    public AjaxResult selectCameraUrl(ApiCamera apiCamera) {
        CommunityHik hik = areaMapper.selectHikByCommunityId(apiCamera.getCommunityId());
        if (null == hik) {
            return AjaxResult.error("请检查小区海康配置后重试！");
        }
        HikUtils.initConfig(hik.getHikIp(), hik.getHikPort(), hik.getHikAk(), hik.getHikSk());
        String url = HikUtils.camerasPreviewURLs(apiCamera.getIndexCode(), apiCamera.getProtocol());
        if (StringUtils.isEmpty(url)) {
            return AjaxResult.error("取流失败！");
        }
        return AjaxResult.success("取流成功！", url);
    }
}
