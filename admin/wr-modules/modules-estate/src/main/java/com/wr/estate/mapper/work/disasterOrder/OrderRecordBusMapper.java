package com.wr.estate.mapper.work.disasterOrder;

import com.wr.remote.work.vo.OrderRecordVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: xuShu
 * @Date: 2022-10-31 16:32:56
 * @Desc:
 */
@Mapper
public interface OrderRecordBusMapper {
    /**
     *  工单执行情况表添加数据
     * @param orderRecordVo
     * @return
     */
    int insertBusRecord(OrderRecordVo orderRecordVo);
}
