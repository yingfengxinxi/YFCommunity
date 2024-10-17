package com.wr.api.owner.mapper.home.icon;
import com.wr.api.owner.entity.home.icon.XcIconType;

import java.util.List;

/**
 * icon分类Mapper接口
 *
 * @author ruoyi
 * @date 2023-03-25
 */
public interface XcIconTypeMapper {
    /**
     * 查询icon分类
     *
     * @param id icon分类主键
     * @return icon分类
     */
    public XcIconType selectXcIconTypeById(Long id);

    /**
     * 查询icon分类列表
     *
     * @param xcIconType icon分类
     * @return icon分类集合
     */
    public List<XcIconType> selectXcIconTypeList(XcIconType xcIconType);

    /**
     * 新增icon分类
     *
     * @param xcIconType icon分类
     * @return 结果
     */
    public int insertXcIconType(XcIconType xcIconType);

    /**
     * 修改icon分类
     *
     * @param xcIconType icon分类
     * @return 结果
     */
    public int updateXcIconType(XcIconType xcIconType);

    /**
     * 删除icon分类
     *
     * @param id icon分类主键
     * @return 结果
     */
    public int deleteXcIconTypeById(Long id);

    /**
     * 批量删除icon分类
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteXcIconTypeByIds(String id);
}
