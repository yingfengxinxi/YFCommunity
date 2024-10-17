package com.wr.estate.service.impl.work.disasterOrder;

import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.OrderConstants;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.manage.StaffMapper;
import com.wr.estate.mapper.work.disasterOrder.DisasterAuditMapper;
import com.wr.estate.mapper.work.disasterOrder.DisasterLedgerMapper;
import com.wr.estate.mapper.work.disasterOrder.DisasterOrderMapper;
import com.wr.estate.mapper.work.inspect.OrderRecordMapper;
import com.wr.estate.service.work.disasterOrder.DisasterOrderService;
import com.wr.remote.work.inspect.OrderRecord;
import com.wr.remote.work.vo.BusDisasterOrderVo;
import com.wr.remote.work.vo.OrderRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-01 15:49:41
 * @Desc: 灾害报事工单
 */
@Service
public class DisasterOrderServiceImpl implements DisasterOrderService {

    @Autowired
    private DisasterOrderMapper disasterOrderMapper;

    @Autowired
    private DisasterLedgerMapper disasterLedgerMapper;

    @Autowired
    private DisasterAuditMapper disasterAuditMapper;

    // 工单转交记录表
    @Autowired
    private OrderRecordMapper orderRecordMapper;

    @Autowired
    private StaffMapper staffMapper;

    /**
     * 查询灾害报事工单列表
     * @param busDisasterOrderVo
     * @return
     */
    @Override
    public List<BusDisasterOrderVo> getDisasterOrderList(BusDisasterOrderVo busDisasterOrderVo) {

        List<BusDisasterOrderVo> list = disasterOrderMapper.getList(busDisasterOrderVo);
        list.forEach(e->{
            List<String> imageList = disasterAuditMapper.getImageList(e.getAuditId());
            e.setImageList(imageList);
        });
        //对主要负责人进行筛选
        if (StringUtils.isNotEmpty(busDisasterOrderVo.getStaffName())){
            String staffName = busDisasterOrderVo.getStaffName();
//              list.stream().filter(d ->d != null && d.getStaffName().contains(busDisasterOrderVo.getStaffName().indexOf(staffName)!=-1)).collect(Collectors.toList());
        }
        return list;
    }

    /**
     * 灾害报事执行人详情
     * @param busDisasterOrderVo
     * @return
     */
    @Override
    public List<BusDisasterOrderVo> getInfoList(BusDisasterOrderVo busDisasterOrderVo) {
        return disasterOrderMapper.getInfoList(busDisasterOrderVo);
    }

    /**
     * 修改和添加灾害报事工单
     * @param busDisasterOrderVo
     * @return
     */
    @Override
    public AjaxResult updateOrder(BusDisasterOrderVo busDisasterOrderVo) {
        String username = SecurityUtils.getUsername();

        if (busDisasterOrderVo.getOrderId()==null){
            //新增
            // 1.新增工单
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
            String format = dateFormat.format(date);
            int ranDom = (int) ((Math.random() * 9 + 1) * 100000);
            busDisasterOrderVo.setOrderNo("xj_" + format + "_" + ranDom);
            busDisasterOrderVo.setCreateBy(username);
            return AjaxResult.success(disasterOrderMapper.insertOrder(busDisasterOrderVo));
        }
        busDisasterOrderVo.setUpdateBy(username);
        busDisasterOrderVo.setUpdateTime(new Date());
        //根据灾害报事申请id查到灾害报事申请表的灾害类型id
        Long disasterId= disasterAuditMapper.getDisasterId(busDisasterOrderVo.getAuditId());
        busDisasterOrderVo.setDisasterId(disasterId);
        disasterLedgerMapper.updateByDisaster(busDisasterOrderVo);
        //修改
        return AjaxResult.success(disasterOrderMapper.updateOrder(busDisasterOrderVo)) ;
    }



    /**
     * 确定分配
     * @param busDisasterOrderVo
     * @return
     */
    @Override
    public AjaxResult shareUpdate(BusDisasterOrderVo busDisasterOrderVo) {
        Long staffId = busDisasterOrderVo.getStaffId();
        String userName= SecurityUtils.getUsername();
        //修改灾害工单表
        busDisasterOrderVo.setAllotWay("1");
        //修改为已分配
        busDisasterOrderVo.setAllotStatus("1");
        busDisasterOrderVo.setCreateBy(userName);
        busDisasterOrderVo.setUpdateBy(userName);
        disasterOrderMapper.updateDisasterOrder(busDisasterOrderVo);

        // 2.新增执行记录
        OrderRecord orderRecord = new OrderRecord();
        orderRecord.setOrderType(OrderConstants.DISASTER_ORDER);
        orderRecord.setOrderId(busDisasterOrderVo.getOrderId());
        orderRecord.setOrderResult("分配方式：管理员分配");
        orderRecord.setUserName(SecurityUtils.getUsername());
        // 判断数据是否重复
        Integer integer = orderRecordMapper.selectDataRepeat(orderRecord);
        if (integer == 0) {
            orderRecordMapper.insertSelective(orderRecord);
        } else {
            orderRecordMapper.updateByPrimaryKeySelective(orderRecord);
        }
        return AjaxResult.success();
    }

    /**
     * 获取执行人
     * @param communityId
     * @return
     */
    @Override
    public List<BusDisasterOrderVo> getNameList(Long communityId) {
        String orderType = OrderConstants.DISASTER_ORDER;
        return disasterOrderMapper.getNameList(communityId,orderType);
    }

    @Override
    public int deleteByOrder(Long orderId) {
        return disasterOrderMapper.deleteById(orderId);
    }

    /**
     * 获取执行情况详情
     * @param orderId
     * @return
     */
    @Override
    public List<OrderRecordVo> deatilListThree(Long orderId) {
        return orderRecordMapper.getRecordTwo(orderId,OrderConstants.DISASTER_ORDER);
    }
    /**
     * 获取执行情况详情 通用
     * @param orderId
     * @return
     */
    @Override
    public List<OrderRecord> detailListThreeGeneral(Long orderId,String orderType) {
        return orderRecordMapper.getRecord(orderId,orderType);
    }

    /**
     * 审核通过并向灾害工单添加一条数据
     * @param busDisasterOrderVo
     */
    @Override
    public int insertOrder(BusDisasterOrderVo busDisasterOrderVo) {
        return disasterOrderMapper.insertOrder(busDisasterOrderVo);
    }
}
