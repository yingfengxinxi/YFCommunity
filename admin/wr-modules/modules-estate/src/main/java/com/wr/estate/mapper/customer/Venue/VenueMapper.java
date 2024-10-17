package com.wr.estate.mapper.customer.Venue;

import com.wr.estate.entity.VenueEntity;
import com.wr.estate.entity.vo.VenueVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/10/25
 * @Description:
 * @FileName: VenueMapper.xml
 * @History:
 */
@Repository
public interface VenueMapper {
    /**
     * 查询场馆信息
     *
     * @param venueId 场馆信息主键
     * @return 场馆信息
     */
    public VenueEntity selectVenueByVenueId(Long venueId);

    /**
     * 查询场馆信息列表
     *
     * @param venue 场馆信息
     * @return 场馆信息集合
     */
    public List<VenueVo> selectVenueList(VenueEntity venue);

    /**
     * 新增场馆信息
     *
     * @param venue 场馆信息
     * @return 结果
     */
    public int insertVenue(VenueEntity venue);

    /**
     * 修改场馆信息
     *
     * @param venue 场馆信息
     * @return 结果
     */
    public int updateVenue(VenueEntity venue);

    /**
     * 删除场馆信息
     *
     * @param venueId 场馆信息主键
     * @return 结果
     */
    public int deleteVenueByVenueId(Long venueId);

    /**
     * 批量删除场馆信息
     *
     * @param venueIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVenueByVenueIds(Long[] venueIds);

    /**
     *
     * @return
     */
    List<VenueVo>g(Long communityId);

    /**
     * 根据社区id查询场馆
     * @param communityId
     * @return
     */
    List<VenueVo> getvenue(Long communityId);

    List<VenueVo> getvenue1();

    /**
     * @Description 场馆管理下拉框
     * @Param [venueVo]
     * @return java.util.List<com.wr.estate.entity.vo.VenueVo>
     * @Date 2022/11/23 9:55
     * @Author DIANWEI
     **/
    public List<VenueVo> downVenueList(VenueVo venueVo);

}
