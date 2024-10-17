package com.wr.estate.service.customer.Venue;

import com.wr.estate.entity.VenueEntity;
import com.wr.estate.entity.vo.VenueVo;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/10/25
 * @Description:
 * @FileName: VenueService
 * @History:
 */
public interface VenueService {

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
     * 批量删除场馆信息
     *
     * @param venueIds 需要删除的场馆信息主键集合
     * @return 结果
     */
    public int deleteVenueByVenueIds(Long[] venueIds);

    /**
     * 删除场馆信息信息
     *
     * @param venueId 场馆信息主键
     * @return 结果
     */
    public int deleteVenueByVenueId(Long venueId);


    /**
     *
     * @return
     */
    List<VenueVo>g(Long communityId);

   /**
    * @Description 场馆管理下拉框
    * @Param [venueVo]
    * @return java.util.List<com.wr.estate.entity.vo.VenueVo>
    * @Date 2022/11/23 9:55
    * @Author DIANWEI
    **/
    public List<VenueVo> downVenueList(VenueVo venueVo);
}
