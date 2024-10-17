package com.wr.api.owner.mapper.serve;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.api.owner.entity.dto.SecondHandDTO;
import com.wr.api.owner.entity.vo.serve.SecondHandVo;
import com.wr.remote.estate.customer.secondhand.SecondHand;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-11 15:24:21
 * @Desc:
 */
public interface SecondHandMapper extends BaseMapper<SecondHand> {
    /**
     * 二手交易列表
     *
     * @param secondHandVo
     * @return
     */
    List<SecondHandDTO> getAllList(SecondHandVo secondHandVo);


    /**
     * 添加二手交易
     *
     * @param secondHandVo
     * @return
     */
    int insertSecond(SecondHandVo secondHandVo);

    /**
     * 下架
     *
     * @param handId     id
     * @param handStatus 状态
     * @return int
     * @author yx
     * @date 2023/1/13 13:15
     */
    int outStack(@Param("handId") Long handId, @Param("handStatus") String handStatus);

    /**
     * 查询二手交易详情
     *
     * @param handId
     * @return
     */
    SecondHandVo getInfo(Long handId);
}
