package com.wr.estate.mapper.customer.Venue;

import com.wr.estate.entity.VenueActEneity;
import com.wr.estate.entity.VenueAppoint;
import com.wr.estate.entity.vo.VenueAppointVo;
import com.wr.estate.entity.vo.VenueAppointVo1;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author: bajie
 * @create: 2022/10/26
 * @Description:
 * @FileName: VenueAppointMapper.xml
 * @History:
 */
@Repository
public interface VenueAppointMapper {

    /**
     * 查询场馆活动
     *
     * @param appointId 场馆活动主键
     * @return 场馆活动
     */
    public VenueAppoint selectVenueAppointByAppointId(Long appointId);


    public VenueAppoint selectVenueAppointByAppointId1(Long appointId);

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
     * 删除场馆活动
     *
     * @param appointId 场馆活动主键
     * @return 结果
     */
    public int deleteVenueAppointByAppointId(Long appointId);

    /**
     * 批量删除场馆活动
     *
     * @param appointIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteVenueAppointByAppointIds(Long[] appointIds);


//    预约时间
    public int getcount(@Param("starttime") Date starttime, @Param("endtime") Date endtime,@Param("venueId")Long venueId);

    public int getg(long communityId);

    public VenueAppoint jzc(Long appointId);

    List<VenueAppointVo>tlist1(VenueAppoint venueAppoint);

    List<VenueAppointVo1> tlist2();

    List<VenueAppoint>getvenueByvenueId(Long venueId);
    List<VenueAppoint>getvenueByvenueId1(Long venueId);


    List<VenueAppoint> venueaz(VenueAppoint venueAppoint);
    VenueAppoint venueazById(Long appointId);


    /**
     * 根据物业小区id获取时间
     * @param communityId
     * @return
     */
    long getEstateId(long communityId);




}
