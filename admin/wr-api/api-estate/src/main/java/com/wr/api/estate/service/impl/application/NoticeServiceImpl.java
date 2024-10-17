package com.wr.api.estate.service.impl.application;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.wr.api.estate.entity.vo.NoticeListVo;
import com.wr.api.estate.entity.vo.application.NoticeVO;
import com.wr.api.estate.mapper.application.NoticeMapper;
import com.wr.api.estate.mapper.application.security.PreventMapper;
import com.wr.api.estate.mapper.application.serve.InvestMapper;
import com.wr.api.estate.service.LoginService;
import com.wr.api.estate.service.application.NoticeService;
import com.wr.api.estate.service.application.info.UserLogService;
import com.wr.remote.domain.NoticeFestival;
import com.wr.remote.domain.SysUser;
import org.apache.commons.compress.utils.Lists;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 社区公告表(BusNotice)表服务实现类
 *
 * @author lvzy
 * @since 2022-11-14 13:21:09
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Autowired
    private InvestMapper investMapper;

    @Autowired
    private PreventMapper preventMapper;

    @Autowired
    private LoginService loginService;

    /**
     * @param noticeId
     * @param userId
     * @param type
     * @param flag
     */
    @Override
    public void insertNoticeRead(Long communityId, String noticeId, String userId, String type, String flag, Long estateId) {
        List<String> ids = Lists.newArrayList();
        if (type.equals("0")) {
            //系统公告
            ids.add(noticeId);
        }
        if (type.equals("1")) {
            //信息公示
            ids = investMapper.getByEstateIdInvestId(estateId, communityId);
        }
        if (type.equals("2")) {
            //防控信息
            ids = preventMapper.getByEstateIdPreventId(estateId, communityId);
        }
        if (type.equals("3")) {
            //节假日公告
            ids.add(noticeId);
        }
        if (CollectionUtils.isNotEmpty(ids)) {
            for (String id : ids) {
                Integer read = noticeMapper.getByUserIdTypeNoticeRead(communityId, userId, type, id);
                if (read == null || read == 0) {
                    noticeMapper.insertNoticeRead(communityId, id, userId, type, flag);
                }
            }
        }


    }

    /**
     * @param estateId
     * @return
     */
    @Override
    public List<NoticeVO> searchByCommunityId(Long estateId, String loginName, Long communityId) {
        List<NoticeVO> noticeVOList = noticeMapper.searchByCommunityId(estateId, communityId);
        if (CollectionUtils.isNotEmpty(noticeVOList)) {
            noticeVOList.stream().forEach(noticeVO -> {
                //消息为已读
                insertNoticeRead(communityId, String.valueOf(noticeVO.getNoticeId()), loginName, "0", "0", estateId);
            });
        }

        return noticeVOList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public NoticeVO detailByNoticeId(Long noticeId) {
        //阅读数 + 1
        noticeMapper.increaseReadNumByNoticeId(noticeId);
        return noticeMapper.detailByNoticeId(noticeId);
    }

    /**
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public NoticeFestival festivalDetail(String id) {

        return noticeMapper.festivalDetail(id);
    }

    /**
     * @param estateId
     * @return
     */
    @Override
    public List<NoticeListVo> getNoticeList(Long estateId, String loginName, Long communityId) {
        Map<String, Integer> map = new HashMap<>();
        List<String> typeList = Lists.newArrayList();
        typeList.add("0");
        typeList.add("1");
        typeList.add("2");
        typeList.add("3");
        SysUser sysUser = loginService.getUserNameNikeName(loginName);
        for (String type : typeList) {
            //类型0=系统消息1=信息公示1=防控信息
            if (type.equals("0")) {
                //全部消息数量
                Integer noticeCount = noticeMapper.getNoticeCount(estateId, communityId);
                if (noticeCount == null) {
                    noticeCount = 0;
                }
                if (noticeCount >= 1) {
                    //已读消息数量
                    Integer userReadCount = noticeMapper.getUserReadCount(loginName, type, communityId);
                    if (userReadCount == null) {
                        userReadCount = 0;
                    }
                    map.put(type, noticeCount - userReadCount);
                } else {
                    map.put(type, 0);
                }

            }

            if (type.equals("1")) {
                //全部消息数量
                Integer noticeCount = noticeMapper.getInvestCount(estateId, communityId);
                if (noticeCount == null) {
                    noticeCount = 0;
                }
                if (noticeCount >= 1) {
                    //已读消息数量
                    Integer userReadCount = noticeMapper.getUserReadCount(loginName, type, communityId);
                    if (userReadCount == null) {
                        userReadCount = 0;
                    }
                    map.put(type, noticeCount - userReadCount);
                } else {
                    map.put(type, 0);
                }

            }

            if (type.equals("2")) {
                //全部消息数量
                Integer noticeCount = noticeMapper.getPreventCount(estateId, communityId);
                if (noticeCount == null) {
                    noticeCount = 0;
                }
                if (noticeCount >= 1) {
                    //已读消息数量
                    Integer userReadCount = noticeMapper.getUserReadCount(loginName, type, communityId);
                    if (userReadCount == null) {
                        userReadCount = 0;
                    }
                    map.put(type, noticeCount - userReadCount);
                } else {
                    map.put(type, 0);
                }

            }

            if (type.equals("3")) {
                //全部消息数量

                Integer noticeCount = noticeMapper.getNoticeFestivalCount(sysUser.getUserId(), communityId);
                if (noticeCount == null) {
                    noticeCount = 0;
                }
                if (noticeCount >= 1) {
                    //已读消息数量
                    Integer userReadCount = noticeMapper.getUserReadCount(loginName, type, communityId);
                    if (userReadCount == null) {
                        userReadCount = 0;
                    }
                    map.put(type, noticeCount - userReadCount);
                } else {
                    map.put(type, 0);
                }

            }

        }

        List<NoticeListVo> noticeList = noticeMapper.getNoticeList(sysUser.getUserId(), communityId);
        if (CollectionUtils.isNotEmpty(noticeList)) {
            noticeList.stream().forEach(noticeListVo -> {
                String flag = noticeListVo.getFlag();
                Integer count = map.get(flag);
                noticeListVo.setIsRead(count);
            });
        }
        return noticeList;
    }
}
