package com.wr.estate.entity.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @Author lvzy
 * @Date 2024/2/19 14:33
 */
@Data
public class OwnerFileImportVo {


    @Excel(name = "楼栋", prompt = "必填")
    private String buildingName;

    @Excel(name = "单元", prompt = "必填")
    private String unitName;


    @Excel(name = "房号", prompt = "必填")
    private String roomName;
    /**
     * 房屋属性，房屋性质：0=商品房,1=商铺,2=成套住宅,3=别墅
     */
    @Excel(name = "房屋属性", readConverterExp = "0=商品房,1=商铺,2=成套住宅,3=别墅", combo = "商品房,商铺,成套住宅,别墅", prompt = "必填")
    private String roomType;

    @Excel(name = "房屋面积", prompt = "必填")
    private Double roomArea;

    @Excel(name = "房屋坐落", prompt = "必填")
    private String houseAddress;

    @Excel(name = "业主姓名", prompt = "必填")
    private String ownerName;

    @Excel(name = "性别", combo = "男,女,未知", readConverterExp = "0=男,1=女,2=未知", prompt = "必填")
    private String gender;

    @Excel(name = "业主手机号", prompt = "必填")
    private String ownerPhone;

    @Excel(name = "证件类型", combo = "公民身份证,军人身份证,警官证,户口簿,临时身份证,外国护照,港澳通行证,台胞通行证,离休干部荣誉证,军官退休证,文职干部退休证,军事院校学员证,武装警察身份证,军官证,文职干部证,军人士兵证,武警士兵证,其他证件", readConverterExp = "I=公民身份证,S=军人身份证,J=警官证,K=户口簿,T=临时身份证,F=外国护照,D=港澳通行证,W=台胞通行证,R=离休干部荣誉证,B=军官退休证,A=文职干部退休证,C=军事院校学员证,E=武装警察身份证,G=军官证,H=文职干部证,L=军人士兵证,M=武警士兵证,U=其他证件", prompt = "必填")
    private String cardType;

    @Excel(name = "证件号码", prompt = "必填")
    private String cardNo;
    /**
     * 与户主关系：0=本人，1=配偶，2=父母，3=子女，4=其他,5=租客
     */
    @Excel(name = "与户主关系", combo = "本人,配偶,父母,子女,其他,租客", readConverterExp = "0=本人,1=配偶,2=父母,3=子女,4=其他,5=租客", prompt = "必填")
    private String ownerRelation;

    /**
     * 政治面貌：0=党员，1=团员，2=其他
     */
    @Excel(name = "政治面貌", combo = "党员,团员,其他", readConverterExp = "0=党员,1=团员,2=其他", prompt = "必填")
    private String politicsStatus;

    //紧急联系人
    @Excel(name = "紧急联系人")
    private String emergencyContact;
    //紧急联系人电话
    @Excel(name = "紧急联系人电话")
    private String emergencyContactPhone;

    //紧急联系人电话

    /**
     * 交房时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "交房时间",prompt ="yyyy-MM-dd")
    private Date deliveryTime;

}
