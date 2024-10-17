package com.wr.api.owner.service.impl.home.advice;

import com.wr.api.owner.entity.home.advice.Suggestion;
import com.wr.api.owner.mapper.home.advice.SuggestionMapper;
import com.wr.api.owner.service.home.advice.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: WuKong
 * @Date: 2022-11-16 15:50:43
 * @Desc: 意见箱业务层
 */
@Service
public class SuggestionServiceImpl implements SuggestionService {
    @Autowired
    private SuggestionMapper suggestionMapper;

    @Override
    public List<Suggestion> selectSuggestionList(Suggestion suggestion) {
        return suggestionMapper.selectSuggestionList(suggestion);
    }

    @Override
    public Suggestion getSuggestionById(Long suggestId) {
        return suggestionMapper.getSuggestionById(suggestId);
    }

    @Override
    public Integer insertSuggestion(Suggestion suggestion) {
        return suggestionMapper.insertSuggestion(suggestion);
    }
}
