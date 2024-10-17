package com.wr.api.estate.mapper.application.serve;

import com.wr.api.estate.entity.vo.application.serve.OlderDetailVO;
import com.wr.api.estate.entity.vo.application.serve.OlderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-11-15 13:24:53
 * @Desc: 老人档案DAO层
 */
@Mapper
public interface OlderMapper {

    /**
     * 查询老人档案列表
     * @param olderVO
     * @return
     */
    List<OlderVO> selectOlderList(OlderVO olderVO);

    /**
     * 获取老人详情信息
     * @param olderId
     * @return
     */
    OlderDetailVO getOlderDetail(Long olderId);

    /**
     * 养老审核
     * @param olderAudit
     * @return
     */
    Integer olderAudit(OlderVO olderAudit);
}
