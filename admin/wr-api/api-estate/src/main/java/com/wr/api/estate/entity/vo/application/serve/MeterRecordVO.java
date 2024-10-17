package com.wr.api.estate.entity.vo.application.serve;

import com.wr.api.estate.entity.application.serve.MeterRecord;
import com.wr.remote.estate.manage.contract.Owner;
import lombok.Data;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-15 09:23:20
 * @Desc: 抄表实现类
 */
@Data
public class MeterRecordVO extends MeterRecord {

    private static final long serialVersionUID = 1L;

    /**
     * 业主名称
     */
    private String ownerName;

    /**
     * 房间名
     */
    private String roomName;

    /**
     * 手机号
     */
    private String ownerPhone;
}
