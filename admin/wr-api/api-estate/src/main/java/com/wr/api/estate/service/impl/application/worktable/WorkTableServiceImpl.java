package com.wr.api.estate.service.impl.application.worktable;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wr.api.estate.mapper.application.worktable.WorkTableMapper;
import com.wr.api.estate.service.LoginService;
import com.wr.api.estate.service.application.manage.StaffService;
import com.wr.api.estate.service.application.worktable.WorkTableService;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.TableUtils;
import com.wr.common.security.utils.DictUtils;
import com.wr.remote.domain.ReportingWarranty;
import com.wr.remote.domain.SysDictData;
import com.wr.remote.domain.SysUser;
import com.wr.remote.domain.vo.GetFoundDataVo;
import com.wr.remote.estate.manage.Staff;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author DIANWEI
 * @Description 工作台 service 实现类
 * @date 2022/11/15 9:42
 */
@Service
public class WorkTableServiceImpl implements WorkTableService {

    @Autowired
    private WorkTableMapper workTableMapper;

    @Autowired
    private LoginService loginService;

    @Autowired
    private StaffService staffService;

    @Override
    public Map<String, Object> getDataStatistics(Long communityId) {
        Map<String, Object> dataStaMap = new HashMap<>(3);
        List<SysDictData> dictCache = DictUtils.getDictCache("event_type");
        List<String> eventTypeList = dictCache.stream().map(SysDictData::getDictValue).collect(Collectors.toList());
        Integer userInOutNum = workTableMapper.getUserInOutNum(communityId, eventTypeList);
        //查询今日出行人数
        dataStaMap.put("userInOutNum", userInOutNum);

        Integer vehicleNum = workTableMapper.getVehicleNum(communityId);
        //查询今日车行数量
        dataStaMap.put("vehicleInOutNum",vehicleNum);

        //查询今日访客数量
        Integer dayVisitorNum = workTableMapper.getDayVisitorNum(communityId);
        if (dayVisitorNum == null) {
            dayVisitorNum = 0;
        }
        dataStaMap.put("visitorInOutNum", dayVisitorNum);

        return dataStaMap;
    }

    /**
     * @param communityId
     * @param executorBy
     * @return
     */
    @Override
    public Map<String, Object> getAppraiseNum(Long communityId, String executorBy) {
        SysUser sysUser = loginService.getUserNameNikeName(executorBy);
        String userType = sysUser.getUserType();
        List<String> workPostList = Lists.newArrayList();
        if (!userType.equals("10")) {
            Staff staff = staffService.getByUserIdStaff(String.valueOf(sysUser.getUserId()), "");
            String workPost = staff.getWorkPost();
            workPostList = Arrays.asList(workPost.split(","));
        } else {
            executorBy = "";
        }

        Map<String, Object> praiseMap = new HashMap<>();

        //待执行
        Integer dzxOrderCount = workTableMapper.getReportingOrderCount(communityId, "0", executorBy, workPostList);
        praiseMap.put("dzxNum", dzxOrderCount);

        //执行中
        Integer zxzOrderCount = workTableMapper.getReportingOrderCount(communityId, "1", executorBy, workPostList);
        praiseMap.put("zxzNum", zxzOrderCount);

        //待评价
        Integer dpjOrderCount = workTableMapper.getReportingOrderCount(communityId, "2", executorBy, workPostList);
        praiseMap.put("dpjNum", dpjOrderCount);

        //已完成
        Integer ywcOrderCount = workTableMapper.getReportingOrderCount(communityId, "3", executorBy, workPostList);
        praiseMap.put("ywcNum", ywcOrderCount);


        //订单评论总数
        Integer totalNum = workTableMapper.getAppraiseNum(null, communityId, executorBy, workPostList);
        praiseMap.put("totalNum", totalNum);
        //好评订单总数【大于等于4算好评】
        Integer praiseNum = workTableMapper.getAppraiseNum(4L, communityId, executorBy, workPostList);
        praiseMap.put("praiseNum", praiseNum);
        Double praiseNumlv = 0.00;
        if (totalNum >= 1) {
            praiseNumlv = Double.valueOf(praiseNum) / Double.valueOf(totalNum);
        }
        praiseMap.put("praiseNumlv", praiseNumlv);
        return praiseMap;
    }

    @Override
    public Map<String, Object> getMonthDbOrder(Long communityId) {
        Map<String, Object> map = new HashMap<>();
        //投诉建议
        Integer complaintSuggestionCount = workTableMapper.getComplaintSuggestionCount(communityId);
        map.put("complaintSuggestionCount", complaintSuggestionCount);
        //报事报修
        Integer reportingWarrantyCount = workTableMapper.getReportingOrderCount(communityId, "0", "", Lists.newArrayList());
        map.put("reportingWarrantyCount", reportingWarrantyCount);
        //装修
        Integer decorateWorkOrderCount = workTableMapper.getDecorateCount(communityId);
        map.put("decorateWorkOrderCount", decorateWorkOrderCount);
        //巡查待分配
        Integer inspectionWorkOrderCount = workTableMapper.getInspectionWorkOrderCount(communityId);
        map.put("inspectionWorkOrderCount", inspectionWorkOrderCount);
        //巡检待分配
        Integer patrolWorkOrderCount = workTableMapper.getPatrolWorkOrderCount(communityId);
        map.put("patrolWorkOrderCount", patrolWorkOrderCount);
        //巡更待分配
        Integer patrolCount = workTableMapper.getPatrolCount(communityId);
        map.put("patrolCount", patrolCount);
        //清洁工
        Integer cleaningWorkOrderCount = workTableMapper.getCleaningWorkOrderCount(communityId);
        map.put("cleaningWorkOrderCount", cleaningWorkOrderCount);
        //抄表工单
        Integer meterReadingWorkOrderCount = workTableMapper.getMeterReadingWorkOrderCount(communityId);
        map.put("meterReadingWorkOrderCount", meterReadingWorkOrderCount);

        return map;
    }

