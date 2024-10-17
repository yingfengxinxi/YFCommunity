package com.wr.api.owner.mapper.home.advice;

import com.wr.api.owner.entity.home.advice.Complaint;
import com.wr.api.owner.entity.home.advice.Suggestion;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WuKong
 * @Date: 2022-11-16 15:44:57
 * @Desc: 意见箱数据层
 */
@Repository
@Mapper
public interface SuggestionMapper {

    /**
     * 查询列表
     *
     * @param suggestion
     * @return
     */
    List<Suggestion> selectSuggestionList(Suggestion suggestion);

    /**
     * 根据id查详情
     *
     * @param suggestId
     * @return
     */
    Suggestion getSuggestionById(Long suggestId);

    /**
     * 新增
     *
     * @param suggestion
     * @return
     */
    Integer insertSuggestion(Suggestion suggestion);

}
