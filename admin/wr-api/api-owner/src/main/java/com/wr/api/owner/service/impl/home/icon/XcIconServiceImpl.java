package com.wr.api.owner.service.impl.home.icon;

import com.wr.api.owner.entity.home.icon.XcIcon;
import com.wr.api.owner.mapper.home.icon.XcIconMapper;
import com.wr.api.owner.service.home.icon.IXcIconService;
import com.wr.common.core.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * icon设置Service业务层处理
 *
 * @author ruoyi
 * @date 2022-12-09
 */
@Service
public class XcIconServiceImpl implements IXcIconService {
    @Resource
    private XcIconMapper xcIconMapper;

    /**
     * 查询icon设置
     *
     * @param id icon设置主键
     * @return icon设置
     */
    @Override
    public XcIcon selectXcIconById(Long id) {
        return xcIconMapper.selectXcIconById(id);
    }

    /**
     * 查询icon设置列表
     *
     * @param xcIcon icon设置
     * @return icon设置
     */
    @Override
    public List<XcIcon> selectXcIconList(XcIcon xcIcon) {
        return xcIconMapper.selectXcIconList(xcIcon);
    }

    /**
     * 新增icon设置
     *
     * @param xcIcon icon设置
     * @return 结果
     */
    @Override
    public int insertXcIcon(XcIcon xcIcon) {
        xcIcon.setCreateTime( DateUtils.getNowDate());
        return xcIconMapper.insertXcIcon(xcIcon);
    }

    /**
     * 修改icon设置
     *
     * @param xcIcon icon设置
     * @return 结果
     */
    @Override
    public int updateXcIcon(XcIcon xcIcon) {
        xcIcon.setUpdateTime(DateUtils.getNowDate());
        return xcIconMapper.updateXcIcon(xcIcon);
    }

    /**
     * 批量删除icon设置
     *
     * @param ids 需要删除的icon设置主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteXcIconByIds(String ids) {
        int i = 0;
        String[] split = ids.split( "," );
        for (String s : split) {
            i = xcIconMapper.deleteXcIconByIds(s);
        }
        return i;
    }

    /**
     * 删除icon设置信息
     *
     * @param id icon设置主键
     * @return 结果
     */
    @Override
    public int deleteXcIconById(Long id) {
        return xcIconMapper.deleteXcIconById(id);
    }
}
