package com.wr.api.owner.service.estateapi;

import com.wr.api.owner.entity.estateapi.BusCommunityCare;
import com.wr.common.core.exception.ServiceException;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-16 13:48:21
 * @Desc: 社区关怀
 */
public interface BusCommunityCareService {


    List<BusCommunityCare> getList(BusCommunityCare communityCare);

    /**
     *
     * @param id
     * @return
     */
    BusCommunityCare getById(Long id);

    /**
     * @param communityCare
     */
    void insert(BusCommunityCare communityCare) throws ServiceException;

    /**
     * @param communityCare
     */
    void update(BusCommunityCare communityCare);

    /**
     * 删除
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 修改发布状态
     *
     * @param status
     * @param id
     */
    void updateByIdStatus(String status, Long id);
}
