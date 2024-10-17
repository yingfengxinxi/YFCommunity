package com.wr.remote.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author lvzy
 * @Date 2024/2/5 17:15
 */
@Data
public class CarportVo {
    //车位id
    private Long carportNoId;
    //车位编号
    private String carportNo;
    //车位属性
    private String carportNature;
    //车位是否绑定
    private String isCarport;

}
