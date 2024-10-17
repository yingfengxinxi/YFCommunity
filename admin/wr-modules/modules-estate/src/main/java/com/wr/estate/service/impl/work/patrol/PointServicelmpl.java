package com.wr.estate.service.impl.work.patrol;

import com.alibaba.fastjson2.JSONObject;
import com.wr.common.core.domain.R;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.customize.util.QrCodeUtils;
import com.wr.estate.mapper.work.patrol.PointMapper;
import com.wr.estate.service.work.patrol.PointService;
import com.wr.remote.domain.SysFile;
import com.wr.remote.system.RemoteFileService;
import com.wr.remote.work.patrol.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/26 10:33
 * @Version 1.5
 */
@Service
public class PointServicelmpl implements PointService {
    @Autowired
    private PointMapper pointMapper;
    @Autowired
    private  RemoteFileService remoteFileService;

    @Override
    public List<Point> selectList(Point point) {
        return pointMapper.selectList(point);
    }
    @Override
    public List<Point> selectList2(Point point) {
        return pointMapper.selectList2(point);
    }

    @Override
    public Point selectById(Long pointId) {
        Point point = pointMapper.selectById(pointId);
        point.setCommunityName(pointMapper.selectName(point.getCommunityId()));
        return point;
    }

    @Override
    public int delete(Long[] pointIds) {
        for (Long pointId : pointIds) {
            pointMapper.delete(pointId);
        }
        return 1;
    }

    @Override
    public int delete1(Long pointId) {
        return pointMapper.delete(pointId);
    }

    @Override
    public int updateStatus(Point point) {
        return pointMapper.updateStatus(point);
    }

    @Override
    public int saveUpdate(Point point) throws Exception {
        if (point.getPointId()==null||point.getPointId()==0){
            pointMapper.insertPoint(point);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",point.getPointId());
            jsonObject.put("pointType",point.getPointType());
            jsonObject.put("type","point");
            try {
                MultipartFile file = QrCodeUtils.createQrCode(jsonObject.toJSONString(), null, point.getPointName());
                R<SysFile> fileResult = remoteFileService.upload(file);
                if (StringUtils.isNotNull(fileResult) && StringUtils.isNotNull(fileResult.getData())) {
                    String url = fileResult.getData().getUrl();
                    point.setPointUrl(url);
                }
            }catch (Exception e){
              throw  new Exception("二维码生成失败");
            }
            return pointMapper.updatePointUrl(point);

        }else {
            return pointMapper.updatePoint(point);
        }
    }

    /**
     * 校验巡点名称是否存在
     * @param pointName
     * @param communityId
     * @return
     */
    @Override
    public int isExist(String pointName, Long communityId) {
        return pointMapper.isExist(pointName, communityId);
    }
}
