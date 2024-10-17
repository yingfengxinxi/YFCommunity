package com.wr.estate.mapper.work.patrol;


import com.wr.remote.work.nightPatrol.vo.NightPatrolPlanPointListVO;
import com.wr.remote.work.patrol.Point;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/26 10:15
 * @Version 1.5
 */
@Mapper
public interface PointMapper {

    /**
     * 查询集合
     *
     * @param point
     * @return
     */
    List<Point> selectList(Point point);
    List<Point> selectList2(Point point);


    /**
     * 根据id查询详细信息
     *
     * @param pointId
     * @return
     */
    Point selectById(Long pointId);


    /**
     * 删除
     *
     * @param pointId
     * @return
     */
    int delete(Long pointId);


    /**
     * 修改状态
     *
     * @param point
     * @return
     */
    int updateStatus(Point point);

    /**
     * 修改巡点
     *
     * @param point
     * @return
     */
    int updatePoint(Point point);


    /**
     * 新增巡点
     *
     * @param point
     * @return
     */
    int insertPoint(Point point);

    int updatePointUrl(Point point);

    String selectName(Long communityId);

    /**
     * 根据巡更路线查询巡点列表
     *
     * @param routeId 巡更路线id
     * @return java.util.List<com.wr.remote.work.nightPatrol.vo.NightPatrolPlanPointListVO>
     * @author yx
     * @date 2022/11/2 13:56
     */
    List<NightPatrolPlanPointListVO> listByRouteId(Long routeId);

    /**
     * 校验巡点名称是否存在
     *
     * @param pointName
     * @param communityId
     * @return
     */
    int isExist(@Param("pointName") String pointName, @Param("communityId") Long communityId);
}
