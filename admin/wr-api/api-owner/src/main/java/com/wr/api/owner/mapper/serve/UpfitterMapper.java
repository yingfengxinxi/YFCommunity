package com.wr.api.owner.mapper.serve;

import com.wr.api.owner.entity.vo.serve.FitmentVo;
import com.wr.api.owner.entity.vo.serve.UpfitterVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-14 15:35:03
 * @Desc:
 */
public interface UpfitterMapper {

    /**
     * 获取装修工人
     * @param decorateId
     * @return
     */
    List<UpfitterVo> getUpfitter(Long decorateId);

    /**
     * 新增装修工人
     * @param upfitterVo
     * @return
     */
    int insertUpfitter(List<UpfitterVo> upfitterVo);

    /**
     * 查询是否是首次装修
     * @param decorateId
     */
    int selectWokrType(Long decorateId);

    /**
     * 修改状态
     *
     * @param id
     * @param decorateId
     * @return
     */
    int updateWorkType(@Param("id") Long id,@Param("decorateId") Long decorateId);


    /**
     * 人员申请
     * @param fitmentVo
     * @return
     */
    int insertUpfitterList(FitmentVo fitmentVo);

    /**
     * 批量添加
     * @param upfitterVos
     */
    void updateUpitter( List<UpfitterVo> upfitterVos);
}
