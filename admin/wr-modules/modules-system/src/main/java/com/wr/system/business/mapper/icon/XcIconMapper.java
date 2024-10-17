package com.wr.system.business.mapper.icon;




import com.wr.system.business.domain.icon.XcIcon;

import java.util.List;

/**
 * icon设置Mapper接口
 *
 * @author ruoyi
 * @date 2022-12-09
 */
public interface XcIconMapper {
    /**
     * 查询icon设置
     *
     * @param id icon设置主键
     * @return icon设置
     */
    public XcIcon selectXcIconById(Long id);

    /**
     * 查询icon设置列表
     *
     * @param xcIcon icon设置
     * @return icon设置集合
     */
    public List<XcIcon> selectXcIconList(XcIcon xcIcon);

    /**
     * 新增icon设置
     *
     * @param xcIcon icon设置
     * @return 结果
     */
    public int insertXcIcon(XcIcon xcIcon);

    /**
     * 修改icon设置
     *
     * @param xcIcon icon设置
     * @return 结果
     */
    public int updateXcIcon(XcIcon xcIcon);

    /**
     * 删除icon设置
     *
     * @param id icon设置主键
     * @return 结果
     */
    public int deleteXcIconById(Long id);

    /**
     * 批量删除icon设置
     *
     * @param id 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteXcIconByIds(String id);
}
