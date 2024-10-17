package com.wr.system.business.mapper;

/**
 * @Author: LiuBei
 * @Date: 2022-01-14 14:47:02
 * @Desc:
 */
public interface RepaireNumberMapper {

    /**查询今天的数据*/
    int selectCountNow();

    /**查询昨天的数据*/
    int selectCountYesterday();

    /**查询一天*/
    int selectCountOne();

    /**查询二天*/
    int selectCountTow();

    /**查询第三天*/
    int selectCountThree();

    /**查询第四天*/
    int selectCountFour();

    /**查询第五天*/
    int selectCountFive();

    /**查询今天处理的数量*/
    int selectNowCountDeal(Long communityId);

    /**查询今天未处理的数量*/
    int selectNowCountNotDeal(Long communityId);

    /**查询报事保修工单总数*/
    int selectOrderStatusCount(Long communityId);
}
