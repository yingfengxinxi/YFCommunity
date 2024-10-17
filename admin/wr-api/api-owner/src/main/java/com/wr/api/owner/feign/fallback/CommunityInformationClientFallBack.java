package com.wr.api.owner.feign.fallback;

import com.wr.api.owner.feign.CommunityInformationClient;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.domain.AutonomyStatute;
import com.wr.remote.domain.CommunityInformation;
import com.wr.remote.estate.customer.lost.PeopleLostVo;
import com.wr.remote.estate.customer.lost.ThingLostVo;
import com.wr.remote.estate.info.CommunityNotice;
import com.wr.remote.estate.info.Invest;
import com.wr.remote.govern.security.Prevent;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName CommunityInformationClientFallBack
 * @Description TODO
 * @Author SUNk
 * @Date 2024/2/1 18:27
 * @ModifyDate 2024/2/1 18:27
 * @Version 1.0
 */
@Component
public class CommunityInformationClientFallBack implements FallbackFactory<CommunityInformationClient> {
    @Override
    public CommunityInformationClient create(Throwable cause) {
        return new CommunityInformationClient(){
            @Override
            public TableDataInfo list(CommunityInformation communityInformation) {
                return new TableDataInfo();
            }

            @Override
            public AjaxResult detail(String id) {
                return AjaxResult.error("获取详情失败:" + cause.getMessage());
            }

            @Override
            public TableDataInfo list(AutonomyStatute autonomyStatute) {
                return new TableDataInfo();
            }

            @Override
            public AjaxResult getById(String id) {
                return AjaxResult.error("获取详情失败:" + cause.getMessage());
            }

            @Override
            public TableDataInfo getList(Prevent prevent) {
                return new TableDataInfo();
            }

            @Override
            public AjaxResult getPreventDetail(Long preventId) {
                return AjaxResult.error("获取详情失败:" + cause.getMessage());
            }

            @Override
            public TableDataInfo list(Invest invest) {
                return new TableDataInfo();
            }

            @Override
            public AjaxResult getInfo(Long investId) {
                return AjaxResult.error("获取详情失败:" + cause.getMessage());
            }

            @Override
            public TableDataInfo list(CommunityNotice notice) {
                return new TableDataInfo();
            }

            @Override
            public AjaxResult getNoticeInfo(Long noticeId) {
                return AjaxResult.error("获取详情失败:" + cause.getMessage());
            }

            @Override
            public TableDataInfo list(PeopleLostVo peopleLost) {
                return new TableDataInfo();
            }

            @Override
            public AjaxResult get(Long lostId) {
                return AjaxResult.error("获取详情失败:" + cause.getMessage());
            }

            @Override
            public TableDataInfo list(ThingLostVo thingLost) {
                return new TableDataInfo();
            }

            @Override
            public AjaxResult thingGet(Long lostId) {
                return AjaxResult.error("获取详情失败:" + cause.getMessage());
            }
        };
    }
}
