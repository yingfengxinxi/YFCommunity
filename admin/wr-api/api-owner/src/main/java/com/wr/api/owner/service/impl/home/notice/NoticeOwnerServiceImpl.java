package com.wr.api.owner.service.impl.home.notice;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.wr.api.owner.entity.home.notice.NoticeOwnerVO;
import com.wr.api.owner.mapper.home.notice.NoticeOwnerMapper;
import com.wr.api.owner.service.home.notice.NoticeOwnerService;
import com.wr.remote.domain.NoticeFestival;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 社区公告表(BusNotice)表服务实现类
 *
 * @author lvzy
 * @since 2022-11-14 13:21:09
 */
@Service
public class NoticeOwnerServiceImpl implements NoticeOwnerService {

    @Autowired
    private NoticeOwnerMapper noticeOwnerMapper;


   /**
   * @Author SUNk
   * @Description 按照分类修改已读状态
   * @Date 18:23 2024/3/21
   * @Param [map]
   * @return void
   **/
    @Override
    public void insertNoticeRead(Map<String, Object> map) {
        String type = MapUtil.getStr(map, "type");
        Long communityId = MapUtil.getLong(map, "communityId");
        String userId = MapUtil.getStr(map, "userId");

        List<String> typesToProcess = new ArrayList<>();

        switch (type) {
            case "4":
                typesToProcess.add("41");
                typesToProcess.add("42");
                break;
            case "6":
                typesToProcess.add("61");
                typesToProcess.add("62");
                typesToProcess.add("63");
                typesToProcess.add("64");
                break;
            default:
                typesToProcess.add(type);
                break;
        }

        for (String typeToProcess : typesToProcess) {
            List<NoticeOwnerVO> noticeOwnerList = selectCountByType(map, typeToProcess);
            if (CollUtil.isNotEmpty(noticeOwnerList)) {
                for (NoticeOwnerVO noticeOwnerVO : noticeOwnerList) {
                    noticeOwnerMapper.insertNoticeRead(communityId, String.valueOf(noticeOwnerVO.getId()), userId, typeToProcess, "1",noticeOwnerVO.getLikeUser());
                }
            }
        }
    }


    /**
     * @param estateId
     * @return
     */
    @Override
    public List<NoticeOwnerVO> searchByCommunityId(Long estateId, String loginName, Long communityId) {
        List<NoticeOwnerVO> noticeVOList = noticeOwnerMapper.searchByCommunityId(estateId, communityId);
        if (CollectionUtils.isNotEmpty(noticeVOList)) {
            noticeVOList.stream().forEach(noticeVO -> {
                //消息为已读
//                insertNoticeRead(communityId, String.valueOf(noticeVO.getNoticeId()), loginName, "0", "0", estateId);
            });
        }

        return noticeVOList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public NoticeOwnerVO detailByNoticeId(Long noticeId) {
        //阅读数 + 1
        noticeOwnerMapper.increaseReadNumByNoticeId(noticeId);
        return noticeOwnerMapper.detailByNoticeId(noticeId);
    }

    /**
     * @param id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public NoticeFestival festivalDetail(String id) {

        return noticeOwnerMapper.festivalDetail(id);
    }

    /**
     * @param reqMap
     * @return
     */
    @Override
    public Map<String, Object> getNoticeList(Map<String,Object> reqMap) {
        Map<String, Object> hashMap = new HashMap<>();

        String[] types = {"0", "1", "2", "3", "41", "42", "5", "61", "62", "63", "64", "7"};

        for (String type : types) {
            List<NoticeOwnerVO> notices = selectCountByType(reqMap, type);
            if (CollUtil.isNotEmpty(notices)) {
                hashMap.put(type, notices);
            }
        }

        return hashMap;
    }

    private List<NoticeOwnerVO> selectCountByType(Map<String, Object> reqMap, String type) {
        switch (type) {
            case "0":
                return noticeOwnerMapper.selectCountSafety(reqMap);
            case "1":
                return noticeOwnerMapper.selectCountWarranty(reqMap);
            case "2":
                return noticeOwnerMapper.selectCountNotice(reqMap);
            case "3":
                return noticeOwnerMapper.selectCountStatute(reqMap);
            case "41":
                return noticeOwnerMapper.selectCountProperty(reqMap);
            case "42":
                return noticeOwnerMapper.selectCountFestival(reqMap);
            case "5":
                return noticeOwnerMapper.selectCountBulletin(reqMap);
            case "61":
                return noticeOwnerMapper.selectCountSocial(reqMap);
            case "62":
                return noticeOwnerMapper.selectCountSocialLike(reqMap);
            case "63":
                return noticeOwnerMapper.selectCountSection(reqMap);
            case "64":
                return noticeOwnerMapper.selectCountSectionLike(reqMap);
            case "7":
                return noticeOwnerMapper.selectCountInvest(reqMap);
            default:
                return Collections.emptyList();
        }
    }

    @Override
    public List<NoticeOwnerVO> selectListProperty(Map<String, Object> reqMap) {

        return noticeOwnerMapper.selectListProperty(reqMap);
    }

    @Override
    public List<NoticeOwnerVO> selectListSocialFeatured(Map<String, Object> map) {

        return noticeOwnerMapper.selectListSocialFeatured( map );
    }

    @Override
    public NoticeOwnerVO findOneMessage(Map<String, Object> map) {
        return noticeOwnerMapper.findOneMessage( map );
    }
}
