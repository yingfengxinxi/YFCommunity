package com.wr.api.owner.entity.vo.location;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: lvzy
 * @Date: 2022-11-16 11:25:01
 * @Desc:   远程控制开门
 */
@Data
public class DoorVo implements Serializable
{
    private static final long serialVersionUID = 1L;
    //门禁设备名称-bus_access
    private String location;
    //门禁点唯一编码-bus_door
    private String indexCode;
}
