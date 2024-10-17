package com.wr.estate.service.customer.Venue;

import com.wr.estate.entity.VenueActEneity;
import com.wr.estate.entity.VenueAppoint;
import com.wr.estate.entity.vo.VenueAppointVo;
import com.wr.estate.entity.vo.VenueAppointVo1;
import com.wr.estate.entity.vo.VenueVo;

import java.util.List;

/**
 * @author: bajie
 * @create: 2022/10/26
 * @Description:
 * @FileName: VenueAppointService
 * @History:
 */
public interface VenueAppointService {

    /**
     * 查询场馆活动
     *
     * @param appointId 场馆活动主键
     * @return 场馆活动
     */
    public VenueAppoint selectVenueAppointByAppointId(Long appointId);
    public VenueAppoint selectVenueAppointByAppointId1(Long appointId);

    public List<VenueAppointVo> tlist(VenueAppoint venueAppoint);

    List<VenueAppoint>getvenueByvenueId(Long venueId);

    /**
     * 查询场馆活动列表
     *
     * @param venueAppoint 场馆活动
     * @return 场馆活动集合
     */
    public List<VenueAppoint> selectVenueAppointList(VenueAppoint venueAppoint);

    /**
     * 新增场馆活动
     *
     * @param venueAppoint 场馆活动
     * @return 结果
     */
    public int insertVenueAppoint(VenueAppoint venueAppoint);

    /**
     * 修改场馆活动
     *
     * @param venueAppoint 场馆活动
     * @return 结果
     */
    public int updateVenueAppoint(VenueAppoint venueAppoint);

    /**
     * 批量删除场馆活动
     *
     * @param appointIds 需要删除的场馆活动主键集合
     * @return 结果
     */
    public int deleteVenueAppointByAppointIds(Long[] appointIds);

    /**
     * 删除场馆活动信息
     *
     * @param appointId 场馆活动主键
     * @return 结果
     */
    public int deleteVenueAppointByAppointId(Long appointId);


    public List<VenueVo> getvenue(Long communityId);

    public List<VenueVo> getvenue1();

    public List<VenueActEneity> getact(Long communityId);

    public VenueAppoint jzc(Long appointId);


    /**
     * 预约终止
     */
    List<VenueAppoint> venueaz(VenueAppoint venueAppoint);

    VenueAppoint venueazById(Long appointId);

}
