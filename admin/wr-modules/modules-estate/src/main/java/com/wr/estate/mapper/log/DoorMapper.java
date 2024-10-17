package com.wr.estate.mapper.log;

import com.wr.common.customize.util.CommunityHik;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author lvzy
 * @Date 2022/10/13 19:38
 * @Version 1.5
 */
@Mapper
public interface DoorMapper {



    CommunityHik selectCommunityHik(Long communityId);

}
