package com.wr.api.owner.service.impl.serve;

import com.wr.api.owner.entity.dto.FitmentDTO;
import com.wr.api.owner.entity.vo.serve.FitmentVo;
import com.wr.api.owner.entity.vo.serve.MyRoomsVo;
import com.wr.api.owner.entity.vo.serve.UpfitterVo;
import com.wr.api.owner.mapper.serve.FitmentMapper;
import com.wr.api.owner.mapper.serve.UpfitterMapper;
import com.wr.api.owner.service.serve.FitmentService;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-14 14:22:08
 * @Desc:
 */
@Service
public class FitmentServiceImpl implements FitmentService {
    @Autowired
    private FitmentMapper fitmentMapper;

    @Autowired
    private UpfitterMapper upfitterMapper;

    /**
     * 查询装修列表
     *
     * @param fitmentVo
     * @return
     */
    @Override
    public List<FitmentDTO> getFitment(FitmentVo fitmentVo) {
        return fitmentMapper.getFitmentList(fitmentVo);
    }

    /**
     * 新增装修
     *
     * @param fitmentVo
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public AjaxResult insertFitment(FitmentVo fitmentVo) {
        BigDecimal deposit = fitmentVo.getDeposit();
        if (deposit == null) {
            deposit = new BigDecimal("0.00");
        }
        fitmentVo.setDeposit(deposit);
        //根据业主和手机号查询住在哪个小区
//        Long ownId = fitmentMapper.findAddress(fitmentVo);
        //判断是内部人员还是外部人员新增
        //装修工内外部:0=内部，1=外部
        if ("1".equals(fitmentVo.getIntOut())) {
            //申请类型
            fitmentVo.setDecorateType("0");
            fitmentVo.setAuditStatus("0");
            //审批人
            fitmentVo.setAuditBy(SecurityUtils.getUsername());
            //新增装修列表
            int i = fitmentMapper.insertFitment(fitmentVo);
            //新增装修人
            List<UpfitterVo> upfitterVos1 = fitmentVo.getUpfitterVos();
            for (UpfitterVo upfitterVo : upfitterVos1) {
                //装修人id
                upfitterVo.setDecorateId(fitmentVo.getDecorateId());
                //审核中
                upfitterVo.setAuditStatus("0");
                upfitterVo.setAccountId(0L);
                upfitterVo.setAuditBy(SecurityUtils.getUsername());
                upfitterVo.setRejectReason(fitmentVo.getRejectReason());
                upfitterVo.setAuditTime(new Date());
                upfitterVo.setIntOut("1");
                upfitterVo.setWorkerType("0");

            }
            upfitterVos1.forEach(t -> t.setWorkerName(t.getStaffName()));
            upfitterVos1.forEach(t -> t.setWorkerPhone(t.getStaffPhone()));
            //新增装修工人
            int insertUpfitter = upfitterMapper.insertUpfitter(upfitterVos1);
            if (i > 0 && insertUpfitter > 0) {
                return AjaxResult.success("添加成功");
            }
            return AjaxResult.error("添加失败");
        } else {

            //申请类型
            fitmentVo.setDecorateType("0");
            fitmentVo.setAuditStatus("0");
            //审批人
            fitmentVo.setAuditBy(SecurityUtils.getUsername());
            //新增装修列表
            fitmentMapper.insertFitment(fitmentVo);
            Long id = fitmentVo.getDecorateId();
            //新增装修人
            List<UpfitterVo> upfitterVos1 = fitmentVo.getUpfitterVos();
            for (UpfitterVo upfitterVo : upfitterVos1) {
                //装修人id
                upfitterVo.setDecorateId(fitmentVo.getDecorateId());

                upfitterVo.setIntOut("0");
                //审核中
                upfitterVo.setAuditStatus("0");
                upfitterVo.setWorkerName(upfitterVo.getStaffName());
                upfitterVo.setAccountId(0L);
                upfitterVo.setWorkerPhone(upfitterVo.getStaffPhone());
                upfitterVo.setAuditBy(SecurityUtils.getUsername());
                upfitterVo.setRejectReason(fitmentVo.getRejectReason());
                upfitterVo.setAuditTime(new Date());
                upfitterVo.setWorkerType("0");

            }
            //新增装修工人
            upfitterVos1.forEach(t -> t.setWorkerName(t.getStaffName()));
            upfitterVos1.forEach(t -> t.setCardNo(t.getCardNo()));

            upfitterVos1.forEach(t -> t.setWorkerPhone(t.getStaffPhone()));
            upfitterMapper.insertUpfitter(upfitterVos1);
        }
        return AjaxResult.error("添加失败");
    }

    /**
     * 查询装修人员信息
     */
    @Override
    public List<FitmentVo> getFitmentEr(FitmentVo fitmentVo) {
        return fitmentMapper.findFitmentEr(fitmentVo);
    }

