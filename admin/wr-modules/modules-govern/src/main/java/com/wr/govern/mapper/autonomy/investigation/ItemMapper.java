package com.wr.govern.mapper.autonomy.investigation;


import com.wr.remote.govern.autonomy.investigation.Item;

import java.util.List;

/**
 * 问卷管理选项信息
 *
 * @author liubei
 * @createDate 2022/10/26
 */
public interface ItemMapper {


    /**
     * 添加问卷调查题目选项数据
     * @param item
     * @return
     */
    public int insertItem(Item item);
    /**
     * 查看问卷调查选项数据
     * @param
     * @return
     */
    public List<Item> selectItem(Long questionId);

    /**
     * 修改题目选项
     * @param item
     * @return
     */
    int updateItem(Item item);
}
