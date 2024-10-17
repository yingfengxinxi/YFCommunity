package com.wr.api.owner.service.impl.serve;

import com.wr.api.owner.entity.vo.serve.MyRoomsVo;
import com.wr.api.owner.entity.vo.serve.OldBasicInfoVo;
import com.wr.api.owner.entity.vo.serve.OlderFilesVo;
import com.wr.api.owner.mapper.serve.OlderMapper;
import com.wr.api.owner.service.serve.OlderService;
import com.wr.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-15 16:21:04
 * @Desc:
 */
@Service
public class OlderServiceImpl implements OlderService {

    @Autowired
    private OlderMapper olderMapper;

    /**
     * 查询老人信息
     * @param olderFilesVo
     * @return
     */
    @Override
    public List<OlderFilesVo> getOlderFiles(OlderFilesVo olderFilesVo) {
            List<OlderFilesVo> olderFilesVoList = olderMapper.getOwner(olderFilesVo.getOwnerPhone());
            return olderFilesVoList;
    }

    /**
     * 查询老人详情
     * @param orderId
     * @return
     */
    @Override
    public OlderFilesVo getOldInfo(Long orderId) {
        OlderFilesVo olderFilesVo= olderMapper.getOwnerInfoList(orderId);
        return olderFilesVo;
    }

    /**
     * 添加老人
     * @param olderFilesVo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public AjaxResult insertOlderFile(OlderFilesVo olderFilesVo) {
        //查询老人是否办理过档案
        int i = olderMapper.getoldfilecount(olderFilesVo.getOwnerId(),olderFilesVo.getCardNo());
        if (i==0){
            int num = olderMapper.insertOlder(olderFilesVo);
            if (num>0){
                return AjaxResult.success("添加成功");
            }
            return AjaxResult.error("添加失败");
        }else {
            return AjaxResult.error("老人已经办理过档案");
        }

    }

    /**
     * 查看老人信息
     * @param oldName
     * @return
     */
    @Override
    public OldBasicInfoVo getOlderByName(Long ownerId,String cardNo) {
        return olderMapper.getOlderName(ownerId,cardNo);
    }

    /**
     * 查询小区
     * @param accountId
     * @return
     */
    @Override
    public List<MyRoomsVo> getCommunity(Long accountId) {
        return olderMapper.getCommunitys(accountId);
    }

    /**
     * 查询楼栋
     * @param communityId
     * @return
     */
    @Override
    public List<MyRoomsVo> getBuilds(Long communityId) {
        return olderMapper.getBuild(communityId);
    }

    /**
     * 查询单元
     * @param buildingId
     * @return
     */
    @Override
    public List<MyRoomsVo> getUnit(Long buildingId) {
        return olderMapper.getUnits(buildingId);
    }

    /**
     * 查询房间
     * @param unitId
     * @return
     */
    @Override
    public List<MyRoomsVo> getRoom(Long unitId) {
        return olderMapper.getRooms(unitId);
    }

    /**
     * 查询老人
     * @param roomId
     * @return
     */
    @Override
    public List<OldBasicInfoVo> getOldName(Long roomId) {
        List<OldBasicInfoVo> allOwner = olderMapper.getAllOwner(roomId);
        return allOwner;
    }


}
