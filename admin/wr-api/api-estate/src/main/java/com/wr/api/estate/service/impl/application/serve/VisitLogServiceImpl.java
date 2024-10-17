package com.wr.api.estate.service.impl.application.serve;

import com.alibaba.ttl.threadpool.agent.internal.javassist.compiler.ast.Variable;
import com.wr.api.estate.entity.application.serve.VisitLog;
import com.wr.api.estate.mapper.SensitiveMapper;
import com.wr.api.estate.mapper.application.TableMapper;
import com.wr.api.estate.mapper.application.serve.VisitLogMapper;
import com.wr.api.estate.service.application.serve.VisitLogService;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.SensitiveUtil;
import com.wr.common.customize.util.TableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author: lvzy
 * @Date: 2022-11-14 11:20:36
 * @Desc: 访客通行记录业务处理层
 */
@Service
public class VisitLogServiceImpl implements VisitLogService {

    @Autowired
    private VisitLogMapper visitLogMapper;

    @Autowired
    private TableMapper tableMapper;

    @Autowired
    private SensitiveMapper sensitiveMapper;

    @Transactional
    @Override
    public Integer insertVisitLog(VisitLog visitLog) {
        String tableName = TableUtils.getTableName(TableConstants.PREFIX_VISITOR_LOG, visitLog.getCommunityId(), new Date());
        Integer result = tableMapper.existTable(tableName);
        tableName = TableConstants.LOG_DB_NAME + "." + tableName;
        if (result != null && result > 0) {
            return visitLogMapper.insertVisitLog(visitLog, tableName);
        }
        tableMapper.createVisitLogTable(tableName);
        return visitLogMapper.insertVisitLog(SensitiveUtil.sensitiveFilter(visitLog,sensitiveMapper.selectSensitiveList()), tableName);
    }

}
