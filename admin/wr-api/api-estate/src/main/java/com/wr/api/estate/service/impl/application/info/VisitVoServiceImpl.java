package com.wr.api.estate.service.impl.application.info;

import com.wr.api.estate.entity.vo.LiaisonVo;
import com.wr.api.estate.entity.vo.application.info.VisitLogVo;
import com.wr.api.estate.mapper.application.TableMapper;
import com.wr.api.estate.mapper.application.info.VisitVoMapper;
import com.wr.api.estate.service.application.info.VisitVoService;
import com.wr.common.customize.constant.TableConstants;
import com.wr.common.customize.util.TableUtils;
import com.wr.common.security.utils.SecurityUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-16 18:54:49
 * @Desc:
 */
@Service
public class VisitVoServiceImpl implements VisitVoService {
    @Autowired
    private VisitVoMapper visitVoMapper;
    @Autowired
    private TableMapper tableMapper;

    @Override
    public List<VisitLogVo> getList(VisitLogVo visitLogVo) {
        return visitVoMapper.getList(visitLogVo);
    }

    @Override
    public List<VisitLogVo> getVisitList(VisitLogVo visitLogVo) {
        String tableNameVisit = TableUtils.getTableName(TableConstants.PREFIX_VISITOR_LOG, visitLogVo.getCommunityId(),new Date());
        String newNameVisit = TableConstants.LOG_DB_NAME + "." + tableNameVisit;
        visitLogVo.setTableName(newNameVisit);
        Integer num = tableMapper.existTable(tableNameVisit);
        if (num == 0) {
           return Lists.newArrayList();
        }
        return visitVoMapper.getVisitList(visitLogVo);
    }

    @Override
    public int getVisitCount(VisitLogVo visitLogVo) {
        String tableNameVisit = TableUtils.getTableName(TableConstants.PREFIX_VISITOR_LOG,visitLogVo.getCommunityId(),new Date());
        String newNameVisit = TableConstants.LOG_DB_NAME + "." + tableNameVisit;
        visitLogVo.setTableName(newNameVisit);
        Integer num = tableMapper.existTable(tableNameVisit);
        if (num == 0) {
            return 0;
        }
        return visitVoMapper.getVisitCount(visitLogVo);
    }

    @Override
    public List<LiaisonVo> getVisitPageList(LiaisonVo liaisonVo) {

        return visitVoMapper.getVisitPageList(liaisonVo);
    }

    /**
     *
     * @param liaisonId
     * @return
     */
    @Override
    public LiaisonVo getVisitorById(Long liaisonId) {
        return visitVoMapper.getVisitorById(liaisonId);
    }
}
