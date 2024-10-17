package com.wr.api.estate.entity.vo.application.serve;

import com.wr.api.estate.entity.application.serve.Older;
import com.wr.remote.estate.manage.contract.Owner;
import lombok.Data;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-15 13:32:20
 * @Desc: 老人档案视图类
 */
@Data
public class OlderVO extends Older {

    private static final long serialVersionUID = -5213213464918954799L;

    /**
     * 业主id
     */
    private Long ownerId;

    /**
     * 业主名称
     */
    private String ownerName;

    /**
     * 业主手机号
     */
    private String ownerPhone;

    /**
     * 业主年龄
     */
    private String age;

    /**
     * 性别 0男 1女
     */
    private String gender;


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

}
