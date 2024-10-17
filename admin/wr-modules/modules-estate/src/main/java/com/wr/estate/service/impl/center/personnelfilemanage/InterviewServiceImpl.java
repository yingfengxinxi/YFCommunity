package com.wr.estate.service.impl.center.personnelfilemanage;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.wr.common.core.exception.ServiceException;
import com.wr.common.core.utils.DateUtils;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.uuid.IdUtils;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.mapper.center.personnelfilemanage.InterviewMapper;
import com.wr.estate.mapper.center.personnelfilemanage.VisitorFileMapper;
import com.wr.estate.mapper.center.useraccountmanage.AccountManageMapper;
import com.wr.estate.service.center.RoomService;
import com.wr.estate.service.center.personnelfilemanage.InterviewService;
import com.wr.estate.service.center.personnelfilemanage.OwnerFileService;
import com.wr.estate.service.center.personnelfilemanage.VisitorFileService;
import com.wr.remote.domain.vo.InterviewVo;
import com.wr.remote.domain.vo.VisitorVo;
import com.wr.remote.estate.center.VehiclePlateNoUtil;
import com.wr.remote.estate.center.vo.BusVehicleVo;
import com.wr.remote.estate.center.vo.OwnerFileVo;
import com.wr.remote.estate.center.vo.VisitorFileVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @Author lvzy
 * @Date 2024/3/14 11:21
 */
@Service
public class InterviewServiceImpl implements InterviewService {

    @Resource
    private InterviewMapper interviewMapper;
    @Resource
    private OwnerFileService ownerFileService;

    @Resource
    private RoomService roomService;

    @Override
    public List<InterviewVo> query(InterviewVo interviewVo) {

        return interviewMapper.query(interviewVo);
    }

    @Override
    public InterviewVo getVisitorById(Long visitorId) {
        return interviewMapper.getVisitorById(visitorId);
    }

    /**
     * @param dataList
     * @param communityId
     * @return
     */
    @Override
    public String importData(List<InterviewVo> dataList, Long communityId) {
        if (CollectionUtils.isEmpty(dataList)) {
            throw new ServiceException("导入访客档案不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (InterviewVo interviewVo : dataList) {
            String visitorName = interviewVo.getVisitorName();
            try {

                if (StringUtils.isNotEmpty(visitorName)) {
                    String visitorPhone = interviewVo.getVisitorPhone();
                    if (StringUtils.isNotEmpty(visitorPhone)) {
                        Integer withNum = interviewVo.getWithNum();
                        if (withNum != null && withNum >= 1) {
                            Date beginTime = interviewVo.getBeginTime();
                            if (beginTime != null) {
                                String ownerPhone = interviewVo.getOwnerPhone();
                                if (StringUtils.isNotEmpty(ownerPhone)) {
                                    OwnerFileVo ownerPhoneInfo = ownerFileService.getOwnerPhoneInfo(ownerPhone);
                                    if (ownerPhoneInfo != null) {
                                        Long roomId = roomService.getByOwnerIdRoomId(ownerPhoneInfo.getOwnerId());
                                        if (roomId != null) {
                                            interviewVo.setPlantCar("0");
                                            if (StringUtils.isNotEmpty(interviewVo.getPlantNo())) {
                                                interviewVo.setPlantCar("1");
                                            }
                                            interviewVo.setId(IdUtils.fastUUID().replace("-", ""));
                                            interviewVo.setCommunityId(communityId);
                                            interviewVo.setAccountId(ownerPhoneInfo.getAccountId());
                                            interviewVo.setCreateBy(SecurityUtils.getUsername());
                                            interviewVo.setUpdateBy(SecurityUtils.getUsername());
                                            interviewVo.setRoomId(roomId);
                                            interviewVo.setOwnerId(ownerPhoneInfo.getOwnerId());
                                            interviewVo.setOwnerName(ownerPhoneInfo.getOwnerName());
                                            interviewVo.setOwnerPhone(ownerPhoneInfo.getOwnerPhone());
                                            interviewVo.setVisitorNum(1);
                                            interviewMapper.insert(interviewVo);
                                            successNum++;
                                            successMsg.append("<br/>" + successNum + "、访客 " + visitorName + " 导入成功");
                                        } else {
                                            failureNum++;
                                            failureMsg.append("<br/>" + failureNum + " 、访客" + visitorName + "拜访业主地址不存在,请进入业主档案中核查");
                                        }
                                    } else {
                                        failureNum++;
                                        failureMsg.append("<br/>" + failureNum + " 、访客" + visitorName + "拜访业主手机不存在,请进入业主档案中核查");
                                    }
                                } else {
                                    failureNum++;
                                    failureMsg.append("<br/>" + failureNum + " 、访客" + visitorName + "拜访业主手机不能为空");
                                }
                            } else {
                                failureNum++;
                                failureMsg.append("<br/>" + failureNum + " 、访客" + visitorName + "拜访时间不能为空");
                            }

                        } else {
                            failureNum++;
                            failureMsg.append("<br/>" + failureNum + " 、访客" + visitorName + "访客人数不能为空");
                        }
                    } else {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + " 、访客" + visitorName + "手机号不能为空");
                    }
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + " 、访客姓名不能为空");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、访客： " + visitorName + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                e.printStackTrace();
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据不正确，错误如下：");
            //throw new ServiceException(failureMsg.toString());
            return failureMsg.toString();
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
            return successMsg.toString();
        }
    }


}