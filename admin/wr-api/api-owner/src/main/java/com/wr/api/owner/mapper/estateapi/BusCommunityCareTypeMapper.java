package com.wr.api.owner.mapper.estateapi;


import com.wr.api.owner.entity.estateapi.BusCommunityCareType;
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
     *
     * @param name
     * @return
     */
    Long getByNameId(@Param("name") String name);

}
