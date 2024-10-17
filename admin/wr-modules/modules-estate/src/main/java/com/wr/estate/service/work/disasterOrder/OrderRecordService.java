package com.wr.estate.service.work.disasterOrder;

import com.wr.remote.work.vo.OrderRecordVo;

/**
 * @Author: xuShu
 * @Date: 2022-10-31 16:30:20
 * @Desc:
 */
public interface OrderRecordService {
    /**
     * 工单执行情况表添加数据
     * @param orderRecordVo
     * @return
     */
    int insertRecord(OrderRecordVo orderRecordVo);
}
