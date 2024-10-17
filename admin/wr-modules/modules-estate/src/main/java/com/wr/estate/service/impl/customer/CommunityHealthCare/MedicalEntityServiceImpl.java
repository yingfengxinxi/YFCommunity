package com.wr.estate.service.impl.customer.CommunityHealthCare;

import com.wr.common.core.utils.DateUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.entity.MedacalOlderEntity;
import com.wr.estate.entity.MedicalEntity;
import com.wr.estate.entity.vo.ExaminerExcelEntity;
import com.wr.estate.mapper.customer.CommunityHealthCare.MedicalEntityMapper;
import com.wr.estate.service.customer.CommunityHealthCare.MedicalEntityService;
import com.wr.estate.service.info.CommunityNoticeService;
import com.wr.remote.estate.info.CommunityNotice;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 医疗检测通告Service业务层处理
 *
 * @author bajie
 * @date 2022-10-13
 */
@Service
public class MedicalEntityServiceImpl implements MedicalEntityService {
    @Autowired
    private MedicalEntityMapper medicalEntityMapper;

    @Autowired
    private CommunityNoticeService communityNoticeService;

    /**
     * 查询医疗检测通告
     *
     * @param medicalId 医疗检测通告主键
     * @return 医疗检测通告
     */
    @Override
    public MedicalEntity selectMedicalEntityByMedicalId(Long medicalId) {
        return medicalEntityMapper.selectMedicalEntityByMedicalId(medicalId);
    }

    /**
     * 查询医疗检测通告列表
     *
     * @param medicalEntity 医疗检测通告
     * @return 医疗检测通告
     */
    @Override
    public List<MedicalEntity> selectMedicalEntityList(MedicalEntity medicalEntity) {
        return medicalEntityMapper.selectMedicalEntityList(medicalEntity);
    }

    @Override
    public List<ExaminerExcelEntity> SelectExaminerExcelEntity(ExaminerExcelEntity medicalEntity) {
        return null;
    }

    /**
     * 新增医疗检测通告
     *
     * @param medicalEntity 医疗检测通告
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertMedicalEntity(MedicalEntity medicalEntity) {
        String content = "体检名称：%s\n参检人群范围：%s\n检测地点：%s\n检测机构：%s\n体检时间：%s\n注意事项：%s";
        //判断名称是否重复
        int row = medicalEntityMapper.getnamecount(medicalEntity.getActivityName());
        if (row > 0) {
            return -5;
        }
        medicalEntity.setCreateTime(DateUtils.getNowDate());
        medicalEntity.setCreateBy(SecurityUtils.getUsername());
        CommunityNotice notice = new CommunityNotice();
        notice.setCreateBy(SecurityUtils.getUsername());
        notice.setNoticeTitle(medicalEntity.getActivityName());
        notice.setCommunityIds(new Long[]{medicalEntity.getCommunityId()});
        String formatContent = String.format(content, medicalEntity.getActivityName(),
                medicalEntity.getAgeLimit() + "岁以上",
                medicalEntity.getAddress(),
                medicalEntity.getMedicalOrg(),
                DateUtils.dateTime(medicalEntity.getStartTime()) + "至" + DateUtils.dateTime(medicalEntity.getEndTime()),
                ObjectUtils.isEmpty(medicalEntity.getRemark()) ? "" : medicalEntity.getRemark()
        );
        notice.setContent(formatContent);
        notice.setRange("0");
        communityNoticeService.insertNotice(notice);
        // 新增通告
        return medicalEntityMapper.insertMedicalEntity(medicalEntity);
    }

    /**
     * 修改医疗检测通告
     *
     * @param medicalEntity 医疗检测通告
     * @return 结果
     */
    @Override
    public int updateMedicalEntity(MedicalEntity medicalEntity) {
        medicalEntity.setUpdateTime(DateUtils.getNowDate());
        String username = SecurityUtils.getUsername();
        medicalEntity.setUpdateBy(username);
        return medicalEntityMapper.updateMedicalEntity(medicalEntity);
    }

