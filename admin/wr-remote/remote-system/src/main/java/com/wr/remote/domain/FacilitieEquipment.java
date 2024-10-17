package com.wr.remote.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import com.wr.common.core.web.domain.BaseEntity;
import com.wr.remote.domain.vo.PatrolWorkOrderVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/2/26 21:06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel("设施设备管理")
@TableName(value = "xccj_community.bus_facilitie_equipment")
public class FacilitieEquipment extends BaseEntity {

    //	主键
    private String id;
    //	小区id，表xccj_community.bus_community
    private Long communityId;
    //	设备名称
    @Excel(name = "设备名称",prompt = "必填")
    private String equipmentName;
    //品牌
    @Excel(name = "设备品牌")
    private String bindName;
    //设备类型 数据字典 facilitie_equipment_type
    @Excel(name = "设备类型", readConverterExp = "0=电梯,1=排污泵,2=高压电房,3=抵押电房,4=灭火器,5=消防栓",combo="电梯,排污泵,高压电房,抵押电房,灭火器,消防栓",prompt = "必填")
    private String facilitieEquipmentType;
    //规格型号
    @Excel(name = "规格型号")
    private String specificationsModel;
    //设备状态
    @Excel(name = "设备状态", readConverterExp = "0=正常,1=维修",combo="正常,维修")
    private String equipmentStatus;
    //供货单位
    @Excel(name = "供货单位")
    private String unitSupply;

    //安装时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "安装时间", dateFormat = "yyyy-MM-dd")
    private Date installationTime;
    //启用时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "启用时间", dateFormat = "yyyy-MM-dd")
    private Date enableTime;
    //质保到期时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "质保到期时间", dateFormat = "yyyy-MM-dd")
    private Date warrantyExpireTime;
    //最后巡检时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date finalInspectionTime;
    //维保单位
    @Excel(name = "维保单位")
    private String originalUnit;
    //营业执照号码
    @Excel(name = "营业执照号码")
    private String businessLicense;
    //承担管理维护负责人
    @Excel(name = "承担管理维护负责人")
    private String chargeName;
    //承担管理维护负责人联系方式
    @Excel(name = "承担管理维护负责人联系方式")
    private String chargePhone;
    //安装位置
    @Excel(name = "安装位置",prompt = "必填")
    private String installationLocation;
    //产品编号
    @Excel(name = "产品编号",prompt = "必填、唯一")
    private String productNumber;
    //使用登记证编号
    @Excel(name = "使用登记证编号")
    private String registrationNumber;
    //其他附件
    private String other;
    //标识牌
    private String signs;
    //说明书
    private String instructions;
    //设备图片
    private String equipmentImages;
    //二维码
    private String qrCode;
    //删除标记：0=未删除，1=已删除
    @TableLogic
    private String delFlag;

    @TableField(exist = false)
    private String startTime;

    @TableField(exist = false)
    private String endTime;

    @TableField(exist = false)
    List<PatrolWorkOrderVo> patrolWorkOrderList;

}
