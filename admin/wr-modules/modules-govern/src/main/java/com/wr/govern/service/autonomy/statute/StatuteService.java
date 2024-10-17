package com.wr.govern.service.autonomy.statute;


import com.wr.common.core.web.domain.AjaxResult;
import com.wr.remote.estate.manage.vo.contract.LeaseVo;
import com.wr.remote.govern.autonomy.statute.Statute;
import com.wr.remote.govern.autonomy.statute.vo.StatuteVo;

import java.text.ParseException;
import java.util.List;

/**
 * @Author: luSu
 * @Date: 2022/10/25 14:46
 * @Desc: 自治规约接口
 */
public interface StatuteService {

    /**
     * 查询自治规约列表
     * @param statute
     * @return
     * @throws ParseException
     */
    List<Statute> getList(Statute statute);


    /**
     *  新增自治规约
     * @param statute
     * @return
     */
    Integer addStatute(Statute statute);

    /**
     * 根据bus_statute_id查询bus_statute的记录
     * @param statuteId
     * @return
     */
    Statute getStatuteById(Long statuteId);

    /**
     * 根据statute_id查询bus_statute_rang的详情
     * @param statuteId
     * @return
     */
    List<Long> selectStatuteCommunityById(Long statuteId);


    /**
     * 修改
     * @param statute
     * @return
     */
    int updateStatute(Statute statute);

    /**
     * 删除
     * @param statuteId
     * @return
     */
    int delStatute(Long[] statuteId);
}
