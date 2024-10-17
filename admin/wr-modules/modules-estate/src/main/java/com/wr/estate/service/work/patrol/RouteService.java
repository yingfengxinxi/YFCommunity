package com.wr.estate.service.work.patrol;

import com.wr.remote.work.patrol.Point;
import com.wr.remote.work.patrol.Route;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/27 13:23
 * @Version 1.5
 */
public interface RouteService {



    /**
     * 首页巡更数据集合
     * @param route
     * @return
     */
    List<Route> selectList(Route route);

    /**
     * 根据id查询巡更数据
     * @param routeId
     * @return
     */
    Route selectById(Long routeId);


    /**
     * 巡点数据集合
     * @param routeId
     * @return
     */
    List<Point> selectPointList(Route route);





    /**
     * 根据id修改状态
     * @param route
     * @return
     */
    int updateStatus(Route route);



    /**
     * 批量删除
     * @param routeIds
     * @return
     */
    int delete(Long[] routeIds);



    /**
     * 删除
     * @param routeId
     * @return
     */
    int delete(Long routeId);



    /**
     * 新增或修改巡点
     */

    int saveUpdate(Route route) throws Exception;

    List<Point> selectPointList1(Long communityId);
    Point selectPoint(Long pointId);

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
