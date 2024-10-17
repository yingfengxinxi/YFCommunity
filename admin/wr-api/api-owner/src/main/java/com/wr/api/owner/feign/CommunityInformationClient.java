package com.wr.api.owner.feign;

import com.wr.api.owner.feign.fallback.CommunityInformationClientFallBack;
import com.wr.common.core.constant.ServiceNameConstants;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.remote.domain.AutonomyStatute;
import com.wr.remote.domain.CommunityInformation;
import com.wr.remote.estate.customer.lost.PeopleLostVo;
import com.wr.remote.estate.customer.lost.ThingLostVo;
import com.wr.remote.estate.info.CommunityNotice;
import com.wr.remote.estate.info.Invest;
import com.wr.remote.govern.security.Prevent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName CommunityInformationClient
 * @Description 调用estate接口
 * @Author SUNk
 * @Date 2024/2/1 18:23
 * @ModifyDate 2024/2/1 18:23
 * @Version 1.0
 */
@FeignClient(
        contextId = "communityInformationClient", value = ServiceNameConstants.ESTATE_SERVICE, fallbackFactory = CommunityInformationClientFallBack.class
)
public interface CommunityInformationClient {

    /**
    * @Author SUNk
    * @Description 查询社区资讯列表
    * @Date 18:33 2024/2/1
    * @Param [communityInformation]
    * @return com.wr.common.core.web.page.TableDataInfo
    **/
    @PostMapping("/communityInformation/list")
    public TableDataInfo list(@RequestBody CommunityInformation communityInformation);

    /**
    * @Author SUNk
    * @Description 查询社区资讯详情
    * @Date 18:34 2024/2/1
    * @Param [id]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    @GetMapping("/communityInformation/detail")
    public AjaxResult detail(@RequestParam("id") String id);

    /**
    * @Author SUNk
    * @Description 自治规约列表
    * @Date 16:17 2024/2/20
    * @Param [autonomyStatute]
    * @return com.wr.common.core.web.page.TableDataInfo
    **/
    @GetMapping("/autonomyStatute/list")
    public TableDataInfo list(AutonomyStatute autonomyStatute);

    /**
     * @Author SUNk
     * @Description 自治规约详情
     * @Date 16:17 2024/2/20
     * @Param [autonomyStatute]
     * @return com.wr.common.core.web.page.TableDataInfo
     **/
    @GetMapping("/autonomyStatute/{id}")
    public AjaxResult getById(@PathVariable(value="id") String id);

    /**
    * @Author SUNk
    * @Description 查询防控信息列表
    * @Date 21:16 2024/2/22
    * @Param [prevent]
    * @return com.wr.common.core.web.page.TableDataInfo
    **/
    @PostMapping("/prevent/getList")
    public TableDataInfo getList(@RequestBody Prevent prevent);

    /**
    * @Author SUNk
    * @Description 查询防控信息详情
    * @Date 21:19 2024/2/22
    * @Param [preventId]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    @GetMapping("/prevent/getPreventDetail/{preventId}")
    public AjaxResult getPreventDetail(@PathVariable(value="preventId") Long preventId);

    /**
    * @Author SUNk
    * @Description 查询信息公示列表
    * @Date 21:24 2024/2/22
    * @Param [invest]
    * @return com.wr.common.core.web.page.TableDataInfo
    **/
    @PostMapping("/invest/list")
    public TableDataInfo list(@RequestBody Invest invest);

    /**
    * @Author SUNk
    * @Description 查询信息公示详情
    * @Date 21:24 2024/2/22
    * @Param [investId]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    @GetMapping(value = "/invest/{investId}")
    public AjaxResult getInfo(@PathVariable(value="investId") Long investId);

    /**
    * @Author SUNk
    * @Description 通知公告列表
    * @Date 21:40 2024/2/22
    * @Param [notice]
    * @return com.wr.common.core.web.page.TableDataInfo
    **/
    @PostMapping("/notice/list")
    public TableDataInfo list(@RequestBody CommunityNotice notice);

    /**
    * @Author SUNk
    * @Description 通知公告详情
    * @Date 21:41 2024/2/22
    * @Param [noticeId]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    @GetMapping(value = "/notice/{noticeId}")
    public AjaxResult getNoticeInfo(@PathVariable(value="noticeId") Long noticeId);

    /**
    * @Author SUNk
    * @Description 寻人启示列表
    * @Date 22:20 2024/2/22
    * @Param [peopleLost]
    * @return com.wr.common.core.web.page.TableDataInfo
    **/
    @PostMapping("/peopleLost/list")
    public TableDataInfo list(@RequestBody PeopleLostVo peopleLost);

    /**
    * @Author SUNk
    * @Description 寻人启示详情
    * @Date 22:21 2024/2/22
    * @Param [lostId]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    @GetMapping("/peopleLost/{lostId}")
    public AjaxResult get(@PathVariable(value="lostId") Long lostId);

    /**
    * @Author SUNk
    * @Description 寻物启事列表
    * @Date 22:27 2024/2/22
    * @Param [thingLost]
    * @return com.wr.common.core.web.page.TableDataInfo
    **/
    @PostMapping("/thingLost/list")
    public TableDataInfo list(@RequestBody ThingLostVo thingLost);

    /**
    * @Author SUNk
    * @Description 寻物启事详情
    * @Date 22:28 2024/2/22
    * @Param [lostId]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    @GetMapping("/thingLost/{lostId}")
    public AjaxResult thingGet(@PathVariable(value="lostId") Long lostId);
}
