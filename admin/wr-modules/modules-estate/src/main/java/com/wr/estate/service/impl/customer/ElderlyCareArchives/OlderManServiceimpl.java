package com.wr.estate.service.impl.customer.ElderlyCareArchives;

import com.wr.estate.entity.HomeEntity;
import com.wr.estate.entity.OldManByTreeEntity;
import com.wr.estate.entity.OldMsgEntity;
import com.wr.estate.entity.PeopleEntity;
import com.wr.estate.entity.vo.OlderVo;
import com.wr.estate.mapper.customer.ElderlyCareArchives.OlderManMapper;
import com.wr.estate.service.customer.ElderlyCareArchives.OlderManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: bajie
 * @create: 2022/10/11
 * @Description:
 * @FileName: OlderManServiceimp
 * @History:
 */
@Service
public class OlderManServiceimpl implements OlderManService {

    @Autowired
    OlderManMapper olderManMapper;


    @Override
    public List<OldManByTreeEntity> getTree() {
        return olderManMapper.getTree();
    }

    @Override
    public List<HomeEntity> getbuilding(long communityId) {
        return olderManMapper.getbuilding(communityId);
    }

    @Override
    public List<HomeEntity> getunit(long buildingId, long communityId) {
        return olderManMapper.getunit(buildingId,communityId);
    }

    @Override
    public List<HomeEntity> getroom(long buildingId, long communityId, long unitId) {
        return olderManMapper.getroom(buildingId,communityId,unitId);
    }

    @Override
    public List<PeopleEntity> getMan(long roomId) {
        List<PeopleEntity> list = new ArrayList<>();
        List<PeopleEntity> list1 = olderManMapper.getMan(roomId);
        for (int i = 0; i <list1.size(); i++) {
            if (list1.get(i).getCardNo().length()==18){
                String year = list1.get(i).getCardNo().substring(6).substring(0, 4);
                String month = list1.get(i).getCardNo().substring(10).substring(0, 2);
                String day = list1.get(i).getCardNo().substring(12).substring(0, 2);
                String time = year + "-" +
                        month + "-" +
                        day;
                list1.get(i).setOldDate(time);
                SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
                try {
                    Date date = sdf.parse(time);
                    list1.get(i).setDate(date);
                    int age = getAge(date);
                    list1.get(i).setOldageT(age);
                }catch (Exception e){
                    return null;
                }
            }
            if (list1.get(i).getOldageT() >=60){
                list.add(list1.get(i));
            }
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addoldfile(OlderVo olderVo) {
        int i = olderManMapper.getoldfilecount(olderVo.getOwnerId());
        if (0==i){
            return olderManMapper.addoldfile(olderVo);
        }
        return -123;
    }

    @Override
    public List<OlderVo> getoldman(OlderVo olderVo) {
        List<OlderVo> list = olderManMapper.getoldman(olderVo);
        return list;
    }

    @Override
    public List oldmsg(long olderId) {
        List list = new ArrayList();
        List list1;
        OldMsgEntity oldMsgEntity = olderManMapper.oldmsg(olderId);
        list.add(oldMsgEntity);
        list1 = olderManMapper.lookolderman(olderId);
        list.add(list1);
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int tg(String updateBy,long olderId) {
        if (olderId !=0){
            return olderManMapper.tg(updateBy,olderId);
        }
        else{
            return -1;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int bh(String updateBy, long olderId, String rejectReason) {
        if (olderId !=0){
            return olderManMapper.bh(updateBy,olderId,rejectReason);
        }
        else{
            return -1;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int del(Long[] olderId) {
        return olderManMapper.del(olderId);
    }

    public static int getAge(Date birthDay) {
        if (birthDay == null) {
            return 0;
        }
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            return 0;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        boolean lowAge = monthBirth == monthNow && dayOfMonthNow < dayOfMonthBirth;

        if (monthNow < monthBirth || lowAge) {
            age--;
        }
        return age;
    }
}
