package com.wr.api.estate.service.application.serve;

import com.wr.api.estate.entity.application.serve.CommunityCareTypeVo;
import com.wr.api.estate.entity.application.serve.CommunityCareVo;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/9 16:00
 */
public interface CommunityCareService {
    List<CommunityCareVo> getList(CommunityCareVo communityCareVo);

    /**
     *
     * @return
     */
    List<CommunityCareTypeVo> getTypeList();

    CommunityCareVo getById(Long id);
}
