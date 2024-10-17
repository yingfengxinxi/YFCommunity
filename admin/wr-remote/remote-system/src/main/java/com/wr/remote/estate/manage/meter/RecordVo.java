package com.wr.remote.estate.manage.meter;

import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: Guan Yu
 * @Date: 2022-10-25 14:55:16
 * @Desc:
 */
@Data
public class RecordVo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1102606505171079209L;

    private Long recordId;

    private Long communityId;

    private Long roomId;

    private String roomName;

    private String unitName;

    private String buildingName;

    /**
     * 是否已推送：0=否，1=是
     */
    private String meterPush;
    //年
    private String meterReadingTime;
    private String meterReadingStatus;
    private String energyConsumptionType;
    private String no;
    private String equipmentNo;

    private String data;

    /**
     * 门牌号
     */

    private String houseName;

    private String ownerName;

    /**
     * 上次抄表数
     */
    private BigDecimal lastConsume;
    /**
     * 本次抄表数
     */
    private BigDecimal thisConsume;
    /**
     * 前端传入日期
     */
    private String selectDate;
    private String flag;
    private String year;
    @Excel(name = "设备类型", sort = 1)
    private String meterReadingType;
    @Excel(name = "1月", sort = 2)
    private String one="-";
    @Excel(name = "2月", sort = 3)
    private String two="-";
    @Excel(name = "3月", sort = 3)
    private String three="-";
    @Excel(name = "4月", sort = 4)
    private String four="-";
    @Excel(name = "5月", sort = 5)
    private String five="-";
    @Excel(name = "6月", sort = 6)
    private String six="-";
    @Excel(name = "7月", sort = 7)
    private String seven="-";
    @Excel(name = "8月", sort = 8)
    private String eight="-";
    @Excel(name = "9月", sort = 9)
    private String nine="-";
    @Excel(name = "10月", sort = 10)
    private String ten="-";
    @Excel(name = "11月", sort = 11)
    private String eleven="-";
    @Excel(name = "12月", sort = 12)
    private String twelve="-";

}
