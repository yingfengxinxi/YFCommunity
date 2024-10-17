package com.wr.govern.service.security;

import com.wr.remote.govern.security.Bulletin;
import com.wr.remote.govern.security.Prevent;
import com.wr.remote.govern.security.vo.BulletinVO;
import com.wr.remote.govern.security.vo.PreventVO;

import java.util.List;

/**
 * @Author: LuZhiShen
 * @Date: 2022-10-24 17:18:11
 * @Desc: 警民互动接口层
 */
public interface BulletinService {

    /**
     * 查看警民互动列表
     * @param bulletin
     * @return com.wr.remote.govern.security.Bulletin
     */
    List<Bulletin> selectBulletinList(Bulletin bulletin);

    /**
     * 新增警民互动
     * @param bulletin
     * @return
     */
    Integer insertBulletin(Bulletin bulletin);

    /**
     * 获取警民互动详细信息
     * @param bulletinId
     * @return  com.wr.remote.govern.security.Bulletin
     */
    Bulletin selectBulletinById(Long bulletinId);

    /**
     * 修改警民互动
     * @param bulletin
     * @return java.lang.Integer
     */
    Integer updateBulletin(Bulletin bulletin);

    /**
     * 删除警民互动
     * @param bulletinId
     * @return java.lang.Integer
     */
    Integer deleteBulletinByIds(Long bulletinId);

    /**
     * 修改归档信息
     * @param bulletinId
     * @return java.lang.Integer
     */
    Integer updateArchive(Long bulletinId);

    /**
     * 查看归档详情
     * @param bulletinId
     * @return com.wr.remote.govern.security.vo.BulletinVO
     */
    BulletinVO getArchiveDetail(Long bulletinId);

    /**
     * 获取用户的类型
     * @return com.wr.remote.govern.security.Bulletin
     */
    Boolean getUserType();

    /**
     * 查询身份证是否存在
     * @param cardNo 身份证号
     * @return java.lang.Integer
     */
    Integer checkBulletinExsist(String cardNo);


}
