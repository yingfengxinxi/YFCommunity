package com.wr.estate.service.log;

import com.wr.remote.estate.visitor.Visitor;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/12 11:07
 * @Version 1.5
 */
public interface VisitorService {

    /**
     * 查询集合
     * @param visitor
     * @return
     */
    List<Visitor> selectList(Visitor visitor);


    /**
     * 查寻小区集合
     * @return
     */
    List<Visitor> selectCommunityIdList();

    /**
     * 根据id查询数据
     * @param visitor
     * @return
     */
    Visitor selectById(Visitor visitor);

}
