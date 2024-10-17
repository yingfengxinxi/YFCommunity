package com.wr.estate.mapper.center.personnelfilemanage;

import com.wr.remote.domain.vo.VisitorVo;
import com.wr.remote.estate.center.vo.VisitorFileVo;
import com.wr.remote.estate.visitor.Visitor;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lvzy
 * @Date: 2022-10-18 14:51:22
 * @Desc: 访客档案
 */
public interface VisitorFileMapper {
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
    VisitorVo getVisitorById(@Param("visitorId") Long visitorId);

    /**
     * 租客解约 新增访客
     * @param visitorVo
     * @return
     */
    int insertVisitor(VisitorVo visitorVo);

    /**
     * 删除 拜访记录
     * @param ids
     * @return
     */
    int removeLiaison(List<Long> ids);
    /**
     * 删除
     * @param ids
     * @return
     */
    int deleted(Long[] ids);


    List<Long> queryAccountId(Long[] ids);
    /**
     * 查询 访客记录
     * @param
     * @return
     */
    List<LinkedHashMap<String,Object>> queryLiaisonByAccountId(Map<String,Object> params);
}
