package com.wr.govern.mapper.autonomy.statute;


import com.wr.remote.estate.manage.vo.contract.LeaseVo;
import com.wr.remote.govern.autonomy.statute.Statute;
import com.wr.remote.govern.autonomy.statute.StatuteRang;
import com.wr.remote.govern.autonomy.statute.vo.StatuteVo;
import org.apache.ibatis.annotations.Mapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: luSu
 * @Date: 2022/10/25 14:46
 * @Desc: 自治规约mapper
 */
@Mapper
public interface StatuteMapper {

    /**
     * 查询自治规约列表
     * @param statute
     * @return
     * @throws ParseException
     */
    List<Statute> getList(Statute statute);

    Integer addStatute(Statute statute);

    Integer AddStatuteRang(ArrayList<StatuteRang> statuteRangs);

    /**
     * 查询自治规约表
     * @param statuteId
     * @return
     */
    Statute getStatuteById(Long statuteId);

    /**
     *根据statute_id查询bus_statute_rang  关联表的详情
     * @param statuteId
     * @return
     */
    List<Long> selectStatuteCommunityById(Long statuteId);

    /**
     * 先修改治约
     * @param statute
     */
    int updateStatute(Statute statute);

    /**
     * 通过治约id删除关联表中的所有社区id， 返回的是影响行数
     * @param statuteId
     * @return
     */
    int delCommunityByStatuteId(Long statuteId);

    /**
     * 删除治约
     * @param statuteId
     * @return
     */
    int delStatute(Long[] statuteId);

    /**
     * 批量删除关联表
     * @param statuteId
     * @return
     */
    int delCommunityByStatuteIds(Long[] statuteId);
}
