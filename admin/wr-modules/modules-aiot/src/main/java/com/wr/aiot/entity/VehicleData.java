package com.wr.aiot.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2022-11-02 10:32:29
 * @Desc:
 */
@Data
public class VehicleData implements Serializable {
    private static final long serialVersionUID = -896470761924805721L;

    private Integer day;

    private Integer inoutTimes;
}
