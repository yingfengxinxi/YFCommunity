package com.wr.api.owner.web.home.notice;

import cn.hutool.core.map.MapUtil;
import com.wr.api.owner.service.home.notice.NoticeOwnerService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.business.annotation.EncryptResponse;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 业主端通知控制层
 *
 * @author lvzy
 * @since 2022-11-14 13:20:48
 * 【业主端】
 */
@RestController
@RequestMapping("/ownerNotice")
@Api(tags = "业主端通知")
public class NoticeOwnerController extends BaseController {

    /**
     * 服务对象
     */
    @Resource
    private NoticeOwnerService noticeService;


    /**
     * 获取业主端通知
     *
     * @param reqMap
     * @return
     */

    @PostMapping("/getOwnerNoticeList")
    @ApiOperation("获取业主端通知列表")
    public AjaxResult getOwnerNoticeList(@RequestBody Map<String,Object> reqMap) {

        return AjaxResult.success(noticeService.getNoticeList(reqMap));
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
   * @Author SUNk
   * @Description 按照分类修改已读状态
   * @Date 18:20 2024/3/21
   * @Param [map]
   * @return com.wr.common.core.web.domain.AjaxResult
   **/
    @ApiOperation("按照分类修改已读状态")
    @PostMapping("/insertNoticeRead")
    public AjaxResult insertNoticeRead(@RequestBody Map<String,Object> map) {
        noticeService.insertNoticeRead(map);
        return AjaxResult.success();
    }

    @PostMapping("/selectListProperty")
    @ApiOperation("查询物业通知")
    public TableDataInfo selectListProperty(@RequestBody Map<String,Object> reqMap) {
        PageUtils.startPage( MapUtil.getInt(reqMap,"pageNum"), MapUtil.getInt(reqMap,"pageSize"));
        return getDataTable(noticeService.selectListProperty(reqMap));
    }

    @PostMapping("/selectListSocialFeatured")
    @ApiOperation("查询特色板块及社交板块的点赞评论")
    public TableDataInfo selectListSocialFeatured(@RequestBody Map<String,Object> reqMap) {
        PageUtils.startPage( MapUtil.getInt( reqMap,"pageNum" ),MapUtil.getInt( reqMap,"pageSize" ) );
        return getDataTable(noticeService.selectListSocialFeatured(reqMap));
    }

    @PostMapping("/findOneMessage")
    @ApiOperation("获取一条最新的通知")
    public AjaxResult findOneMessage(@RequestBody Map<String,Object> reqMap) {

        return AjaxResult.success(noticeService.findOneMessage(reqMap));
    }
}

