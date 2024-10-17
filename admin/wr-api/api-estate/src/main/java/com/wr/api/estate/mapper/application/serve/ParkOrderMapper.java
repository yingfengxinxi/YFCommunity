package com.wr.api.estate.mapper.application.serve;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.api.estate.entity.vo.application.serve.ParkOrderVo;
import com.wr.remote.estate.financial.ParkOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/1/21 10:24
 */
public interface ParkOrderMapper extends BaseMapper<ParkOrder> {

    /**
     * @param userId
     * @return
     */
    List<ParkOrderVo> getList(@Param("userId") Long userId);

    ParkOrderVo get(@Param("id") String id);
}
