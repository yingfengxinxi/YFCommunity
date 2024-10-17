package com.wr.api.owner.web;

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
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName UniversalEstateController
 * @Description 调用estate接口
 * @Author SUNk
 * @Date 2024/2/20 16:22
 * @ModifyDate 2024/2/20 16:22
 * @Version 1.0
 */
@RestController
@RequestMapping("/universalEstateController")
@Api(tags = "调用estate接口")
public class UniversalEstateController {
    @Resource
    private CommunityInformationClient communityInformationClient;

    /**
    * @Author SUNk
    * @Description 查询社区咨询列表
    * @Date 17:01 2024/2/21
    * @Param [communityInformation]
    * @return com.wr.common.core.web.page.TableDataInfo
    **/
    @PostMapping("/communityInformation/list")
    public TableDataInfo list(@RequestBody CommunityInformation communityInformation){
        return communityInformationClient.list( communityInformation );
    }

    /**
    * @Author SUNk
    * @Description 查询社区咨询详情
    * @Date 17:02 2024/2/21
    * @Param [id]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    @GetMapping("/communityInformation/detail")
    public AjaxResult detail(@RequestParam("id") String id){
        return communityInformationClient.detail( id );
    }

    /**
    * @Author SUNk
    * @Description 自治规约列表
    * @Date 17:22 2024/2/21
    * @Param [autonomyStatute]
    * @return com.wr.common.core.web.page.TableDataInfo
    **/
    @PostMapping("/autonomyStatute/list")
    public TableDataInfo list(@RequestBody AutonomyStatute autonomyStatute){
        return communityInformationClient.list( autonomyStatute );
    }

    /**
    * @Author SUNk
    * @Description 自治规约详情
    * @Date 17:22 2024/2/21
    * @Param [id]
    * @return com.wr.common.core.web.domain.AjaxResult
    **/
    @GetMapping("/autonomyStatute/{id}")
    public AjaxResult getById(@PathVariable String id){
        return communityInformationClient.getById( id );
    }

    /**
     * @Author SUNk
     * @Description 查询防控信息列表
     * @Date 17:22 2024/2/21
     * @Param [autonomyStatute]
     * @return com.wr.common.core.web.page.TableDataInfo
     **/
    @PostMapping("/prevent/getList")
    public TableDataInfo getList(@RequestBody Prevent prevent){
        return communityInformationClient.getList( prevent );
    }

    /**
     * @Author SUNk
     * @Description 查询防控信息详情
     * @Date 17:22 2024/2/21
     * @Param [id]
     * @return com.wr.common.core.web.domain.AjaxResult
     **/
    @GetMapping("/prevent/getPreventDetail/{preventId}")
    public AjaxResult getPreventDetail(@PathVariable Long preventId){
        return communityInformationClient.getPreventDetail( preventId );
    }

    /**
     * @Author SUNk
     * @Description 查询信息公示列表
     * @Date 17:22 2024/2/21
     * @Param [autonomyStatute]
     * @return com.wr.common.core.web.page.TableDataInfo
     **/
    @PostMapping("/invest/list")
    public TableDataInfo list(@RequestBody Invest invest){
        return communityInformationClient.list( invest );
    }

    /**
     * @Author SUNk
     * @Description 查询信息公示详情
     * @Date 17:22 2024/2/21
     * @Param [id]
     * @return com.wr.common.core.web.domain.AjaxResult
     **/
    @GetMapping("/invest/{investId}")
    public AjaxResult getInfo(@PathVariable Long investId){
        return communityInformationClient.getInfo( investId );
    }

    /**
     * @Author SUNk
     * @Description 通知公告列表
     * @Date 17:22 2024/2/21
     * @Param [autonomyStatute]
     * @return com.wr.common.core.web.page.TableDataInfo
     **/
    @PostMapping("/notice/list")
    public TableDataInfo list(@RequestBody CommunityNotice notice){
        return communityInformationClient.list( notice );
    }

    /**
     * @Author SUNk
     * @Description 通知公告详情
     * @Date 17:22 2024/2/21
     * @Param [id]
     * @return com.wr.common.core.web.domain.AjaxResult
     **/
    @GetMapping("/notice/{noticeId}")
    public AjaxResult getNoticeInfo(@PathVariable Long noticeId){
        return communityInformationClient.getNoticeInfo( noticeId );
    }

    /**
     * @Author SUNk
     * @Description 寻人启示列表
     * @Date 17:22 2024/2/21
     * @Param [autonomyStatute]
     * @return com.wr.common.core.web.page.TableDataInfo
     **/
    @PostMapping("/peopleLost/list")
    public TableDataInfo list(@RequestBody PeopleLostVo peopleLost){
        return communityInformationClient.list( peopleLost );
    }

    /**
     * @Author SUNk
     * @Description 寻人启示详情
     * @Date 17:22 2024/2/21
     * @Param [id]
     * @return com.wr.common.core.web.domain.AjaxResult
     **/
    @GetMapping("/peopleLost/{lostId}")
    public AjaxResult get(@PathVariable Long lostId){
        return communityInformationClient.get( lostId );
    }

    /**
     * @Author SUNk
     * @Description 寻物启事列表
     * @Date 17:22 2024/2/21
     * @Param [autonomyStatute]
     * @return com.wr.common.core.web.page.TableDataInfo
     **/
    @PostMapping("/thingLost/list")
    public TableDataInfo list(@RequestBody ThingLostVo thingLost){
        return communityInformationClient.list( thingLost );
    }

    /**
     * @Author SUNk
     * @Description 寻物启事详情
     * @Date 17:22 2024/2/21
     * @Param [id]
     * @return com.wr.common.core.web.domain.AjaxResult
     **/
    @GetMapping("/thingLost/{lostId}")
    public AjaxResult thingGet(@PathVariable Long lostId){
        return communityInformationClient.thingGet( lostId );
    }
}
