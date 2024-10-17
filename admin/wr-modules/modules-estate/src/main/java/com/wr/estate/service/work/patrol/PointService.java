package com.wr.estate.service.work.patrol;

import com.wr.remote.work.patrol.Point;

import java.util.List;

/**
 * @Author lvzy
 * @Date 2022/10/26 10:32
 * @Version 1.5
 */
public interface PointService {

    /**
     * 查询集合
     * @param point
     * @return
     */
    List<Point> selectList(Point point);
    List<Point> selectList2(Point point);



    /**
     * 根据id查询详细信息
     * @param poindId
     * @return
     */
    Point selectById(Long poindId);

    /**
     * 批量删除
     * @param pointIds
     * @return
     */
    int delete(Long[] pointIds);
    int delete1(Long pointId);

    /**
     * 修改状态
     * @param point
     * @return
     */
    int updateStatus(Point point);


    /**
     * 新增或修改巡点
     */

    int saveUpdate(Point point) throws Exception;

    /**
     * 校验巡点名称是否存在
     * @param pointName
     * @param communityId
     * @return
     */
    int isExist(String pointName, Long communityId);
}
