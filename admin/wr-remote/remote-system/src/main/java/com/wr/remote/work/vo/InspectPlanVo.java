package com.wr.remote.work.vo;

import com.wr.remote.work.inspect.InspectDevice;
import com.wr.remote.work.inspect.InspectPlan;
import lombok.Data;

import java.util.List;

/**
 * 巡检工单表计划类
 * @Author: lvzy
 * @Date: 2022-10-25 11:56:40
 * @Desc: 巡检工单表计划类
 */
@Data
public class InspectPlanVo extends InspectPlan {

    private static final long serialVersionUID = 5020419162248907918L;

    /**
     * 巡检类型名称
     */
    private String tourName;

    /**
     * 分配方式名称
     */
    private String allotWayName;

    /**
     * 执行人姓名
     */
    private String executeName;

    /**
     * 设备类型名称
     */
    private String deviceType;

    /**
     * 设备名称
     */
    private String deviceTypeName;

    /**
     * 计划名称
     */
    private String planName;

    /**
     * 唯一标识符
     */
    private String indexCode;

    /**
     * 设备所在地址
     */
    private String addressName;

    /**
     * 巡检模式名称
     */
    private String planModelName;

    /**
     * 人员姓名 (执行人)
     */
    private String staffName;

    /**
     * 手机号 (执行人)
     */
    private String staffPhone;

    /**
     * 身份证号 (执行人)
     */
    private String cardNo;

    /**
     * 人员类别 (执行人)
     */
    private String staffTypeName;

    /**
     * 设备编号
     */
    private String noName;

    /**
     * 设备类型编号
     */
    private String typeName;

    /**
     * 设备名称
     */
    private String facilityName;

    /**
     * 工单编号
     */
    private String inspectNo;

    /**
     * 工单名称
     */
    private String inspectName;

    /**
     * 工单来源：0=计划生成，1=后台添加，2=其他工单
     */
    private String dataSource;

    /**
     * 设备编号 多选
     */
    private List<InspectDevice> tourListValueTwos;

    /**
     * 设备编号 - 单选
     */
    private String tourListValue;

    /**
     * 巡检日期
     */
    private String startAndEndTime;

    /**
     * 间隔时间
     */
    private String gaptimeName;

    /**
     * 定时任务传参用
     */
    private String orderType;


}
