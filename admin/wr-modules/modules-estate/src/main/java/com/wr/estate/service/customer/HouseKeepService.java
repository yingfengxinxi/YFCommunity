package com.wr.estate.service;

import com.wr.remote.estate.housekeep.vo.HouseKeepDetailVO;
import com.wr.remote.estate.housekeep.vo.HouseKeepListVO;

import java.util.List;
import java.util.Map;

/**
 * @description 管家管理service
 * @author 荀彧
 * @date 2022-10-13
 */
public interface HouseKeepService {


    /**
     * 分页 查询
     * @param houseKeepListVO 查询条件
     * @return
     */
    List<HouseKeepListVO> pageList (HouseKeepListVO houseKeepListVO);


    /**
     * 删除
     * @param majordomoId
     * @return
     */
    int del (Long majordomoId);


    /**
     * 岗位下拉表
     * @return
     */
    List<Map<String,Object>> postSelect();


    /**
     * 修改带回数据
     * @param majordomoId
     * @return
     */
    HouseKeepListVO queryDetail(Long majordomoId);


    /**
     * 修改
     * @param houseKeepListVO
     * @return
     */
    Integer updateKeepList(HouseKeepListVO houseKeepListVO);

    /**
     * 查询楼栋名
     * @return
     */
    List<HouseKeepListVO> selectBuild(Long communityId);

    List<HouseKeepListVO> selectStaff(HouseKeepListVO houseKeepListVO);

    HouseKeepListVO selectPhone(Long staffId);

    int insertBuild(HouseKeepListVO houseKeepListVO);

    /**
     * 查看唯一性
     * @param houseKeepListVO
     * @return: java.lang.Integer
     */
    String checkBuildUnique(HouseKeepListVO houseKeepListVO);
}
