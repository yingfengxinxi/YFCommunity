package com.wr.api.estate.entity.vo.application.serve;

import com.wr.api.estate.entity.application.serve.Older;
import com.wr.remote.estate.manage.contract.Owner;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-22 14:54:05
 * @Desc: 老人档案详情视图类
 */
@Data
public class OlderDetailVO extends Older {
    private static final long serialVersionUID = 6614456378088539135L;

    /**
     * 业主信息
     */
    private Owner owner;

    /**
     * 楼宇名称
     */
    private String buildingName;

    /**
     * 单元名称
     */
    private String unitName;

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 小区名称
     */
    private String communityName;

    /**
     * 房间状态  0=未售，1=已售，2=入住，3=装修，4=已拿房，5=整租，6=合租，7=群租
     */
    private String roomStatus;
}
