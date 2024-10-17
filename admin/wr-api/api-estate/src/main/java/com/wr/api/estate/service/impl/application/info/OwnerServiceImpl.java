package com.wr.api.estate.service.impl.application.info;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.wr.api.estate.entity.vo.application.info.OwnerVo;
import com.wr.api.estate.entity.vo.application.info.RoomInfoVo;
import com.wr.api.estate.entity.vo.application.info.VehicleVo;
import com.wr.api.estate.entity.vo.application.serve.OwnerInfoVo;
import com.wr.api.estate.mapper.application.info.OwnerMapper;
import com.wr.api.estate.service.application.info.OwnerService;
import com.wr.api.estate.service.application.info.VehicleService;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.DictUtils;
import com.wr.remote.domain.SysDictData;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 14:06:29
 * @Desc: 业务处理层
 */
@Service
public class OwnerServiceImpl implements OwnerService {
    @Autowired
    private OwnerMapper ownerMapper;

    @Autowired
    private VehicleService vehicleService;


    /**
     * 列表
     *
     * @param ownerVo
     * @return
     */
    @Override
    public List<OwnerVo> getList(OwnerVo ownerVo) {
        return ownerMapper.getList(ownerVo);
    }

    /**
     * 详情
     *
     * @param ownerVo
     * @return
     */
    @Override
    public AjaxResult getDetail(OwnerVo ownerVo) {
        AjaxResult ajaxResult = AjaxResult.success();
        //房屋信息
        List<RoomInfoVo> roomList = ownerMapper.getHouse(ownerVo.getOwnerId(), ownerVo.getCommunityId());
        if (CollectionUtils.isNotEmpty(roomList)) {
            roomList.stream().forEach(roomInfoVo -> {
                roomInfoVo.setSelectRoom(roomInfoVo.getCommunityName() + "小区" + roomInfoVo.getBuildingName() + roomInfoVo.getUnitName() + roomInfoVo.getRoomName());
            });
        }
        ajaxResult.put("roomList", roomList);
        //业主信息
        OwnerInfoVo ownerDetail = ownerMapper.getOwnerDetail(ownerVo.getOwnerId());
        String cardType = ownerDetail.getCardType();
        if (StringUtils.isNotEmpty(cardType)) {
            List<SysDictData> dictCache = DictUtils.getDictCache("sys_certificate");
            String dictLabel = dictCache.stream().filter(SysDictData -> SysDictData.getDictValue().equals(cardType)).collect(Collectors.toList()).get(0).getDictLabel();
            ownerDetail.setCardType(dictLabel);
        }

        ajaxResult.put(AjaxResult.DATA_TAG, ownerDetail);

        //车辆信息
        Long accountId = ownerDetail.getAccountId();
        Long communityId = ownerVo.getCommunityId();
        List<VehicleVo> vehicleList = vehicleService.getVehicleDetail(accountId, communityId, null);
        ajaxResult.put("vehicleList", vehicleList);
        List<String> plateNoList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(vehicleList)) {
            plateNoList = vehicleList.stream().map(VehicleVo::getPlateNo).distinct().collect(Collectors.toList());
        }
        ajaxResult.put("plateNoList", plateNoList);
        return ajaxResult;
    }

    @Override
    public int getOwnerCount(Long communityId) {
        return ownerMapper.getOwnerCount(communityId);
    }
}
