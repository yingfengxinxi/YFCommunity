package com.wr.estate.mapper.customer.ElderlyCareArchives;

import com.wr.estate.entity.*;
import com.wr.estate.entity.vo.OlderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/10/11
 * @Description:
 * @FileName: OlderManMapper
 * @History:
 */
public interface OlderManMapper {

    /**
     * 获取小区树状图
     */
   List<OldManByTreeEntity> getTree();

    List<HomeEntity> getbuilding(@Param("communityId") long communityId);

    List<HomeEntity> getunit(@Param("buildingId") long buildingId,@Param("communityId") long communityId);

    List<HomeEntity> getroom(@Param("buildingId") long buildingId,@Param("communityId") long communityId,@Param("unitId") long unitId);

    List<PeopleEntity> getMan(long roomId);

    int addoldfile (OlderVo olderVo);

    OldMsgEntity oldmsg(long olderId);
    List<OlderVo> getoldman(OlderVo olderVo);

    int tg(@Param("updateBy") String updateBy,@Param("olderId") long olderId);

    int bh(@Param("updateBy") String updateBy,@Param("olderId") long olderId,@Param("rejectReason")String rejectReason);

    int del(Long[] olderId);

    int getoldfilecount(long ownerId);

    List<OlderExaminerEntity> lookolderman(long olderId);

}
