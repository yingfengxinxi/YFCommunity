package com.wr.estate.mapper.work.patrol;

import com.wr.remote.work.patrol.Point;
import com.wr.remote.work.patrol.Route;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/27 10:04
 * @Version 1.5
 */

@Mapper
public interface RouteMapper {

    /**
     * 首页巡更数据集合
     *
     * @param route
     * @return
     */
    List<Route> selectList(Route route);

    /**
     * 根据id查询巡更数据
     *
     * @param routeId
     * @return
     */
    Route selectById(Long routeId);


    /**
     * 巡点数据集合
     *
     * @param routeId
     * @return
     */
    List<Point> selectPointList(Route route);

    /**
     * 巡更表新增
     *
     * @param route
     * @return
     */
    int insertRoute(Route route);

    /**
     * 关联表新增
     *
     * @param route
     * @return
     */
    int insertRoutePoint(Route route);

    /**
     * 根据id修改状态
     *
     * @param route
     * @return
     */
    int updateStatus(Route route);


    /**
     * 删除
     *
     * @param routeId
     * @return
     */
    int delete(Long routeId);


    /**
     * 修改
     *
     * @param route
     * @return
     */
    int updateRoute(Route route);

    /**
     * 删除关联表数据
     */
    int deleteRoutePoint(Long routeId);


    List<Point> selectPointList1(Long communityId);

    Point selectPoint(Long pointId);

    List<Long> selectPointId(Long routeId);

    /**
     * 巡更路线列表
     *
     * @param communityId 社区id
     * @return java.util.List<com.wr.remote.work.patrol.Route>
     * @author yx
     * @date 2022/11/1 16:09
     */
    List<Route> listRoutes(Long communityId);



    /**
     * 下载二维码接口
     *
     * @param routeId
     * @return
     */
    List<Point> selectPointListUrl(Long routeId);

    /**
     * 校验巡更路线名称不能重复
     * @param routeName
     * @return
     */
    int checkRouteNameUnique(String routeName);
}
