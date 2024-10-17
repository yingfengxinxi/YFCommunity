package com.wr.govern.service.impl.manage;

import com.wr.common.security.utils.SecurityUtils;
import com.wr.govern.mapper.manage.GridMapper;
import com.wr.govern.service.manage.GridService;
import com.wr.remote.estate.manage.Staff;
import com.wr.remote.govern.manage.Grid;
import com.wr.remote.govern.manage.vo.GridVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-31 09:20:10
 * @Desc: 网格管理业务层
 */
@Service
public class GridServiceImpl implements GridService {

    @Autowired
    private GridMapper gridMapper;

    @Override
    public List<GridVO> getList(GridVO grid) {
        return gridMapper.getList(grid);
    }

    @Override
    public List<Staff> selectStaffList(Long communityId) {
        return gridMapper.selectStaffList(communityId);
    }

    @Override
    public Integer addGrid(GridVO gridVO) {
        gridVO.setCreateBy(SecurityUtils.getUsername());
        gridVO.setEstateId(SecurityUtils.getLoginUser().getSysUser().getEstateId());
        return gridMapper.addGrid(gridVO);
    }

    @Override
    public Integer updateGrid(GridVO gridVO) {
        gridVO.setUpdateBy(SecurityUtils.getUsername());
        return gridMapper.updateGrid(gridVO);
    }

    @Override
    public GridVO getGridDetail(Long gridId) {
        return gridMapper.getGridDetail(gridId);
    }

    @Override
    public Integer delGrid(Long gridId) {
        return gridMapper.delGrid(gridId);
    }
}
