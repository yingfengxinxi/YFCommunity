package com.wr.estate.mapper;

import com.wr.remote.estate.phone.BusContact;

import java.util.List;

/**
 * @description 常用电话mapper
 * @author 荀彧
 * @date 2022-10-14
 */
public interface PhoneMapper {

    /**
     * 分页查询
     * @param busContact
     * @return
     */
    List<BusContact> pageList(BusContact busContact);

    /**
     * 批量删除信息
     *
     * @param contactId 需要删除的ID
     * @return 结果
     */
    int deleteByIds(Long[] contactId);

    /**
     * 新增
     * @param busContact
     * @return
     */
    int add(BusContact busContact);

    /**
     * 修改带回数据
     * @param contactId
     * @return
     */
    BusContact queryDeatil(Long contactId);

    /**
     * 修改
     * @param busContact
     * @return
     */
    int updatePhone(BusContact busContact);

}