    /**
     * 查询详情
     *
     * @param fitmentVo
     * @return
     */
    @Override
    public List<FitmentVo> getFitmentInfo(FitmentVo fitmentVo) {
        //获取装修工人信息
        List<UpfitterVo> upfitter = upfitterMapper.getUpfitter(fitmentVo.getDecorateId());
        fitmentVo.setUpfitterVos(upfitter);
        List<FitmentVo> fitmentVos = fitmentMapper.FitmentInfo(fitmentVo);
        for (FitmentVo fitmentVo1 : fitmentVos) {
            fitmentVo1.setUpfitterVos(upfitter);
        }
        return fitmentVos;
    }

    /**
     * 我要延期
     *
     * @param fitmentVo
     * @return
     */
    @Override
    public int updatePostpone(FitmentVo fitmentVo) {
        //0=装修申请，1=延期申请
        fitmentVo.setDecorateType("1");
        //0=审核中，1=驳回，2=通过（进行中），3=延期待确认,4=已延期，5=已完成
        fitmentVo.setAuditStatus("3");
        int row = fitmentMapper.updateByDecorate(fitmentVo);
        return row;
    }

    /**
     * 装修状态修改
     *
     * @param fitmentVo
     * @return
     */
    @Override
    public int updateState(FitmentVo fitmentVo) {
        //0=审核中，1=驳回，2=通过（进行中），3=延期待确认,4=已延期，5=已完成
//        fitmentVo.setAuditStatus("5");
        int row = fitmentMapper.updateByState(fitmentVo);
        return row;
    }

    /**
     * 人员申请
     *
     * @param fitmentVo
     * @return
     */
    @Override
    public AjaxResult insertHuman(FitmentVo fitmentVo) {
//        装修工内外部:0=内部，1=外部
        if ("0".equals(fitmentVo.getIntOut())) {
            List<UpfitterVo> upfitterVos = fitmentVo.getUpfitterVos();
            for (UpfitterVo upfitterVo : upfitterVos) {
                //装修人id
                upfitterVo.setDecorateId(fitmentVo.getDecorateId());
                //审核中
                upfitterVo.setAuditStatus("0");
                upfitterVo.setAuditBy(SecurityUtils.getUsername());
                upfitterVo.setWorkerName(upfitterVo.getStaffName());
                upfitterVo.setAccountId(0L);
                upfitterVo.setAuditTime(new Date());
                upfitterVo.setIntOut(fitmentVo.getIntOut());
                upfitterVo.setWorkerType("1");

            }
            upfitterVos.forEach(t -> t.setWorkerName(t.getStaffName()));
            upfitterVos.forEach(t -> t.setWorkerPhone(t.getStaffPhone()));
            int row = upfitterMapper.insertUpfitter(upfitterVos);
            return AjaxResult.success(row);
        } else {
            fitmentVo.setAuditTime(new Date());
            fitmentVo.setAuditBy(SecurityUtils.getUsername());
            //审核中
            fitmentVo.setAuditStatus("0");
            fitmentVo.setWorkerType("1");
            int i = upfitterMapper.insertUpfitterList(fitmentVo);

            return AjaxResult.success(i);
        }

    }

    /**
     * 修改装修
     *
     * @param fitmentVo
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public int updateAll(FitmentVo fitmentVo) {
//        //根据小区id、楼栋名称查询楼栋号
//       Long buildingId = fitmentMapper.selectBuilding(fitmentVo.getCommunityId(),fitmentVo.getBuildingName());
//        //根据小区id、楼栋号、单元名称查询单元号
//        Long unitId = fitmentMapper.selectUnit(fitmentVo.getCommunityId(),buildingId,fitmentVo.getUnitName());
//        //根据小区id、楼栋号、单元号、房号查询主键id
//        Long roomId = fitmentMapper.selectRoom(fitmentVo.getCommunityId(),buildingId,unitId,fitmentVo.getRoomName());
//        //小区id和楼栋id和单元号必须有值，精确到每一户
//        if (buildingId!=null && unitId!=null && roomId !=null){
        //将小区id、楼栋号、单元号重新赋值
//            fitmentVo.setBuildingId(buildingId);
//            fitmentVo.setUnitId(unitId);
//            fitmentVo.setRoomId(roomId);
        fitmentMapper.updateAllFit(fitmentVo);
        List<UpfitterVo> upfitterVos = fitmentVo.getUpfitterVos();
        //修改装修工
        upfitterMapper.updateUpitter(upfitterVos);
        return 1;
//        }
//        return -1;
    }

    @Override
    public List<MyRoomsVo> getMyRoomS(Long ownerId, Long communityId) {
        return fitmentMapper.getMyAllRoom(ownerId, communityId);
    }

    /**
     * 获取物业手机号
     *
     * @param communityId
     * @return
     */
    @Override
    public String getEstatePhone(Long communityId) {
        String phoneEs = fitmentMapper.getPhoneEs(communityId);
        return phoneEs;
    }


}
