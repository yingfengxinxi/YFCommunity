package com.wr.govern.mapper.security;


import com.wr.remote.domain.SysUser;
import com.wr.remote.govern.security.Bulletin;
import com.wr.remote.govern.security.Clue;
import com.wr.remote.govern.security.Prevent;
import com.wr.remote.govern.security.vo.BulletinVO;
import com.wr.remote.govern.security.vo.PreventVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-24 17:20:05
 * @Desc: 警民互动DAO层
 */
@Mapper
public interface BulletInMapper {

    /**
     * 查看警民互动集合
     * @param bulletin
     * @return
     */
    List<Bulletin> selectBulletinList(Bulletin bulletin);

    /**
     * 新增警民互动
     * @param bulletin
     * @return
     */
    Integer insertBulletin(Bulletin bulletin);

    /**
     * 获取警民互动详情
     * @param bulletinId
     * @return
     */
    Bulletin selectBulletinById(Long bulletinId);

    /**
     * 修改警民互动
     * @param bulletin
     * @return
     */
    Integer updateBulletin(Bulletin bulletin);

    /**
     * 删除警民互动
     * @param bulletinId
     * @param userName
     * @return
     */
    Integer deleteBulletinByIds(@Param("bulletinId") Long bulletinId,@Param("userName")String userName);

    /**
     * 修改归档
     * @param bulletinId
     * @param userName
     * @return
     */
    Integer updateArchive(@Param("bulletinId") Long bulletinId,@Param("userName")String userName);

    /**
     * 获取线索反馈信息集合
     * @param bulletinId
     * @return
     */
    List<Clue> selectClueListByBulletinId(Long bulletinId);

    /**
     * 获取user类型
     * @param userId
     * @return
     */
    SysUser selectUserType(Long userId);

    /**
     * 查询身份证是否存在
     * @param cardNo 身份证号
     * @return java.lang.Integer
     */
    Integer checkBulletinExsist(String cardNo);

}