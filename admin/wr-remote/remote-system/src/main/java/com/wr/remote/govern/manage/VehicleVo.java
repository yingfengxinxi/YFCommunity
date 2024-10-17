package com.wr.remote.govern.manage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-02 09:22:15
 * @Desc: 车辆进出记录实体类
 */
@Data
public class VehicleVo implements Serializable {

    private static final long serialVersionUID = 1196930967924866234L;

    /**
     * 00-02
     */
    private Integer zeroToTwo;
    /**
     * 02-04
     */
    private Integer twoToFour;
    /**
     * 04-06
     */
    private Integer fourToSix;
    /**
     * 06-08
     */
    private Integer sixToEight;
    /**
     * 08-10
     */
    private Integer eightToTen;
    /**
     * 10-12
     */
    private Integer tenToTwelve;
    /**
     * 12-14
     */
    private Integer twelveToFourteen;
    /**
     * 14-16
     */
    private Integer fourteenToSixteen;
    /**
     * 16-18
     */
    private Integer sixteenToEighteen;
    /**
     * 18-20
     */
    private Integer eighteenToTwenty;
    /**
     * 20-22
     */
    private Integer twentyToTwentytwo;
    /**
     * 22-24
     */
    private Integer twentytwoTotwentyfour;

}
