package com.wr.api.owner.mapper;

import java.util.List;

/**
 * @Author: RainCity
 * @Date: 2022-11-15 10:34:13
 * @Desc: 查询敏感词
 */
public interface SensitiveMapper {

    /**
     * 查询所有敏感词
     * @return java.util.List<java.lang.String>
     */
    List<String> selectSensitiveList();
}
