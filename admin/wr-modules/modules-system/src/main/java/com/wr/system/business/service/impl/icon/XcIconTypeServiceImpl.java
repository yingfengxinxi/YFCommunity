package com.wr.system.business.service.impl.icon;

import com.wr.common.core.utils.DateUtils;
import com.wr.system.business.domain.icon.XcIconType;
import com.wr.system.business.mapper.icon.XcIconTypeMapper;
import com.wr.system.business.service.icon.IXcIconTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * icon分类Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-25
 */
@Service
public class XcIconTypeServiceImpl implements IXcIconTypeService {
    @Resource
    private XcIconTypeMapper xcIconTypeMapper;

    /**
     * 查询icon分类
     *
     * @param id icon分类主键
     * @return icon分类
     */
    @Override
    public XcIconType selectXcIconTypeById(Long id) {
        return xcIconTypeMapper.selectXcIconTypeById(id);
    }

    /**
     * 查询icon分类列表
     *
     * @param xcIconType icon分类
     * @return icon分类
     */
    @Override
    public List<XcIconType> selectXcIconTypeList(XcIconType xcIconType) {
        return xcIconTypeMapper.selectXcIconTypeList(xcIconType);
    }

    /**
     * 新增icon分类
     *
     * @param xcIconType icon分类
     * @return 结果
     */
    @Override
    public int insertXcIconType(XcIconType xcIconType) {
        xcIconType.setCreateTime( DateUtils.getNowDate());
        return xcIconTypeMapper.insertXcIconType(xcIconType);
    }

    /**
     * 修改icon分类
     *
     * @param xcIconType icon分类
     * @return 结果
     */
    @Override
    public int updateXcIconType(XcIconType xcIconType) {
        xcIconType.setUpdateTime(DateUtils.getNowDate());
        return xcIconTypeMapper.updateXcIconType(xcIconType);
    }

    /**
     * 批量删除icon分类
     *
     * @param ids 需要删除的icon分类主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteXcIconTypeByIds(String ids) {
        int i = 0;
        String[] split = ids.split( "," );
        for (String id : split) {
            i = xcIconTypeMapper.deleteXcIconTypeByIds( id );
        }
        return i;
    }

    /**
     * 删除icon分类信息
     *
     * @param id icon分类主键
     * @return 结果
     */
    @Override
    public int deleteXcIconTypeById(Long id) {
        return xcIconTypeMapper.deleteXcIconTypeById(id);
    }
}
