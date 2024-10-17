package com.wr.govern.service.autonomy.decide;


import com.wr.remote.govern.autonomy.decide.Decide;
import com.wr.remote.govern.autonomy.decide.DecideItem;
import com.wr.remote.govern.autonomy.decide.vo.decideVo;

import java.util.List;

/**
 * @Author: luSu
 * @Date: 2022/10/26 14:46
 * @Desc: 自治表决接口
 */
public interface DecideService {

    /**
     * 查询自治表决列表
     * @param decide
     * @return
     */
    List<Decide> getList(Decide decide);

    /**
     * 增加
     * @param decideVo
     * @return
     */
    Integer addDecide(decideVo decideVo);

    /**
     * 修改
     * @param decideVo
     * @return
     */
    int updateDecide(decideVo decideVo);

    /**
     * id查详情
     * @param decideId
     * @return
     */
    Decide queryDecideByIdOne(Long decideId);

    /**
     * id查询自联表
     * @param decideId
     * @return
     */
    List<Long> queryDecideRangById(Long decideId);

    /**
     * id查询选项表
     * @param decideId
     * @return
     */
    List<DecideItem> queryDecideItemById(Long decideId);

    /**
     * 删除表决记录
     * @param decideId
     * @return
     */
    int delDecide(Long[] decideId);

    /**
     * 获取选项信息
     * @param statuteId
     * @return
     */
    List<DecideItem> getDecideItemById(Long statuteId);

    /**
     * 修改选项表新消息，投票记录新增
     * @param itemId
     * @return
     */
    int updateDecideItemById(Long itemId);
}
