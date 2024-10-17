package com.wr.estate.mapper;

import com.wr.remote.estate.custom.vo.SuggestDeatilVO;
import com.wr.remote.estate.custom.vo.SuggestListVO;

import java.util.List;

/**
 * @Author: 荀彧
 * @Date: 2022-10-12 14:01:07
 * @Desc: 意见箱Mapper
 */
public interface SuggestMapper {

    /**
     * 查询所有意见箱数据
     * @param suggestListVO
     * @return
     */
    List<SuggestListVO> getSuggestList(SuggestListVO suggestListVO);

    /**
     * 根据ID查询意见详情
     * @return
     */
    SuggestDeatilVO getSuggestDeatilById(Long suggestId);


}
