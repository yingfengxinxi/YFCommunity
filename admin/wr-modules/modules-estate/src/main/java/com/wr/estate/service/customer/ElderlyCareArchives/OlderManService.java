package com.wr.estate.service.customer.ElderlyCareArchives;

import com.wr.estate.entity.HomeEntity;
import com.wr.estate.entity.OldManByTreeEntity;
import com.wr.estate.entity.OldMsgEntity;
import com.wr.estate.entity.PeopleEntity;
import com.wr.estate.entity.vo.OlderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/10/11
 * @Description:
 * @FileName: OlderManService
 * @History:
 */
public interface OlderManService {

    /**
     * 获取小区树状图
     */
    List<OldManByTreeEntity> getTree();


    List<HomeEntity> getbuilding(long communityId);

    List<HomeEntity> getunit(long buildingId,long communityId);

    List<HomeEntity> getroom(long buildingId,long communityId,long unitId);

    List<PeopleEntity> getMan(long roomId);

    int addoldfile (OlderVo olderVo);

    List<OlderVo> getoldman(OlderVo olderVo);

    List oldmsg(long olderId);

    int tg(String updateBy,long olderId);

    int bh(String updateBy,long olderId,String rejectReason);

    int del(Long[] olderId);


}
