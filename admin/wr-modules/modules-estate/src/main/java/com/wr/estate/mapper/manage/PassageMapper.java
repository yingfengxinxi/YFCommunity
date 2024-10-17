package com.wr.estate.mapper.manage;

import com.wr.remote.estate.manage.Passage;

import java.util.List;

/**
 * @Author: lvzy
 * @Date: 2022-10-12 11:29:12
 * @Desc: 出入口管理
 */
public interface PassageMapper {

    /**
     * 查询出入口数据集合
     *
     * @param passage 出入口信息
     * @return 出入口集合
     */
    public List<Passage> selectPassageList(Passage passage);

    /**
     * 通过出入口ID查询出入口信息
     *
     * @param passageId 出入口ID
     * @return 出入口信息
     */
    public Passage selectPassageById(Long passageId);


    /**
     * 批量删除出入口信息
     *
     * @param passageIds 需要删除的出入口ID
     * @return 结果
     */
    public int deletePassageByIds(Long[] passageIds);

    /**
     * 修改出入口信息
     *
     * @param passage 出入口信息
     * @return 结果
     */
    public int updatePassage(Passage passage);

    /**
     * 新增出入口
     *
     * @param  passage
     * @return 结果
     */
    public int insertPassage(Passage passage);

    /**
     * 校验出入口名称
     *
     * @param passage 出入口名称
     * @return 结果
     */
    public int checkPassageNameUnique(Passage passage);

}
