package com.wr.remote.work.vo;

/**
 * @Author: xuShu
 * @Date: 2022-11-01 15:05:20
 * @Desc: 工单执行人信息
 */
public class BusDisasterRecordVo {

    /**
     *人员姓名
     */
    private String staffName;

    /**
     * 手机号
     */
    private String staffPhone;

    /**
     * 身份证号
     */
    private String cardNo;

    /**
     * 岗位：0=安防人员，1=维修人员，2=装修人员，3=场馆人员，4=保洁人员，5=志愿者
     */
    private String workPost;
}
