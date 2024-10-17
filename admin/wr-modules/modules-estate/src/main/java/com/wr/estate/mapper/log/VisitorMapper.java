package com.wr.estate.mapper.log;

import com.wr.remote.estate.visitor.Visitor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/12 10:27
 * @Version 1.5
 */
@Mapper
public interface VisitorMapper {

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
    Visitor selectById( Visitor visitor);


    /**
     * 查询第一个小区id
     * @return
     */
    Long selectMinId();


}
