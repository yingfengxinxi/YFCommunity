package com.wr.estate.service.impl.customer.Venue;

import com.wr.common.core.utils.DateUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.VenueActEneity;
import com.wr.estate.entity.VenueAppoint;
import com.wr.estate.entity.vo.VenueAppointVo;
import com.wr.estate.entity.vo.VenueAppointVo1;
import com.wr.estate.entity.vo.VenueVo;
import com.wr.estate.mapper.customer.Venue.VenueActMapper;
import com.wr.estate.mapper.customer.Venue.VenueAppointMapper;
import com.wr.estate.mapper.customer.Venue.VenueMapper;
import com.wr.estate.service.customer.Venue.VenueAppointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author: bajie
 * @create: 2022/10/26
 * @Description:
 * @FileName: VenueAppointServiceImpl
 * @History:
 */
@Service
public class VenueAppointServiceImpl implements VenueAppointService {
    @Autowired
    private VenueAppointMapper venueAppointMapper;

    @Autowired
    private VenueMapper venueMapper;

    @Autowired
    private VenueActMapper venueActMapper;

    /**
     * 查询场馆活动
     *
     * @param appointId 场馆活动主键
     * @return 场馆活动
     */
    @Override
    public VenueAppoint selectVenueAppointByAppointId(Long appointId)
    {
        VenueAppoint venueAppoint = venueAppointMapper.selectVenueAppointByAppointId(appointId);
        if (venueAppoint.getPlanStart()!=null){
            //当前时间是否早于开始时间
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            Calendar now = Calendar.getInstance();
            Calendar s = (Calendar) now.clone();
            Calendar e = (Calendar) now.clone();
            s.setTime(venueAppoint.getPlanStart());
            e.setTime(venueAppoint.getPlanEnd());
            // before 之前
            //after 之后
            if (now.before(e) && now.after(s)){
                //进行中
                //进行中终止操作
                if (venueAppoint.getAppointStatus().equals("2") || venueAppoint.getAppointStatus().equals("3")){
                    venueAppoint.setStatusName("4");
                }else {
                    venueAppoint.setStatusName("0");
                }
            } else if (now.before(s) && now.before(e)) {
                //未开始
                venueAppoint.setStatusName("1");
            }else if (now.after(s) && now.after(e)){
                //已结束
                venueAppoint.setStatusName("2");
            }
            else {
                venueAppoint.setStatusName("3");
            }
        }
        return venueAppoint;
    }

    @Override
    public VenueAppoint selectVenueAppointByAppointId1(Long appointId) {
        VenueAppoint venueAppoint = venueAppointMapper.selectVenueAppointByAppointId1(appointId);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        DateFormat dateFormatY = new SimpleDateFormat("yyyy-MM-dd");
        String PlanStart = dateFormat.format(venueAppoint.getPlanStart());
        String PlanEnd = dateFormat.format(venueAppoint.getPlanEnd());
        String date = dateFormatY.format(venueAppoint.getPlanStart());
        try {
            venueAppoint.setPlanStart1(dateFormat.parse(PlanStart));
            venueAppoint.setPlanEnd1(dateFormat.parse(PlanEnd));
            venueAppoint.setDate(dateFormatY.parse(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return venueAppoint;
    }

    @Override
    public List<VenueAppointVo> tlist(VenueAppoint venueAppoint) {
        List<VenueAppointVo> list1 =  venueAppointMapper.tlist1(venueAppoint);
        List<VenueAppointVo1> list2 = venueAppointMapper.tlist2();
        List<VenueAppointVo> list3 = new ArrayList<>();
        List<VenueAppointVo> list4 = new ArrayList<>();
        List list = new ArrayList();
        for (int i = 0; i < list1.size(); i++) {
            List<VenueAppointVo1> l = new ArrayList<>();
            for (int j = 0; j < list2.size(); j++) {
                if (list1.get(i).getVenueId().equals(list2.get(j).getVenueId())){
                    l.add(list2.get(j));
                    list1.get(i).setUser(l);
                }
            }
        }
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i).getVenueStatus().equals("0")){
                list3.add(list1.get(i));
            }
            if (list1.get(i).getVenueStatus().equals("1")){
                list4.add(list1.get(i));
            }
        }

        list.add(list3);
        list.add(list4);
        return list;
    }

