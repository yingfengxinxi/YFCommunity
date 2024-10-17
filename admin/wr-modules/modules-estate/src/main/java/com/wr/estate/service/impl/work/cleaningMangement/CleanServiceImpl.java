package com.wr.estate.service.impl.work.cleaningMangement;

import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.OrderConstants;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.vo.*;
import com.wr.estate.mapper.work.cleaningMangement.CleanMapper;
import com.wr.estate.mapper.work.cleaningMangement.CleanPlanMapper;
import com.wr.estate.mapper.work.inspect.OrderRecordMapper;
import com.wr.estate.service.manage.StaffService;
import com.wr.estate.service.work.cleaningMangement.CleanService;
import com.wr.estate.web.work.inspect.ExtractCode;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.work.cleaningManagement.Clean;
import com.wr.remote.work.cleaningManagement.CleanExample;
import com.wr.remote.work.cleaningManagement.CleanPlan;
import com.wr.remote.work.distribution.Distribution;
import com.wr.remote.work.inspect.OrderAudit;
import com.wr.remote.work.inspect.OrderRecord;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-31 15:59:28
 * @Desc:
 */
@Service
public class CleanServiceImpl  implements CleanService {

    //保洁工单
    @Resource
    private CleanMapper cleanMapper;

    //工单执行流程
    @Resource
    private OrderRecordMapper orderRecordMapper;

    /**
     * 工作人员
     */
    @Resource
    private StaffService staffService;

    /**
     * 保洁计划
     */
    @Resource
    private CleanPlanMapper clenPlanMapper;
    @Override
    public List<CleanVo> selectListByPlanId(Long planId, Long communityId) {
        CleanExample cleanExample = new CleanExample();
        CleanExample.Criteria criteria = cleanExample.createCriteria();
        criteria.andPlanIdEqualTo(planId).andDelFlagEqualTo("0").andCommunityIdEqualTo(communityId);
        return  cleanMapper.selectByExample(cleanExample);
    }

    @Override
    public CleanExecuteNameAndnumbersVO selectCleanList(Clean clean) {
        //返回对象
        CleanExecuteNameAndnumbersVO cleanExecuteNameAndnumbersVO =new CleanExecuteNameAndnumbersVO();
        List<CleanExecuteNameVO> cleanExecuteNameVOList =new ArrayList();
        //条件类
        CleanExample cleanExample = new CleanExample();
        CleanExample.Criteria criteria = cleanExample.createCriteria();
        criteria.andCommunityIdEqualTo(clean.getCommunityId());
        criteria.andDelFlagEqualTo("0");
        cleanExample.setOrderByClause("clean_id DESC");
        //工单编号
        if (clean.getCleanNo()!=null && clean.getCleanNo()!="")
        {
            criteria.andCleanNoEqualTo(clean.getCleanNo());
        }
        //工单名称
        if (clean.getCleanName() != null && clean.getCleanName()!="")
        {
            criteria.andCleanNameLike("%"+clean.getCleanName()+"%");
        }
        //负责人
        if (clean.getAddress()!=null)
        {
            criteria.andAddressLike("%"+clean.getAddress()+"%");
        }
        //执行状态
        if (clean.getExecuteStatus()!=null && clean.getExecuteStatus()!="")
        {
            criteria.andExecuteStatusEqualTo(clean.getExecuteStatus());
        }
        //工单集合
         List<CleanVo> cleanList = cleanMapper.selectByExample(cleanExample);
        cleanExecuteNameAndnumbersVO.setNumbers(cleanMapper.selectNumber(cleanExample));
        //遍历集合 把查到负责人名称
        for (CleanVo cleans : cleanList) {
            //判断负责人不为0
            if (cleans.getStaffId()!=0){
            Staff staff = cleanMapper.selectByPrimaryexecuteId(cleans.getStaffId());
            if(null == staff){
                CleanExecuteNameVO cleanExecuteNameVO = new CleanExecuteNameVO(cleans,null);
                cleanExecuteNameVOList.add(cleanExecuteNameVO);
            }else{
                CleanExecuteNameVO cleanExecuteNameVO = new CleanExecuteNameVO(cleans,staff.getStaffName());
                cleanExecuteNameVOList.add(cleanExecuteNameVO);
            }

            }else {
                CleanExecuteNameVO cleanExecuteNameVO = new CleanExecuteNameVO(cleans,null);
                cleanExecuteNameVOList.add(cleanExecuteNameVO);
            }
        }
        cleanExecuteNameAndnumbersVO.setCleanExecuteNameVOList(cleanExecuteNameVOList);
        return cleanExecuteNameAndnumbersVO;
    }

    //查询单个工单信息
    @Override
    public CleanVo selectCleanOne(Long cleanId) {
        return cleanMapper.selectByPrimaryKey(cleanId);
    }

