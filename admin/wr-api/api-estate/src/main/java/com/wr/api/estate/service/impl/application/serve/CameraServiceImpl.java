package com.wr.api.estate.service.impl.application.serve;

import com.wr.api.estate.entity.vo.application.serve.CameraVO;
import com.wr.api.estate.mapper.application.serve.CameraMapper;
import com.wr.api.estate.service.application.serve.CameraService;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.ProtocolConstants;
import com.wr.common.customize.util.CommunityHik;
import com.wr.common.customize.util.HaiKUtils;
import com.wr.common.customize.util.HikUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 20:12:58
 * @Desc:
 */
@Service
public class CameraServiceImpl implements CameraService {

    @Resource
    private CameraMapper cameraMapper;

    @Override
    public List<CameraVO> searchByCommunityId(Long communityId, String cameraName) {
        return cameraMapper.searchByCommunityId(communityId, cameraName);
    }

    @Override
    public AjaxResult selectCameraUrl(String indexCode, Long communityId) {
        try {
            CommunityHik hik = cameraMapper.selectHikByCommunityId(communityId);
            if (null == hik) {
                return AjaxResult.error("请检查小区海康配置后重试！");
            }
            String url = HaiKUtils.camerasPreviewURLs(hik, indexCode, ProtocolConstants.PROTOCOL_WSS);
            if (StringUtils.isEmpty(url)) {
                return AjaxResult.error("取流失败！");
            }
            return AjaxResult.success("取流成功！", url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
