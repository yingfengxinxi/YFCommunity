package com.wr.api.estate.service.application.serve;

import com.wr.api.estate.entity.application.serve.VisitLog;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: lvzy
 * @Date: 2021-11-14 10:42:25
 * @Desc: 访客通行记录业务层
 */
public interface VisitLogService {

    /**
     * 增加访客通信记录
     * @param visitLog
     * @return
     */
    Integer insertVisitLog(VisitLog visitLog);

}
