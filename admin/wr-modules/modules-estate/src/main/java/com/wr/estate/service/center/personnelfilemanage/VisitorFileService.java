package com.wr.estate.service.center.personnelfilemanage;

import com.wr.remote.domain.vo.VisitorVo;
import com.wr.remote.estate.center.vo.VisitorFileVo;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-18 15:05:10
 * @Desc:
 */
public interface VisitorFileService {
    /**
     * 查询
     * @param visitorFileVo
     * @return
     */
    List<VisitorFileVo> query(VisitorFileVo visitorFileVo);


    /**
     *
     * @param visitorId
     * @return
     */
    VisitorVo getVisitorById(Long visitorId);
    /**
     * 删除
     * @param ids
     * @return
     */
    int deleted(Long[] ids);
    /**
     * 查询 访客记录
     * @param params
     * @return
     */
    List<LinkedHashMap<String,Object>> queryLiaisonByAccountId(Map<String,Object> params);
}