    //修改或新增工单信息
    @Override
    public int insertAndupdataCleanOneand(Clean clean) {
        //判断有ID没 有就是修改 没有就是新增
        if (clean.getCleanId()!=null ){
            //设置修改人和修改时间
            clean.setUpdateBy(SecurityUtils.getLoginUser().getSysUser().getUserName());
            clean.setUpdateTime(new Date());
            int update = cleanMapper.updateByPrimaryKeySelective(clean);
            return update;
        }else {
            // 获取工单分配方式
            Distribution allotByType = cleanMapper.getClearOrderDistribution(clean.getCommunityId());
            if (ObjectUtils.isEmpty(allotByType)){
                AjaxResult.error("分配方式不存在，请联系管理员！");
            }
            if ("0".equals(allotByType.getAllotWay())) {
                // 随机分配一个闲置用户
                Staff staff = staffService.getStaffsId(allotByType.getAllotId(), clean.getCommunityId());
                if (staff != null) {
                    Long staffId = staff.getStaffId();
                    clean.setStaffId(staffId);
                    clean.setAllotStatus("1");
                }
            }
            //设置配置方式(管理员分配)
            clean.setAllotWay(allotByType.getAllotWay());

            //工单编号
            String cleanNo = "";
            //随机合成工单编号开头
            String cleanNohead = "BJ-";
            //获取当前年月日----工单中间
            // 普通的时间转换
            String cleanNobody = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
            //6位随机数---工单尾部
            int cleanNotail =(int)((Math.random()*9+1)*100000);
            cleanNo = cleanNohead + cleanNobody+"-" + cleanNotail;
            //设置工单编号
            clean.setCleanNo(cleanNo);
            //设置创建人和创建时间
            clean.setCreateBy(SecurityUtils.getLoginUser().getSysUser().getUserName());
            clean.setCreateTime(new Date());
            //设置工单来源
            clean.setDataSource("1");
            int insert = cleanMapper.insertSelective(clean);
            return insert;

        }
    }

    @Override
    public void delectClean(Long[] cleanIds) {
        for (Long cleanId : cleanIds) {
          cleanMapper.deleteByPrimaryKey(cleanId);
        }
    }

    @Override
    public List<Staff> getsafftName(Clean clean) {
        return cleanMapper.getSatffNameList(clean.getCommunityId());
    }


    /*
    分配责任人
     */
    @Override
    public int updataEexecuteIdAndstaffId(Clean clean) {

        //设置修改人和修改时间
        clean.setUpdateBy(SecurityUtils.getLoginUser().getSysUser().getUserName());
        clean.setUpdateTime(new Date());
        cleanMapper.updateByPrimaryKeySelective(clean);

        //查询出提交人的姓名
        Staff satffByIdStaffId = cleanMapper.getSatffByIdStaffId(clean.getStaffId());
        //工单执行情况表
        OrderRecord orderRecord =new OrderRecord();
        //保洁类型
        orderRecord.setOrderType(OrderConstants.CLEAN);
        //工单ID
        orderRecord.setOrderId(clean.getCleanId());
        //结果
        orderRecord.setOrderResult("工单分配");
        //提交人
        orderRecord.setUserName(satffByIdStaffId.getStaffName());
        //保存执行流程
        return orderRecordMapper.insertSelective(orderRecord);

    }

    /**
     * 查询出执行人列表
     * @param cleanId
     * @return
     */
    @Override
    public List<Executor> getExecutorList(Long cleanId) {
        List<Executor> e = new ArrayList<>();
        Clean clean = cleanMapper.selectByPrimaryKeySingle(cleanId);
        if(clean.getStaffId() >0){
        //执行人id
        HashSet<Long> staffIds=new HashSet<>();
        //负责人
        staffIds.add(clean.getStaffId());

        //工单审核表
        OrderAudit orderAudit = new OrderAudit();
        //设置审核类型
        orderAudit.setAuditType("1");
        //设置工单类型
        orderAudit.setOrderType(OrderConstants.CLEAN);
        //设置工单ID
        orderAudit.setOrderId(cleanId);
        //审核通过
        orderAudit.setAuditStatus("1");
        List<OrderAssistVo> orderAssistVos = cleanMapper.selectExecutorList(orderAudit);
        if(orderAssistVos.size()>0){
            for (OrderAssistVo orderAssistVo : orderAssistVos) {
                //协助人
                staffIds.add(orderAssistVo.getStaffId());
            }
        }
        if(staffIds.size()>0)
        {

            for (Long staffId : staffIds) {
                Executor byIdStaffId = cleanMapper.getByIdStaffId(staffId);
               if(byIdStaffId==null){
                   break;
               }
                //判断那个是负责人
                if (clean.getStaffId().equals(byIdStaffId.getStaffId())){
                    byIdStaffId.setMainStaffName("是");
                    byIdStaffId.setPersonnelType(OrderConstants.CLEAN);
                    e.add(byIdStaffId);
                    continue;
                }
                byIdStaffId.setPersonnelType( OrderConstants.CLEAN);
                e.add(byIdStaffId);
            }
        }
            return e;
        }
        return e;
    }


