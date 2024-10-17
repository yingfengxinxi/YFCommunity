package com.wr.api.owner.service.impl.home.icon;

import com.wr.api.owner.entity.home.icon.XcWstService;
import com.wr.api.owner.entity.home.icon.XcWstServiceType;
import com.wr.api.owner.mapper.home.icon.XcWstServiceMapper;
import com.wr.api.owner.service.home.icon.IXcWstServiceService;
import com.wr.common.core.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * icon服务Service业务层处理
 *
 * @author ruoyi
 * @date 2023-03-07
 */
@Service
public class XcWstServiceServiceImpl implements IXcWstServiceService {
    @Resource
    private XcWstServiceMapper xcWstServiceMapper;

    /**
     * 查询icon服务
     *
     * @param id icon服务主键
     * @return icon服务
     */
    @Override
    public XcWstService selectXcWstServiceById(Long id) {
        return xcWstServiceMapper.selectXcWstServiceById( id );
    }

    /**
     * 查询icon服务列表
     *
     * @param xcWstService icon服务
     * @return icon服务
     */
    @Override
    public List<XcWstService> selectXcWstServiceList(XcWstService xcWstService) {
        return xcWstServiceMapper.selectXcWstServiceList( xcWstService );
    }

    @Override
    public List<XcWstService> selectXcWstServiceListByKey(String key, Integer offset, Integer pageSize) {
        return xcWstServiceMapper.selectXcWstServiceListByKey( key, offset, pageSize );
    }

    /**
     * 新增icon服务
     *
     * @param xcWstService icon服务
     * @return 结果
     */
    @Override
    public int insertXcWstService(XcWstService xcWstService) {
        xcWstService.setCreateTime( DateUtils.getNowDate() );
        return xcWstServiceMapper.insertXcWstService( xcWstService );
    }

    /**
     * 修改icon服务
     *
     * @param xcWstService icon服务
     * @return 结果
     */
    @Override
    public int updateXcWstService(XcWstService xcWstService) {
        xcWstService.setUpdateTime( DateUtils.getNowDate() );
        return xcWstServiceMapper.updateXcWstService( xcWstService );
    }

    /**
     * 批量删除icon服务
     *
     * @param ids 需要删除的icon服务主键
     * @return 结果
     */
    @Override
    public int deleteXcWstServiceByIds(Long[] ids) {
        return xcWstServiceMapper.deleteXcWstServiceByIds( ids );
    }

    /**
     * 删除icon服务信息
     *
     * @param id icon服务主键
     * @return 结果
     */
    @Override
    public int deleteXcWstServiceById(Long id) {
        return xcWstServiceMapper.deleteXcWstServiceById( id );
    }

    @Override
    public List<XcWstServiceType> selectWstServiceListByGroup() {
        return xcWstServiceMapper.selectWstServiceListByGroup();
    }
}
