package com.wr.api.owner.service.home.advice;

import com.wr.api.owner.entity.home.advice.Suggestion;

import java.util.List;

/**
 * @Author: WuKong
 * @Date: 2022-11-16 15:49:52
 * @Desc: 意见箱
 */
public interface SuggestionService {
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
