package com.wr.remote.estate.manage.contract;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.sql.Date;
/**
 * @Author: lusu
 * @Date: 2022/10/12 14:46
 * @Desc: 租客档案表
 */
@Data
public class Tenant extends BaseEntity {

  private static final long serialVersionUID = 8211345593168194433L;
  /**
   *主键
   */
  private Long tenantId;
  /**
   *小区id，表bus_community
   */
  private Long communityId;
  /**
   *楼栋id，表bus_building
   */
  private Long buildingId;
  /**
   *单元id，表bus_unit
   */
  private Long unitId;
  /**
   *当前所住房间id，表bus_room
   */
  private Long roomId;
  /**
   *账号id，表bus_account
   */
  private Long accountId;
  /**
   *业主id，表bus_owner，物业承租时为0
   */
  private Long ownerId;
  /**
   *租客姓名
   */
  private String tenantName;
  /**
   *手机号
   */
  private String tenantPhone;
  /**
   *性别：0=男，1=女，2=未知
   */
  private String gender;
  /**
   *证件类型：见数据字典 sys_certificate
   */
  private String cardType;
  /**
   *证件号码
   */
  private String cardNo;
  /**
   *证件照（正面）
   */
  private String cardFont;
  /**
   *证件照（反面）
   */
  private String cardBack;
  /**
   *人脸URL
   */
  private String faceUrl;
  /**
   *户籍地址
   */
  private String domicileAddress;
  /**
   *政治面貌：0=党员，1=团员，2=其他
   */
  private String politicsStatus;
  /**
   *民族
   */
  private String nation;
  /**
   *籍贯
   */
  private String nativePlace;
  /**
   *宗教信仰
   */
  private String religion;
  /**
   *文化程度
   */
  private String eduLevel;
  /**
   *兵役状况
   */
  private String military;
  /**
   *婚姻状况：0=未婚，1=已婚
   */
  private String marriageStatus;
  /**
   *国籍
   */
  private String nationality;
  /**
   *人口类型：0=户籍人口，1=常驻人口，2=暂住人口
   */
  private String population;
  /**
   *职业
   */
  private String career;
  /**
   *QQ号
   */
  private String qqNumber;
  /**
   *邮箱
   */
  private String email;
  /**
   *数据来源：0=物业注册，1=手机注册
   */
  private String dataSource;
  /**
   *个人介绍
   */
  private String selfIntro;
  /**
   *审核状态：0=审核中，1=通过，2=驳回
   */
  private String auditStatus;
  /**
   *审核时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date auditTime;
  /**
   *驳回理由
   */
  private String rejectReason;
  /**
   *删除标记：0=未删除，1=已删除
   */
  private String delFlag;
}
