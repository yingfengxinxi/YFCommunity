package com.wr.estate.web.vote;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wr.common.core.utils.PageUtils;
import com.wr.common.core.utils.uuid.IdUtils;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.estate.service.center.RoomService;
import com.wr.estate.service.vote.BusVoteOwnerService;
import com.wr.remote.domain.vo.BusVoteVo;
import com.wr.remote.estate.center.vo.RoomQueryVO;
import com.wr.remote.estate.vote.BusVoteOwner;
import com.wr.remote.estate.vote.BusVoteScope;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author lvzy
 * @Date 2024/2/23 15:28
 */
@RestController
@RequestMapping("voteScopeOwner")
@Api(tags = "物管平台-投票范围业主")
public class BusVoteScopeOwnerController extends BaseController {

    @Autowired
    private BusVoteOwnerService busVoteOwnerService;

    @Autowired
    private RoomService roomService;

    /**
     * @param voteId
     * @param roomId
     * @return
     */
    @GetMapping("/getList")
    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "voteId", value = "投票表id", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "roomId", value = "房间id", dataType = "Long", required = true, paramType = "query"),
    })
    public AjaxResult getList(@RequestParam("voteId") String voteId, @RequestParam("roomId") Long roomId) {

        List<BusVoteOwner> list = busVoteOwnerService.getList(voteId, roomId);
        RoomQueryVO roomQueryVO = new RoomQueryVO();
        roomQueryVO.setFlag(0);
        roomQueryVO.setLevel(4);
        roomQueryVO.setId(roomId);
        List<BusVoteScope> busVoteScope = roomService.getVoteListRooms(roomQueryVO);
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put("list", list);
        ajaxResult.put("houseAddress", "--");
        ajaxResult.put("ownerName", "--");
        List<BusVoteScope> voteScopes = busVoteScope.stream().filter(BusVoteScope -> BusVoteScope.getOwnerRelation().equals("0")).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(voteScopes)) {
            BusVoteScope voteScope = voteScopes.get(0);
            ajaxResult.put("houseAddress", voteScope.getHouseAddress());
            ajaxResult.put("ownerName", voteScope.getOwnerName());
        }

        return ajaxResult;
    }

    /**
     * 添加投票表决
     *
     * @param busVoteOwner
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("添加投票人")
    @Log(title = "添加投票人", businessType = BusinessType.INSERT)
    @PostMapping("saveVoteOwner")
    public AjaxResult saveVoteOwner(@Validated @RequestBody BusVoteOwner busVoteOwner) {
        busVoteOwner.setDelFlag("0");
        busVoteOwner.setCreateBy(SecurityUtils.getUsername());
        busVoteOwner.setCreateTime(new Date());
        busVoteOwner.setUpdateBy(SecurityUtils.getUsername());
        busVoteOwner.setUpdateTime(new Date());
        busVoteOwner.setId(IdUtils.fastUUID());
        busVoteOwnerService.save(busVoteOwner);
        return AjaxResult.success();
    }

    /**
     * @param id
     * @return
     */
    @ApiOperation("删除投票人")
    @DeleteMapping("removeById")
    public AjaxResult removeById(@RequestParam("id") String id) {

        busVoteOwnerService.removeById(id);
        return AjaxResult.success();
    }
}
