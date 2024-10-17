package com.wr.api.estate.mapper.application;

import com.wr.api.estate.entity.application.serve.CommunityCareTypeVo;
import com.wr.api.estate.entity.application.serve.CommunityCareVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/9 14:23
 */
public interface CommunityCareMapper {


    List<CommunityCareVo> getList(CommunityCareVo communityCare);

    /**
     *
     * @return
     */
    List<CommunityCareTypeVo> getTypeList();

    /**
     *
     * @param id
     * @return
     */
    CommunityCareVo getById(@Param("id") Long id);

}
