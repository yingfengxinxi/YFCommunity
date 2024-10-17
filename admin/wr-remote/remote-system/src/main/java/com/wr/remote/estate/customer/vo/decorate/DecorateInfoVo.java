package com.wr.remote.estate.customer.vo.decorate;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.remote.estate.customer.decorate.Worker;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: 晁盖
 * @Date: 2022-10-11 13:40:57
 * @Desc: 装修详情
 */
@Data
public class DecorateInfoVo {

    private static final long serialVersionUID = -1875527112323569432L;
    //工单编号
    private String no;

    //装修进度
    private String renovationAuditStatus;
    private String renovationAuditStatusName;

    //工单创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 社区名称
     */
    private String communityName;
    /**
     * 所属楼栋
     */
    private String buildingName;
    /**
     * 单元号
     */
    private String unitName;
    /**
     * 门牌号
     */
    private String roomName;
    //所在楼层
    private String floor;
    //房屋面积
    private String roomArea;
    //房屋坐落
    private String houseAddress;
    //房屋属性
    private String roomType;
    /**
     * 计划装修开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date planStart;
    /**
     * 计划装修结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date planEnd;

    //装修公司
    private String companyName;
    //负责人
    private String personName;
    //负责人联系方式
    private String personPhone;
    //项目
    private String projectItem;
    private String remarks;
    //产权
    private String warrant;
    private List<String> warrants;
    //身份证正面
    private String cardFont;
    //身份证反面
    private String cardBack;
    //方案
    private String designScheme;
    private List<String> designSchemes;
    //装修合同
    private String decorationContract;
    private List<String> decorationContracts;
    //其他
    private String other;
    private Long roomId;
    private List<String> others;
}
