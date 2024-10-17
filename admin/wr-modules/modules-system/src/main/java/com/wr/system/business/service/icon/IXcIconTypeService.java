package com.wr.system.business.service.icon;

import com.wr.system.business.domain.icon.XcIconType;

import java.util.List;

/**
 * icon分类Service接口
 *
 * @author ruoyi
 * @date 2023-03-25
 */
public interface IXcIconTypeService {
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
     * 批量删除icon分类
     *
     * @param ids 需要删除的icon分类主键集合
     * @return 结果
     */
    public int deleteXcIconTypeByIds(String ids);

    /**
     * 删除icon分类信息
     *
     * @param id icon分类主键
     * @return 结果
     */
    public int deleteXcIconTypeById(Long id);
}
