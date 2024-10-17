package com.wr.govern.mapper.home;


import com.wr.remote.govern.home.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * 首页
 *
 * @Author: luSu
 * @Date: 2022-12-1 10:25:05
 * @Desc:首页Mapper
 */
@Mapper
public interface HomePageMapper {

    /**
     * 查询入党审核数据
     *
     * @param estateId
     * @return
     */
    Census selectMemberById(Long estateId);

    /**
     * 查询救助信息
     * @param estateId
     * @return
     */
    Census selectRescueByEstateId(Long estateId);

    /**
     * 查询通报信息
     * @param estateId
     * @return
     */
    Census selectBulletinByEstateId(Long estateId);

    /**
     *党员统计
     * @param estateId
     * @return
     */
    ArrayList<MemberCensus> selectMemberNumById(Long estateId);

    /**
     * /线索反馈次数
     * @param estateId
     * @return
     */
    ArrayList<Clue> selectClueNumById(Long estateId);

    /**
     * 业主委员会
     * @param estateId
     * @return
     */
    ArrayList<Committee> selectCommitteeNumById(Long estateId);

    /**
     * 党费统计
     * @param census
     * @return
     */
    ArrayList<HomePartyPay> selectPartyPayNumById(Census census);
}