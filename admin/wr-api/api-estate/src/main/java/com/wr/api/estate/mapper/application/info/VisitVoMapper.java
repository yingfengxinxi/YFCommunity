package com.wr.api.estate.mapper.application.info;

import com.wr.api.estate.entity.vo.LiaisonVo;
import com.wr.api.estate.entity.vo.application.info.VisitLogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-11-15 14:04:22
 * @Desc: 数据层
 */
public interface VisitVoMapper {
    /**
     * 列表
     *
     * @param visitLogVo
     * @return
     */
    List<VisitLogVo> getList(VisitLogVo visitLogVo);

    /**
     * 拜访列表
     *
     * @param visitLogVo
     * @return
     */
    List<VisitLogVo> getVisitList(VisitLogVo visitLogVo);

    /**
     * 访客总数
     *
     * @param visitLogVo
     * @return
     */
    int getVisitCount(VisitLogVo visitLogVo);

    List<LiaisonVo> getVisitPageList(LiaisonVo liaisonVo);

    /**
     *
     * @param liaisonId
     * @return
     */
    LiaisonVo getVisitorById(@Param("liaisonId") Long liaisonId);
}
