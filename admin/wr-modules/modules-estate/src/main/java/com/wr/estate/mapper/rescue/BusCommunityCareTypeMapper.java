package com.wr.estate.mapper.rescue;


import com.wr.estate.entity.BusCommunityCareType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/9 14:23
 */
public interface BusCommunityCareTypeMapper {


    List<BusCommunityCareType> getList(BusCommunityCareType communityCareType);

    /**
     * @param id
     * @return
     */
    BusCommunityCareType getById(@Param("id") Long id);

    /**
     * @param communityCareType
     */
    void insert(BusCommunityCareType communityCareType);

    /**
     * @param communityCareType
     */
    void update(BusCommunityCareType communityCareType);

    /**
     * 删除
     *
     * @param id
     */
    void delete(@Param("id") Long id);

    /**
     * @param name
     * @param id
     * @return
     */
    Long getByNameId(@Param("name") String name, @Param("id") Long id);

}