    @Override
    public BigDecimal getCommunityIdMeterReadingTotal(Long communityId, String energyConsumptionType, String meterReadingTime) {
        return workTableMapper.getCommunityIdMeterReadingTotal(communityId, energyConsumptionType, meterReadingTime);
    }

    @Override
    public Map<String, Object> getExecuteNum(Long communityId) {
        Map<String, Object> praiseMap = new HashMap<>(2);
        //待执行
        Integer stayExecuteNum = workTableMapper.getExecuteNum(communityId, 1L, 1L, 0L);
        //已完成
        Integer completeNum = workTableMapper.getExecuteNum(communityId, 1L, 1L, 2L);
        praiseMap.put("stayExecuteNum", stayExecuteNum);
        praiseMap.put("completeNum", completeNum);
        return praiseMap;
    }

    @Override
    public Map<String, Object> getToDoTaskNum(Long communityId) {
        Map<String, Object> praiseMap = new HashMap<>(2);
        //待办任务数
        Integer toDoTaskNum = workTableMapper.getExecuteNum(communityId, 1L, null, 0L);
        //总任务数
        Integer totalTaskNum = workTableMapper.getExecuteNum(communityId, null, null, null);
        praiseMap.put("toDoTaskNum", toDoTaskNum);
        praiseMap.put("totalTaskNum", totalTaskNum);
        return praiseMap;
    }

    @Override
    public Map<String, Object> getOrToBeReNum(Long communityId) {
        Map<String, Object> orToBeReMap = new HashMap<>(2);
        //投诉建议
        orToBeReMap.put("orComplaintNum", workTableMapper.getOrComplaintNum(communityId));
        //待办审核
        orToBeReMap.put("orToBeReNum", workTableMapper.getOrToBeReNum(communityId));
        return orToBeReMap;
    }

    @Override
    public Map<String, Object> getTotalMeterTypeSum(Long communityId, Long meterType, String meterTime) {

        return workTableMapper.getTotalMeterTypeSum(communityId, meterType, meterTime);
    }

    @Override
    public List<Map<String, Object>> getMeterTypeSum(Long communityId, Long meterType, String meterTime) {

        return workTableMapper.getMeterTypeSum(communityId, meterType, meterTime);
    }

    @Override
    public Map<String, Object> getBenefitCommunity(Long communityId, String payTime) {
        Map<String, Object> bigMap = new HashMap<>(3);
        String tableName = TableUtils.getTableName(TableConstants.PREFIX_PAY_LOG, communityId);
        String newName = TableConstants.LOG_DB_NAME + "." + tableName;
        Integer numVisitor = workTableMapper.existTable(tableName);
        if (numVisitor > 0) {
            //小区收益 停车费
            bigMap.put("parkingFee", workTableMapper.getAmountPaid(newName, communityId, 0L, payTime));
            //其他费用收益
            bigMap.put("otherFee", workTableMapper.getOtherAmountPaid(newName, communityId, 0L, payTime));
        } else {
            bigMap.put("parkingFee", 0);
            bigMap.put("otherFee", 0);
        }
        //物业费收益
        BigDecimal propertyFee = workTableMapper.getProAmountPaid(communityId, payTime);
        if (null != propertyFee) {
            bigMap.put("propertyFee", propertyFee);
        } else {
            bigMap.put("propertyFee", 0);
        }


        return bigMap;
    }

    @Override
    public AjaxResult getFoundData(Long communityId,String date) {
        AjaxResult ajaxResult = new AjaxResult();


        List<GetFoundDataVo> foundDataVoList = Lists.newArrayList();
        List<SysDictData> dictCache = DictUtils.getDictCache("pay_type");
        List<GetFoundDataVo> foundDataList = workTableMapper.getFoundData(communityId,date);
        final BigDecimal[] totalAmount = {new BigDecimal("0.00")};
        dictCache.stream().forEach(sysDictData -> {
            GetFoundDataVo getFoundDataVo = new GetFoundDataVo();
            if (CollectionUtils.isNotEmpty(foundDataList)) {
                List<GetFoundDataVo> collect = foundDataList.stream().filter(gfv -> gfv.getName().equals(sysDictData.getDictValue())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(collect)) {
                    getFoundDataVo.setValue(collect.get(0).getValue());
                    totalAmount[0] = totalAmount[0].add(getFoundDataVo.getValue());
                }else{
                    getFoundDataVo.setValue(new BigDecimal("0.00"));
                }
            }else {
                getFoundDataVo.setValue(new BigDecimal("0.00"));
            }
            getFoundDataVo.setName(sysDictData.getDictLabel());
            foundDataVoList.add(getFoundDataVo);
        });

        ajaxResult.put("data", foundDataVoList);
        ajaxResult.put("totalAmount", totalAmount[0]);
        return ajaxResult;
    }
}
