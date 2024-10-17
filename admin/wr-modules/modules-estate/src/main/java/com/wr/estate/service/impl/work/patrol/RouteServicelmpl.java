package com.wr.estate.service.impl.work.patrol;


import com.wr.estate.mapper.work.patrol.RouteMapper;
import com.wr.estate.service.work.patrol.RouteService;
import com.wr.remote.work.patrol.Point;
import com.wr.remote.work.patrol.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/27 13:24
 * @Version 1.5
 */
@Service
public class RouteServicelmpl implements RouteService {
    @Autowired
    private RouteMapper mapper;

    @Override
    public List<Route> selectList(Route route) {
        return mapper.selectList(route);
    }

    @Override
    public Route selectById(Long routeId) {

        Route route = mapper.selectById(routeId);
        route.setPointIdList(mapper.selectPointId(routeId));
        return route;
    }

    @Override
    public List<Point> selectPointList(Route route) {
        Long a = 1l;
        List<Point> list = mapper.selectPointList(route);
        for (Point point : list) {
            point.setRouteId(a);
            a++;
        }
        return list;
    }


    @Override
    @Transactional
    public int updateStatus(Route route) {
        return mapper.updateStatus(route);
    }

    @Override
    @Transactional
    public int delete(Long[] routeIds) {
        for (Long routeId : routeIds) {
            mapper.deleteRoutePoint(routeId);
            mapper.delete(routeId);
        }
        return 1;
    }

    @Override
    @Transactional
    public int delete(Long routeId) {
        mapper.deleteRoutePoint(routeId);
        return mapper.delete(routeId);
    }


    @Override
    @Transactional
    public int saveUpdate(Route route) throws Exception {

        if (route.getRouteId()==null||route.getRouteId()==0){
            mapper.insertRoute(route);
            List<Long> list=route.getPointIdList();
            for (Long aLong : list) {
                route.setPointId(aLong);
                mapper.insertRoutePoint(route);
            }
            return 11;
        }else {
            mapper.deleteRoutePoint(route.getRouteId());
            List<Long> list=route.getPointIdList();
            for (Long aLong : list) {
                route.setPointId(aLong);
                mapper.insertRoutePoint(route);
            }
            return mapper.updateRoute(route);
        }
    }

    @Override
    public List<Point> selectPointList1(Long communityId) {
        return mapper.selectPointList1(communityId);
    }

    @Override
    public Point selectPoint(Long pointId) {
        return mapper.selectPoint(pointId);
    }

    /**
     * 巡更路线列表
     *
     * @param communityId 社区id
     * @return java.util.List<com.wr.remote.work.patrol.Route>
     * @author yx
     * @date 2022/11/1 16:09
     */
    @Override
    public List<Route> listRoutes(Long communityId) {
        return mapper.listRoutes(communityId);
    }

    @Override
    public List<Point> selectPointListUrl(Long routeId) {
        Long a = 1l;
        List<Point> list = mapper.selectPointListUrl(routeId);
        for (Point point : list) {
            point.setRouteId(a);
            a++;
        }
        return list;
    }

    /**
     * 校验巡更路线名称不能重复
     * @param routeName
     * @return
     */
    @Override
    public int checkRouteNameUnique(String routeName) {
        return mapper.checkRouteNameUnique(routeName);
    }
}
