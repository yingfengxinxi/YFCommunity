package com.wr.estate.service.impl.work;

import com.wr.common.core.constant.UserConstants;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.work.TourMapper;
import com.wr.estate.service.work.patrol.TourService;
import com.wr.remote.domain.SysUser;
import com.wr.remote.work.Tour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 巡X类型Service业务层处理
 *
 * @author 奔波儿灞
 * @date 2022-10-28
 */
@Service
public class TourServiceImpl implements TourService {
    @Autowired
    private TourMapper tourMapper;

    /**
     * 查询巡X类型
     *
     * @param tourId 巡X类型主键
     * @return 巡X类型
     */
    @Override
    public Tour selectTourByTourId(Long tourId) {
        return tourMapper.selectTourByTourId(tourId);
    }

    /**
     * 查询巡X类型列表
     *
     * @param tour 巡X类型
     * @return 巡X类型
     */
    @Override
    public List<Tour> selectTourList(Tour tour) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        tour.setEstateId(sysUser.getEstateId());
        tour.setTourStatus("0");
        tour.setDelFlag("0");
        return tourMapper.selectTourList(tour);
    }

    /**
     * 新增巡X类型
     *
     * @param tour 巡X类型
     * @return 结果
     */
    @Override
    public int insertTour(Tour tour) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        tour.setCreateBy(sysUser.getUserName());
        tour.setUpdateBy(sysUser.getUserName());
        tour.setEstateId(sysUser.getEstateId());
        return tourMapper.insertTour(tour);
    }

    /**
     * 修改巡X类型
     *
     * @param tour 巡X类型
     * @return 结果
     */
    @Override
    public int updateTour(Tour tour) {
        String username = SecurityUtils.getUsername();
        tour.setUpdateBy(username);
        return tourMapper.updateTour(tour);
    }

    /**
     * 批量删除巡X类型
     *
     * @param tourIds 需要删除的巡X类型主键
     * @return 结果
     */
    @Override
    public int deleteTourByTourIds(Long[] tourIds) {
        return tourMapper.deleteTourByTourIds(tourIds);
    }

    /**
     * 删除巡X类型信息
     *
     * @param tourId 巡X类型主键
     * @return 结果
     */
    @Override
    public int deleteTourByTourId(Long tourId) {
        return tourMapper.deleteTourByTourId(tourId);
    }

    @Override
    public String checkName(Tour tour) {
        SysUser sysUser = SecurityUtils.getLoginUser().getSysUser();
        tour.setEstateId(sysUser.getEstateId());
        int count =tourMapper.checkName(tour);
        if (count > 0) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
