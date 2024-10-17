package com.wr.estate.service.log;

import com.wr.remote.estate.peoples.Peoples;

import java.text.ParseException;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/11 16:39
 * @Version 1.5
 */
public interface PeoplesService {




    /**
     * 查询集合列表
     * @param peoples
     * @return
     */
    List<Peoples> selectList(Peoples peoples) throws ParseException;


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
}
