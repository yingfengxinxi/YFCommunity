package com.wr.api.owner.entity.vo.pay;

import com.wr.remote.domain.PropertyLog;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: RainCity
 * @Date: 2022-11-17 10:47:42
 * @Desc: 物业缴费记录
 */
@Data
public class PropertyLogPageVO extends PropertyLog implements Serializable {

    private String communityName;
    private String buildingName;
    private String unitName;
    private String roomName;
    private String showStatus;
    //房屋性质：1=安置房，2=商品房，3=公租房
    private String roomAttribute;
    //房屋属性：1=商品房，2=商铺，3=成套住宅，4=别墅
    private String roomType;

}
