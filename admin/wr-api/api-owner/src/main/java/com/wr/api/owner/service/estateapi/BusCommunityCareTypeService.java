package com.wr.api.owner.service.estateapi;

import com.wr.api.owner.entity.estateapi.BusCommunityCareType;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/9 14:43
 */
public interface BusCommunityCareTypeService {


    List<BusCommunityCareType> getList(BusCommunityCareType communityCareType);


    /**
     *
     * @param id
     * @return
     */
    BusCommunityCareType getById(Long id);

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
    void delete( Long id);

    /**
     *
     * @param name
     * @return
     */
    Long getByNameId(String name);
}
