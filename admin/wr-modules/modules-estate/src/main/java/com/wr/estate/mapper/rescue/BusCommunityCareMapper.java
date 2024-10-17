package com.wr.estate.mapper.rescue;

import com.wr.estate.entity.BusCommunityCare;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/9 14:23
 */
public interface BusCommunityCareMapper {


    List<BusCommunityCare> getList(BusCommunityCare communityCare);

    /**
     * @param id
     * @return
     */
    BusCommunityCare getById(@Param("id") Long id);

    /**
     * @param communityCare
     */
    void insert(BusCommunityCare communityCare);

    /**
     * @param communityCare
     */
    void update(BusCommunityCare communityCare);

    /**
     * 删除
     *
     * @param id
     */
    void delete(@Param("id") Long id);

    /**
     * 修改发布状态
     *
     * @param eventReleaseTime
     * @param status
     * @param id
     */
    void updateByIdStatus(@Param("eventReleaseTime") Date eventReleaseTime, @Param("eventReleaseBy") String eventReleaseBy, @Param("status") String status, @Param("id") Long id);
}