    /**
     * 返回执行流程步骤
     * @param cleanId
     * @return
     */
    @Override
    public List<OrderRecord> selectOrderRecordList(Long cleanId) {
        return cleanMapper.selcetOrderRecordList(cleanId);
    }





    //定时计划
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void scheduledCleaningTasks() {
        //需要生成的保洁计划工单
        List<CleanPlan> cleanPlans = clenPlanMapper.SelectOpenPlanOrderCleanPlan();
        if (cleanPlans.size()>0)
        {
            //创建一个保洁工单对象
            Clean clean = new Clean();
            for (CleanPlan cleanPlan : cleanPlans) {
                //给保洁工单赋值
                //小区ID
                clean.setCommunityId(cleanPlan.getCommunityId());
                //工单编号
                String cleanNo = "";
                //随机合成工单编号开头
                String cleanNohead = "BJ-";
                //获取当前年月日----工单中间
                // 普通的时间转换
                String cleanNobody = new SimpleDateFormat("yyyyMMdd").format(new Date()).toString();
                //6位随机数---工单尾部
                int cleanNotail =(int)((Math.random()*9+1)*100000);
                cleanNo = cleanNohead + cleanNobody+"-" + cleanNotail;
                //工单编号
                clean.setCleanNo(cleanNo);
                //工单名称
                clean.setCleanName(cleanPlan.getPlanName());
                //保洁地址
                clean.setAddress(cleanPlan.getAddress());
                //一日多次
                if (cleanPlan.getPlanModel().equals("0")){
                    //一天几次
                    clean.setPatrolNum(cleanPlan.getPlanNum());
                    //频率
                    clean.setGapTime(cleanPlan.getGapTime());
                }else {
                    //多日一次
                    clean.setPatrolNum(cleanPlan.getPlanNum());
                }
                try {
                    //开始时间
                    clean.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new ExtractCode().dateHandle(cleanPlan.getStartTime())));
                    //结束时间
                    clean.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(new ExtractCode().dateHandle(cleanPlan.getEndTime())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //计划id
                clean.setPlanId(cleanPlan.getPlanId());
                //配置设置
                Distribution distribution = clenPlanMapper.selectAllot(cleanPlan.getCommunityId(), OrderConstants.CLEAN);
                //分配方式
                clean.setAllotWay(distribution.getAllotWay());
                //设置协助人数
                clean.setAssistNum(distribution.getAssistNum());
                //工单来源
                clean.setDataSource("0");
                //自动分配 ----  分配负责人人员
                if("0".equals(distribution.getAllotWay()))
                {
                    //空闲的工作人员
                    List<Staff> staff = staffService.listLeisureStaffs(distribution.getAllotId(), cleanPlan.getCommunityId());
                    //如果没有空闲的人就-------管理员分配
                    if(staff != null && staff.size()>0) {
                        //随机一个工作人员作为负责人
                        int num = (int) (Math.random() * (staff.size() + 1));
                        //负责人
                        clean.setStaffId(staff.get(num).getStaffId());
                        //查询出提交人的姓名
                        Staff satffByIdStaffId = cleanMapper.getSatffByIdStaffId(clean.getStaffId());
                        //工单执行情况表
                        OrderRecord orderRecord = new OrderRecord();
                        //保洁类型
                        orderRecord.setOrderType(OrderConstants.CLEAN);
                        //工单ID
                        orderRecord.setOrderId(clean.getCleanId());
                        //结果
                        orderRecord.setOrderResult("工单分配");
                        //提交人
                        orderRecord.setUserName(satffByIdStaffId.getStaffName());
                        //保存执行流程
                        orderRecordMapper.insertSelective(orderRecord);
                        //分配状态
                        clean.setAllotStatus("1");
                    }
                }
                //判断多日一次是否生成工单
                if ("1".equals(cleanPlan.getPlanModel())){
                    //设置时间格式
                    SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
                    //查询最新生成的工单
                     Clean cleanTwo = cleanMapper.selectByPrimaryKeyOne(cleanPlan.getPlanId());
                     //不为空判断
                     if (cleanTwo !=null){
                         //获取下次生成工单的时间
                        int day = LocalDate.parse(s.format(cleanTwo.getCreateTime())).getDayOfYear()+cleanPlan.getPlanNum();
                        //获取当前是今年的第几天
                        int YearDay = LocalDate.parse(s.format(new Date())).getDayOfYear();
                        //判断今天是否生成工单
                         if(day == YearDay)
                         {
                             cleanMapper.insertSelective(clean);
                         }
                     }else {
                         cleanMapper.insertSelective(clean);
                     }
                }
                else{
                    //不是一日多次
                    cleanMapper.insertSelective(clean);
                }
            }
        }
    }


}
