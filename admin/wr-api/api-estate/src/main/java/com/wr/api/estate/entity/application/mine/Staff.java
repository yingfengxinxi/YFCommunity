package com.wr.api.estate.entity.application.mine;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工表实体类,表staff
 * @author DIANWEI
 */
@Data
public class Staff extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 员工id
     */
    private Long staffId;
    /**
     * 员工名称
     */
    private String staffName;
    /**
     * 员工年龄
     */
    private Integer age;
    /**
     * 手机号
     */
    private String staffPhone;
    /**
     * 性别：0=男，1=女 2 = 未知
     */
    private String gender;
    /**
     * 身份证号码
     */
    private String idNumber;
    /**
     * 健康状态 0：健康 1：残疾
     */
    private String healthStatus;
    /**
     * 学历水平
     */
    private String degreeLevel;
    /**
     * 职业证书
     */
    private String vocationalCertificate;
    /**
     * 员工账号
     */
    private String accountNumber;
    /**
     * 员工账号密码
     */
    private String password;
    /**
     * 工作类型 0：安防人员 1：维修人员 2：场馆人员
     */
    private String workPost;
    /**
     * 工作资历
     */
    private String qualifications;
    /**
     * 入职时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date hiredate;
    /**
     * 家庭住址
     */
    private String address;
    /**
     * 个人介绍
     */
    private String selfIntroduction;
    /**
     * 员工照片
     */
    private String avatar;
    /**
     * 离职    0:在职     1:已离职
     */
    private String dimissionFlag;
    /**
     * 所属物业
     */
    private Long deptId;
    /**
     * 删除标记0：未删除1已删除
     */
    private String delFlag;
    /**
     * 在职状态：0=在职，1=离职
     */
    private String jobStatus;
    /**
     * 离职时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date resignationTime;
    /**
     * 车牌号
     */
    private String plantNo;


}