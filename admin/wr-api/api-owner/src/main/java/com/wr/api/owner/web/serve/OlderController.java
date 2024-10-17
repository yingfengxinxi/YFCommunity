package com.wr.api.owner.web.serve;

import com.wr.api.owner.entity.vo.serve.MyRoomsVo;
import com.wr.api.owner.entity.vo.serve.OldBasicInfoVo;
import com.wr.api.owner.entity.vo.serve.OlderFilesVo;
import com.wr.api.owner.service.serve.OlderService;
import com.wr.common.core.business.annotation.AuthRequired;
import com.wr.common.core.web.controller.BaseController;
import com.wr.common.core.web.domain.AjaxResult;
import com.wr.common.core.web.page.TableDataInfo;
import com.wr.common.log.annotation.ApiLog;
import com.wr.common.log.enums.BusinessType;
import com.wr.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: xuShu
 * @Date: 2022-11-15 16:17:46
 * @Desc: 老人信息
 */
@RestController
@RequestMapping("/v2/older")
public class OlderController extends BaseController {

    @Autowired
    private OlderService olderService;

    /**
     * 查询老人信息
     * @param olderFilesVo
     * @return
     */
    @GetMapping("/older")
    @AuthRequired
    public TableDataInfo getOldFiles(OlderFilesVo olderFilesVo){
        startPage();
       List<OlderFilesVo> list= olderService.getOlderFiles(olderFilesVo);
        return getDataTable(list);
    }

    /**
     * 查询老人详情
     * @param olderFilesVo
     * @return
     */
    @GetMapping("/oldInfo")
    @AuthRequired
    public AjaxResult getOldInfo(OlderFilesVo olderFilesVo){
        OlderFilesVo oldInfo = olderService.getOldInfo(olderFilesVo.getOlderId());
        return AjaxResult.success(oldInfo);
    }

    /**
     * 添加老人
     * @param olderFilesVo
     * @return
     */
    @PostMapping("/insert")
    @AuthRequired
    @ApiLog(title = "新增老人",businessType = BusinessType.INSERT)
    public AjaxResult insertOlderFile(@RequestBody OlderFilesVo olderFilesVo){
       return AjaxResult.success(olderService.insertOlderFile(olderFilesVo));
    }

    /**
     * 根据老人姓名查看信息
     * @param ownerId
     * @return
     */
    @GetMapping("/ByNameInfo")
    @AuthRequired
    public AjaxResult getOlderByName(Long  ownerId,String cardNo){
        OldBasicInfoVo oldInfo = olderService.getOlderByName(ownerId,cardNo);
        return AjaxResult.success(oldInfo);
    }

    /**
     * 查询小区
     * @param roomsVo
     * @return
     */
    @GetMapping("/getCommunity")
    @AuthRequired
    public AjaxResult getMyRoomS(MyRoomsVo roomsVo){
        return AjaxResult.success(olderService.getCommunity(roomsVo.getAccountId()));
    }

    /**
     * 查询楼栋
     * @param roomsVo
     * @return
     */
    @GetMapping("/getBuilds")
    @AuthRequired
    public AjaxResult getBuilds(MyRoomsVo roomsVo){
        return AjaxResult.success(olderService.getBuilds(roomsVo.getCommunityId()));
    }

    /**
     * 查询单元
     * @param roomsVo
     * @return
     */
    @GetMapping("/getUnit")
    @AuthRequired
    public AjaxResult getUnit(MyRoomsVo roomsVo){
        return AjaxResult.success(olderService.getUnit(roomsVo.getBuildingId()));
    }

    /**
     * 查询房间
     * @param roomsVo
     * @return
     */
    @GetMapping("/getRoom")
    @AuthRequired
    public AjaxResult getRoom(MyRoomsVo roomsVo){
        return AjaxResult.success(olderService.getRoom(roomsVo.getUnitId()));
    }

    /**
     * 查询老人
     * @param roomId
     * @return
     */
    @GetMapping("/getOldName")
    @AuthRequired
    public AjaxResult getOldName(Long roomId){
        return AjaxResult.success(olderService.getOldName(roomId));
    }

}
