package com.wr.estate.service.impl;

import com.wr.estate.mapper.SuggestMapper;
import com.wr.estate.service.SuggestService;
import com.wr.remote.estate.custom.vo.SuggestDeatilVO;
import com.wr.remote.estate.custom.vo.SuggestListVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SuggestServiceImpl implements SuggestService {

    @Resource
    private SuggestMapper suggestMapper;


    @Override
    public List<SuggestListVO> getSuggestList(SuggestListVO suggestListVO) {
        return suggestMapper.getSuggestList(suggestListVO);
    }

    @Override
    public SuggestDeatilVO getSuggestDeatilById(Long suggestId) {
        return suggestMapper.getSuggestDeatilById(suggestId);
    }
}
