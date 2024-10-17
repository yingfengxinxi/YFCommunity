package com.wr.estate.mapper.work.disasterOrder;

import com.wr.remote.estate.base.BusDisaster;
import com.wr.remote.work.vo.BusDisasterOrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-10-27 18:32:47
 * @Desc:
 */
@Mapper
public interface DisasterLedgerMapper {

    /**
     * 分页查询灾害报事信息
     * @param busDisaster
     * @return
     */
    List<BusDisaster> listDisasterLedger(BusDisaster busDisaster);

    /**
     * 添加灾害报事信息
     *
     * @param busDisaster
     * @return
     */
    int insertBusDisaster(BusDisaster busDisaster);

    /**
     * 删除灾害报事信息
     * @param disasterId
     * @return
     */
    Integer deleteById(Long disasterId);

    /**
     * 修改灾害报事信息
     *
     * @param busDisaster
     * @return 修改结果
     */
    int updateById(BusDisaster busDisaster);


    int updateByDisaster(BusDisasterOrderVo busDisasterOrderVo);

    List<BusDisaster> getType(Long estateId);

    /**
     * 添加时查询灾害类型
     * @param disasterName
     * @return
     */
    int getDisasterName(@Param("estateId") Long estateId, @Param("disasterName") String disasterName);

    /**
     * 修改时查询灾害类型
     * @param busDisaster
     * @return
     */
    int getUpdateDisasterName(BusDisaster busDisaster);
}
