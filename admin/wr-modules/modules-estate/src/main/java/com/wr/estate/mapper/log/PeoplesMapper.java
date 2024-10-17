package com.wr.estate.mapper.log;

import java.util.List;
import com.wr.remote.estate.peoples.Peoples;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author lvzy
 * @Date 2022/10/11 15:26
 * @Version 1.5
 */

@Mapper
public interface PeoplesMapper {


    /**
     * 查询集合列表
     * @param peoples
     * @return
     */
    List<Peoples> selectList(Peoples peoples);


    /**
     * 查询所有小区
     * @return
     */
    List<Peoples> selectCommunityIdList();


    /**
     * 根据id查询人员出入详细信息
     * @param logId
     * @return
     */
    Peoples selectPeoplesById(Peoples peoples);

    Peoples selectMinId();

}
