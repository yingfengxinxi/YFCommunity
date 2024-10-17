package com.wr.govern.mapper.autonomy.decide;


import com.wr.remote.govern.autonomy.decide.Decide;
import com.wr.remote.govern.autonomy.decide.DecideItem;
import com.wr.remote.govern.autonomy.decide.DecideRang;
import com.wr.remote.govern.autonomy.decide.DecideOwner;
import com.wr.remote.govern.autonomy.decide.vo.decideVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: luSu
 * @Date: 2022/10/25 14:46
 * @Desc: 自治表决mapper
 */
@Mapper
public interface DecideMapper {


    /**
     * 查询自治表决列表
     * @param decide
     * @return
     */
    List<Decide> getList(Decide decide);

    /**
     * 新增
     * @param decideVo
     * @return
     */
    Integer addDecide(decideVo decideVo);

    /**
     * 新增关联比较
     * @param list
     */
    void addDecideRang(ArrayList<DecideRang> list);

    /**
     * 根据decideId， 将选项添加到选项表中
     * @param decideVo
     */
    void addDecideItem(decideVo decideVo);

    /**
     * id查详情
     * @param decideId
     * @return
     */
    Decide queryDecideByIdOne(Long decideId);

    /**
     * id查询当前的自联表
     * @param decideId
     * @return
     */
    List<Long> queryDecideRangById(Long decideId);

    /**
     * id查询当前指定的选项
     * @param decideId
     * @return
     */
    List<DecideItem> queryDecideItemById(Long decideId);

    /**
     * 修改表决主表中信息
     * @param decideVo
     * @return
     */
    int updateDecide(decideVo decideVo);

    /**
     * 根据表决表删除关联表信息
     * @param statuteId
     */
    void delDecideRangById(Long statuteId);


    /**
     *修改第一个选项信息
     * @param decideItem
     */
    void updateItemTitleOne(DecideItem decideItem);


    /**
     * 查询第一条记录的信息
     * @param decideId
     */
    Long queryItemTitleOneId(Long decideId);

    /**
     * 查询第二条记录的信息
     * @param decideId
     * @return
     */
    Long queryItemTitleTwoId(Long decideId);

    /**
     * 删除主表信息
     * @param decideId
     * @return
     */
    int delDecide(Long[] decideId);

    /**
     * 删除关联表信息
     * @param decideId
     */
    void delDecideRandByIds(Long[] decideId);

    /**
     * 删除选项表信息
     * @param decideId
     */
    void delItemTitleByIds(Long[] decideId);

    /**
     * 根据自觉表id
     * @param statuteId
     * @return
     */
    List<DecideItem> getDecideItemById(Long statuteId);

    /**
     * 修改选项表信息
     * @param itemId
     * @return
     */
    int updateDecideItemById(Long itemId);

    /**
     * 查业主是否存在
     * @param userId
     * @return
     */
    int queryOwnerById(Long userId);

    /**
     * 查询关联表是否用投票过
     * @param userId
     * @return
     */
    int queryDecideOwner(Long userId);

    /**
     * 讲投票信息插入到关联表中
     * @param decideOwner
     */
    void insertDecideItemByOwnerId(DecideOwner decideOwner);
}