    @Override
    public List<VenueAppoint> getvenueByvenueId(Long venueId) {
        List l = new ArrayList();
        List<VenueAppoint> list = venueAppointMapper.getvenueByvenueId(venueId);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPlanStart()!=null){
                //当前时间是否早于开始时间
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Calendar now = Calendar.getInstance();
                Calendar s = (Calendar) now.clone();
                Calendar e = (Calendar) now.clone();
                s.setTime(list.get(i).getPlanStart());
                e.setTime(list.get(i).getPlanEnd());
                // before 之前
                //after 之后
                if (now.before(e) && now.after(s)){
                    //进行中
                    //进行中终止操作
                    if ("2".equals(list.get(i).getAppointStatus()) || "3".equals(list.get(i).getAppointStatus())){
                        list.get(i).setStatusName("4");
                    }else {
                        list.get(i).setStatusName("0");
                    }
                } else if (now.before(s) && now.before(e)) {
                    //未开始
                    list.get(i).setStatusName("1");
                }else if (now.after(s) && now.after(e)){
                    //已结束
                    list.get(i).setStatusName("2");
                }
                else {
                    list.get(i).setStatusName("3");
                }
            }
        }
        l.add(list);
        List<VenueAppoint> list1 = venueAppointMapper.getvenueByvenueId1(venueId);
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i).getPlanStart()!=null){
                //当前时间是否早于开始时间
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Calendar now = Calendar.getInstance();
                Calendar s = (Calendar) now.clone();
                Calendar e = (Calendar) now.clone();
                s.setTime(list1.get(i).getPlanStart());
                e.setTime(list1.get(i).getPlanEnd());
                // before 之前
                //after 之后
                if (now.before(e) && now.after(s)){
                    //进行中
                    //进行中终止操作
                    if ("2".equals(list1.get(i).getAppointStatus()) || "3".equals(list1.get(i).getAppointStatus())){
                        list1.get(i).setStatusName("4");
                    }else {
                        list1.get(i).setStatusName("0");
                    }
                } else if (now.before(s) && now.before(e)) {
                    //未开始
                    list1.get(i).setStatusName("1");
                }else if (now.after(s) && now.after(e)){
                    //已结束
                    list1.get(i).setStatusName("2");
                }
                else {
                    list1.get(i).setStatusName("3");
                }
            }
        }
        l.add(list1);
        return l;
    }

    /**
     * 查询场馆活动列表
     *
     * @param venueAppoint 场馆活动
     * @return 场馆活动
     */
    @Override
    public List<VenueAppoint> selectVenueAppointList(VenueAppoint venueAppoint)
    {
        List<VenueAppoint> list = venueAppointMapper.selectVenueAppointList(venueAppoint);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPlanStart()!=null){
                //当前时间是否早于开始时间
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Calendar now = Calendar.getInstance();
                Calendar s = (Calendar) now.clone();
                Calendar e = (Calendar) now.clone();
                s.setTime(list.get(i).getPlanStart());
                e.setTime(list.get(i).getPlanEnd());
                // before 之前
                //after 之后
                if (now.before(e) && now.after(s)){
                    //进行中
                    //进行中终止操作
                    if ("2".equals(list.get(i).getAppointStatus()) || "3".equals(list.get(i).getAppointStatus())){
                        list.get(i).setStatusName("4");
                    }else {
                        list.get(i).setStatusName("0");
                    }
                } else if (now.before(s) && now.before(e)) {
                    //未开始
                    list.get(i).setStatusName("1");
                }else if (now.after(s) && now.after(e)){
                    //已结束
                    list.get(i).setStatusName("2");
                }
                else {
                    list.get(i).setStatusName("3");
                }
            }
        }
       //使用装箱去除终止状态数据    stream +索引 去除
        //过滤单个条件可以，多个条件过虑不了
        IntStream.range(0,list.size()).filter(i-> "4".equals(list.get(i).getStatusName())).boxed().findFirst().map(integer -> list.remove((int)integer));
        return list;
    }

    /**
     * 新增场馆活动
     *
     * @param venueAppoint 场馆活动
     * @return 结果
     */
    @Override
    public int insertVenueAppoint(VenueAppoint venueAppoint)
    {
        int row = venueAppointMapper.getcount(venueAppoint.getPlanStart(),venueAppoint.getPlanEnd(), venueAppoint.getVenueId());
        if (row !=0){
            return -6;
        }

        //获取当前小区物业id
        long estateId = venueAppointMapper.getEstateId(venueAppoint.getCommunityId());

        //获取间隔时间段
        int i = venueAppointMapper.getg(estateId);

        //获取先i个小时的时间
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(venueAppoint.getPlanStart());
        c1.add(Calendar.HOUR, -i);
        Date dt1=c1.getTime();
        c2.setTime(venueAppoint.getPlanEnd());
        c2.add(Calendar.HOUR, +i);
        Date dt2=c2.getTime();
        //查询时间段有无被预约
        int getcount = venueAppointMapper.getcount(dt1,dt2, venueAppoint.getVenueId());
        if (getcount == 0){
            //如果没有 就添加
            venueAppoint.setCreateTime(DateUtils.getNowDate());
            return venueAppointMapper.insertVenueAppoint(venueAppoint);
        }
        else {
            return -6;
        }
    }

    /**
     * 修改场馆活动
     *
     * @param venueAppoint 场馆活动
     * @return 结果
     */
    @Override
    public int updateVenueAppoint(VenueAppoint venueAppoint)
    {

        if (venueAppoint.getAppointStatus().equals("3") || venueAppoint.getAppointStatus().equals("2") || venueAppoint.getAppointStatus().equals("1")){
            venueAppoint.setUpdateTime(DateUtils.getNowDate());
            return venueAppointMapper.updateVenueAppoint(venueAppoint);
        }


        int row = venueAppointMapper.getcount(venueAppoint.getPlanStart(),venueAppoint.getPlanEnd(), venueAppoint.getVenueId());
        if (row !=0){
            return -6;
        }
        //获取物业id
        long estateId = venueAppointMapper.getEstateId(venueAppoint.getCommunityId());
        //获取间隔时间段
        int i = venueAppointMapper.getg(estateId);

        //获取先i个小时的时间
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(venueAppoint.getPlanStart());
        c1.add(Calendar.HOUR, -i);
        Date dt1=c1.getTime();
        c2.setTime(venueAppoint.getPlanEnd());
        c2.add(Calendar.HOUR, +i);
        Date dt2=c2.getTime();
        //查询时间段有无被预约
        int getcount = venueAppointMapper.getcount(dt1,dt2, venueAppoint.getVenueId());
        if (getcount == 0){
            //如果没有 就添加
            venueAppoint.setUpdateTime(DateUtils.getNowDate());
            return venueAppointMapper.updateVenueAppoint(venueAppoint);
        }
        else {
            return -6;
        }
    }

    /**
     * 批量删除场馆活动
     *
     * @param appointIds 需要删除的场馆活动主键
     * @return 结果
     */
    @Override
    public int deleteVenueAppointByAppointIds(Long[] appointIds)
    {
        return venueAppointMapper.deleteVenueAppointByAppointIds(appointIds);
    }

    /**
     * 删除场馆活动信息
     *
     * @param appointId 场馆活动主键
     * @return 结果
     */
    @Override
    public int deleteVenueAppointByAppointId(Long appointId)
    {
        return venueAppointMapper.deleteVenueAppointByAppointId(appointId);
    }

    @Override
    public List<VenueVo> getvenue(Long communityId) {
        List<VenueVo> list = venueMapper.getvenue(communityId);
        for (VenueVo venueVo : list) {
            if ("0".equals(venueVo.getVenueStatus())) {
                venueVo.setVenueStatusName("可用");
            }
            if ("1".equals(venueVo.getVenueStatus())) {
                venueVo.setVenueStatusName("关闭");
            }
        }
        return list;
    }

    @Override
    public List<VenueVo> getvenue1() {
        List<VenueVo> list = venueMapper.getvenue1();
        return list;
    }

    @Override
    public List<VenueActEneity> getact(Long communityId) {
        return venueActMapper.getact(communityId);
    }

    @Override
    public VenueAppoint jzc(Long appointId) {
        if (appointId !=null){
            VenueAppoint jzc = venueAppointMapper.jzc(appointId);
            //计算总金额预约小时 x 每小时费用
            Date planStart = jzc.getPlanStart();
            Date currentEnd = jzc.getPlanEnd();
            BigDecimal hourFee = jzc.getHourFee();

            //计算预约小时
            Integer datePoor = getDatePoor(planStart, currentEnd);
            BigDecimal b = BigDecimal.valueOf((double) datePoor / 60);
            //第一个参数是保留小数的位数
            Double hour = b.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();

            //计算总金额
            Double pay = hourFee.doubleValue();
            jzc.setPayAmount(BigDecimal.valueOf(pay*hour));
            return jzc;
        }
        return null;
    }

    @Override
    public List<VenueAppoint> venueaz(VenueAppoint venueAppoint) {
        return venueAppointMapper.venueaz(venueAppoint);
    }

    @Override
    public VenueAppoint venueazById(Long appointId) {
        return venueAppointMapper.venueazById(appointId);
    }


    //计算两个时间的小时数
    public static Integer getDatePoor(Date startDate, Date endDate) {

        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        Long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        Long mymint = diff/1000/60;
        int intValue = mymint.intValue();
        return intValue;
    }


}