    /**
     * 批量删除医疗检测通告
     *
     * @param medicalIds 需要删除的医疗检测通告主键
     * @return 结果
     */
    @Override
    public int deleteMedicalEntityByMedicalIds(Long[] medicalIds) {
        return medicalEntityMapper.deleteMedicalEntityByMedicalIds(medicalIds);
    }

    /**
     * 删除医疗检测通告信息
     *
     * @param medicalId 医疗检测通告主键
     * @return 结果
     */
    @Override
    public int deleteMedicalEntityByMedicalId(Long medicalId) {
        return medicalEntityMapper.deleteMedicalEntityByMedicalId(medicalId);
    }

    @Override
    public List<MedacalOlderEntity> lookoldere(MedacalOlderEntity medacalOlderEntity) {
        List<MedacalOlderEntity> list = new ArrayList<>();
        List<MedacalOlderEntity> list1 = medicalEntityMapper.lookoldere(medacalOlderEntity);
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i).getCardNo().length() == 18 && list1.get(i).getCardNo() != null) {
                String year = list1.get(i).getCardNo().substring(6).substring(0, 4);
                String month = list1.get(i).getCardNo().substring(10).substring(0, 2);
                String day = list1.get(i).getCardNo().substring(12).substring(0, 2);
                int gender = 0;
                char c = list1.get(i).getCardNo().charAt(list1.get(i).getCardNo().length() - 2);
                gender = Integer.parseInt(String.valueOf(c));
                if (gender % 2 == 1) {
                    list1.get(i).setOlderGender(0);
                } else if (gender % 2 == 0) {
                    list1.get(i).setOlderGender(1);
                } else {
                    list1.get(i).setOlderGender(-1);
                }
                String time = year + "-" +
                        month + "-" +
                        day;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = sdf.parse(time);
                    int age = getAge(date);
                    list1.get(i).setOlderAge(age);
                    list.add(list1.get(i));
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return list;
    }

    @Override
    public MedacalOlderEntity lookoldere1(long examinerId) {
        MedacalOlderEntity medacalOlderEntity = new MedacalOlderEntity();
        MedacalOlderEntity medacalOlderEntity1 = medicalEntityMapper.lookoldere1(examinerId);
        if (medacalOlderEntity1.getCardNo().length() == 18 && medacalOlderEntity1.getCardNo() != null) {
            String year = medacalOlderEntity1.getCardNo().substring(6).substring(0, 4);
            String month = medacalOlderEntity1.getCardNo().substring(10).substring(0, 2);
            String day = medacalOlderEntity1.getCardNo().substring(12).substring(0, 2);
            int gender = 0;
            char c = medacalOlderEntity1.getCardNo().charAt(medacalOlderEntity1.getCardNo().length() - 2);
            gender = Integer.parseInt(String.valueOf(c));
            if (gender % 2 == 1) {
                medacalOlderEntity1.setOlderGender(1);
            } else {
                medacalOlderEntity1.setOlderGender(2);
            }
            String time = year + "-" +
                    month + "-" +
                    day;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(time);
                int age = getAge(date);
                medacalOlderEntity1.setOlderAge(age);
                medacalOlderEntity = medacalOlderEntity1;
            } catch (Exception e) {
                return null;
            }
        }
        return medacalOlderEntity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String importUser(List<ExaminerExcelEntity> userList, Boolean isUpdateSupport, String operName) {
        try {
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getCreateBy() == null) {
                    userList.get(i).setCreateBy(operName);
                }
                if (userList.get(i).getExaminerMedical() == null) {
                    userList.get(i).setExaminerMedical(medicalEntityMapper.meid(userList.get(i).getActivityName()));
                }
            }
            int i = medicalEntityMapper.insoldere(userList);
            // 添加参与人数
            medicalEntityMapper.addJoinNum(userList.get(0).getExaminerMedical(), userList.size());
            if (i > 0) {
                return "导入成功";
            } else {
                return "导入失败";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "导入失败";
        }
    }

    @Override
    public int addpath(String path, long examinerId) {
        String username = SecurityUtils.getUsername();
        int addpath = medicalEntityMapper.addpath(path, examinerId, username);
        return addpath;
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
