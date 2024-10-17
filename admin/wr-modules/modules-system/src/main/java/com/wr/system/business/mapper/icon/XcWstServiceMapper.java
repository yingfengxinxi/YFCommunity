package com.wr.system.business.mapper.icon;

import com.wr.system.business.domain.icon.XcWstService;
import com.wr.system.business.domain.icon.XcWstServiceType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * icon服务Mapper接口
 *
 * @author ruoyi
 * @date 2023-03-07
 */
public interface XcWstServiceMapper {
    /**
     * 查询icon服务
     *
     * @param id icon服务主键
     * @return icon服务
     */
    public XcWstService selectXcWstServiceById(Long id);

    /**
     * 查询icon服务列表
     *
     * @param xcWstService icon服务
     * @return icon服务集合
     */
    public List<XcWstService> selectXcWstServiceList(XcWstService xcWstService);

    public List<XcWstService> selectXcWstServiceListByKey(@Param("key") String key, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    /**
     * 新增icon服务
     *
     * @param xcWstService icon服务
     * @return 结果
     */
    public int insertXcWstService(XcWstService xcWstService);

    /**
     * 修改icon服务
     *
     * @param xcWstService icon服务
     * @return 结果
     */
    public int updateXcWstService(XcWstService xcWstService);

    /**
     * 删除icon服务
     *
     * @param id icon服务主键
     * @return 结果
     */
    public int deleteXcWstServiceById(Long id);

    /**
     * 批量删除icon服务
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteXcWstServiceByIds(Long[] ids);


    List<XcWstServiceType> selectWstServiceListByGroup();
}
