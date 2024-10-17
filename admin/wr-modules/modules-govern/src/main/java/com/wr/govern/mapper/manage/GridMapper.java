package com.wr.govern.mapper.manage;

import com.wr.remote.estate.manage.Staff;
import com.wr.remote.govern.manage.Grid;
import com.wr.remote.govern.manage.vo.GridVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-31 09:20:10
 * @Desc: 网格管理DAO层
 */
@Mapper
public interface GridMapper {


    /**
     * 获取网格管理列表
     * @param grid
     * @return
     */
    List<GridVO> getList(GridVO grid);

    /**
     * 获取员工信息集合
     * @param communityId
     * @return
     */
    List<Staff> selectStaffList(Long communityId);

    /**
     * 添加网格管理列表
     * @param gridVO
     * @return
     */
    Integer addGrid(GridVO gridVO);

    /**
     * 修改网格管理
     * @param gridVO
     * @return
     */
    Integer updateGrid(GridVO gridVO);

    /**
     * 网格管理详情
     * @param gridId
     * @return
     */
    GridVO getGridDetail(Long gridId);


    /**
     * 删除网格管理
     * @param gridId
     * @return
     */
    Integer delGrid(Long gridId);
}
