package com.wr.api.estate.web.application;

import com.wr.api.estate.service.application.NoticeService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.business.annotation.EncryptResponse;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.customize.util.DecryptUtil;
import com.wr.common.redis.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 社区公告表(BusNotice)表控制层
 *
 * @author lvzy
 * @since 2022-11-14 13:20:48
 * 【物业移动端】
 */
@RestController
@RequestMapping("v2/notice")
@Api(tags = "物业移动端-通知公告")
public class NoticeController extends BaseController {

    /**
     * 服务对象
     */
    @Resource
    private NoticeService noticeService;


    /**
     * 消息公告列表
     *
     * @param estateId
     * @return
     */

    @GetMapping("getNoticeList")
    @ApiOperation("通知公告列表1")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "estateId", value = "物业id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "loginName", value = "当前登录用户", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public AjaxResult getNoticeList(@RequestParam("estateId") Long estateId, @RequestParam("loginName") String loginName, @RequestParam("communityId") Long communityId) {

        return AjaxResult.success(noticeService.getNoticeList(estateId, loginName, communityId));
    }

    /**
     * 系统消息列表
     *
     * @param estateId
     * @param loginName
     * @return
     */
    @GetMapping("search")
    @EncryptResponse
    @AuthRequired
    @ApiOperation("通知公告列表2")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "estateId", value = "物业id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "communityId", value = "小区id", dataType = "Long", required = false, paramType = "query"),
            @ApiImplicitParam(name = "loginName", value = "当前登录用户", dataType = "String", required = false, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "显示数量", dataType = "Integer", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "第几页", dataType = "Integer", required = true, paramType = "query"),
    })
    public TableDataInfo search(@RequestParam("estateId") Long estateId, @RequestParam("loginName") String loginName, @RequestParam("communityId") Long communityId) {
        startPage();
        return getDataTable(noticeService.searchByCommunityId(estateId, loginName, communityId));
    }

    /**
     * @param noticeId
     * @return
     */
    @ApiOperation("通知公告详情")
    @GetMapping("detail")
    @EncryptResponse
    @AuthRequired
    public AjaxResult detail(@RequestParam("noticeId") Long noticeId) {

        return AjaxResult.success(noticeService.detailByNoticeId(noticeId));
    }


    /**
     * @param id
     * @return
     */
    @ApiOperation("节假日公告详情")
    @GetMapping("festivalDetail")
    @EncryptResponse
    @AuthRequired
    public AjaxResult festivalDetail(@RequestParam("id") String id) {

        return AjaxResult.success(noticeService.festivalDetail(id));
    }


    /**
     * @param id
     * @param loginName
     * @param type
     * @return
     */
    @ApiOperation("修改未读状态")
    @GetMapping("insertNoticeRead")
    public AjaxResult insertNoticeRead(
            @RequestParam("communityId") Long communityId,
            @RequestParam("id") String id,
            @RequestParam("loginName") String loginName,
            @RequestParam("type") String type,
            @RequestParam("estateId") Long estateId
    ) {
        noticeService.insertNoticeRead(communityId, id, loginName, type, "0", estateId);
        return AjaxResult.success();
    }
}

