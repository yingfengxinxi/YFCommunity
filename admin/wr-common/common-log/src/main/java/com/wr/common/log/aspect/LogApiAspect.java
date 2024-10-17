package com.wr.common.log.aspect;

import com.alibaba.fastjson2.JSON;
import com.wr.common.core.utils.ServletUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.ip.IpUtils;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessStatus;
import com.wr.common.log.service.AsyncLogService;
import com.wr.remote.domain.ApiOperLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

/**
 * @Author: RainCity
 * @Date: 2022-09-03 11:06:06
 * @Desc: api 操作日志记录处理
 */
@Aspect
@Component
public class LogApiAspect {
    private static final Logger log = LoggerFactory.getLogger(LogApiAspect.class);
    
    @Autowired
    private AsyncLogService asyncLogService;

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(apiControllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, ApiLog apiControllerLog, Object jsonResult) {
        
        handleLog(joinPoint, apiControllerLog, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "@annotation(apiControllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, ApiLog apiControllerLog, Exception e) {
        
        handleLog(joinPoint, apiControllerLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, ApiLog apiControllerLog, final Exception e, Object jsonResult) {
        try {
            // *========数据库日志=========*//
            ApiOperLog operLog = new ApiOperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            operLog.setOperateIp(ip);
            operLog.setOperateUrl(ServletUtils.getRequest().getRequestURI());

            if (null != e) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, apiControllerLog, operLog, jsonResult);
            // 保存数据库
            // TODO: 2022/9/18 部署时放开
            //asyncLogService.saveApiLog(operLog);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * @param log 日志
     * @param apiOperLog 操作日志
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, ApiLog log, ApiOperLog apiOperLog, Object jsonResult) {
        // 设置action动作
        apiOperLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        apiOperLog.setTitle(log.title());
        // 设置操作人类别
        apiOperLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, apiOperLog);
        }
        // 是否需要保存response，参数和值
        if (log.isSaveResponseData() && StringUtils.isNotNull(jsonResult)) {
            apiOperLog.setJsonResult(StringUtils.substring(JSON.toJSONString(jsonResult), 0, 2000));
        }
    }

    /**
     * 获取请求的参数，放到log中
     * @param operLog 操作日志
     */
    private void setRequestValue(JoinPoint joinPoint, ApiOperLog operLog) {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = LogAspectUtil.argsArrayToString(joinPoint.getArgs());
            operLog.setOperateParam(StringUtils.substring(params, 0, 2000));
        }
    }


}
