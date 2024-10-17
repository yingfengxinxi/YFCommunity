package com.wr.remote.estate.center.vo;

import com.wr.common.core.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: lvzy
 * @Date: 2022-10-14 10:06:19
 * @Desc: 租客 excel
 */
@Data
public class TenantFileVo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Excel(name = "姓名")
    private String tenantName;

    @Excel(name = "手机号码")
    private String tenantPhone;
    @Excel(name = "身份证号码")
    private String cardNo;
    @Excel(name = "性别",readConverterExp = "0=男,1=女")
    private String gender;
    @Excel(name = "所属小区")
    private String communityName;
    @Excel(name = "所属楼宇")
    private String buildingName;
    @Excel(name = "所属单元")
    private String unitName;
    @Excel(name = "所属房间")
    private String roomName;
    @Excel(name = "业主名称")
    private String ownerName;
    @Excel(name = "业主电话")
    private String ownerPhone;
    @Excel(name = "户籍地址")
    private String domicileAddress;

    private String phone;
    private String communityId;
    private String state;
    private String name;
    private Long[] ids;
}
