package com.wr.remote.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wr.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * @Author: RainCity
 * @Date: 2022-09-03 11:06:06
 * @Desc: api 操作日志记录表
 */
public class ApiOperLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 日志主键 */
    private Long operateId;

    /** 操作模块 */
    private String title;

    /** 业务类型（0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据） */
    private Integer businessType;

    /** 业务类型数组 */
    private Integer[] businessTypes;

    /** 请求方法 */
    private String method;

    /** 请求方式 */
    private String requestMethod;

    /** 操作类别（2：手机端用户 3：公众号用户 4：小程序用户 5：H5用户） */
    private Integer operatorType;

    /** 请求url */
    private String operateUrl;

    /** 操作地址 */
    private String operateIp;

    /** 操作地点 */
    private String operateSite;

    /** 请求参数 */
    private String operateParam;

    /** 返回参数 */
    private String jsonResult;

    /** 操作状态（0正常 1异常） */
    private Integer status;

    /** 错误消息 */
    private String errorMsg;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;

    public Long getOperateId()
    {
        return operateId;
    }

    public void setOperateId(Long operateId)
    {
        this.operateId = operateId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Integer getBusinessType()
    {
        return businessType;
    }

    public void setBusinessType(Integer businessType)
    {
        this.businessType = businessType;
    }

    public Integer[] getBusinessTypes()
    {
        return businessTypes;
    }

    public void setBusinessTypes(Integer[] businessTypes)
    {
        this.businessTypes = businessTypes;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getRequestMethod()
    {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod)
    {
        this.requestMethod = requestMethod;
    }

    public Integer getOperatorType()
    {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType)
    {
        this.operatorType = operatorType;
    }

    public String getOperateUrl()
    {
        return operateUrl;
    }

    public void setOperateUrl(String operateUrl)
    {
        this.operateUrl = operateUrl;
    }

    public String getOperateIp()
    {
        return operateIp;
    }

    public void setOperateIp(String operateIp)
    {
        this.operateIp = operateIp;
    }

    public String getOperateSite()
    {
        return operateSite;
    }

    public void setOperateSite(String operateSite)
    {
        this.operateSite = operateSite;
    }

    public String getOperateParam()
    {
        return operateParam;
    }

    public void setOperateParam(String operateParam)
    {
        this.operateParam = operateParam;
    }

    public String getJsonResult()
    {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult)
    {
        this.jsonResult = jsonResult;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public Date getOperateTime()
    {
        return operateTime;
    }

    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }
}
