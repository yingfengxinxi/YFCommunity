package com.wr.estate.mapper.device;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wr.remote.aiot.VisitingSecord;
import com.wr.remote.domain.vo.VisitingSecordVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2024/4/7 19:27
 */
public interface VisitingSecordMapper extends BaseMapper<VisitingSecord> {

    /**
     *
     * @param visitingSecordVo
     * @return
     */
    List<VisitingSecordVo> getList(VisitingSecordVo visitingSecordVo);

    /**
     *
     * @param id
     * @return
     */
    VisitingSecordVo getByIdVisitingSecordInfo(@Param("id") String id);
}
