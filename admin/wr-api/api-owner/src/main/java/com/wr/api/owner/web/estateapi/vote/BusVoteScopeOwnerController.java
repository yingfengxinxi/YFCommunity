package com.wr.api.owner.web.estateapi.vote;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wr.api.owner.service.estateapi.vote.BusVoteOwnerService;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.log.annotation.Log;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import com.wr.remote.estate.center.vo.RoomQueryVO;
import com.wr.remote.estate.vote.BusVoteOwner;
import com.wr.remote.estate.vote.BusVoteScope;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author lvzy
 * @Date 2024/2/23 15:28
 */
@RestController
@RequestMapping("/estateapi/voteScopeOwner")
@Api(tags = "业主端-投票范围业主")
public class BusVoteScopeOwnerController extends BaseController {

    @Autowired
    private BusVoteOwnerService busVoteOwnerService;

    /**
     * @param voteScopeId
     * @return
     */
    @GetMapping("/getList")
    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "voteScopeId", value = "投票范围表id", dataType = "String", required = true, paramType = "query"),
            @ApiImplicitParam(name = "roomId", value = "房间id", dataType = "Long", required = true, paramType = "query"),
    })
    public AjaxResult getList(@RequestParam("voteScopeId") String voteScopeId, @RequestParam("roomId") Long roomId) {
        List<BusVoteOwner> list = busVoteOwnerService.getList(voteScopeId);
        RoomQueryVO roomQueryVO = new RoomQueryVO();
        roomQueryVO.setFlag(0);
        roomQueryVO.setLevel(4);
        roomQueryVO.setId(roomId);
        BusVoteScope busVoteScope = busVoteOwnerService.getVoteListRooms(roomQueryVO).get(0);
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.put("list", list);
        ajaxResult.put("houseAddress", busVoteScope.getHouseAddress());
        ajaxResult.put("ownerName", busVoteScope.getOwnerName());
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

    /**
     * 投票
     *
     * @param busVoteOwner
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("投票")
    @Log(title = "投票", businessType = BusinessType.UPDATE)
    @PostMapping("updateVoteOwner")
    public AjaxResult updateVoteOwner(@Validated @RequestBody BusVoteOwner busVoteOwner) {
        //判断是业主下是否有配偶投票，获取业主投票及配偶投票的相关信息
//        Long owner = busVoteOwnerService.findOneOwnerIdByOwner( busVoteOwner.getAccountId() );
//        if(owner != null){
//            //查询出配偶的投票详情
//            Map<String,Object> oneStatusByAccount = busVoteOwnerService.findOneStatusByAccount( busVoteOwner.getId(), busVoteOwner.getAccountId(), owner );
//            if(CollUtil.isNotEmpty( oneStatusByAccount )){
//                Integer integer = MapUtil.getInt( oneStatusByAccount,"ownerStatus" ) ;
//                Integer integerOther = Integer.valueOf( busVoteOwner.getOwnerStatus() );
//                if(integer != 0 && integerOther != 0){
//                    if (integer == 1 || integerOther == 1) {
//                        busVoteOwner.setOwnerStatus("1");
//                    } else if (integer == 3 || integerOther == 3) {
//                        busVoteOwner.setOwnerStatus("3");
//                    } else {
//                        busVoteOwner.setOwnerStatus("2");
//                    }
//                }
//                LambdaUpdateWrapper<BusVoteOwner> updateWrapper = new LambdaUpdateWrapper<>();
//                updateWrapper.eq( BusVoteOwner::getId,MapUtil.getStr( oneStatusByAccount,"id" ) )
//                        .eq( BusVoteOwner::getDelFlag,"0" )
//                        .set( BusVoteOwner::getOwnerStatus,busVoteOwner.getOwnerStatus() )
//                        .set( StringUtils.isNoneBlank( busVoteOwner.getSetReason() ),BusVoteOwner::getSetReason,busVoteOwner.getSetReason() )
//                        .set( BusVoteOwner::getUpdateTime,new Date() )
//                        .set( StringUtils.isNoneBlank( busVoteOwner.getCreateBy() ),BusVoteOwner::getUpdateBy,busVoteOwner.getCreateBy() );
//                boolean updateOwner = busVoteOwnerService.update( updateWrapper );
//            }
//        }

        //判断是业主投票还是本人投票
        LambdaUpdateWrapper<BusVoteOwner> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq( BusVoteOwner::getId,busVoteOwner.getId() )
                .eq( BusVoteOwner::getDelFlag,"0" )
                .set( BusVoteOwner::getOwnerStatus,busVoteOwner.getOwnerStatus() )
                .set( StringUtils.isNoneBlank( busVoteOwner.getSetReason() ),BusVoteOwner::getSetReason,busVoteOwner.getSetReason() )
                .set( BusVoteOwner::getUpdateTime,new Date() )
                .set( StringUtils.isNoneBlank( busVoteOwner.getCreateBy() ),BusVoteOwner::getUpdateBy,busVoteOwner.getCreateBy() );
        boolean update = busVoteOwnerService.update( wrapper );
        return AjaxResult.success(update?"投票成功":"投票失败，请刷新后重新投票");
    }

    /**
     * 投票详情
     *
     * @param id
     * @return com.wr.common.core.web.domain.AjaxResult
     */
    @ApiOperation("投票详情")
    @Log(title = "投票详情", businessType = BusinessType.OTHER)
    @GetMapping("findOneVoteOwner")
    public AjaxResult findOneVoteOwner(@RequestParam("id") String id) {
        BusVoteOwner byId = busVoteOwnerService.getById( id );
        return AjaxResult.success(byId);
    }
}
