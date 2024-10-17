package com.wr.api.owner.mapper.home.advice;

import com.wr.api.owner.entity.home.advice.Complaint;
import com.wr.api.owner.entity.home.advice.Illegal;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: WuKong
 * @Date: 2022-11-16 15:44:57
 * @Desc: 违法举报数据层
 */
@Repository
@Mapper
public interface IllegalMapper {
    /**
     * 查询列表
     *
     * @param illegal
     * @return
     */
    List<Illegal> selectIllegalList(Illegal illegal);


    /**
     * 根据id查详情
     *
     * @param illegalId
     * @return
     */
    Illegal getIllegalById(Long illegalId);

    /**
     * 新增
     *
     * @param illegal
     * @return
     */
    Integer insertIllegal(Illegal illegal);

}
